package reflex.objets;

import reflex.Intersection;
import reflex.Utils;
import reflex.Vec2f;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Mirror extends Objet {

    float width;
    Vec2f normal;

    public Mirror(Vec2f position, float width, float angle) {
        super(position, angle, -1f, Color.BLACK);
        this.width = width;
        recalc();
    }

    @Override
    public void recalc() {
        this.normal = Vec2f.fromPolar(1, (float) (angle + Math.PI / 2)).normalize();
    }

    @Override
    public Intersection intersect(Vec2f origin, Vec2f end) {

        final Vec2f mirrorEnd = position.plus(Vec2f.fromPolar(width, angle));
        final Vec2f intersection = Utils.segmentIntersect(position, mirrorEnd, origin, end);
        //System.out.printf("intersection: %s%n", intersection);

        return intersection == null ? null : new Intersection(intersection, normal, indice);
    }

    @Override
    public boolean isClickedOn(Vec2f click) {
        Vec2f a = getPosition();
        Vec2f b = getPosition().plus(Vec2f.fromPolar(width, angle));
        return Utils.testBoundingBox(a, b, click, CLICKED_BIAS);
    }

    @Override
    public void draw(Graphics2D g) {
        if (selected) {
            g.setStroke(STROKE_SELECTED);
        }
        g.setColor(Color.BLACK);
        g.draw(new Line2D.Float(position.x, position.y, (float) (position.x + width * Math.cos(angle)), (float) (position.y + width * Math.sin(angle))));
        g.setColor(Color.ORANGE);
        g.draw(new Line2D.Float(position.x, position.y, position.x + normal.x * 10, position.y + normal.y * 10));
    }

    @Override
    public String toString() {
        return "reflex.objets.Mirror{" +
                   "width=" + width +
                   ", angle=" + angle +
                   ", normal=" + normal +
                   ", position=" + position +
                   '}';
    }
}
