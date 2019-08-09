package deco2800.skyfall.worlds;

import java.util.List;
import java.util.ArrayList;

/**
 * A class used in the world generation procedure to help the world and biomes
 * have a natural looking shape. To see how they are being used, this class is
 * heavily based on
 * <a href="http://www-cs-students.stanford.edu/~amitp/game-programming/polygon-map-generation/?fbclid=IwAR30I7ILTznH6YzYYqZfjIE3vcqPsed85ta9bohPZWi74SfWMwWpD8AVddQ#source">This</a>
 */
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

    public int comparePosition(WorldGenNode other) {
        if (other == null) {
            //TODO throw exception
        }
        if (this.getY() == other.getY()) {
            if (this.getX() == other.getX()) {
                return 0;
            }
            if (this.getX() < other.getX()) {
                return -1;
            }
            return 1;
        }
        if (this.getY() < other.getY()) {
            return -1;
        }
        return 1;
    }

    /**
     * Quicksort the list of nodes. Quicksort method taken from
     * <a href="https://www.geeksforgeeks.org/quick-sort/">Geeks for Geeks</a>
     * @param nodes
     */
    public static void sortNodes(List<WorldGenNode> nodes) {
        quickSort(nodes, 0, nodes.size() - 1);
    }

    /*
     * Helper method for sortNodes
     */
    private static void quickSort(List<WorldGenNode> nodes, int start, int end) {
        if (start < end) {
            int pivotIndex = start - 1;
            WorldGenNode pivot = nodes.get(end);
            for (int i = start; i < end; i++) {
                if (nodes.get(i).comparePosition(pivot) <= 0) {
                    pivotIndex++;
                    WorldGenNode temp = nodes.get(pivotIndex);
                    nodes.set(pivotIndex, nodes.get(i));
                    nodes.set(i, temp);
                }
            }
            pivotIndex++;
            WorldGenNode temp = nodes.get(end);
            nodes.set(end, nodes.get(pivotIndex));
            nodes.set(pivotIndex, temp);

            quickSort(nodes, start, pivotIndex - 1);
            quickSort(nodes, pivotIndex + 1, end);
        }
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
     * Associates a tile with this node
     * @param tile the tile in question
     */
    public void addTile(Tile tile) {
        this.tiles.add(tile);
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
                // TODO reapply Fortune's algorithm
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
        // TODO check if osrt is necessary (may have already been sorted in
        // TODO fortune's algorithm)
        // Ensure nodes are stored in order of Y value
        sortNodes(nodes);
        for (Tile tile : tiles) {
            // TODO see if this needs to be transformed according to dimensions of hexagon
            // Y coordinate of the tile
            float tileY = tile.getCoordinates().getRow();
            // Find the index of the node with the node with one of the nearest
            // Y values (note, if there is no node with the exact Y value, it)
            // Can choose the node on either side, not the strictly closest one
            int nearestIndex = binarySearch(tileY, nodes, 0, nodes.size() - 1);
            boolean lowerLimitFound = false;
            boolean upperLimitFound = false;
            // Store the minimum distance to a node, and the index of that node
            float minDistance = nodes.get(nearestIndex).distanceToTile(tile);
            int minDistanceIndex = nearestIndex;
            int iterations = 1;
            // Starting from the initial index, this loop checks the 1st node on
            // either side, then the 2nd node on either side, continuing
            // outwards (kept track of by iterations).
            while (!(upperLimitFound && lowerLimitFound)) {
                int lower = nearestIndex - iterations;
                int upper = nearestIndex + iterations;
                // Stop the algorithm from checking off the end of the list
                if (lower < 0) {
                    lowerLimitFound = true;
                }
                if (upper > nodes.size() - 1) {
                    upperLimitFound = true;
                }

                if (!lowerLimitFound) {
                    float distance = nodes.get(lower).distanceToTile(tile);
                    // Update the closest node if necessary
                    if (distance < minDistance) {
                        minDistance = distance;
                        minDistanceIndex = lower;
                    }
                    // As distance to a node is necessarily >= the difference in
                    // y value, if the difference in y value is greater than the
                    // smallest distance to a node, all future nodes in that
                    // direction will be further away
                    if (nodes.get(lower).yDistanceToTile(tile) > minDistance) {
                        lowerLimitFound = true;
                    }
                }
                if (!upperLimitFound) {
                    float distance = nodes.get(upper).distanceToTile(tile);
                    if (distance < minDistance) {
                        minDistance = distance;
                        minDistanceIndex = upper;
                    }
                    if (nodes.get(upper).yDistanceToTile(tile) > minDistance) {
                        upperLimitFound = true;
                    }
                }
                iterations++;
            }
            // Assign tile to the node
            nodes.get(minDistanceIndex).addTile(tile);
        }
    }

    public float distanceToTile(Tile tile) {
        // TODO see if this needs to be transformed according to dimensions of hexagon
        float[] tileCoords = {tile.getCoordinates().getCol(), tile.getCoordinates().getRow()};
        return (float) Math.sqrt(Math.pow(this.getX() - tileCoords[0], 2) + Math.pow(this.getY() - tileCoords[1], 2));
    }

    public float yDistanceToTile(Tile tile) {
        return Math.abs(this.getY() - tile.getCoordinates().getRow());
    }

    private static int binarySearch(float toFind, List<WorldGenNode> nodes, int start, int end) {
        float tolerance = 0.0001f;
        int middle = (end + start) / 2;
        float middleValue = nodes.get(middle).getY();
        if (middleValue == toFind || start >= end) {
            return middle;
        }
        if (middleValue < toFind) {
            return binarySearch(toFind, nodes, middle + 1, end);
        }
        return binarySearch(toFind, nodes, start, middle - 1);
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
