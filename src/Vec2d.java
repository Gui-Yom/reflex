import static java.lang.Math.acos;
import static java.lang.Math.atan2;

/**
 * A vector in 2D space with double components.
 */
public class Vec2d implements Cloneable {

    public static final Vec2d NULL = new Vec2d();
    public static final Vec2d UNIT_X = new Vec2d(1.0, 0.0);
    public static final Vec2d UNIT_Y = new Vec2d(0.0, 1.0);

    /**
     * The first component of this vector
     */
    public double x;
    /**
     * The second component of this vector
     */
    public double y;

    public Vec2d() {
        this(0, 0);
    }

    /**
     * Creates a new vector from its cartesian coordinates
     *
     * @param x
     * @param y
     */
    public Vec2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new vector from its polar coordinates
     *
     * @param r
     * @param theta
     * @return the newly created vector
     */
    public static Vec2d fromPolar(double r, double theta) {
        return new Vec2d((float) (r * Math.cos(theta)), (float) (r * Math.sin(theta)));
    }

    /**
     * @return conversion to polar coordinates
     */
    public Vec2d polar() {
        return new Vec2d(length(), (float) Math.atan2(y, x));
    }

    /**
     * @return conversion to cartesian coordinates
     */
    public Vec2d cartesian() {
        return new Vec2d((float) (x * Math.cos(y)), (float) (x * Math.sin(y)));
    }

    public Vec2d plus(Vec2d other) {
        return new Vec2d(x + other.x, y + other.y);
    }

    public Vec2d minus(Vec2d other) {
        return plus(other.negate());
    }

    public Vec2d negate() {
        return new Vec2d(-x, -y);
    }

    public Vec2d scale(double scalar) {
        return new Vec2d(x * scalar, y * scalar);
    }

    /**
     * Component-wise multiplication
     *
     * @param other
     * @return a new vector
     */
    public Vec2d mul(Vec2d other) {
        return new Vec2d(x * other.x, y * other.y);
    }

    public double dot(Vec2d other) {
        return x * other.x + y * other.y;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public Vec2d normalize() {
        double length = length();
        return new Vec2d(x / length, y / length);
    }

    public double angle(Vec2d other) {
        return acos(dot(other) / (length() * other.length()));
    }

    public double orientedAngle(Vec2d other) {
        return atan2(x * other.y - y * other.x, dot(other));
    }

    public Vec2d x() {
        return new Vec2d(x, 0);
    }

    public Vec2d y() {
        return new Vec2d(0, y);
    }

    public Vec2d yx() {
        return new Vec2d(y, x);
    }

    public Vec2d x(double x) {
        return new Vec2d(x, y);
    }

    public Vec2d y(double y) {
        return new Vec2d(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec2d vec2D = (Vec2d) o;
        return Double.compare(vec2D.x, x) == 0 && Double.compare(vec2D.y, y) == 0;
    }

    @Override
    public String toString() {
        return "Vec2d{" + "x=" + x + ", y=" + y + '}';
    }
}
