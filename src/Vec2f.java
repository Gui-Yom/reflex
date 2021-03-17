import java.util.Objects;

import static java.lang.Math.acos;

/**
 * A vector in 2D space with float components.
 */
public class Vec2f implements Cloneable {

    public static final Vec2f NULL = new Vec2f();
    public static final Vec2f UNIT_X = new Vec2f(1.0f, 0.0f);
    public static final Vec2f UNIT_Y = new Vec2f(0.0f, 1.0f);

    float x;
    float y;

    public Vec2f() {
        this(0, 0);
    }

    public Vec2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Vec2f fromPolar(float r, float theta) {
        return new Vec2f((float) (r * Math.cos(theta)), (float) (r * Math.sin(theta)));
    }

    /**
     * @return the same vector but in polar
     */
    public Vec2f polar() {
        return new Vec2f(length(), (float) Math.atan2(y, x));
    }

    /**
     * @return the same vector but in cartesian
     */
    public Vec2f cartesian() {
        return new Vec2f((float) (x * Math.cos(y)), (float) (x * Math.sin(y)));
    }

    public Vec2f plus(Vec2f other) {
        return new Vec2f(x + other.x, y + other.y);
    }

    public Vec2f minus(Vec2f other) {
        return plus(other.negate());
    }

    public Vec2f negate() {
        return new Vec2f(-x, -y);
    }

    public Vec2f scale(float scalar) {
        return new Vec2f(x * scalar, y * scalar);
    }

    /**
     * Component-wise multiplication
     *
     * @param other
     * @return a new vector
     */
    public Vec2f mul(Vec2f other) {
        return new Vec2f(x * other.x, y * other.y);
    }

    public float dot(Vec2f other) {
        return x * other.x + y * other.y;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vec2f normalize() {
        float length = length();
        return new Vec2f(x / length, y / length);
    }

    public float angle(Vec2f other) {
        return (float) acos(dot(other) / (length() * other.length()));
    }

    public Vec2f x() {
        return new Vec2f(x, 0);
    }

    public Vec2f y() {
        return new Vec2f(0, y);
    }

    public Vec2f yx() {
        return new Vec2f(y, x);
    }

    public Vec2f x(float x) {
        return new Vec2f(x, y);
    }

    public Vec2f y(float y) {
        return new Vec2f(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec2f vec2f = (Vec2f) o;
        return Float.compare(vec2f.x, x) == 0 && Float.compare(vec2f.y, y) == 0;
    }

    @Override
    public String toString() {
        return "Vec2f{" +
                       "x=" + x +
                       ", y=" + y +
                       '}';
    }
}
