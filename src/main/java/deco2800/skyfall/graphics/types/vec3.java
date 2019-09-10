package deco2800.skyfall.graphics.types;

/**
 * An implementation of vec3
 * Simpler than
 */
public class vec3 {
    public float x;
    public float y;
    public float z;

    /**
     * Component wise constructor
     * @param x x component
     * @param y y component
     * @param z z component
     */
    public vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Alternative constructor for single value initialisation
     * @param a value set to each component
     */
    public vec3(float a) {
        this.x = a;
        this.y = a;
        this.z = a;
    }

}
