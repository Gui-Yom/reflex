import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {

    private static JFrame frame;
    private static Simulation sim;
    private static Canvas canvas;

    public static void main(String[] args) {

        // Force l'utilisation du gpu pour le rendu
        System.setProperty("sun.java2d.opengl", "true");

        frame = new JFrame("Reflex");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispose();
                }
            }
        });
        frame.setLayout(new BorderLayout());

        sim = new Simulation();
        canvas = new Canvas(sim);
        frame.add(canvas, BorderLayout.CENTER);

        JPanel menu = new JPanel();
        frame.add(menu, BorderLayout.NORTH);
        menu.setLayout(new FlowLayout());

        JButton reinit = new JButton("Reinitialiser");
        reinit.addActionListener(e -> {
            sim.clear();
            canvas.recalc();
        });
        menu.add(reinit);
        JButton btnDemidisque = new JButton("Demi-disque");
        btnDemidisque.addActionListener(e -> {
            sim.add(new DemiDisque(new Vec2d(500, 400), 0, Constants.REFRAC_GLASS, 100));
            canvas.recalc();
        });
        menu.add(btnDemidisque);
        JButton btnMiroir = new JButton("Miroir");
        btnMiroir.addActionListener(e -> {
            sim.add(new Mirror(new Vec2d(250, 300), 100, 0));
            canvas.recalc();
        });
        menu.add(btnMiroir);
        JButton btnLamesP = new JButton("Lame a faces paralleles");
        btnLamesP.addActionListener(e -> {
            sim.add(new LamesP(new Vec2d(200f, 300f), 200, 100, Constants.REFRAC_GLASS));
            canvas.recalc();
        });
        menu.add(btnLamesP);

        frame.setVisible(true);
    }
}
