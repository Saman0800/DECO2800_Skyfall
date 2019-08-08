package deco2800.skyfall.worlds;

import java.util.List;
import java.util.ArrayList;

public class WorldGenNode {

    // position
    private float x;
    private float y;

    // List of nodes whose polygon share a line with this nodes polygon
    private List<WorldGenNode> neighbours;

    // List of vertices for this polygon in the form [x, y]
    private List<float[]> vertices;

    // List of tiles that are within the polygon defined by this node
    private List<Tile> tiles;

    /**
     * Constructor for a WorldGenNode
     * @param x the x coordinate of the node
     * @param y the y coordinate of the node
     */
    public WorldGenNode(float x, float y) {
        this.x = x;
        this.y = y;
        this.neighbours = new ArrayList<>();
        this.vertices = new ArrayList<>();
        this.tiles = new ArrayList<>();
    }

    /**
     * Calculates the vertices of each node using Fortune's algorithm.
     * @param nodes The nodes to perform the algorithm with
     */
    public static void calculateVertices(List<WorldGenNode> nodes) {

    }

    /**
     * Calculates the approximate centroid of the polygon defined by this node.
     * The centroid is approximated as the average position of the vertices of
     * the polygon, rather than integrating to calculate the exact centroid
     * @return The approximate centroid of the polygon defined by this node
     */
    public float[] getCentroid() {
        float[] centroid = {0, 0};
        for (float[] vertex : this.vertices) {
            try {
                centroid[0] += vertex[0];
                centroid[1] += vertex[1];
            } catch (ArrayIndexOutOfBoundsException e) {
                // TODO handle this
            }
        }

        // Currently centroid contains the 'total' coordinates of the vertices.
        // Divide by the number of vertices to get the average coordinates
        centroid[0] /= this.vertices.size();
        centroid[1] /= this.vertices.size();
        return centroid;
    }

    /**
     * Adds another node to this node's list of adjacent nodes
     * @param other the adjacent node
     */
    public void assignNeighbour(WorldGenNode other) {
        this.neighbours.add(other);
    }

    /**
     * Uses a variation of Lloyd's Algorithm to make a list of nodes more evenly
     * spread apart.
     *
     * For info on what Lloyd's Algorithm is, see
     * <a href="https://en.wikipedia.org/wiki/Lloyd%27s_algorithm">the Wikipedia page</a>
     *
     * One key simplification from the method described on Wikipedia used here
     * is approximating the centroid as the average position of the vertices of
     * the polygon, rather than integrating to calculate the exact centroid
     * @param nodes The list of nodes to apply the Lloyd's Algorithm
     * @param iterations The number of times to apply the algorithm. Too few
     *                   iterations can result in the algorithm not evening out
     *                   the points enough, and too many iterations can
     *                   eliminate the randomness of the node placement
     */
    public static void lloydRelaxation(List<WorldGenNode> nodes, int iterations) {
        for (int i = 0; i < iterations; i++) {
            for (WorldGenNode node : nodes) {
                float[] centroid = node.getCentroid();
                node.setCoords(centroid);
            }
        }
    }

    /**
     * This method is O(n^2). If you can find a more efficient implementation
     * go ahead
     */
    public static void assignNeighbours(List<WorldGenNode> nodes) {
        // Compare each node with each other node
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = i + 1; j < nodes.size(); j++) {
                if (isAdjacent(nodes.get(i), nodes.get(j))) {
                    nodes.get(i).assignNeighbour(nodes.get(j));
                    nodes.get(j).assignNeighbour(nodes.get(i));
                }
            }
        }
    }

    /**
     * Determines if two nodes are adjacent. Two nodes are deemed to be adjacent
     * if they share a vertex
     * @param a The first node
     * @param b The second node
     * @return True if the nodes share a vertex, false otherwise
     */
    public static boolean isAdjacent(WorldGenNode a, WorldGenNode b) {
        // Avoid floating point rounding errors by checking for equality using
        // a tolerance. If there is a global/standard tolerance for the rest of
        // the project, replace this with that
        float tolerance = 0.0001f;

        // Compare each vertex of one with each vertex of the other
        for (float[] vertexA : a.getVertices()) {
            for (float[] vertexB : b.getVertices()) {
                // If the vertices are sufficiently close (ie the nodes share at
                // least one vertex
                try {
                    if (Math.abs(vertexA[0] - vertexB[0]) <= tolerance
                            && Math.abs(vertexA[1] - vertexB[1]) <= tolerance) {
                        return true;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    //TODO handle this
                }
            }
        }
        return false;
    }

    /**
     * Assigns each tile in the world to the nearest node
     * @param nodes
     * @param tiles
     */
    public static void assignTiles(List<WorldGenNode> nodes, List<Tile> tiles) {

    }

    /* ------------------------------------------------------------------------
     * 				GETTERS AND SETTERS BELOW THIS COMMENT.
     * ------------------------------------------------------------------------ */

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void setCoords(float[] coords) {
        this.x = coords[0];
        this.y = coords[1];
    }

    public List<WorldGenNode> getNeighbours() {
        return this.neighbours;
    }

    public List<float[]> getVertices() {
        return this.vertices;
    }

    public List<Tile> getTiles() {
        return this.tiles;
    }
}
