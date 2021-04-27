import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Mirror extends Objet {

    float width;
    float angle;
    Vec2f normal;

    public Mirror(Vec2f position, float width, float angle) {
        super(position, 1.5f);
        this.width = width;
        this.angle = angle;
        recalc();
    }

    @Override
    public void recalc() {
        this.normal = Vec2f.fromPolar(1, (float) (angle + Math.PI / 2)).normalize();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.draw(new Line2D.Float(position.x, position.y, (float) (position.x + width * Math.cos(angle)), (float) (position.y + width * Math.sin(angle))));
        g.setColor(Color.ORANGE);
        g.draw(new Line2D.Float(position.x, position.y, position.x + normal.x * 10, position.y + normal.y * 10));
    }

    @Override
    public Intersection intersect(Vec2f origin, Vec2f end) {

        final Vec2f mirrorEnd = position.plus(Vec2f.fromPolar(width, angle));
        final Vec2f intersection = Utils.segmentIntersect(position, mirrorEnd, origin, end);
        //System.out.printf("intersection: %s%n", intersection);

        return intersection == null ? null : new Intersection(intersection, normal, indice, false);
    }

    @Override
    public String toString() {
        return "Mirror{" +
                   "width=" + width +
                   ", angle=" + angle +
                   ", normal=" + normal +
                   ", position=" + position +
                   '}';
    }
}
