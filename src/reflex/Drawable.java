package reflex;

import java.awt.Graphics2D;

public interface Drawable {
    /**
     * Called when the object should be drawn
     *
     * @param g the Graphics2D instance to draw the object with
     */
    void draw(Graphics2D g);
}
