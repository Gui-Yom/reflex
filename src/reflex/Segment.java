package reflex;

public class Segment {
    public Vec2f pointA;
    public Vec2f pointB;
    public Vec2f normale;

    public Segment(Vec2f a, Vec2f b) {
        this.pointA = a;
        this.pointB = b;
        this.normale = this.getNormale();
    }

    public Vec2f getNormale() {
        float dx = pointB.x - pointA.x;
        float dy = pointB.y - pointA.y;

        return new Vec2f(-dy, dx);
    }
}

