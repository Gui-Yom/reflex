import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Mirror extends Objet {

    private double width;
    private Vec2d normal;

    public Mirror(Vec2d position, double width, double angle) {
        super(position, angle, -1f, Color.BLACK);
        this.width = width;
        recalc();
    }

    @Override
    public void recalc() {
        this.normal = Vec2d.fromPolar(1, angle + Math.PI / 2).normalize();
    }

    @Override
    public Intersection intersect(Vec2d origin, Vec2d end) {
        final Vec2d mirrorEnd = position.plus(Vec2d.fromPolar(width, angle));
        final Vec2d intersection = Utils.segmentIntersect(position, mirrorEnd, origin, end);
        return intersection == null ? null : new Intersection(intersection, normal, refracIndex);
    }

    @Override
    public boolean isClickedOn(Vec2d click) {
        Vec2d a = getPosition();
        Vec2d b = getPosition().plus(Vec2d.fromPolar(width, angle));
        return Utils.testBoundingBox(a, b, click, CLICK_BIAS);
    }

    @Override
    public void draw(Graphics2D g) {
        if (selected) {
            g.setStroke(STROKE_SELECTED);
        }
        g.setColor(Color.BLACK);
        g.draw(new Line2D.Double(position.x, position.y, position.x + width * Math.cos(angle), position.y + width * Math.sin(angle)));
        g.setColor(Color.ORANGE);
        g.draw(new Line2D.Double(position.x, position.y, position.x + normal.x * 10, position.y + normal.y * 10));
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

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
        recalc();
    }

    public Vec2d getNormal() {
        return normal;
    }

    public void setNormal(Vec2d normal) {
        this.normal = normal;
        recalc();
    }

    @Override
    public double getMainDimension() {
        return width;
    }

    @Override
    public void setMainDimension(double mainDimension) {
        this.width = mainDimension;
    }
}
