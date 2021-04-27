import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Carre extends Objet {

    float length;

    public Carre(Vec2f pos, float length, float n, Color c) {
        super(pos, n, c);
        this.length = length;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(couleurObjet);
        g.draw(new Rectangle2D.Float(position.x, position.y, 10, 10));
    }

    @Override
    public Intersection intersect(Vec2f origin, Vec2f end) {
        return null;
    }

    @Override
    public void recalc() {

    }
}
