package reflex.utils;

public class Segment {
    private final Vec2d a;
    private final Vec2d b;
    private final Vec2d normal;

    public Segment(Vec2d a, Vec2d b) {
        this.a = a;
        this.b = b;

        double dx = this.b.x - this.a.x;
        double dy = this.b.y - this.a.y;
        this.normal = new Vec2d(-dy, dx).normalize();
    }

    public Vec2d getA() {
        return a;
    }

    public Vec2d getB() {
        return b;
    }

    public Vec2d getNormal() {
        return normal;
    }
}

