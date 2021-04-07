import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.geom.*;

public class demiSphere extends Objet {
	
	private float R;
	
	public demiSphere (	Vec2f pos, float n, float r){
		super(pos, n);
		R = r;
		
		Aretes = new ArrayList<Segment>();
		Vec2f a = new Vec2f(position.x - (float) (R*Math.cos(Math.PI/2+ angleHoriz)), position.y-(float) (R*Math.sin(Math.PI/2+ angleHoriz)));
		Vec2f b = new Vec2f(position.x + (float) (R*Math.cos(Math.PI/2+ angleHoriz)), position.y+ (float) (R*Math.sin(Math.PI/2+ angleHoriz)));
		Segment diam = new Segment(a, b);
		Aretes.add(diam);
		ArrayList<Vec2f>pAs = pArc(position, R);
		pAs.add(0, b);
		pAs.add(a);
		for (int i=0; i<pAs.size()-1; i++){
			Aretes.add(new Segment(pAs.get(i), pAs.get(i+1)));
		}
	}
		
		
		//Segment diam = new Segment(
	
	
	public ArrayList<Vec2f> pArc (Vec2f centre, float r){
		ArrayList<Vec2f> pA = new ArrayList<Vec2f>();
		for (int i=0; i<1001; i++){
			float alpha = (float) (i*Math.PI/1000 -Math.PI/2) + angleHoriz;
			float X = (float) (r*Math.cos(alpha));
			float Y = (float) (r*Math.sin(alpha));
			Vec2f p = new Vec2f(X, Y);
			pA.add(p);
		}
		return pA;
	}
	
	public Intersection intersect(Vec2f origin, Vec2f end){
		for (Segment seg : Aretes){
			Vec2f testintersect = Utils.segmentIntersect(seg.pointA, seg.pointB, origin, end);
			if (testintersect!= null){
				return new Intersection(testintersect, seg.normale, indice, true);
			}
		}
		return null;		
	}
			
	public void dessine (Graphics g){
		g.setColor(couleurObjet);
		AffineTransform afftran = new AffineTransform();
		afftran.rotate(angleHoriz);
		g.setTransform(afftran);
		g.draw(new Arc2D.Float(position.x, position.y, R, R, 270, Math.PI*R, CHORD));
	}
}

