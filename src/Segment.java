public class Segment {
    private Vec2d pointA;
    private Vec2d pointB;
    private Vec2d normal;

    public Segment(Vec2d a, Vec2d b) {
        this.pointA = a;
        this.pointB = b;

        double dx = pointB.x - pointA.x;
        double dy = pointB.y - pointA.y;
        this.normal = new Vec2d(-dy, dx);
    }

    public Vec2d getPointA() {
        return pointA;
    }

    public Vec2d getPointB() {
        return pointB;
    }

    public Vec2d getNormal() {
        return normal;
    }
}

