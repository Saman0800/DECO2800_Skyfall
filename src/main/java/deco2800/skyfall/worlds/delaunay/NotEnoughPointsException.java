package deco2800.skyfall.worlds.delaunay;

/**
 * Exception thrown when attempting to apply Delaunay Triangulation with less
 * than 3 points
 * Code adapted from https://github.com/jdiemke/delaunay-triangulator/blob/master/library/src/main/java/io/github/jdiemke/triangulation/NotEnoughPointsException.java
 * @author Johannes Diemke
 */
public class NotEnoughPointsException extends WorldGenException {

    private static final long serialVersionUID = 7061712854155625067L;

    public NotEnoughPointsException() {
    }

    public NotEnoughPointsException(String s) {
        super(s);
    }

}