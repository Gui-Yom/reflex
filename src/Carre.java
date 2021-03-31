import java.awt.Color;

public class Carre extends Objet {


    public Carre(Vec2f pos, float n, Color c) {
        super(pos, n, c);
    }

    @Override
    public Intersection intersect(Vec2f origin, Vec2f end) {
        return null;
    }
}
