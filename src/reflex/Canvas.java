package reflex;

import reflex.objets.Objet;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Canvas extends JPanel {

    Simulation simulation;

    public Canvas(Simulation simulation) {
        super(true);
        this.simulation = simulation;
    }

    @Override
    public void paint(Graphics g) {
        long startTime = System.currentTimeMillis();

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        for (Objet objet : simulation.objets) {
            objet.draw((Graphics2D) g2d.create());
        }
        for (Ray ray : simulation.rays) {
            ray.draw((Graphics2D) g2d.create());
        }
        System.out.printf("Draw time : %d ms%n", System.currentTimeMillis() - startTime);
    }
}
