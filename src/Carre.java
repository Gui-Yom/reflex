import java.awt.Color;

public class Carre extends Objet {

    float length;

    public Carre(Vec2f pos, float length, float n, Color c) {
        super(pos, n, c);
        this.length = length;
    }

    @Override
    public Intersection intersect(Vec2f origin, Vec2f end) {
        return null;
    }
}
