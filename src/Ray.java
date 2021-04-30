import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 * A ray of light as a finite segment
 */
public class Ray implements Drawable, Sampler {

    private Vec2d start;
    private Vec2d end;

    private double intensity;
    private double wavelength;
    private double phaseShift;

    public Ray(Vec2d start, Vec2d end, double intensity, double wavelength, double phaseShift) {
        this.start = start;
        this.end = end;
        this.intensity = intensity;
        this.wavelength = wavelength;
        this.phaseShift = phaseShift;
    }

    @Override
    public Vec2d sample(double time) {
        return Vec2d.UNIT_X.scale(intensity * Math.cos(time * 2 * Math.PI * Utils.wavelengthToFreq(wavelength) + phaseShift));
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Utils.waveLengthToRGB(wavelength));
        g.draw(new Line2D.Double(start.x, start.y, end.x, end.y));
    }

    public Vec2d getStart() {
        return start;
    }

    public Vec2d getEnd() {
        return end;
    }

    public double getIntensity() {
        return intensity;
    }

    public double getWavelength() {
        return wavelength;
    }

    public double getPhaseShift() {
        return phaseShift;
    }
}
