package reflex.objets;

import reflex.Sampler;
import reflex.utils.Constants;
import reflex.utils.Intersection;
import reflex.utils.Utils;
import reflex.utils.Vec2d;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Laser extends Objet implements Sampler {

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
        this(position, angle, wavelength, Constants.INTENSITY_MAX);
    }

    @Override
    public Vec2d sample(double time) {
        return new Vec2d().x(intensity * (float) Math.cos(2 * Math.PI * Utils.wavelengthToFreq(wavelength) * time));
    }

    @Override
    public void recalc() {
        direction = Vec2d.fromPolar(1, angle);
        color = Utils.waveLengthToRGB(wavelength);
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        g.draw(new Rectangle2D.Double(position.x - 5, position.y - 5, 10, 10));
    }

    @Override
    public boolean isClickedOn(Vec2d click) {
        Vec2d extent = new Vec2d(5, 5);
        Vec2d a = getPosition().minus(extent);
        Vec2d b = getPosition().plus(extent);
        return Utils.testBoundingBox(a, b, click, CLICK_BIAS);
    }

    @Override
    public Intersection intersect(Vec2d origin, Vec2d end) {
        // Un laser ne dévie pas les rayons
        return null;
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

    @Override
    public double getMainDim() {
        return wavelength;
    }

    @Override
    public void setMainDim(double mainDimension) {
        wavelength = mainDimension;
    }

    @Override
    public double getSecondaryDim() {
        return intensity;
    }

    @Override
    public void setSecondaryDim(double secondaryDim) {
        intensity = Math.min(secondaryDim, Constants.INTENSITY_MAX);
    }
}
