import java.util.ArrayList;
import java.util.List;

/**
 * Calcule les
 */
public class Simulation {

    // Means big number
    private static final float INFINITY = 999999;
    List<Objet> objets = new ArrayList<>();
    // Computed rays
    List<Ray> rays = new ArrayList<>();

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

    void computeRay(Vec2f origin, Vec2f direction, ParentRay parentRay) {
        //System.out.printf("ray: %s, %s%n", origin, direction);
        Vec2f end = direction.scale(INFINITY).plus(origin);
        Intersection intersection = null;
        float distance = INFINITY;
        // World objects
        for (Objet objet : objets) {
            if (!(objet instanceof Laser)) {
                Intersection i = objet.intersect(origin, end);
                if (i != null) {
                    float d_ = i.point.minus(origin).length();
                    if (d_ > 0.0001 && d_ < distance) {
                        intersection = i;
                        distance = d_;
                    }
                }
            }
        }
        if (intersection != null) {
            // We calculate the reflected and refracted rays with a recursion
            // Spawn the reflected ray
            Vec2f reflected = Utils.reflect(direction.normalize(), intersection.normal);
            //System.out.println("Reflected : " + reflected);
            computeRay(intersection.point, reflected, parentRay);
            // TODO mettre les indices de réfraction des reflex.objets
            // Spawn the refracted ray
            if (intersection.canTransmit()) {
                // TODO inverser les indices de réfraction dans le cas où on sort de l'objet
                Vec2f refracted = Utils.refract(direction.normalize(), intersection.normal, 1.0f, intersection.n);
                //System.out.println("Refracted : " + refracted);
                if (refracted != null) {
                    computeRay(intersection.point, refracted, parentRay);
                }
            }
            end = intersection.point;
        }
        rays.add(new Ray(origin, end, parentRay.intensity, parentRay.wavelength, 0));
    }

    private static class ParentRay {
        float intensity;
        float wavelength;
        float nEnvironment; 
        // TODO add polarization

        public ParentRay(float intensity, float wavelength, float nEnvironment) {
            this.intensity = intensity;
            this.wavelength = wavelength;
            this.nEnvironment = nEnvironment;
        }

        static ParentRay fromRay(Ray ray, float nEnv) {
            return new ParentRay(ray.intensity, ray.wavelength, nEnv);
        }

        static ParentRay fromLaser(Laser laser) {
            return new ParentRay(laser.getIntensity(), laser.getWavelength(), 1.0f);
        }
    }
}
