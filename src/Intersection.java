public class Intersection {
    private final Vec2d point;
    private final Vec2d normal;

    /**
     * A negative value indicates the interface can't transmit/refract light
     */
    private final double n;

    public Intersection(Vec2d point, Vec2d normal, double n) {
        this.point = point;
        this.normal = normal;
        this.n = n;
    }

    public boolean canTransmit() {
        return n > 0;
    }

    public Vec2d getPoint() {
        return point;
    }

    public Vec2d getNormal() {
        return normal;
    }

    public double getN() {
        return n;
    }
}
