package deco2800.skyfall.graphics.types;

import static deco2800.skyfall.util.MathUtil.clamp;

import java.util.Objects;

/**
 * An implementation of vec3 more convenient than array When referring to colour
 * x,y,z maps to r,g,b
 */
public class Vec3 {
    private float x;
    private float y;
    private float z;

    /**
     * Component wise constructor
     * 
     * @param x x component, might also be r for colours
     * @param y y component, might also be g for colours
     * @param z z component, might also be b for colours
     */
    public Vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Alternative constructor for single value initialisation
     * 
     * @param a value set to each component
     */
    public Vec3(float a) {
        this.x = a;
        this.y = a;
        this.z = a;
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
     * @return Returns the z component of the vector
     */
    public float getZ() {
        return this.z;
    }

    /**
     * returns a vec3 with each component clamped
     * 
     * @param min minimum value for each component
     * @param max minimum value for each component
     * @return must be caught, vec3 called against will not be changed
     */
    public Vec3 getClampedComponents(float min, float max) {
        return new Vec3(clamp(x, min, max), clamp(y, min, max), clamp(z, min, max));
    }

    /**
     * Tests equality of components Will cast up vec2 and return true if z = 0
     * 
     * @return true iff obj is equivalent vec3, or vec2 with equivalent x,y and z =
     *         0
     */
    @Override
    public boolean equals(Object obj) {
        Vec3 v;
        if (obj instanceof Vec3) {
            v = (Vec3) obj;
        } else if (obj instanceof Vec2) {
            if (this.z != 0.0f) {
                return false;
            }
            v = new Vec3(((Vec2) obj).getX(), ((Vec2) obj).getY(), 0.0f);
        } else {
            return false;
        }

        return v.x == this.x && v.y == this.y && v.z == this.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
