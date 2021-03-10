public class Mirror {

    Vec2f position;
    float width;
    float angle;
    Vec2f normal;

    public Mirror(Vec2f position, float width, float angle) {
        this.position = position;
        this.width = width;
        this.angle = angle;
        recalcNormal();
    }

    public void recalcNormal() {
        this.normal = Vec2f.fromPolar(1, (float) (angle + Math.PI / 2)).normalize();
    }
}
