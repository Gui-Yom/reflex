import java.util.ArrayList;
import java.util.List;

/**
 * Les calculs se passent ici
 */
public class Simulation {

    // Un nombre suffisamment grand, pour éviter les problèmes avec Double.POSITIVE_INFINITY
    private static final double INFINITY = 999999;
    private static final double ENV_REFRAC_INDEX = Constants.REFRAC_VACUUM;
    private final List<Objet> objets = new ArrayList<>();
    // Les rayons calculés
    private final List<Ray> rays = new ArrayList<>();
    // Nombre maximal de récursions
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
        // Les sources de rayon sont des lasers
        for (Objet o : objets) {
            if (o instanceof Laser) {
                Laser laser = (Laser) o;
                System.out.println("start ray");
                computeRay(new ComputeState(laser));
            }
        }
    }

    /**
     * Calcule le tracé des rayons par récursion
     *
     * @param state informations sur le rayon et l'état actuel de la simulation
     * @see ComputeState
     */
    void computeRay(ComputeState state) {
        // Par défaut, le rayon actuel va à l'infini
        Vec2d end = state.direction.scale(INFINITY).plus(state.origin);

        // On limite la récursion pour éviter le stack overflow en cas de réflections infinies
        if (state.depth >= maxDepth) {
            rays.add(new Ray(state.origin, end, state.intensity, state.wavelength, 0));
            return;
        }

        Intersection intersection = null;
        double distance = INFINITY;
        // On cherche l'intersection la plus proche de l'origine du rayon
        for (Objet objet : objets) {
            Intersection i = objet.intersect(state.origin, end);
            if (i != null) {
                double d = i.getPoint().minus(state.origin).length();
                // La première condition permet d'éviter l'intersection entre le rayon et la surface d'où il part
                if (d > 0.0001 && d < distance) {
                    intersection = i;
                    distance = d;
                }
            }
        }

        if (intersection != null) {
            // Le rayon actuel se finit au point d'intersection
            end = intersection.getPoint();

            // Indice de réfraction du milieu du rayon incident
            double currIndex = ENV_REFRAC_INDEX;
            // Indice de réfraction du milieu du rayon transmis
            double targetIndex = intersection.getN();
            // On inverse les index dans le cas où on sort d'un objet
            if (state.currRefracIndex != ENV_REFRAC_INDEX) {
                currIndex = state.currRefracIndex;
                targetIndex = ENV_REFRAC_INDEX;
            }

            // On calcule l'intensité du rayon réfléchi
            double reflectIntensity;
            if (intersection.canTransmit()) {
                reflectIntensity = state.intensity * Utils.coefReflectPower(currIndex, targetIndex);
            } else {
                reflectIntensity = state.intensity;
            }
            System.out.println("reflect: " + reflectIntensity);
            // On lance le calcul du rayon réfléchi
            Vec2d reflected = Utils.reflect(state.direction.normalize(), intersection.getNormal());

            computeRay(state.goInDepth(intersection.getPoint(), reflected, reflectIntensity, state.currRefracIndex));

            // Il peut exister un rayon réfracté / transmis
            if (intersection.canTransmit()) {
                // On calcule le rayon réfracté
                Vec2d refracted = Utils.refract(state.direction.normalize(), intersection.getNormal(), currIndex, targetIndex);
                System.out.println("t: " + refracted);
                if (refracted != null) {
                    // Intensité du rayon transmis
                    double refractIntensity = state.intensity * Utils.coefTransmitPower(currIndex, targetIndex);
                    System.out.println("refract: " + refractIntensity);
                    computeRay(state.goInDepth(intersection.getPoint(), refracted, refractIntensity, targetIndex));
                }
            }
        }
        // On ajoute le rayon actuel à la liste des rayons calculés
        rays.add(new Ray(state.origin, end, state.intensity, state.wavelength, 0));
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

    public void configuration1() {
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

    public void configuration2() {
        clear();

        add(new Laser(new Vec2d(100, 400), -Math.PI / 5, 632));

        add(new Mirror(new Vec2d(400, 100), 100, 0));

        add(new DemiDisque(new Vec2d(500, 400), 0, Constants.REFRAC_GLASS, 100));
    }

    public void configuration3() {
        clear();

        add(new Laser(new Vec2d(100, 400), -Math.PI / 5, 632));

        add(new Mirror(new Vec2d(400, 100), 200, 0));
    }

    /**
     * Stocke les informations de calcul pour la récursion
     */
    private static class ComputeState {
        Vec2d origin;
        Vec2d direction;
        double intensity;
        double wavelength;
        double currRefracIndex;
        int depth;

        ComputeState(Vec2d origin, Vec2d direction, double intensity, double wavelength, double currRefracIndex, int depth) {
            this.origin = origin;
            this.direction = direction;
            this.intensity = intensity;
            this.wavelength = wavelength;
            this.currRefracIndex = currRefracIndex;
            this.depth = depth;
        }

        /**
         * Crée une instance de départ à partir d'un laser
         *
         * @param laser le laser qui crée le rayon initial
         */
        ComputeState(Laser laser) {
            this(laser.getPosition(), laser.getDirection(), laser.getIntensity(), laser.getWavelength(), ENV_REFRAC_INDEX, 0);
        }

        /**
         * Construit une nouvelle instance pour un nouveau rayon fils
         *
         * @param origin    l'origine du nouveau rayon
         * @param direction la nouvelle direction du rayon
         * @param intensity la nouvelle intensité du rayon
         * @param n         le nouvel indice de réfraction du milieu
         * @return la nouvelle instance de ComputeState
         */
        ComputeState goInDepth(Vec2d origin, Vec2d direction, double intensity, double n) {
            return new ComputeState(origin, direction, intensity, wavelength, n, depth + 1);
        }
    }
}
