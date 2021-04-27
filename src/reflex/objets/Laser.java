package reflex.objets;

import reflex.Drawable;
import reflex.Utils;
import reflex.Vec2f;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class Laser implements Drawable {

    Vec2f origin;
    Vec2f direction;

    float intensity = 1;
    float wavelength;

    public Laser(Vec2f origin, Vec2f direction, float wavelength) {
        this.origin = origin;
        this.direction = direction.normalize();
        this.wavelength = wavelength;
    }

    public float sample(float time) {
        return intensity * (float) Math.cos(2 * Math.PI * Utils.wavelengthToFreq(wavelength) * time);
    }

    public Vec2f getOrigin() {
        return origin;
    }

    public Vec2f getDirection() {
        return direction;
    }

    public float getIntensity() {
        return intensity;
    }

    public float getWavelength() {
        return wavelength;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Utils.waveLengthToRGB(wavelength));
        g.draw(new Rectangle2D.Float(origin.x - 5, origin.y - 5, 10, 10));
        g.draw(new Line2D.Float(origin.x, origin.y, origin.x + direction.x * 10, origin.y + direction.y * 10));
    }
}
