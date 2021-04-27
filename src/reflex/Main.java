package reflex;

import reflex.objets.Laser;
import reflex.objets.Mirror;
import reflex.objets.Objet;
import reflex.objets.Parallepipede;

import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicReference;

public class Main {

    private static JFrame frame;
    private static Simulation sim;
    private static Canvas canvas;

    public static void main(String[] args) {

        System.setProperty("sun.java2d.opengl", "true");

        frame = new JFrame("Reflex");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        //frame.setLayout(new BorderLayout());

        sim = new Simulation();
        canvas = new Canvas(sim);
        //frame.add(canvas, BorderLayout.CENTER);

        frame.add(canvas);
        /*
        JPanel menu = new JPanel();
        frame.add(menu, BorderLayout.NORTH);
        menu.setLayout(new FlowLayout());

        JButton reinit = new JButton("Reinitialiser");
        menu.add(reinit);
        JButton dSphere = new JButton("Demi-sphere");
        menu.add(dSphere);
        JButton miroir = new JButton("Miroir");
        menu.add(miroir);
        JButton fLame = new JButton("Face a lames paralleles");
        menu.add(fLame);*/

        configuration1();
        sim.compute();

        AtomicReference<Objet> selected = new AtomicReference<>();

        canvas.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //System.out.println(e.getPoint());
                Vec2f clicked = new Vec2f(e.getX(), e.getY());
                for (Objet o : sim.objets) {
                    if (o.isClickedOn(clicked)) {
                        selected.set(o);
                        break;
                    } else {
                        selected.set(null);
                    }
                }
            }
        });

        frame.addMouseWheelListener(e -> {
            Objet o = selected.get();
            if (o != null) {
                o.setAngle(o.getAngle() + e.getWheelRotation() * (float) Math.PI / 128);
                o.recalc();
                redessiner();
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_1:
                        clear();
                        configuration1();
                        redessiner();
                        break;
                    case KeyEvent.VK_2:
                        clear();
                        configuration2();
                        redessiner();
                        break;
                    case KeyEvent.VK_UP:
                        Objet o = selected.get();
                        if (o != null)
                            o.setPosition(new Vec2f(o.getPosition().x, o.getPosition().y - 1));
                        redessiner();
                        break;
                    case KeyEvent.VK_DOWN:
                        o = selected.get();
                        if (o != null)
                            o.setPosition(new Vec2f(o.getPosition().x, o.getPosition().y + 1));
                        redessiner();
                        break;
                    case KeyEvent.VK_RIGHT:
                        o = selected.get();
                        if (o != null)
                            o.setPosition(new Vec2f(o.getPosition().x + 1, o.getPosition().y));
                        redessiner();
                        break;
                    case KeyEvent.VK_LEFT:
                        o = selected.get();
                        if (o != null)
                            o.setPosition(new Vec2f(o.getPosition().x - 1, o.getPosition().y));
                        redessiner();
                        break;
                    case KeyEvent.VK_ENTER:
                        sim.objets.add(new Parallepipede(new Vec2f(50, 50), 100, 50, -1f));
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
        // On ajoute nos reflex.objets
        sim.lasers.add(new Laser(new Vec2f(100, 400), Vec2f.fromPolar(1, (float) (-Math.PI / 5)), 720));

        Mirror m = new Mirror(new Vec2f(400, 100), 100, 0);
        sim.objets.add(m);
        Mirror m2 = new Mirror(new Vec2f(300, 400), 100, 0);
        sim.objets.add(m2);
        Mirror m3 = new Mirror(new Vec2f(200, 150), 100, (float) -Math.PI / 4);
        sim.objets.add(m3);
        Parallepipede lame = new Parallepipede(new Vec2f(200f, 300f), 200, 100, Constants.REFRAC_GLASS);
        sim.objets.add(lame);
        //demiSphere d = new demiSphere(new reflex.Vec2f(50, 50), reflex.Constants.REFRAC_GLASS, 5f);
    }

    static void configuration2() {
        sim.lasers.add(new Laser(new Vec2f(100, 400), Vec2f.fromPolar(1, (float) (-Math.PI / 5)), 720));

        sim.objets.add(new Mirror(new Vec2f(400, 100), 100, 0));
    }

    static void redessiner() {
        sim.compute();
        canvas.repaint();
    }

    static void clear() {
        sim.objets.clear();
        sim.lasers.clear();
    }
}
