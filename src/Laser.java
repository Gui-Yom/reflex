import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Laser extends Objet {

    private Vec2d direction;

    private double intensity;
    private double wavelength;

    public Laser(Vec2d position, double angle, double wavelength, double intensity) {
        super(position, angle, -1, Utils.waveLengthToRGB(wavelength));
        this.intensity = intensity;
        this.wavelength = wavelength;
        recalc();
    }

    public Laser(Vec2d position, double angle, double wavelength) {
        this(position, angle, wavelength, 1);
    }

    public double sample(double time) {
        return intensity * (float) Math.cos(2 * Math.PI * Utils.wavelengthToFreq(wavelength) * time);
    }

    @Override
    public void recalc() {
        direction = Vec2d.fromPolar(1, angle);
        color = Utils.waveLengthToRGB(wavelength);
    }

    @Override
    public void draw(Graphics2D g) {
        if (selected) {
            g.setStroke(STROKE_SELECTED);
        }
        g.setColor(Utils.waveLengthToRGB(wavelength));
        g.draw(new Rectangle2D.Double(position.x - 5, position.y - 5, 10, 10));
    }

    @Override
    public boolean isClickedOn(Vec2d click) {
        Vec2d extent = new Vec2d(5, 5);
        Vec2d a = getPosition().minus(extent);
        Vec2d b = getPosition().plus(extent);
        return Utils.testBoundingBox(a, b, click, CLICKED_BIAS);
    }

    @Override
    public Intersection intersect(Vec2d origin, Vec2d end) {
        throw new UnsupportedOperationException("Laser can't interact with rays");
    }

    public Vec2d getDirection() {
        return direction;
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public double getWavelength() {
        return wavelength;
    }

    public void setWavelength(double wavelength) {
        this.wavelength = wavelength;
    }
}
