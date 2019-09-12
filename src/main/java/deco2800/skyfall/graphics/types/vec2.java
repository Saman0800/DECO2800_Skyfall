package deco2800.skyfall.graphics.types;

import static deco2800.skyfall.util.MathUtil.clamp;

/**
 * An implementation of vec2
 * more convenient than array.
 */
public class vec2 {
    public float x;
    public float y;

    /**
     * Component wise constructor
     * @param x x component
     * @param y y component
     */
    public vec2(float x, float y) {
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

    /**
     * returns a vec2 with each component clamped
     * @param min minimum value for each component
     * @param max minimum value for each component
     * @return must be caught, vec3 called against will not be changed
     */
    public vec2 getClampedComponents(float min, float max) {
        return new vec2( clamp(x, min, max), clamp(y, min, max) );
    }

}