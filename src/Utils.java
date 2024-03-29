import java.awt.Color;
import java.util.List;

import static java.lang.Math.*;

public final class Utils {

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
            (int) round((double) 255 * pow(r * factor, 0.80)),
            (int) round((double) 255 * pow(g * factor, 0.80)),
            (int) round((double) 255 * pow(b * factor, 0.80)));
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
        Vec2d diff21 = diff12.negate();
        Vec2d diff34 = p3.minus(p4);
        Vec2d diff13 = p1.minus(p3);
        double a = diff13.x * diff34.y - diff13.y * diff34.x;
        double b = diff12.x * diff34.y - diff12.y * diff34.x;
        double c = diff21.x * diff13.y - diff21.y * diff13.x;
        if ((a < 0 && b > 0) || (a > 0 && b < 0) || (c < 0 && b > 0) || (c > 0 && b < 0) || abs(a) > abs(b) || abs(c) > abs(b)) {
            // If sign is different, t < 0, no intersection
            // If |a| > |b|, t > 1, no intersection
            // We check for t = a/b and for u = c/b
            return null;
        } else { // Intersection
            double t = a / b;
            return new Vec2d(p1.x + t * (p2.x - p1.x), p1.y + t * (p2.y - p1.y));
        }
    }

    public static Intersection segmentListIntersect(Vec2d origin, Vec2d end, List<Segment> segments) {
        Intersection best = null;
        double d = Double.POSITIVE_INFINITY;
        for (Segment seg : segments) {
            Vec2d testintersect = segmentIntersect(seg.getA(), seg.getB(), origin, end);
            if (testintersect != null) {
                double a = testintersect.minus(origin).length();
                if (a > 0.0001 && a < d) {
                    d = a;
                    best = new Intersection(testintersect, seg.getNormal(), 0);
                }
            }
        }
        //System.out.println(best);
        return best;
    }

    /**
     * Trouve le Axis Aligned Bounding Box d'une liste de segments.
     */
    public static Vec2d[] findAABB(List<Segment> segments) {
        Vec2d a = new Vec2d(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        Vec2d b = new Vec2d(0, 0);

        // Cherche le point le plus proche et le plus éloigné de l'origine
        // Ces 2 points nous permettent de créer le rectangle de sélection
        // qui englobe cet objet
        for (Segment seg : segments) {
            if (seg.getA().x < a.x) {
                a = a.x(seg.getA().x);
            }
            if (seg.getA().y < a.y) {
                a = a.y(seg.getA().y);
            }
            if (seg.getA().x > b.x) {
                b = b.x(seg.getA().x);
            }
            if (seg.getA().y > b.y) {
                b = b.y(seg.getA().y);
            }
        }
        return new Vec2d[] { a, b };
    }

    /**
     * @return true si click est dans le aabb défini par a et b
     */
    public static boolean testBoundingBox(Vec2d a, Vec2d b, Vec2d click, float bias) {
        return click.x <= max(a.x, b.x) + bias && click.x >= min(a.x, b.x) - bias
                   && click.y <= max(a.y, b.y) + bias && click.y >= min(a.y, b.y) - bias;
    }

    /**
     * https://docs.gl/sl4/reflect
     * R = I - 2*(I.N)*N
     *
     * @param i le vecteur incident, normalisé.
     * @param n le vecteur normal à la surface, normalisé. Sa direction n'a pas d'importance.
     * @return le vecteur réfléchi
     */
    public static Vec2d reflect(Vec2d i, Vec2d n) {
        double dot = i.dot(n);

        /*
        System.out.println("reflect");
        System.out.println("i: " + i);
        System.out.println("n: " + n);
        System.out.println("dot: " + dot);*/

        return i.minus(n.scale(2 * dot));
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

    /**
     * @return le coefficient de réflexion de l'onde en puissance R
     */
    public static double coefReflectPower(double n1, double n2) {
        return pow((n1 - n2) / (n1 + n2), 2);
    }

    /**
     * @return le coefficient de transmission de l'onde en puissance T
     */
    public static double coefTransmitPower(double n1, double n2) {
        return 4 * n1 * n2 / pow(n1 + n2, 2);
    }
}
