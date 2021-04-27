package reflex.objets;

import reflex.Intersection;
import reflex.Segment;
import reflex.Utils;
import reflex.Vec2f;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

public class DemiSphere extends Objet {

    protected ArrayList<Segment> aretes;
    private float r;

    public DemiSphere(Vec2f pos, float angle, float n, float r, Color c) {
        super(pos, angle, n, c);

        this.r = r;

        aretes = new ArrayList<>();
        Vec2f a = new Vec2f(position.x - (float) (this.r * Math.cos(Math.PI / 2 + angle)), position.y - (float) (this.r * Math.sin(Math.PI / 2 + angle)));
        Vec2f b = new Vec2f(position.x + (float) (this.r * Math.cos(Math.PI / 2 + angle)), position.y + (float) (this.r * Math.sin(Math.PI / 2 + angle)));
        Segment diam = new Segment(a, b);
        aretes.add(diam);
        ArrayList<Vec2f> pAs = pArc(position, this.r);
        pAs.add(0, b);
        pAs.add(a);
        for (int i = 0; i < pAs.size() - 1; i++) {
            aretes.add(new Segment(pAs.get(i), pAs.get(i + 1)));
        }
    }


    public DemiSphere(Vec2f pos, float angle, float n, float r) {
        this(pos, angle, n, r, Color.BLACK);
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
        return false;
    }

    @Override
    public void recalc() {

    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        AffineTransform afftran = new AffineTransform();
        afftran.rotate(angle);
        g.setTransform(afftran);
        g.draw(new Arc2D.Float(position.x, position.y, r, r, 270, (float) Math.PI * r, Arc2D.CHORD));
    }
}

