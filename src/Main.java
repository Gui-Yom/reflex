import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Reflex");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        frame.setLayout(new BorderLayout());

        World w = new World();
        w.lasers.add(new Laser(new Vec2f(100, 400), Vec2f.fromPolar(1, (float) (-Math.PI / 5)), 720));
        Mirror m = new Mirror(new Vec2f(400, 100), 100, 0);
        w.mirrors.add(m);
        Mirror m2 = new Mirror(new Vec2f(400, 400), 100, (float) Math.PI);
        w.mirrors.add(m2);
        Mirror m3 = new Mirror(new Vec2f(200, 150), 100, (float) -Math.PI / 4);
        w.mirrors.add(m3);
        w.compute();

        Canvas c = new Canvas(w);
        frame.add(c);

        frame.addMouseWheelListener(e -> {
            m.angle += e.getWheelRotation() * Math.PI / 128;
            m.recalcNormal();
            w.compute();
            c.repaint();
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispose();
                }
            }
        });

        frame.setVisible(true);
    }
}
