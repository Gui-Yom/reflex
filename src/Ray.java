public class Ray {

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

    public Vec2f value(float time) {
        return Vec2f.UNIT_X.scale((float) (intensity * Math.cos(time * 2 * Math.PI * Utils.wavelengthToFreq(wavelength) + phaseShift)));
    }
}
