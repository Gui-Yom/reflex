import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicReference;

public class Main {

    private static JFrame frame;
    private static Simulation sim;
    private static Canvas canvas;

    public static void main(String[] args) {

        // This allows Java2D to use a gpu when rendering
        System.setProperty("sun.java2d.opengl", "true");

        frame = new JFrame("Reflex");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        frame.setLayout(new BorderLayout());

        sim = new Simulation();
        canvas = new Canvas(sim);
        frame.add(canvas, BorderLayout.CENTER);

        JPanel menu = new JPanel();
        frame.add(menu, BorderLayout.NORTH);
        menu.setLayout(new FlowLayout());

        JButton reinit = new JButton("Reinitialiser");
        reinit.addActionListener(e -> {
            clear();
            redessiner();
        });
        menu.add(reinit);
        JButton btnDemidisque = new JButton("Demi-disque");
        menu.add(btnDemidisque);
        JButton btnMiroir = new JButton("Miroir");
        menu.add(btnMiroir);
        JButton btnLamesP = new JButton("Lame à faces parallèles");
        menu.add(btnLamesP);

        configuration1();
        recalculer();

        AtomicReference<Objet> selected = new AtomicReference<>();

        canvas.addMouseListener(new MouseInputAdapter() {

            Vec2d initialDragPos = Vec2d.NULL;

            @Override
            public void mousePressed(MouseEvent e) {
                // Becaus java swing is a bitch
                canvas.requestFocus();
                //canvas.requestFocusInWindow();
                //System.out.println(canvas.hasFocus());

                //System.out.println(e.getPoint());
                Vec2d clicked = new Vec2d(e.getX(), e.getY());
                initialDragPos = clicked;
                for (Objet o : sim.getObjets()) {
                    if (o.isClickedOn(clicked)) {
                        Objet sel = selected.get();
                        if (sel != null)
                            sel.setSelected(false);
                        selected.set(o);
                        o.setSelected(true);
                        redessiner();
                        return;
                    }
                }
                Objet o = selected.get();
                if (o != null) {
                    selected.get().setSelected(false);
                    selected.set(null);
                    redessiner();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Objet o = selected.get();
                //System.out.println("Drag : " + e.getX() + ", " + e.getY());
                if (o != null) {
                    o.setPosition(o.getPosition().plus(new Vec2d(e.getX(), e.getY()).minus(initialDragPos)));
                    recalculer();
                }
            }
        });

        canvas.addMouseWheelListener(e -> {
            Objet o = selected.get();
            if (o != null) {
                o.setAngle(o.getAngle() + e.getWheelRotation() * Math.PI / 128);
                o.recalc();
                recalculer();
            }
        });

        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_1:
                        System.out.println("1");
                        clear();
                        configuration1();
                        recalculer();
                        break;
                    case KeyEvent.VK_2:
                        clear();
                        configuration2();
                        recalculer();
                        break;
                    case KeyEvent.VK_UP:
                        Objet o = selected.get();
                        if (o != null)
                            o.setPosition(new Vec2d(o.getPosition().x, o.getPosition().y - 1));
                        recalculer();
                        break;
                    case KeyEvent.VK_DOWN:
                        o = selected.get();
                        if (o != null)
                            o.setPosition(new Vec2d(o.getPosition().x, o.getPosition().y + 1));
                        recalculer();
                        break;
                    case KeyEvent.VK_RIGHT:
                        o = selected.get();
                        if (o != null)
                            o.setPosition(new Vec2d(o.getPosition().x + 1, o.getPosition().y));
                        recalculer();
                        break;
                    case KeyEvent.VK_LEFT:
                        o = selected.get();
                        if (o != null)
                            o.setPosition(new Vec2d(o.getPosition().x - 1, o.getPosition().y));
                        recalculer();
                        break;
                    case KeyEvent.VK_ENTER:
                        sim.add(new LamesP(new Vec2d(50, 50), 100, 50, -1));
                        recalculer();
                        break;
                    case KeyEvent.VK_ESCAPE:
                        frame.dispose();
                        break;
                }
            }
        });

        frame.setVisible(true);
    }

    static void configuration1() {
        // On ajoute nos reflex.objets
        sim.add(new Laser(new Vec2d(100, 400), -Math.PI / 5, 632));

        Mirror m = new Mirror(new Vec2d(400, 100), 100, 0);
        sim.add(m);
        Mirror m2 = new Mirror(new Vec2d(300, 400), 100, 0);
        sim.add(m2);
        Mirror m3 = new Mirror(new Vec2d(200, 150), 100, -Math.PI / 4);
        sim.add(m3);
        LamesP lame = new LamesP(new Vec2d(200f, 300f), 200, 100, Constants.REFRAC_GLASS);
        sim.add(lame);
        //demiSphere d = new demiSphere(new Vec2f(50, 50), Constants.REFRAC_GLASS, 5f);
    }

    static void configuration2() {
        sim.add(new Laser(new Vec2d(100, 400), -Math.PI / 5, 632));

        sim.add(new Mirror(new Vec2d(400, 100), 100, 0));

        sim.add(new DemiDisque(new Vec2d(500, 400), 0, Constants.REFRAC_GLASS, 100));
    }

    static void recalculer() {
        sim.compute();
        redessiner();
    }

    static void redessiner() {
        canvas.repaint();
    }

    static void clear() {
        sim.clear();
        sim.clear();
    }
}
