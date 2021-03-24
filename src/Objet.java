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
	
}
