package reflex.objets;

import reflex.utils.Segment;
import reflex.utils.Vec2d;

import java.awt.Color;

public class LamesP extends ObjetComplexe {

    private double longueur;
    private double largeur;

    public LamesP(Vec2d pos, double angle, double lon, double larg, double refracIndex, Color color) {
        super(pos, angle, refracIndex, color, 4);
        this.longueur = lon;
        this.largeur = larg;
        recalc();
    }

    public LamesP(Vec2d position, double longueur, double largeur, double angle, double refracIndex) {
        this(position, angle, longueur, largeur, refracIndex, Color.BLACK);
    }

    @Override
    public void recalc() {

        Vec2d center = new Vec2d(longueur / 2, largeur / 2);
        Vec2d a = center.negate().rotate(angle).plus(position);
        Vec2d b = new Vec2d(longueur / 2, -largeur / 2).rotate(angle).plus(position);
        Vec2d c = new Vec2d(longueur / 2, largeur / 2).rotate(angle).plus(position);
        Vec2d d = new Vec2d(-longueur / 2, largeur / 2).rotate(angle).plus(position);

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

    @Override
    public double getMainDim() {
        return longueur;
    }

    @Override
    public void setMainDim(double mainDimension) {
        this.longueur = mainDimension;
    }

    @Override
    public double getSecondaryDim() {
        return largeur;
    }

    @Override
    public void setSecondaryDim(double secondaryDim) {
        largeur = secondaryDim;
    }
}

