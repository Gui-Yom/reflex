package reflex;

public class Intersection {
    Vec2f point;
    Vec2f normal;

    /**
     * A negative value indicates this object can't transmit/refract light
     */
    float n;

    public Intersection(Vec2f point, Vec2f normal, float n) {
        this.point = point;
        this.normal = normal;
        this.n = n;
    }

    public boolean canTransmit() {
        return n > 0;
    }
}
