public class Segment {
	protected Vec2f pointA;
	protected Vec2f pointB;
	
	public Segment (Vec2f a, Vec2f b){
		this.pointA = a;
		this.pointB = b;
	}
	
	public Vec2f getNormale(){
		float dx = pointB.x - pointA.x;
		float dy = pointB.y - pointA.y;
		
		return new Vec2f(-dy, dx);
	}
}

