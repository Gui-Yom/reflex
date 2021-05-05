package reflex.utils;

public class Intersection {
    private final Vec2d point;
    private final Vec2d normal;

    /**
     * A negative value indicates the interface can't transmit/refract light
     */
    private final double index;

    public Intersection(Vec2d point, Vec2d normal, double index) {
        this.point = point;
        this.normal = normal;
        this.index = index;
    }

    public boolean canTransmit() {
        return index > 0;
    }

    public Vec2d getPoint() {
        return point;
    }

    public Vec2d getNormal() {
        return normal;
    }

    public double getIndex() {
        return index;
    }
}
