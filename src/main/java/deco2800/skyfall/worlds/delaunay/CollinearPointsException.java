package deco2800.skyfall.worlds.delaunay;

/**
 * An exception thrown when trying to find the circumcentre of three collinear
 * points
 */
public class CollinearPointsException extends WorldGenException {

    public CollinearPointsException() {
    }

    public CollinearPointsException(String s) {
        super(s);
    }

}
