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
    public static Color waveLengthToRGB(double wavelength) {

        double r, g, b;
        if ((wavelength >= 380) && (wavelength < 440)) {
            r = -(wavelength - 440) / (440 - 380);
            g = 0.0;
            b = 1.0;
        } else if ((wavelength >= 440) && (wavelength < 490)) {
            r = 0.0;
            g = (wavelength - 440) / (490 - 440);
            b = 1.0;
        } else if ((wavelength >= 490) && (wavelength < 510)) {
            r = 0.0;
            g = 1.0;
            b = -(wavelength - 510) / (510 - 490);
        } else if ((wavelength >= 510) && (wavelength < 580)) {
            r = (wavelength - 510) / (580 - 510);
            g = 1.0;
            b = 0.0;
        } else if ((wavelength >= 580) && (wavelength < 645)) {
            r = 1.0;
            g = -(wavelength - 645) / (645 - 580);
            b = 0.0;
        } else if ((wavelength >= 645) && (wavelength < 781)) {
            r = 1.0;
            g = 0.0;
            b = 0.0;
        } else {
            r = 0.0;
            g = 0.0;
            b = 0.0;
        }

        // Let the intensity fall off near the vision limits
        double factor;
        if ((wavelength >= 380) && (wavelength < 420)) {
            factor = 0.3 + 0.7 * (wavelength - 380) / (420 - 380);
        } else if ((wavelength >= 420) && (wavelength < 701)) {
            factor = 1.0;
        } else if ((wavelength >= 701) && (wavelength < 781)) {
            factor = 0.3 + 0.7 * (780 - wavelength) / (780 - 700);
        } else {
            factor = 0.0;
        }

        return new Color(
            (int) Math.round(IntensityMax * Math.pow(r * factor, Gamma)),
            (int) Math.round(IntensityMax * Math.pow(g * factor, Gamma)),
            (int) Math.round(IntensityMax * Math.pow(b * factor, Gamma)));
    }

    public static double wavelengthToFreq(double wavelength) {
        return Constants.SPEED_OF_LIGHT * 1_000_000_000 / wavelength;
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
    public static Vec2d segmentIntersect(Vec2d p1, Vec2d p2, Vec2d p3, Vec2d p4) {
        Vec2d diff12 = p1.minus(p2);
        Vec2d diff34 = p3.minus(p4);
        Vec2d diff13 = p1.minus(p3);
        double a = diff13.x * diff34.y - diff13.y * diff34.x;
        double b = diff12.x * diff34.y - diff12.y * diff34.x;
        if ((a < 0 && b > 0) || (a > 0 && b < 0) || abs(a) > abs(b)) {
            // If sign is different, t < 0, no intersection
            // If |a| > |b|, t > 1, no intersection
            return null;
        } else { // Intersection
            double t = a / b;
            return new Vec2d(p1.x + t * (p2.x - p1.x), p1.y + t * (p2.y - p1.y));
        }
    }

    /**
     * @param a
     * @param b
     * @param click
     * @return true if click is inside the rectangle ab
     */
    public static boolean testBoundingBox(Vec2d a, Vec2d b, Vec2d click, float bias) {
        return click.x <= max(a.x, b.x) + bias && click.x >= min(a.x, b.x) - bias
                   && click.y <= max(a.y, b.y) + bias && click.y >= min(a.y, b.y) - bias;
    }

    /**
     * https://docs.gl/sl4/reflect
     * R = I - 2*(I.N)*N
     *
     * @param i the incident vector, normalized
     * @param n normal vector, normalized
     * @return the reflected vector
     */
    public static Vec2d reflect(Vec2d i, Vec2d n) {
        return i.minus(n.scale(2 * i.dot(n)));
    }

    /**
     * @param i  incident vector, normalized
     * @param n  normal vector, normalized
     * @param n1
     * @param n2
     * @return the refracted vector
     */
    public static Vec2d refract(Vec2d i, Vec2d n, double n1, double n2) {
        double dot = i.dot(n);
        if (dot < 0) dot = -dot;
        else n = n.negate();
        double eta = n1 / n2;
        double k = 1 - eta * eta * (1 - dot * dot);
        return k < 0 ? null : i.scale(eta).plus(n.scale(eta * dot - (float) sqrt(k)));
    }
}
