package deco2800.skyfall.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Rectangular shaped box used for collision detection of sprites/entities in
 * the world. Defined by a corner point in X,Y and extends in x (xLength) and y
 * (yLength).
 *
 * @author Kaleb Day adapted from @leggy (Box3D)
 *
 */
public class Collider {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(Collider.class);

    private float x;
    private float y;

    private float xLength;
    private float yLength;


    /**
     * Constructs a new Collider with the given corner point and dimensions.
     *
     * @param x
     *            The corner point x-coordinate.
     * @param y
     *            The corner point y-coordinate.
     * @param xLength
     *            The xLength (in x).
     * @param yLength
     *            The yLength (in y).
     */
    public Collider(float x, float y, float xLength, float yLength) {
        this.x = x;
        this.y = y;
        this.xLength = xLength;
        this.yLength = yLength;
    }

    /**
     * Constructs a new Collider based on the given Collider.
     *
     * @param collider
     *            The collider to copy.
     */
    public Collider(Collider collider) {
        this.x = collider.x;
        this.y = collider.y;
        this.xLength = collider.xLength;
        this.yLength = collider.yLength;
    }

    /**
     * Returns the x coordinate.
     *
     * @return Returns the x coordinate.
     */
    public float getX() {
        return x;
    }

    /**
     * Sets the x coordinate.
     *
     * @param x
     *            The new x coordinate.
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Returns the y coordinate.
     *
     * @return Returns the y coordinate.
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the y coordinate.
     *
     * @param y
     *            The new y coordinate.
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Returns the xLength (in x).
     *
     * @return Returns the xLength.
     */
    public float getXLength() {
        return xLength;
    }

    /**
     * Returns the yLength (in y).
     *
     * @return Returns the yLength.
     */
    public float getYLength() {
        return yLength;
    }

    /**
     * Checks for collisions
     * @param collider the Collider to check for collisions
     * @return true if there is an overlap, false if there isn't
     */
    public boolean overlaps(Collider collider) {

        // x smaller
        if (x + xLength < collider.x) {
            return false;
        }

        // x larger
        if (x > collider.x + collider.xLength) {
            return false;
        }

        // y smaller
        if (y + yLength < collider.y) {
            return false;
        }

        // y larger
        return !(y > collider.y + collider.yLength);
    }

    /**
     * gets the diagonal distance of the Collider
     * @param o the Collider
     * @return the diagonal distance of the Collider
     */
    public float distance(Collider o) {
        return (float)(Math.sqrt((o.x - this.x) * (o.x - this.x) + (o.y - this.y) * (o.y - this.y)));
    }

}
