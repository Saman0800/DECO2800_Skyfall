package deco2800.skyfall.graphics.types;

import static deco2800.skyfall.util.MathUtil.clamp;

/**
 * An implementation of vec3
 * more convenient than array
 * When referring to colour x,y,z maps to r,g,b
 */
public class vec3 {
    public float x;
    public float y;
    public float z;

    /**
     * Component wise constructor
     * @param x x component, might also be r for colours
     * @param y y component, might also be g for colours
     * @param z z component, might also be b for colours
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

    /**
     * returns a vec3 with each component clamped
     * @param min minimum value for each component
     * @param max minimum value for each component
     * @return must be caught, vec3 called against will not be changed
     */
    public vec3 getClampedComponents(float min, float max) {
        return new vec3( clamp(x, min, max), clamp(y, min, max), clamp(z, min, max) );
    }
}
