public class Segment {
    public Vec2d pointA;
    public Vec2d pointB;
    public Vec2d normale;

    public Segment(Vec2d a, Vec2d b) {
        this.pointA = a;
        this.pointB = b;
        this.normale = this.getNormale();
    }

    public Vec2d getNormale() {
        double dx = pointB.x - pointA.x;
        double dy = pointB.y - pointA.y;

        return new Vec2d(-dy, dx);
    }
}

