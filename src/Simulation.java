import java.util.ArrayList;
import java.util.List;

/**
 * Calcule les rayons
 */
public class Simulation {

    // Un nombre suffisamment grand
    private static final double INFINITY = 999999;
    private final List<Objet> objets = new ArrayList<>();
    // Les rayons calculés
    private final List<Ray> rays = new ArrayList<>();

    /**
     * Will compute the rays
     */
    void compute() {
        rays.clear();
        System.out.println("Start compute");
        // Ray sources
        for (Objet o : objets) {
            if (o instanceof Laser) {
                Laser laser = (Laser) o;
                computeRay(laser.getPosition(), laser.getDirection(), ParentRay.fromLaser(laser));
            }
        }
    }

    void computeRay(Vec2d origin, Vec2d direction, ParentRay parentRay) {
        Vec2d end = direction.scale(INFINITY).plus(origin);
        Intersection intersection = null;
        double distance = INFINITY;
        // World objects
        for (Objet objet : objets) {
            if (!(objet instanceof Laser)) {
                Intersection i = objet.intersect(origin, end);
                if (i != null) {
                    double d = i.point.minus(origin).length();
                    if (d > 0.0001 && d < distance) {
                        intersection = i;
                        distance = d;
                    }
                }
            }
        }
        if (intersection != null) {
            // We calculate the reflected and refracted rays with a recursion
            // Spawn the reflected ray
            Vec2d reflected = Utils.reflect(direction.normalize(), intersection.normal);
            //System.out.println("Reflected : " + reflected);
            computeRay(intersection.point, reflected, parentRay);
            // TODO mettre les indices de réfraction des reflex.objets
            // Spawn the refracted ray
            if (intersection.canTransmit()) {
                // TODO inverser les indices de réfraction dans le cas où on sort de l'objet
                Vec2d refracted = Utils.refract(direction.normalize(), intersection.normal, 1.0f, intersection.n);
                //System.out.println("Refracted : " + refracted);
                if (refracted != null) {
                    computeRay(intersection.point, refracted, parentRay);
                }
            }
            end = intersection.point;
        }
        rays.add(new Ray(origin, end, parentRay.intensity, parentRay.wavelength, 0));
    }

    public List<Objet> getObjets() {
        return objets;
    }

    public List<Ray> getRays() {
        return rays;
    }

    public void add(Objet o) {
        objets.add(o);
    }

    public void clear() {
        objets.clear();
    }

    private static class ParentRay {
        double intensity;
        double wavelength;
        double nEnvironment;
        // TODO add polarization

        public ParentRay(double intensity, double wavelength, double nEnvironment) {
            this.intensity = intensity;
            this.wavelength = wavelength;
            this.nEnvironment = nEnvironment;
        }

        static ParentRay fromRay(Ray ray, double nEnv) {
            return new ParentRay(ray.intensity, ray.wavelength, nEnv);
        }

        static ParentRay fromLaser(Laser laser) {
            return new ParentRay(laser.getIntensity(), laser.getWavelength(), 1.0);
        }
    }
}
