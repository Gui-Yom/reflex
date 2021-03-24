import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public abstract class Objet {
    protected Vec2f position;
    protected float indice;
    protected Color couleurObjet;

    protected float angleHoriz = 0;
    protected ArrayList<Segment> Arretes;
    
    public Objet (Vec2f pos, float n){
		this.position = pos;
		this.indice = n;
	}
	public Objet (Vec2f pos, float n, Color c){
		this.position = pos;
		this.indice = n;
		this.couleurObjet = c;
	}
	
	//public abstract void dessine (Graphics g);
	


    public abstract Intersection intersect(Vec2f origin, Vec2f end);

    public static class Intersection {
        Vec2f point;
        Vec2f normal;
        float n;
        boolean canTransmit;

        public Intersection(Vec2f point, Vec2f normal, float n, boolean canTransmit) {
            this.point = point;
            this.normal = normal;
            this.n = n;
            this.canTransmit = canTransmit;
        }
    }
}
