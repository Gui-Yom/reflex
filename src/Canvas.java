import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class Canvas extends JPanel {

    Simulation simulation;

    public Canvas(Simulation simulation) {
        super(true);
        this.simulation = simulation;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setStroke(new BasicStroke(2));
        for (Laser laser : simulation.lasers) {
            traceLaser(g2d, laser);
        }
        for (Ray ray : simulation.rays) {
            traceRay(g2d, ray);
        }
        for (Objet objet : simulation.objets) {
            if (objet instanceof Mirror) {
                traceMirror(g2d, (Mirror) objet);
            }
        }
    }

    void traceLaser(Graphics2D g, Laser laser) {
        g.setColor(Utils.waveLengthToRGB(laser.wavelength));
        g.draw(new Rectangle2D.Float(laser.origin.x - 5, laser.origin.y - 5, 10, 10));
        g.draw(new Line2D.Float(laser.origin.x, laser.origin.y, laser.origin.x + laser.direction.x * 10, laser.origin.y + laser.direction.y * 10));
    }

    void traceRay(Graphics2D g, Ray ray) {
        g.setColor(Utils.waveLengthToRGB(ray.wavelength));
        g.draw(new Line2D.Float(ray.start.x, ray.start.y, ray.end.x, ray.end.y));
    }

    void traceMirror(Graphics2D g, Mirror m) {
        g.setColor(Color.BLACK);
        g.draw(new Line2D.Float(m.position.x, m.position.y, (float) (m.position.x + m.width * Math.cos(m.angle)), (float) (m.position.y + m.width * Math.sin(m.angle))));
        g.setColor(Color.ORANGE);
        g.draw(new Line2D.Float(m.position.x, m.position.y, m.position.x + m.normal.x * 10, m.position.y + m.normal.y * 10));
    }
}
