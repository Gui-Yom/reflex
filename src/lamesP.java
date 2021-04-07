import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class lamesP extends Objet {

    float longueur;
    float largeur;

    public lamesP(Vec2f pos, float n, float lon, float larg) {
        super(pos, n);
        this.longueur = lon;
        this.largeur = larg;
        ArrayList<Vec2f> p = new ArrayList<>();
        Vec2f A = new Vec2f(position.x - (float) (largeur * Math.cos(Math.PI / 2 + angleHoriz)), position.y - (float) (longueur * Math.sin(Math.PI / 2 + angleHoriz)));
        Vec2f B = new Vec2f(position.x - (float) (largeur * Math.cos(Math.PI / 2 + angleHoriz)), position.y + (float) (longueur * Math.sin(Math.PI / 2 + angleHoriz)));
        Vec2f C = new Vec2f(position.x + (float) (largeur * Math.cos(Math.PI / 2 + angleHoriz)), position.y + (float) (longueur * Math.sin(Math.PI / 2 + angleHoriz)));
        Vec2f D = new Vec2f(position.x + (float) (largeur * Math.cos(Math.PI / 2 + angleHoriz)), position.y - (float) (longueur * Math.sin(Math.PI / 2 + angleHoriz)));
        p.add(A);
        p.add(B);
        p.add(C);
        p.add(D);
        p.add(A);
        for (int i = 0; i < p.size() - 1; i++) {
            Aretes.add(new Segment(p.get(i), p.get(i + 1)));
        }
    }

    public Intersection intersect(Vec2f origin, Vec2f end) {
        for (Segment seg : Aretes) {
            Vec2f testintersect = Utils.segmentIntersect(seg.pointA, seg.pointB, origin, end);
            if (testintersect != null) {
                return new Intersection(testintersect, seg.normale, indice, true);
            }
        }
        return null;
    }

    public void dessine(Graphics2D g) {
        g.setColor(couleurObjet);
        g.draw(new Rectangle2D.Float(position.x, position.y, largeur, longueur));
    }

}

