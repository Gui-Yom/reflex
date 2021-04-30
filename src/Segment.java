public class Segment {
    private Vec2d a;
    private Vec2d b;
    private Vec2d normal;

    public Segment(Vec2d a, Vec2d b) {
        this.a = a;
        this.b = b;

        double dx = this.b.x - this.a.x;
        double dy = this.b.y - this.a.y;
        this.normal = new Vec2d(-dy, dx);
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

