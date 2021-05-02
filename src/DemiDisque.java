import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DemiDisque extends Objet {

    private double radius;
    private ArrayList<Segment> aretes;

    /*
    This object position is the center of the circle
     */

    public DemiDisque(Vec2d pos, double angle, double n, double radius, Color c) {
        super(pos, angle, n, c);

        this.radius = radius;
        recalc();
    }


    public DemiDisque(Vec2d pos, double angle, double n, double radius) {
        this(pos, angle, n, radius, Color.BLACK);
    }

    public ArrayList<Vec2d> pArc(double r) {
        ArrayList<Vec2d> pA = new ArrayList<>();
        int numPoints = 20;
        for (int i = 0; i < numPoints; i++) {
            double alpha = i * Math.PI / numPoints - Math.PI / 2 + angle;
            double X = r * Math.cos(alpha);
            double Y = r * Math.sin(alpha);
            Vec2d p = new Vec2d(X, Y);
            pA.add(p);
        }
        return pA;
    }

    @Override
    public Intersection intersect(Vec2d origin, Vec2d end) {
        for (Segment seg : aretes) {
            Vec2d testintersect = Utils.segmentIntersect(seg.getA(), seg.getB(), origin, end);
            if (testintersect != null) {
                return new Intersection(testintersect, seg.getNormal(), indice);
            }
        }
        return null;
    }

    @Override
    public boolean isClickedOn(Vec2d click) {
        Vec2d a = getPosition();
        Vec2d b = getPosition().plus(new Vec2d(2 * radius, 2 * radius));
        return Utils.testBoundingBox(a, b, click, CLICKED_BIAS);
    }

    @Override
    public void recalc() {
        aretes = new ArrayList<>();
        Vec2d a = new Vec2d(position.x - this.radius * Math.cos(Math.PI / 2 + angle), position.y - this.radius * Math.sin(Math.PI / 2 + angle));
        Vec2d b = new Vec2d(position.x + this.radius * Math.cos(Math.PI / 2 + angle), position.y + this.radius * Math.sin(Math.PI / 2 + angle));
        Segment diam = new Segment(a, b);
        aretes.add(diam);
        ArrayList<Vec2d> pAs = pArc(this.radius);
        pAs.add(0, b);
        pAs.add(a);
        for (int i = 0; i < pAs.size() - 1; i++) {
            aretes.add(new Segment(pAs.get(i), pAs.get(i + 1)));
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if (selected) {
            g.setStroke(STROKE_SELECTED);
        }
        g.setColor(color);
        g.translate(position.x, position.y);
        g.draw(new Rectangle2D.Double(0, 0, 2 * radius, 2 * radius));
        g.rotate(angle);
        g.draw(new Arc2D.Double(0, 0, 2 * radius, 2 * radius, Math.toDegrees(-Math.PI/2), Math.toDegrees(Math.PI), Arc2D.CHORD));
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
        recalc();
    }
}

