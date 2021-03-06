package deco2800.skyfall.util;

import java.util.Objects;

public class Vector2 {
    private float x;
    private float y;

    /**
     * Constructor for a Vector2
     * @param x the x value for the vector
     * @param y the y value for the vector
     */
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for the x value of the vector
     * @return the x value
     */
    public float getX() {
        return x;
    }

    /**
     * Getter for the y value of the vector
     * @return the y value
     */
    public float getY() {
        return y;
    }

    /**
     * Setter for the x value
     * @param x the x value to be set
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Setter for the y value
     * @param y the y value to be set
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Adds the argument vector and returns the result
     * @param arg the vector to be added
     */
    public Vector2 add(Vector2 arg) { return new Vector2(this.x + arg.x, this.y + arg.y); }

    public Vector2 mul(float arg){
        return new Vector2(this.x*arg, this.y*arg);
    }

    /**
     * Subtracts the argument vector and returns the result
     * @param arg the vector to be subtracted
     */
    public Vector2 subtract(Vector2 arg) { return new Vector2(this.x - arg.x, this.y - arg.y); }

    /**
     * Equals Method returns true iff the two objects are equal 
     * based on their X and Y Value.
     * @return true if the two objects are the same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector2)) {
            return false;
        }
        Vector2 vector = (Vector2) obj;
        return vector.getX() == this.getX() && vector.getY() == this.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
