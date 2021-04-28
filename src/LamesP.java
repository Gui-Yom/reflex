import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class LamesP extends Objet {

    protected ArrayList<Segment> aretes;
    protected float longueur;
    protected float largeur;

    public LamesP(Vec2f pos, float angle, float lon, float larg, float n, Color color) {
        super(pos, angle, n, color);
        this.longueur = lon;
        this.largeur = larg;
        //ArrayList<Vec2f> p = new ArrayList<>();
        //Vec2f A = new Vec2f(position.x - (float) (largeur * Math.cos(Math.PI / 2 + angle)), position.y - (float) (longueur * Math.sin(Math.PI / 2 + angle)));
        //Vec2f B = new Vec2f(position.x - (float) (largeur * Math.cos(Math.PI / 2 + angle)), position.y + (float) (longueur * Math.sin(Math.PI / 2 + angle)));
        //Vec2f C = new Vec2f(position.x + (float) (largeur * Math.cos(Math.PI / 2 + angle)), position.y + (float) (longueur * Math.sin(Math.PI / 2 + angle)));
        //Vec2f D = new Vec2f(position.x + (float) (largeur * Math.cos(Math.PI / 2 + angle)), position.y - (float) (longueur * Math.sin(Math.PI / 2 + angle)));
        //p.add(A);
        //p.add(B);
        //p.add(C);
        //p.add(D);
        //p.add(A);

        //this.aretes = new ArrayList<>();
        //for (int i = 0; i < p.size() - 1; i++) {
          //  aretes.add(new Segment(p.get(i), p.get(i + 1)));
        //}
        recalc();
    }

    public LamesP(Vec2f position, float longueur, float largeur, float indice) {
        this(position, 0f, longueur, largeur, indice, Color.BLACK);
    }

    public Intersection intersect(Vec2f origin, Vec2f end) {
        for (Segment seg : aretes) {
            Vec2f testintersect = Utils.segmentIntersect(seg.pointA, seg.pointB, origin, end);
            if (testintersect != null) {
                return new Intersection(testintersect, seg.normale, indice);
            }
        }
        return null;
    }

    @Override
    public boolean isClickedOn(Vec2f click) {
        Vec2f a = getPosition();
        Vec2f b = getPosition().plus(new Vec2f(largeur, longueur));
        // TODO use angle to rotate bounding box
        return Utils.testBoundingBox(a, b, click, CLICKED_BIAS);
    }
    
    public void recalc(){
		ArrayList<Vec2f> p = new ArrayList<>();
        Vec2f A = new Vec2f(position.x - (float) (largeur * Math.cos(Math.PI / 2 + angle)), position.y - (float) (longueur * Math.sin(Math.PI / 2 + angle)));
        Vec2f B = new Vec2f(position.x - (float) (largeur * Math.cos(Math.PI / 2 + angle)), position.y + (float) (longueur * Math.sin(Math.PI / 2 + angle)));
        Vec2f C = new Vec2f(position.x + (float) (largeur * Math.cos(Math.PI / 2 + angle)), position.y + (float) (longueur * Math.sin(Math.PI / 2 + angle)));
        Vec2f D = new Vec2f(position.x + (float) (largeur * Math.cos(Math.PI / 2 + angle)), position.y - (float) (longueur * Math.sin(Math.PI / 2 + angle)));
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
        g.draw(new Rectangle2D.Float(-largeur / 2, -longueur / 2, largeur, longueur));
    }

    public float getLongueur() {
        return longueur;
    }

    public void setLongueur(float longueur) {
        this.longueur = longueur;
    }

    public float getLargeur() {
        return largeur;
    }

    public void setLargeur(float largeur) {
        this.largeur = largeur;
    }
}

