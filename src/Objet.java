import java.awt.Color;
import java.awt.Graphics;

public abstract class Objet {
    protected Vec2f position;
    protected float indice;
    protected Color couleur;
    protected Vec2f normale;
    
    
    public Objet (Vec2f pos, float n, Color c){
		this.position = pos;
		this.indice = n;
		this.couleur = c;
	}
}
