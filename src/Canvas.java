import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Cette classe gère les inputs (clavier, souris) et l'affichage. Aucun calculs ici.
 *
 * @see Simulation
 */
public class Canvas extends JPanel implements KeyListener, MouseWheelListener, MouseInputListener {

    private Simulation sim;
    private AtomicReference<Objet> selected = new AtomicReference<>();
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
        //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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
        g2d.drawString(String.format("%d ms", System.currentTimeMillis() - startTime), getWidth() - 30, 10);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_1:
                System.out.println("1");
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
            case KeyEvent.VK_ENTER:
                sim.add(new LamesP(new Vec2d(50, 50), 100, 50, -1));
                recalc();
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

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

        // La touche ctrl est pressée
        // On modifie le maxDepth de la simulation
        if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0) {
            sim.setMaxDepth(sim.getMaxDepth() - e.getWheelRotation());
            recalc();
            return;
        }

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
        //System.out.println("Drag : " + e.getX() + ", " + e.getY());
        if (o != null) {
            Vec2d newDragPos = new Vec2d(e.getX(), e.getY());
            o.setPosition(o.getPosition().plus(newDragPos.minus(initialDragPos)));
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
