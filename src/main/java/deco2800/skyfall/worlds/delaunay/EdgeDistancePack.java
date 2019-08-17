package deco2800.skyfall.worlds.delaunay;

/**
 * A class that contains a WorldGenEdge, and a distance to that edge from a
 * point
 * Code is taken from https://github.com/jdiemke/delaunay-triangulator/blob/master/library/src/main/java/io/github/jdiemke/triangulation/EdgeDistancePack.java
 *
 * @author Johannes Diemke
 */
public class EdgeDistancePack implements Comparable<EdgeDistancePack> {

    public WorldGenEdge edge;
    public double distance;

    /**
     * Constructor of the edge distance pack class used to create a new edge
     * distance pack instance from a 2D edge and a scalar value describing a
     * distance.
     *
     * @param edge
     *            The edge
     * @param distance
     *            The distance of the edge to some point
     */
    public EdgeDistancePack(WorldGenEdge edge, double distance) {
        this.edge = edge;
        this.distance = distance;
    }

    @Override
    public int compareTo(EdgeDistancePack o) {
        return Double.compare(this.distance, o.distance);
    }

}