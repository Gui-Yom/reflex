import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;


public abstract class Objet implements Drawable {
    protected static final float CLICK_BIAS = 6f;
    protected static final Stroke STROKE_SELECTED = new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 5f }, 0);

    protected Vec2d position;
    protected double refracIndex;
    protected Color color;
    protected double angle;
    /**
     * true if the current object is selected
     */
    protected boolean selected = false;

    public Objet(Vec2d position, double angle, double refracIndex, Color color) {
        this.position = position;
        this.refracIndex = refracIndex;
        this.color = color;
        this.angle = angle;
    }

    public Objet(double refracIndex, Color color) {
        this(Vec2d.NULL, 0.0f, refracIndex, color);
    }

    public Objet(double refracIndex) {
        this(refracIndex, Color.BLACK);
    }

    public Objet() {
        this(Constants.REFRAC_VACUUM, Color.BLACK);
    }

    /**
     * Let the object recalc some values it has after updating a value.
     */
    public void recalc() {
        // Default no op impl
    }

    /**
     * @param origin the origin point of the ray
     * @param end    the end point of the ray
     * @return a non null value to indicate the ray has intersected with this object
     */
    public abstract Intersection intersect(Vec2d origin, Vec2d end);

    public abstract boolean isClickedOn(Vec2d click);

    public Vec2d getPosition() {
        return position;
    }

    public void setPosition(Vec2d position) {
        this.position = position;
    }

    public double getRefracIndex() {
        return refracIndex;
    }

    public void setRefracIndex(double refracIndex) {
        this.refracIndex = refracIndex;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double ang) {
        this.angle = ang;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public abstract double getMainDimension();

    public abstract void setMainDimension(double mainDimension);
}
