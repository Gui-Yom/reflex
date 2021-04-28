package reflex.objets;

import reflex.Intersection;
import reflex.Segment;
import reflex.Utils;
import reflex.Vec2f;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DemiSphere extends Objet {

    protected ArrayList<Segment> aretes;
    private float radius;

    public DemiSphere(Vec2f pos, float angle, float n, float radius, Color c) {
        super(pos, angle, n, c);

        this.radius = radius;

        aretes = new ArrayList<>();
        Vec2f a = new Vec2f(position.x - (float) (this.radius * Math.cos(Math.PI / 2 + angle)), position.y - (float) (this.radius * Math.sin(Math.PI / 2 + angle)));
        Vec2f b = new Vec2f(position.x + (float) (this.radius * Math.cos(Math.PI / 2 + angle)), position.y + (float) (this.radius * Math.sin(Math.PI / 2 + angle)));
        Segment diam = new Segment(a, b);
        aretes.add(diam);
        ArrayList<Vec2f> pAs = pArc(position, this.radius);
        pAs.add(0, b);
        pAs.add(a);
        for (int i = 0; i < pAs.size() - 1; i++) {
            aretes.add(new Segment(pAs.get(i), pAs.get(i + 1)));
        }
    }


    public DemiSphere(Vec2f pos, float angle, float n, float radius) {
        this(pos, angle, n, radius, Color.BLACK);
    }

    public ArrayList<Vec2f> pArc(Vec2f centre, float r) {
        ArrayList<Vec2f> pA = new ArrayList<>();
        for (int i = 0; i < 1001; i++) {
            float alpha = (float) (i * Math.PI / 1000 - Math.PI / 2) + angle;
            float X = (float) (r * Math.cos(alpha));
            float Y = (float) (r * Math.sin(alpha));
            Vec2f p = new Vec2f(X, Y);
            pA.add(p);
        }
        return pA;
    }

    public Intersection intersect(Vec2f origin, Vec2f end) {
        for (Segment seg : aretes) {
            Vec2f testintersect = Utils.segmentIntersect(seg.pointA, seg.pointB, origin, end);
            if (testintersect != null) {
                return new Intersection(testintersect, seg.normale, indice);
            }
        }
        return null;
    }

    @Override
    public boolean isClickedOn(Vec2f click) {
        Vec2f a = getPosition();
        Vec2f b = getPosition().plus(new Vec2f(2 * radius, 2 * radius));
        return Utils.testBoundingBox(a, b, click, CLICKED_BIAS);
    }

    @Override
    public void recalc() {

    }

    public void draw(Graphics2D g) {
        if (selected) {
            g.setStroke(STROKE_SELECTED);
        }
        g.setColor(color);
        g.translate(position.x, position.y);
        g.draw(new Rectangle2D.Float(0, 0, 2 * radius, 2 * radius));
        g.rotate(angle);
        g.draw(new Arc2D.Float(0, 0, 2 * radius, 2 * radius, 0, (float) Math.toDegrees(Math.PI), Arc2D.CHORD));
    }
}

