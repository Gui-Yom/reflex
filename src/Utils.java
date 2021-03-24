import java.awt.Color;

import static java.lang.Math.*;

public final class Utils {
    private static final double Gamma = 0.80;
    private static final double IntensityMax = 255;

    /**
     * Taken from Earl F. Glynn's web page:
     * <a href="http://www.efg2.com/Lab/ScienceAndEngineering/Spectra.htm">Spectra Lab Report</a>
     *
     * @param wavelength the wavelength in nanometers, from 380 to 780 nm
     */
    public static Color waveLengthToRGB(float wavelength) {

        float r, g, b;
        if ((wavelength >= 380) && (wavelength < 440)) {
            r = -(wavelength - 440) / (440 - 380);
            g = 0.0f;
            b = 1.0f;
        } else if ((wavelength >= 440) && (wavelength < 490)) {
            r = 0.0f;
            g = (wavelength - 440) / (490 - 440);
            b = 1.0f;
        } else if ((wavelength >= 490) && (wavelength < 510)) {
            r = 0.0f;
            g = 1.0f;
            b = -(wavelength - 510) / (510 - 490);
        } else if ((wavelength >= 510) && (wavelength < 580)) {
            r = (wavelength - 510) / (580 - 510);
            g = 1.0f;
            b = 0.0f;
        } else if ((wavelength >= 580) && (wavelength < 645)) {
            r = 1.0f;
            g = -(wavelength - 645) / (645 - 580);
            b = 0.0f;
        } else if ((wavelength >= 645) && (wavelength < 781)) {
            r = 1.0f;
            g = 0.0f;
            b = 0.0f;
        } else {
            r = 0.0f;
            g = 0.0f;
            b = 0.0f;
        }

        // Let the intensity fall off near the vision limits
        float factor;
        if ((wavelength >= 380) && (wavelength < 420)) {
            factor = 0.3f + 0.7f * (wavelength - 380) / (420 - 380);
        } else if ((wavelength >= 420) && (wavelength < 701)) {
            factor = 1.0f;
        } else if ((wavelength >= 701) && (wavelength < 781)) {
            factor = 0.3f + 0.7f * (780 - wavelength) / (780 - 700);
        } else {
            factor = 0.0f;
        }

        return new Color(
            (int) Math.round(IntensityMax * Math.pow(r * factor, Gamma)),
            (int) Math.round(IntensityMax * Math.pow(g * factor, Gamma)),
            (int) Math.round(IntensityMax * Math.pow(b * factor, Gamma)));
    }

    public static float wavelengthToFreq(float wavelength) {
        return Constants.SPEED_OF_LIGHT * 1000000000 / wavelength;
    }

    /**
     * Line segment intersection,
     * <a href="https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection#Given_two_points_on_each_line_segment">wikipedia</a>
     *
     * @param p1 a point on line 1
     * @param p2 another point on line 1
     * @param p3 a point on line 2
     * @param p4 another point on line 2
     * @return the intersection point or null if segments do not intersect
     */
    public static Vec2f segmentIntersect(Vec2f p1, Vec2f p2, Vec2f p3, Vec2f p4) {
        Vec2f diff12 = p1.minus(p2);
        Vec2f diff34 = p3.minus(p4);
        Vec2f diff13 = p1.minus(p3);
        float a = diff13.x * diff34.y - diff13.y * diff34.x;
        float b = diff12.x * diff34.y - diff12.y * diff34.x;
        if ((a < 0 && b > 0) || (a > 0 && b < 0) || abs(a) > abs(b)) {
            // If sign is different, t < 0, no intersection
            // If |a| > |b|, t > 1, no intersection
            return null;
        } else { // Intersection
            float t = a / b;
            return new Vec2f(p1.x + t * (p2.x - p1.x), p1.y + t * (p2.y - p1.y));
        }
    }

    /**
     * https://docs.gl/sl4/reflect
     * R = I - 2*(I.N)*N
     *
     * @param i the incident vector, normalized
     * @param n normal vector, normalized
     * @return the reflected vector
     */
    public static Vec2f reflect(Vec2f i, Vec2f n) {
        return i.minus(n.scale(2 * i.dot(n)));
    }

    /**
     * @param i  incident vector, normalized
     * @param n  normal vector, normalized
     * @param n1
     * @param n2
     * @return the refracted vector
     */
    public static Vec2f refract2(Vec2f i, Vec2f n, float n1, float n2) {
        float i1 = n.orientedAngle(i);
        System.out.println("i1: " + i1);
        // n1 sin(i1) = n2 sin(i2)
        float i2 = (float) asin(n1 / n2 * sin(i1));
        float sens = i.dot(n);
        float angle;
        if (sens > 0) {
            angle = -i2;
        } else {
            angle = (float) (PI - i2);
        }
        Vec2f r = n.polar();
        r.y += angle;
        return r.cartesian().normalize();
    }

    /**
     * @param i  incident vector, normalized
     * @param n  normal vector, normalized
     * @param n1
     * @param n2
     * @return the refracted vector
     */
    public static Vec2f refract(Vec2f i, Vec2f n, float n1, float n2) {
        float dot = i.dot(n);
        if (dot < 0) dot = -dot;
        else n = n.negate();
        float eta = n1 / n2;
        float k = 1 - eta * eta * (1 - dot * dot);
        return k < 0 ? null : i.scale(eta).plus(n.scale(eta * dot - (float) sqrt(k)));
    }
}
