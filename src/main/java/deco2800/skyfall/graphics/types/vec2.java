package deco2800.skyfall.graphics.types;

/**
 * An implementation of vec2
 * Simpler than
 */
public class vec2 {
    public float x;
    public float y;

    /**
     * Component wise constructor
     * @param x x component
     * @param y y component
     */
    public vec2(float x, float y, float z) {
        this.x = x;
        this.y = y;
    }

    /**
     * Alternative constructor for single value initialisation
     * @param a value set to each component
     */
    public vec2(float a) {
        this.x = a;
        this.y = a;
    }

}