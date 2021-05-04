import java.awt.Color;

public class LamesP extends ObjetComplexe {

    private double longueur;
    private double largeur;

    public LamesP(Vec2d pos, double angle, double lon, double larg, double n, Color color) {
        super(pos, angle, n, color, 4);
        this.longueur = lon;
        this.largeur = larg;
        recalc();
    }

    public LamesP(Vec2d position, double longueur, double largeur, double indice) {
        this(position, 0, longueur, largeur, indice, Color.BLACK);
    }

    @Override
    public void recalc() {

        Vec2d a = position;
        Vec2d b = new Vec2d(largeur, 0).rotate(angle).plus(a);
        Vec2d c = new Vec2d(largeur, longueur).rotate(angle).plus(a);
        Vec2d d = new Vec2d(0, longueur).rotate(angle).plus(a);

        segments.clear();

        segments.add(new Segment(a, b));
        segments.add(new Segment(b, c));
        segments.add(new Segment(c, d));
        segments.add(new Segment(d, a));
    }

    public double getLongueur() {
        return longueur;
    }

    public void setLongueur(double longueur) {
        this.longueur = longueur;
    }

    public double getLargeur() {
        return largeur;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }
}

