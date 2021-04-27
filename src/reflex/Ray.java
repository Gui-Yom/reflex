package reflex;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 * A ray of light as a finite segment
 */
public class Ray implements Drawable {

    Vec2f start;
    Vec2f end;

    float intensity;
    float wavelength;
    float phaseShift;

    public Ray(Vec2f start, Vec2f end, float intensity, float wavelength, float phaseShift) {
        this.start = start;
        this.end = end;
        this.intensity = intensity;
        this.wavelength = wavelength;
        this.phaseShift = phaseShift;
    }

    public Vec2f sample(float time) {
        return Vec2f.UNIT_X.scale((float) (intensity * Math.cos(time * 2 * Math.PI * Utils.wavelengthToFreq(wavelength) + phaseShift)));
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Utils.waveLengthToRGB(wavelength));
        g.draw(new Line2D.Float(start.x, start.y, end.x, end.y));
    }
}
