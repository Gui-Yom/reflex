import java.util.ArrayList;
import java.util.List;

/**
 * Calcule les rayons
 */
public class Simulation {

    // Un nombre suffisamment grand
    private static final double INFINITY = 999999;
    private static final double indiceMilieu = Constants.REFRAC_VACUUM;
    private final List<Objet> objets = new ArrayList<>();
    // Les rayons calculés
    private final List<Ray> rays = new ArrayList<>();
    private int maxDepth;

    public Simulation(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public Simulation() {
        this(8);
    }

    /**
     * Will compute the rays
     */
    void compute() {
        rays.clear();
        //System.out.println("Start compute");
        // Ray sources
        for (Objet o : objets) {
            if (o instanceof Laser) {
                Laser laser = (Laser) o;
                computeRay(laser.getPosition(), laser.getDirection(), ParentRay.fromLaser(laser, 0));
            }
        }
    }

    void computeRay(Vec2d origin, Vec2d direction, ParentRay parentRay) {
        Vec2d end = direction.scale(INFINITY).plus(origin);
        if (parentRay.depth >= maxDepth) {
            rays.add(new Ray(origin, end, parentRay.intensity, parentRay.wavelength, 0));
            return;
        }

        Intersection intersection = null;
        double distance = INFINITY;
        // World objects
        for (Objet objet : objets) {
            if (!(objet instanceof Laser)) {
                Intersection i = objet.intersect(origin, end);
                if (i != null) {
                    double d = i.getPoint().minus(origin).length();
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
            Vec2d reflected = Utils.reflect(direction.normalize(), intersection.getNormal());
            //System.out.println("Reflected : " + reflected);
            double newIntensity = parentRay.intensity;
            if (intersection.canTransmit())
                newIntensity /= 2;
            computeRay(intersection.getPoint(), reflected, parentRay.goInDepth(newIntensity, parentRay.nEnvironment));
            // TODO mettre les indices de réfraction des reflex.objets
            // Spawn the refracted ray
            if (intersection.canTransmit()) {
                // TODO inverser les indices de réfraction dans le cas où on sort de l'objet
                Vec2d refracted = null;
                if (parentRay.nEnvironment != indiceMilieu) {
                    refracted = Utils.refract(direction.normalize(), intersection.getNormal(), intersection.getN(), indiceMilieu);
                } else {
                    refracted = Utils.refract(direction.normalize(), intersection.getNormal(), indiceMilieu, intersection.getN());
                }
                //System.out.println("Refracted : " + refracted);
                if (refracted != null) {
                    if (parentRay.nEnvironment == indiceMilieu) {
                        computeRay(intersection.getPoint(), refracted, parentRay.goInDepth(newIntensity, intersection.getN()));
                    } else {
                        computeRay(intersection.getPoint(), refracted, parentRay.goInDepth(newIntensity, indiceMilieu));
                    }
                }
            }
            end = intersection.getPoint();
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
        rays.clear();
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    void configuration1() {
        clear();

        // On ajoute nos reflex.objets
        add(new Laser(new Vec2d(100, 400), -Math.PI / 5, 632));

        Mirror m = new Mirror(new Vec2d(400, 100), 100, 0);
        add(m);
        Mirror m2 = new Mirror(new Vec2d(300, 400), 100, 0);
        add(m2);
        Mirror m3 = new Mirror(new Vec2d(200, 150), 100, -Math.PI / 4);
        add(m3);
        LamesP lame = new LamesP(new Vec2d(200f, 300f), 200, 100, Constants.REFRAC_GLASS);
        add(lame);
        //demiSphere d = new demiSphere(new Vec2f(50, 50), Constants.REFRAC_GLASS, 5f);
    }

    void configuration2() {
        clear();

        add(new Laser(new Vec2d(100, 400), -Math.PI / 5, 632));

        add(new Mirror(new Vec2d(400, 100), 100, 0));

        add(new DemiDisque(new Vec2d(500, 400), 0, Constants.REFRAC_GLASS, 100));
    }

    void configuration3() {
        clear();

        add(new Laser(new Vec2d(100, 400), -Math.PI / 5, 632));

        add(new Mirror(new Vec2d(400, 100), 200, 0));
    }

    private static class ParentRay {
        double intensity;
        double wavelength;
        double nEnvironment;
        int depth;
        // TODO add polarization

        public ParentRay(double intensity, double wavelength, double nEnvironment, int depth) {
            this.intensity = intensity;
            this.wavelength = wavelength;
            this.nEnvironment = nEnvironment;
            this.depth = depth;
        }

        static ParentRay fromRay(Ray ray, double nEnv, int depth) {
            return new ParentRay(ray.getIntensity(), ray.getWavelength(), nEnv, depth);
        }

        static ParentRay fromLaser(Laser laser, int depth) {
            return new ParentRay(laser.getIntensity(), laser.getWavelength(), indiceMilieu, depth);
        }

        ParentRay goInDepth(double intensity, double n) {
            return new ParentRay(intensity, wavelength, n, depth + 1);
        }
    }
}
