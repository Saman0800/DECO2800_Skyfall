package deco2800.skyfall.graphics.types;

import java.util.Objects;

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
     * @return Returns the x component of the vector
     */
    public float getX() {
        return this.x;
    }

    /**
     * @return Returns the y component of the vector
     */
    public float getY() {
        return this.y;
    }

    /**
     * returns a vec2 with each component clamped
     * @param min minimum value for each component
     * @param max minimum value for each component
     * @return must be caught, vec2 called against will not be changed
     */
    public vec2 getClampedComponents(float min, float max) {
        return new vec2(clamp(x, min, max), clamp(y, min, max));
    }

    /**
     * Tests equality of components
     * Will cast down vec3 and return true if z = 0
     * @return true iff obj is equivalent vec2,
     * or vec3 with equivalent x,y and z = 0
     */
    @Override
    public boolean equals(Object obj) {
        vec2 v;
        if (obj instanceof vec2) {
            v = (vec2)obj;
        }
        else if (obj instanceof vec3) {
            if (((vec3) obj).z != 0.0f) {
                return false;
            }
            v = new vec2(((vec3) obj).x , ((vec3) obj).y);
        }
        else {
            return false;
        }

        if (v.x == this.x && v.y == this.y) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}