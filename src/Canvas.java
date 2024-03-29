import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Cette classe gère les inputs (clavier, souris) et l'affichage. Aucun calculs ici.
 *
 * @see Simulation
 */
public class Canvas extends JPanel implements KeyListener, MouseWheelListener, MouseInputListener {

    static {
        ToolTipManager.sharedInstance().setDismissDelay(2000);
        ToolTipManager.sharedInstance().setInitialDelay(100);
        ToolTipManager.sharedInstance().setReshowDelay(200);
    }

    private final Simulation sim;
    private final AtomicReference<Objet> selected = new AtomicReference<>();
    private Vec2d initialDragPos = Vec2d.NULL;

    public Canvas(Simulation sim) {
        super(true);
        this.sim = sim;

        // Nécessaire car un JPanel ne peut pas recevoir certains évènements par défaut
        setFocusable(true);

        addKeyListener(this);
        addMouseWheelListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        sim.configuration1();
        recalc();

        setToolTipText("");
    }

    /**
     * Recalcule la simulation et redessine le canvas
     */
    public void recalc() {
        sim.compute();
        repaint();
    }

    /**
     * Code appelé pour le dessin du canvas.
     * On y dessine les objets et les rayons, ainsi que certaines informations texte utiles.
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        long startTime = System.currentTimeMillis();

        Graphics2D g2d = (Graphics2D) g;
        // Antialiasing may cause some lines to simply disappear
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        for (Drawable d : sim.getObjets()) {
            d.draw((Graphics2D) g2d.create());
        }
        for (Drawable d : sim.getRays()) {
            d.draw((Graphics2D) g2d.create());
        }

        g2d.setColor(Color.BLACK);
        g2d.drawString(String.format("rendu: %d ms", System.currentTimeMillis() - startTime), getWidth() - 70, 10);
        g2d.drawString(String.format("depth: %d", sim.getMaxDepth()), getWidth() - 52, 22);
        g2d.drawString(String.format("display treshold: %.3f", Ray.RAY_DISPLAY_TRESHOLD), getWidth() - 120, 34);
    }

    @Override
    public Point getToolTipLocation(MouseEvent event) {
        //System.out.printf("%d, %d%n", event.getX(), event.getY());
        for (Objet o : sim.getObjets()) {
            if (o.isClickedOn(new Vec2d(event.getX(), event.getY()))) {
                setToolTipText(o.getTooltipText());
                return new Point(10, 10);
            }
        }
        setToolTipText("");
        return new Point(10, 10);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_1:
                sim.configuration1();
                recalc();
                break;
            case KeyEvent.VK_2:
                sim.configuration2();
                recalc();
                break;
            case KeyEvent.VK_3:
                sim.configuration3();
                recalc();
                break;
            case KeyEvent.VK_4:
                sim.configuration4();
                recalc();
                break;
            case KeyEvent.VK_UP:
                Objet o = selected.get();
                if (o != null) {
                    o.setPosition(new Vec2d(o.getPosition().x, o.getPosition().y - 1));
                    o.recalc();
                    recalc();
                }
                break;
            case KeyEvent.VK_DOWN:
                o = selected.get();
                if (o != null) {
                    o.setPosition(new Vec2d(o.getPosition().x, o.getPosition().y + 1));
                    o.recalc();
                    recalc();
                }
                break;
            case KeyEvent.VK_RIGHT:
                o = selected.get();
                if (o != null) {
                    o.setPosition(new Vec2d(o.getPosition().x + 1, o.getPosition().y));
                    o.recalc();
                    recalc();
                }
                break;
            case KeyEvent.VK_LEFT:
                o = selected.get();
                if (o != null) {
                    o.setPosition(new Vec2d(o.getPosition().x - 1, o.getPosition().y));
                    o.recalc();
                    recalc();
                }
                break;
            case KeyEvent.VK_R:
                recalc();
                break;
            case KeyEvent.VK_DELETE:
                o = selected.get();
                if (o != null) {
                    sim.remove(o);
                    recalc();
                }
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        // Important, sinon le canvas ne peut jamais avoir le focus
        // On ne pourra pas recevoir les évènements clavier sans ça
        requestFocus();

        Vec2d clicked = new Vec2d(e.getX(), e.getY());
        initialDragPos = clicked;
        for (Objet o : sim.getObjets()) {
            if (o.isClickedOn(clicked)) {
                Objet sel = selected.get();
                if (sel != null)
                    sel.setSelected(false);
                selected.set(o);
                o.setSelected(true);
                repaint();
                return;
            }
        }
        Objet o = selected.get();
        if (o != null) {
            selected.get().setSelected(false);
            selected.set(null);
            repaint();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        // ctrl+molette pour contrôler le maximum de récursion
        // ctrl+molette avec un objet sélectionné pour modifier sa taille principale
        if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0) {

            Objet o = selected.get();
            if (o != null) {
                o.setMainDim(Math.max(o.getMainDim() - e.getWheelRotation(), 0));
                o.recalc();
                recalc();
                return;
            }

            sim.setMaxDepth(Math.max(sim.getMaxDepth() - e.getWheelRotation(), 0));
            recalc();
            return;
        }

        // shift+molette pour contrôler la limite d'intensité pour le dessin des rayons
        // shift+molette avec un objet sélectionné pour modifier sa taille secondaire
        if ((e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) != 0) {

            Objet o = selected.get();
            if (o != null) {
                o.setSecondaryDim(Math.max(o.getSecondaryDim() - e.getWheelRotation(), 0));
                o.recalc();
                recalc();
                return;
            }

            Ray.RAY_DISPLAY_TRESHOLD = Math.max(Ray.RAY_DISPLAY_TRESHOLD - e.getWheelRotation() * 0.001, 0);
            repaint();
            return;
        }

        // molette pour faire tourner l'objet sélectionné
        Objet o = selected.get();
        if (o != null) {
            o.setAngle(o.getAngle() + e.getWheelRotation() * Math.PI / 128);
            o.recalc();
            recalc();
        }

    }


    @Override
    public void mouseDragged(MouseEvent e) {
        Objet o = selected.get();
        if (o != null) {
            Vec2d newDragPos = new Vec2d(e.getX(), e.getY());
            o.setPosition(o.getPosition().plus(newDragPos.minus(initialDragPos)));
            o.recalc();
            initialDragPos = newDragPos;
            recalc();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) { }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) {
        initialDragPos = Vec2d.NULL;
    }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}

