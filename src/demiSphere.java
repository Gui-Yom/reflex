import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class demiSphere extends Objet {
	
	private float R;
	
	public demiSphere (	Vec2f pos, float n, float r){
		super(pos, n);
		R = r;
		
		Arretes = new ArrayList<Segment>();
		Vec2f a = new Vec2f(position.x, position.y-R);
		Vec2f b = new Vec2f(position.x, position.y+R);
		Segment diam = new Segment(a, b);
		Arretes.add(diam);
		ArrayList<Vec2f>pAs = pArc(position, R);
		for (int i=0; i<pAs.size()-1; i++){
			Arretes.add(new Segment(pAs.get(i), pAs.get(i+1)));
		}
	}
		
		
		//Segment diam = new Segment(
	
	
	public ArrayList<Vec2f> pArc (Vec2f centre, float r){
		ArrayList<Vec2f> pA = new ArrayList<Vec2f>();
		for (int i=0; i<1001; i++){
			float alpha = (float) (i*Math.PI/1000 -Math.PI/2);
			float X = (float) (r*Math.cos(alpha));
			float Y = (float) (r*Math.sin(alpha));
			Vec2f p = new Vec2f(X, Y);
			pA.add(p);
		}
		return pA;
	}
			
		
	
}

