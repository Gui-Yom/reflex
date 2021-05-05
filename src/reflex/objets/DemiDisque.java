package reflex.objets;

import reflex.utils.Segment;
import reflex.utils.Vec2d;

import java.awt.Color;
import java.util.ArrayList;

public class DemiDisque extends ObjetComplexe {

    private static final int NUM_POINTS = 128;
    private double radius;

    /*
    This object position is the center of the circle
     */

    public DemiDisque(Vec2d pos, double angle, double n, double radius, Color c) {
        super(pos, angle, n, c, NUM_POINTS + 2);
        this.radius = radius;
        recalc();
    }

    public DemiDisque(Vec2d pos, double angle, double n, double radius) {
        this(pos, angle, n, radius, Color.BLACK);
    }

    @Override
    public void recalc() {
        segments.clear();
        Vec2d a = new Vec2d(position.x - this.radius * Math.cos(Math.PI / 2 + angle), position.y - this.radius * Math.sin(Math.PI / 2 + angle));
        Vec2d b = new Vec2d(position.x + this.radius * Math.cos(Math.PI / 2 + angle), position.y + this.radius * Math.sin(Math.PI / 2 + angle));
        Segment diam = new Segment(b, a);
        segments.add(diam);
        ArrayList<Vec2d> pA = new ArrayList<>(NUM_POINTS);
        for (int i1 = 0; i1 < NUM_POINTS; i1++) {
            double alpha = i1 * Math.PI / NUM_POINTS - Math.PI / 2 + angle;
            double X = this.radius * Math.cos(alpha);
            double Y = this.radius * Math.sin(alpha);
            Vec2d p = new Vec2d(X, Y).plus(position);
            pA.add(p);
        }
        pA.add(0, a);
        pA.add(b);
        for (int i = 0; i < pA.size() - 1; i++) {
            segments.add(new Segment(pA.get(i), pA.get(i + 1)));
        }
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
        recalc();
    }

    @Override
    public double getMainDim() {
        return radius;
    }

    @Override
    public void setMainDim(double mainDimension) {
        this.radius = mainDimension;
    }

    @Override
    public double getSecondaryDim() {
        return 0;
    }

    @Override
    public void setSecondaryDim(double secondaryDim) { }
}

