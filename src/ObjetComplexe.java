import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * Un objet construit à partir de ses segments
 */
public abstract class ObjetComplexe extends Objet {

    protected final ArrayList<Segment> segments;

    public ObjetComplexe(Vec2d position, double angle, double refracIndex, Color color, int numSegments) {
        super(position, angle, refracIndex, color);
        segments = new ArrayList<>(numSegments);
    }

    public ObjetComplexe(double refracIndex, Color color, int numSegments) {
        super(refracIndex, color);
        segments = new ArrayList<>(numSegments);
    }

    public ObjetComplexe(double refracIndex, int numSegments) {
        super(refracIndex);
        segments = new ArrayList<>(numSegments);
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
    public void draw(Graphics2D g) {
        super.draw(g);
        for (Segment s : segments) {
            g.draw(new Line2D.Double(s.getA().x, s.getA().y, s.getB().x, s.getB().y));
        }
    }
}
