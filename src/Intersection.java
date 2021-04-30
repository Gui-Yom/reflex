public class Intersection {
    Vec2d point;
    Vec2d normal;

    /**
     * A negative value indicates this object can't transmit/refract light
     */
    double n;

    public Intersection(Vec2d point, Vec2d normal, double n) {
        this.point = point;
        this.normal = normal;
        this.n = n;
    }

    public boolean canTransmit() {
        return n > 0;
    }
}
