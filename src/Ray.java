import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 * A ray of light as a finite segment
 */
public class Ray implements Drawable, Sampler {

    public static double RAY_DISPLAY_TRESHOLD = 0.1;
    private final Vec2d start;
    private final Vec2d end;
    private final double intensity;
    private final double wavelength;
    private final double phaseShift;

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
        if (intensity > RAY_DISPLAY_TRESHOLD * Constants.INTENSITY_MAX) {
            Color c = Utils.waveLengthToRGB(wavelength);
            c = new Color(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, (float) (intensity * 0.75 / Constants.INTENSITY_MAX + 1.0 - 0.75));
            g.setColor(c);
            g.draw(new Line2D.Double(start.x, start.y, end.x, end.y));
        } /*else {
            g.setColor(Color.LIGHT_GRAY);
            g.draw(new Line2D.Double(start.x, start.y, end.x, end.y));
        }*/
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

    @Override
    public String toString() {
        return "Ray{" +
                   "start=" + start +
                   ", end=" + end +
                   ", intensity=" + intensity +
                   ", wavelength=" + wavelength +
                   ", phaseShift=" + phaseShift +
                   '}';
    }
}
