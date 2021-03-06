package deco2800.skyfall.gui;

import java.io.Serializable;

/**
 * Class to store positions for inventory items.
 * Uses builder prototype so can chain calls
 * i.e. Tuple.setX(1).setY(2)
 */
public class Tuple implements Serializable {
    private int x;
    private int y;

    public Tuple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Tuple setX(int x) {
        this.x = x;
        return this;
    }

    public Tuple setY(int y) {
        this.y = y;
        return this;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", getX(), getY());
    }

    @Override
    public boolean equals(Object obj) {
       if (obj instanceof Tuple) {
           Tuple t = (Tuple) obj;
           return t.getX() == getX() && t.getY() == getY();
       }
       return false;
    }

    @Override
    public int hashCode() {
        return x * 13 + y;
    }

}
