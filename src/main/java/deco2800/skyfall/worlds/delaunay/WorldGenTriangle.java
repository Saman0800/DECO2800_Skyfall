package deco2800.skyfall.worlds.delaunay;

import java.util.Arrays;

/**
 * A triangle of WorldGenNodes.
 * Code taken from https://github.com/jdiemke/delaunay-triangulator/blob/master/library/src/main/java/io/github/jdiemke/triangulation/Triangle2D.java
 *
 * @author Johannes Diemke
 */
public class WorldGenTriangle {

    public WorldGenNode a;
    public WorldGenNode b;
    public WorldGenNode c;

    /**
     * Constructor of the 2D triangle class used to create a new triangle
     * instance from three 2D vectors describing the triangle's vertices.
     *
     * @param a The first vertex of the triangle
     * @param b The second vertex of the triangle
     * @param c The third vertex of the triangle
     */
    public WorldGenTriangle(WorldGenNode a, WorldGenNode b, WorldGenNode c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Tests if a 2D point lies inside this 2D triangle. See Real-Time Collision
     * Detection, chap. 5, p. 206.
     *
     * @param point The point to be tested
     * @return Returns true iff the point lies inside this 2D triangle
     */
    public boolean contains(WorldGenNode point) {
        double pab = point.subtract(a).crossProduct(b.subtract(a));
        double pbc = point.subtract(b).crossProduct(c.subtract(b));

        if (!hasSameSign(pab, pbc)) {
            return false;
        }

        double pca = point.subtract(c).crossProduct(a.subtract(c));

        if (!hasSameSign(pab, pca)) {
            return false;
        }

        return true;
    }

    /**
     * Tests if a given point lies in the circumcircle of this triangle. Let the
     * triangle ABC appear in counterclockwise (CCW) order. Then when det &gt;
     * 0, the point lies inside the circumcircle through the three points a, b
     * and c. If instead det &lt; 0, the point lies outside the circumcircle.
     * When det = 0, the four points are cocircular. If the triangle is oriented
     * clockwise (CW) the result is reversed. See Real-Time Collision Detection,
     * chap. 3, p. 34.
     *
     * @param point The point to be tested
     * @return Returns true iff the point lies inside the circumcircle through
     *         the three points a, b, and c of the triangle
     */
    public boolean isPointInCircumcircle(WorldGenNode point) {
        double a11 = a.getX() - point.getX();
        double a21 = b.getX() - point.getX();
        double a31 = c.getX() - point.getX();

        double a12 = a.getY() - point.getY();
        double a22 = b.getY() - point.getY();
        double a32 = c.getY() - point.getY();

        double a13 = (a.getX() - point.getX()) * (a.getX() - point.getX()) + (a.getY() - point.getY()) * (a.getY() - point.getY());
        double a23 = (b.getX() - point.getX()) * (b.getX() - point.getX()) + (b.getY() - point.getY()) * (b.getY() - point.getY());
        double a33 = (c.getX() - point.getX()) * (c.getX() - point.getX()) + (c.getY() - point.getY()) * (c.getY() - point.getY());

        double det = a11 * a22 * a33 + a12 * a23 * a31 + a13 * a21 * a32 - a13 * a22 * a31 - a12 * a21 * a33
                - a11 * a23 * a32;

        if (isOrientedCCW()) {
            return det > 0.0d;
        }

        return det < 0.0d;
    }

    /**
     * Test if this triangle is oriented counterclockwise (CCW). Let A, B and C
     * be three 2D points. If det &gt; 0, C lies to the left of the directed
     * line AB. Equivalently the triangle ABC is oriented counterclockwise. When
     * det &lt; 0, C lies to the right of the directed line AB, and the triangle
     * ABC is oriented clockwise. When det = 0, the three points are colinear.
     * See Real-Time Collision Detection, chap. 3, p. 32
     *
     * @return Returns true iff the triangle ABC is oriented counterclockwise
     *         (CCW)
     */
    public boolean isOrientedCCW() {
        double a11 = a.getX() - c.getX();
        double a21 = b.getX() - c.getX();

        double a12 = a.getY() - c.getY();
        double a22 = b.getY() - c.getY();

        double det = a11 * a22 - a12 * a21;

        return det > 0.0d;
    }

    /**
     * Returns true if this triangle contains the given edge.
     *
     * @param edge The edge to be tested
     * @return Returns true if this triangle contains the edge
     */
    public boolean isNeighbour(WorldGenEdge edge) {
        return (a == edge.a || b == edge.a || c == edge.a) && (a == edge.b || b == edge.b || c == edge.b);
    }

    /**
     * Returns the vertex of this triangle that is not part of the given edge.
     *
     * @param edge The edge
     * @return The vertex of this triangle that is not part of the edge
     */
    public WorldGenNode getNoneEdgeVertex(WorldGenEdge edge) {
        if (a != edge.a && a != edge.b) {
            return a;
        } else if (b != edge.a && b != edge.b) {
            return b;
        } else if (c != edge.a && c != edge.b) {
            return c;
        }

        return null;
    }

    /**
     * Returns true if the given vertex is one of the vertices describing this
     * triangle.
     *
     * @param vertex The vertex to be tested
     * @return Returns true if the Vertex is one of the vertices describing this
     *         triangle
     */
    public boolean hasVertex(WorldGenNode vertex) {
        if (a == vertex || b == vertex || c == vertex) {
            return true;
        }

        return false;
    }

    /**
     * Returns an EdgeDistancePack containing the edge and its distance nearest
     * to the specified point.
     *
     * @param point The point the nearest edge is queried for
     * @return The edge of this triangle that is nearest to the specified point
     */
    public EdgeDistancePack findNearestEdge(WorldGenNode point) {
        EdgeDistancePack[] edges = new EdgeDistancePack[3];

        edges[0] = new EdgeDistancePack(new WorldGenEdge(a, b),
                computeClosestPoint(new WorldGenEdge(a, b), point).subtract(point).magnitude());
        edges[1] = new EdgeDistancePack(new WorldGenEdge(b, c),
                computeClosestPoint(new WorldGenEdge(b, c), point).subtract(point).magnitude());
        edges[2] = new EdgeDistancePack(new WorldGenEdge(c, a),
                computeClosestPoint(new WorldGenEdge(c, a), point).subtract(point).magnitude());
        Arrays.sort(edges);
        return edges[0];
    }

    /**
     * Computes the closest point on the given edge to the specified point.
     *
     * @param edge
     *            The edge on which we search the closest point to the specified
     *            point
     * @param point
     *            The point to which we search the closest point on the edge
     * @return The closest point on the given edge to the specified point
     */
    private WorldGenNode computeClosestPoint(WorldGenEdge edge, WorldGenNode point) {
        WorldGenNode ab = edge.b.subtract(edge.a);
        double t = point.subtract(edge.a).dotProduct(ab) / ab.dotProduct(ab);

        if (t < 0.0d) {
            t = 0.0d;
        } else if (t > 1.0d) {
            t = 1.0d;
        }

        return edge.a.add(ab.scalarMultiply(t));
    }

    /**
     * Tests if the two arguments have the same sign.
     *
     * @param a
     *            The first floating point argument
     * @param b
     *            The second floating point argument
     * @return Returns true iff both arguments have the same sign
     */
    private boolean hasSameSign(double a, double b) {
        return Math.signum(a) == Math.signum(b);
    }

    @Override
    public String toString() {
        return "Triangle2D[" + a + ", " + b + ", " + c + "]";
    }

    /**
     * Calculates the coordinates circumcentre of the triangle
     * This method was not taken from the source of the rest of this class
     *
     * @author Daniel Nathan
     * @return the coordinates of the circumcentre
     */
    public double[] circumcentre() throws CollinearPointsException {
        double ax = a.getX();
        double ay = a.getY();
        double bx = b.getX();
        double by = b.getY();
        double cx = c.getX();
        double cy = c.getY();
        // Check if the points are colinear
        if ((by - ay) / (bx - ax) == (cy - ay) / (cx - ax)) {
            throw new CollinearPointsException();
        }
        double[] coords = {0, 0};
        coords[0] = ((ay - by) * (by - cy) * (cy - ay) + (ay * (cx * cx - bx * bx) + by * (ax * ax - cx * cx) + cy * (bx * bx - ax * ax)))
                / (2*(ax * (by - cy) + bx * (cy - ay) + cx * (ay - by)));
        coords[1] = (ay + by) / 2 + (bx - ax) / (ay - by) * (coords[0] - (ax + bx) / 2);
        return coords;
    }

}