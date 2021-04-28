package reflex.objets;

import reflex.Intersection;
import reflex.Utils;
import reflex.Vec2f;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Laser extends Objet {

    Vec2f direction;

    float intensity;
    float wavelength;

    public Laser(Vec2f position, float angle, float wavelength, float intensity) {
        super(position, angle, -1, Utils.waveLengthToRGB(wavelength));
        this.intensity = intensity;
        this.wavelength = wavelength;
        recalc();
    }

    public Laser(Vec2f position, float angle, float wavelength) {
        this(position, angle, wavelength, 1);
    }

    public float sample(float time) {
        return intensity * (float) Math.cos(2 * Math.PI * Utils.wavelengthToFreq(wavelength) * time);
    }

    @Override
    public void recalc() {
        direction = Vec2f.fromPolar(1, angle);
        color = Utils.waveLengthToRGB(wavelength);
    }

    @Override
    public void draw(Graphics2D g) {
        if (selected) {
            g.setStroke(STROKE_SELECTED);
        }
        g.setColor(Utils.waveLengthToRGB(wavelength));
        g.draw(new Rectangle2D.Float(position.x - 5, position.y - 5, 10, 10));
    }

    @Override
    public boolean isClickedOn(Vec2f click) {
        Vec2f extent = new Vec2f(5, 5);
        Vec2f a = getPosition().minus(extent);
        Vec2f b = getPosition().plus(extent);
        return Utils.testBoundingBox(a, b, click, CLICKED_BIAS);
    }

    @Override
    public Intersection intersect(Vec2f origin, Vec2f end) {
        throw new UnsupportedOperationException("Laser can't interact with rays");
    }

    public Vec2f getDirection() {
        return direction;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public float getWavelength() {
        return wavelength;
    }

    public void setWavelength(float wavelength) {
        this.wavelength = wavelength;
    }
}
