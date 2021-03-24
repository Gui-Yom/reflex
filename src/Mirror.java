public class Mirror extends Objet {

    float width;
    float angle;
    Vec2f normal;

    public Mirror(Vec2f position, float width, float angle) {
        super(position, 1.5f);
        this.width = width;
        this.angle = angle;
        recalcNormal();
    }

    public void recalcNormal() {
        this.normal = Vec2f.fromPolar(1, (float) (angle + Math.PI / 2)).normalize();
    }

    @Override
    public Intersection intersect(Vec2f origin, Vec2f end) {

        final Vec2f mirrorEnd = position.plus(Vec2f.fromPolar(width, angle));
        final Vec2f intersection = Utils.segmentIntersect(position, mirrorEnd, origin, end);
        //System.out.printf("intersection: %s%n", intersection);

        return intersection == null ? null : new Intersection(intersection, normal, indice, false);
    }
}
