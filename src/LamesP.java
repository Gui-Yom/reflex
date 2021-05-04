import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class LamesP extends Objet {

    private final ArrayList<Segment> segments = new ArrayList<>(4);
    private double longueur;
    private double largeur;

    public LamesP(Vec2d pos, double angle, double lon, double larg, double n, Color color) {
        super(pos, angle, n, color);
        this.longueur = lon;
        this.largeur = larg;
        recalc();
    }

    public LamesP(Vec2d position, double longueur, double largeur, double indice) {
        this(position, 0, longueur, largeur, indice, Color.BLACK);
    }

    @Override
    public Intersection intersect(Vec2d origin, Vec2d end) {
        Intersection i = Utils.segmentListIntersect(origin, end, segments);
        i = i != null ? new Intersection(i.getPoint(), i.getNormal(), refracIndex) : null;
        return i;
    }

    @Override
    public boolean isClickedOn(Vec2d click) {
        Vec2d[] bounds = Utils.findAABB(segments);
        return Utils.testBoundingBox(bounds[0], bounds[1], click, CLICK_BIAS);
    }

    @Override
    public void recalc() {

        Vec2d a = position;
        Vec2d b = new Vec2d(largeur, 0).rotate(angle).plus(a);
        Vec2d c = new Vec2d(largeur, longueur).rotate(angle).plus(a);
        Vec2d d = new Vec2d(0, longueur).rotate(angle).plus(a);

        segments.clear();

        segments.add(new Segment(a, b));
        segments.add(new Segment(b, c));
        segments.add(new Segment(c, d));
        segments.add(new Segment(d, a));
    }

    @Override
    public void draw(Graphics2D g) {
        if (selected) {
            g.setStroke(STROKE_SELECTED);
        }
        g.setColor(color);
        double centerX = position.x + largeur / 2;
        double centerY = position.y + longueur / 2;
        //g.translate(centerX, centerY);
        //g.rotate(angle);
        //g.draw(new Rectangle2D.Double(-largeur / 2, -longueur / 2, largeur, longueur));

        for (Segment s : segments) {
            g.draw(new Line2D.Double(s.getA().x, s.getA().y, s.getB().x, s.getB().y));
        }
    }

    public double getLongueur() {
        return longueur;
    }

    public void setLongueur(double longueur) {
        this.longueur = longueur;
    }

    public double getLargeur() {
        return largeur;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }
}

