import java.awt.Color;
import java.awt.Graphics;

public abstract class Objet {
    protected Vec2f position;
    protected float indice;
    protected Color couleurObjet;
    protected float angleDeg = 0;
    
    
    public Objet (Vec2f pos, float n){
		this.position = pos;
		this.indice = n;
	}
	public Objet (Vec2f pos, float n, Color c){
		this.position = pos;
		this.indice = n;
		this.couleurObjet = c;
	}
	
	
	
}
