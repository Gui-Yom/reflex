import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class LamesP extends Objet {

    private ArrayList<Segment> aretes;
    private double longueur;
    private double largeur;

    public LamesP(Vec2d pos, double angle, double lon, double larg, double n, Color color) {
        super(pos, angle, n, color);
        this.longueur = lon;
        this.largeur = larg;
        recalc();
    }

    public LamesP(Vec2d position, double longueur, double largeur, double indice) {
        this(position, 0, longueur, largeur, indice, Color.BLACK);
    }

    public Intersection intersect(Vec2d origin, Vec2d end) {
        for (Segment seg : aretes) {
            Vec2d testintersect = Utils.segmentIntersect(seg.getPointA(), seg.getPointB(), origin, end);
            if (testintersect != null) {
                return new Intersection(testintersect, seg.getNormal(), indice);
            }
        }
        return null;
    }

    @Override
    public boolean isClickedOn(Vec2d click) {
        Vec2d a = getPosition();
        Vec2d b = getPosition().plus(new Vec2d(largeur, longueur));
        // TODO use angle to rotate bounding box
        return Utils.testBoundingBox(a, b, click, CLICKED_BIAS);
    }

    public void recalc() {
        ArrayList<Vec2d> p = new ArrayList<>();
        Vec2d A = new Vec2d(position.x - (float) (largeur * Math.cos(Math.PI / 2 + angle)), position.y - (float) (longueur * Math.sin(Math.PI / 2 + angle)));
        Vec2d B = new Vec2d(position.x - (float) (largeur * Math.cos(Math.PI / 2 + angle)), position.y + (float) (longueur * Math.sin(Math.PI / 2 + angle)));
        Vec2d C = new Vec2d(position.x + (float) (largeur * Math.cos(Math.PI / 2 + angle)), position.y + (float) (longueur * Math.sin(Math.PI / 2 + angle)));
        Vec2d D = new Vec2d(position.x + (float) (largeur * Math.cos(Math.PI / 2 + angle)), position.y - (float) (longueur * Math.sin(Math.PI / 2 + angle)));
        p.add(A);
        p.add(B);
        p.add(C);
        p.add(D);
        p.add(A);

        this.aretes = new ArrayList<>();
        for (int i = 0; i < p.size() - 1; i++) {
            aretes.add(new Segment(p.get(i), p.get(i + 1)));
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if (selected) {
            g.setStroke(STROKE_SELECTED);
        }
        g.setColor(color);
        double centerX = position.x + largeur / 2;
        double centerY = position.y + longueur / 2;
        g.translate(centerX, centerY);
        g.rotate(angle);
        g.draw(new Rectangle2D.Double(-largeur / 2, -longueur / 2, largeur, longueur));
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

