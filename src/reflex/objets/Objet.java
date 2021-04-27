package reflex.objets;

import reflex.Constants;
import reflex.Drawable;
import reflex.Intersection;
import reflex.Vec2f;

import java.awt.Color;


public abstract class Objet implements Drawable {
    protected static final float clickedBias = 6f;
    protected Vec2f position;
    protected float indice;
    protected Color color;
    protected float angle;
    /**
     * true if the current object is selected
     */
    protected boolean selected = false;

    public Objet(Vec2f position, float angle, float indice, Color color) {
        this.position = position;
        this.indice = indice;
        this.color = color;
        this.angle = angle;
    }

    public Objet(float indice, Color color) {
        this(Vec2f.NULL, 0.0f, indice, color);
    }

    public Objet(float indice) {
        this(indice, Color.BLACK);
    }

    public Objet() {
        this(Constants.REFRAC_VACUUM, Color.BLACK);
    }

    /**
     * Let the object recalc some values it has after updating a value.
     */
    public void recalc() {
        // Default no op impl
    }

    /**
     * @param origin the origin point of the ray
     * @param end    the end point of the ray
     * @return a non null value to indicate the ray has intersected with this object
     */
    public abstract Intersection intersect(Vec2f origin, Vec2f end);

    public abstract boolean isClickedOn(Vec2f click);

    public Vec2f getPosition() {
        return position;
    }

    public void setPosition(Vec2f position) {
        this.position = position;
    }

    public float getIndice() {
        return indice;
    }

    public void setIndice(float indice) {
        this.indice = indice;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float ang) {
        this.angle = ang;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
