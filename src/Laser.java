public class Laser {

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
}
