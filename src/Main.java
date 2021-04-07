import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Main {

    private static JFrame frame;
    private static Simulation sim;
    private static Canvas canvas;

    public static void main(String[] args) {

        frame = new JFrame("Reflex");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        frame.setLayout(new BorderLayout());

        sim = new Simulation();
        canvas = new Canvas(sim);

        configuration1();
        sim.compute();

        frame.add(canvas);

        AtomicReference<Mirror> selected = new AtomicReference<>();

        canvas.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //System.out.println(e.getPoint());
                Vec2f clicked = new Vec2f(e.getX(), e.getY());
                for (Objet o : sim.objets) {
                    if (o instanceof Mirror) {
                        Mirror m = (Mirror) o;
                        Vec2f a = m.position;
                        Vec2f b = m.position.plus(Vec2f.fromPolar(m.width, m.angle));
                        float bias = 6f;
                        if (clicked.x <= max(a.x, b.x) + bias && clicked.x >= min(a.x, b.x) - bias
                                && clicked.y <= max(a.y, b.y) + bias && clicked.y >= min(a.y, b.y) - bias) {
                            // On vient de cliquer sur un miroir
                            selected.set(m);
                            System.out.println("Touché !");
                            break;
                        } else {
                            selected.set(null);
                        }
                    }
                }
            }
        });

        frame.addMouseWheelListener(e -> {
            Mirror m = selected.get();
            if (m != null) {
                m.angle += e.getWheelRotation() * Math.PI / 128;
                m.recalcNormal();
                redessiner();
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_1:
                        // Charger la configuration 1
                        // On vide les listes d'objets
                        configuration1();
                        redessiner();
                        break;
                    case KeyEvent.VK_2:
                        // Charger la configuration 2
                        sim.objets.clear();
                        sim.lasers.clear();

                        // On ajoute nos objets
                        sim.lasers.add(new Laser(new Vec2f(100, 400), Vec2f.fromPolar(1, (float) (-Math.PI / 5)), 720));

                        sim.objets.add(new Mirror(new Vec2f(400, 100), 100, 0));
                        sim.objets.add(new Carre(new Vec2f(500, 60), 1.0f, Color.BLACK));

                        redessiner();
                        break;
                    case KeyEvent.VK_UP:
                        Mirror m = selected.get();
                        if (m != null)
                            m.position = new Vec2f(m.position.x, m.position.y - 1);
                        redessiner();
                        break;
                    case KeyEvent.VK_DOWN:
                        m = selected.get();
                        if (m != null)
                            m.position = new Vec2f(m.position.x, m.position.y + 1);
                        redessiner();
                        break;
                    case KeyEvent.VK_RIGHT:
                        m = selected.get();
                        if (m != null)
                            m.position = new Vec2f(m.position.x + 1, m.position.y);
                        redessiner();
                        break;
                    case KeyEvent.VK_LEFT:
                        m = selected.get();
                        if (m != null)
                            m.position = new Vec2f(m.position.x - 1, m.position.y);
                        redessiner();
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
        sim.objets.clear();
        sim.lasers.clear();

        // On ajoute nos objets
        sim.lasers.add(new Laser(new Vec2f(100, 400), Vec2f.fromPolar(1, (float) (-Math.PI / 5)), 720));

        sim.objets.add(new Carre(new Vec2f(0, 0), 1.0f, Color.BLUE));

        Mirror m = new Mirror(new Vec2f(400, 100), 100, 0);
        sim.objets.add(m);
        Mirror m2 = new Mirror(new Vec2f(300, 400), 100, 0f);
        sim.objets.add(m2);
        Mirror m3 = new Mirror(new Vec2f(200, 150), 100, (float) -Math.PI / 4);
        sim.objets.add(m3);
    }

    static void redessiner() {
        sim.compute();
        canvas.repaint();
    }
}
