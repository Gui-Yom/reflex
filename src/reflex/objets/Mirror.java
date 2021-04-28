package reflex.objets;

import reflex.Intersection;
import reflex.Utils;
import reflex.Vec2f;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import static java.lang.Math.max;
import static java.lang.Math.min;

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
        Vec2f a = position;
        Vec2f b = position.plus(Vec2f.fromPolar(width, angle));
        return (click.x <= max(a.x, b.x) + clickedBias && click.x >= min(a.x, b.x) - clickedBias
                    && click.y <= max(a.y, b.y) + clickedBias && click.y >= min(a.y, b.y) - clickedBias);
    }

    @Override
    public void draw(Graphics2D g) {
        if (selected) {
            g.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 5f }, 0));
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
