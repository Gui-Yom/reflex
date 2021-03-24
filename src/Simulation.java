import java.util.ArrayList;
import java.util.List;

public class Simulation {

    // Means big number
    private static final float INFINITY = 999999;
    List<Laser> lasers = new ArrayList<>();
    List<Mirror> mirrors = new ArrayList<>();
    //List<Milieu> milieus = new ArrayList<>();
    // Computed rays
    List<Ray> rays = new ArrayList<>();

    /**
     * Will compute the rays
     */
    void compute() {
        rays.clear();
        System.out.println("Start compute");
        // Ray sources
        for (Laser laser : lasers) {
            computeRay(laser.origin, laser.direction, ParentRay.fromLaser(laser), null);
        }
    }

    void computeRay(Vec2f origin, Vec2f direction, ParentRay parentRay, Mirror sourceMirror) {
        //System.out.printf("ray: %s, %s%n", origin, direction);
        Vec2f end = direction.scale(INFINITY).plus(origin);
        // World objects
        for (Mirror mirror : mirrors) {
            // intersection point collide with the mirror
            // without this condition we get intersection of the reflected ray with the mirror
            if (mirror == sourceMirror)
                continue;

            final Vec2f mirrorEnd = mirror.position.plus(Vec2f.fromPolar(mirror.width, mirror.angle));
            final Vec2f intersection = Utils.segmentIntersect(mirror.position, mirrorEnd, origin, end);
            //System.out.printf("intersection: %s%n", intersection);
            if (intersection != null) {
                // We calculate the reflected and refracted rays with a recursion
                // Spawn the reflected ray
                Vec2f reflected = Utils.reflect(direction.normalize(), mirror.normal);
                System.out.println("Reflected : " + reflected);
                computeRay(intersection, reflected, parentRay, mirror);
                // TODO mettre les indices de réfraction des objets
                // Spawn the refracted ray
                //Vec2f refracted = Utils.refract(direction, mirror.normal, 1.0f, 1.5f);
                //System.out.println("Refracted : " + reflected);
                //computeRay(intersection, refracted, parentRay, mirror);
                end = intersection;
                break;
            }
        }
        rays.add(new Ray(origin, end, parentRay.intensity, parentRay.wavelength, 0));
    }

    private static class ParentRay {
        float intensity;
        float wavelength;
        // TODO add polarization

        public ParentRay(float intensity, float wavelength) {
            this.intensity = intensity;
            this.wavelength = wavelength;
        }

        static ParentRay fromRay(Ray ray) {
            return new ParentRay(ray.intensity, ray.wavelength);
        }

        static ParentRay fromLaser(Laser laser) {
            return new ParentRay(laser.intensity, laser.wavelength);
        }
    }
}
