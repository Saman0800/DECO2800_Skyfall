package deco2800.skyfall.worlds.generation.delaunay;

import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.generation.DeadEndGenerationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * A class to represent an edge of a polygon in a Voronoi Diagram
 */
public class VoronoiEdge {

    // The coordinates of the two endpoints of the edge
    private double[] pointA;
    private double[] pointB;

    // The edges that share the respective vertex
    private List<VoronoiEdge> pointANeighbours;
    private List<VoronoiEdge> pointBNeighbours;

    // The nodes on either side of the edge (ie they share 2 vertices with the
    // line)
    private List<WorldGenNode> edgeNodes;

    // The nodes on the end points of the line (ie they share 1 vertex with the
    // line)
    private List<WorldGenNode> endNodes;

    // The tiles that this edge passes through
    private List<Tile> tiles;

    /**
     * Constructor for a VoronoiEdge
     *
     * @param pointA The first vertex of the edge
     * @param pointB The second vertex of the edge
     */
    public VoronoiEdge(double[] pointA, double[] pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.pointANeighbours = new ArrayList<>();
        this.pointBNeighbours = new ArrayList<>();
        this.edgeNodes = new ArrayList<>();
        this.endNodes = new ArrayList<>();
        this.tiles = new ArrayList<>();
    }

    /**
     * If an edge node for the edge appears in all sub-lists of previousNodes, return false
     */
    private static boolean validNeighbour (List<List<WorldGenNode>> previousNodes, VoronoiEdge edge) {
        // TODO make 2 a parameter
        if (previousNodes.size() < 2) {
            return true;
        }
        for (WorldGenNode node : edge.edgeNodes) {
            boolean legalNode = false;
            for (List<WorldGenNode> nodes : previousNodes) {
                if (!nodes.contains(node)) {
                    legalNode = true;
                    break;
                }
            }
            if (!legalNode) {
                return false;
            }
        }
        return true;
    }

    // TODO see if this method should throw a different exception
    public static List<VoronoiEdge> generatePath(List<VoronoiEdge> edges, VoronoiEdge startEdge, double[] startVertex, Random random, int maxTimesOnNode)
            throws DeadEndGenerationException {
        // TODO get initial edge
        VoronoiEdge edge = startEdge;
        List<VoronoiEdge> path = new ArrayList<>();
        List<List<WorldGenNode>> previousNodes = new ArrayList<>(maxTimesOnNode);
        double[] vertex = startVertex;

        path.add(edge);
        previousNodes.add(edge.getEdgeNodes());

        while (true) {
            double[] other = edge.otherVertex(vertex);
            boolean validNeighbour = false;
            List<VoronoiEdge> tempEdges = new ArrayList<>(edge.getVertexSharingEdges(edge.otherVertex(vertex)));
            while (!validNeighbour) {
                if (tempEdges.size() == 0) {
                    throw new DeadEndGenerationException();
                }
                VoronoiEdge neighbour = tempEdges.get(random.nextInt(tempEdges.size()));
                validNeighbour = validNeighbour(previousNodes, neighbour);
                if (!validNeighbour) {
                    // Don't check the same edge again
                    tempEdges.remove(neighbour);
                    continue;
                }

                path.add(neighbour);

                if (path.size() == 1) {
                    boolean endOfPath = true;
                    for (WorldGenNode node : neighbour.endNodes) {
                        if (node == null) {
                            return path;
                        }
                        String biomeName = node.getTiles().get(0).getBiome().getBiomeName();
                        // If the path is already at the ocean
                        if (biomeName.equals("ocean")) {
                            return path;
                        }

                        // If either end node isn't a river the path has not ended
                        if (!biomeName.equals("lake")) {
                            endOfPath = false;
                        }
                    }
                    // If both of the end nodes are lakes, the path is already complete
                    if (endOfPath) {
                        return path;
                    }
                } else {
                    for (WorldGenNode node : neighbour.endNodes) {
                        if (node == null) {
                            return path;
                        }
                        String biomeName = node.getTiles().get(0).getBiome().getBiomeName();
                        // If the new edge ends with the ocean or a lake
                        if (biomeName.equals("ocean") || biomeName.equals("lake")) {
                            return path;
                        }
                    }
                    for (WorldGenNode node : neighbour.edgeNodes) {
                        if (node == null) {
                            return path;
                        }
                        String biomeName = node.getTiles().get(0).getBiome().getBiomeName();
                        // If the new edge ends with the ocean or a lake
                        if (biomeName.equals("ocean") || biomeName.equals("lake")) {
                            return path;
                        }
                    }
                }

                validNeighbour = true;
                // Move to the next edge
                vertex = edge.otherVertex(vertex);
                edge = neighbour;

                // Update the list of previous nodes
                if (previousNodes.size() == maxTimesOnNode) {
                    previousNodes.remove(0);
                }
                previousNodes.add(edge.getEdgeNodes());
            }
        }
    }

    public static void assignTiles(List<VoronoiEdge> edges, List<Tile> tiles) {
        // A tile is considered close to a line if the square of the distance
        // between them is 1/3.
        final double MIN_SQUARE_DISTANCE = 1f / 3f;
        final double MIN_DISTANCE = Math.sqrt(MIN_SQUARE_DISTANCE);
        // TODO make this more efficient (e.g. by sorting tiles first)
        for (Tile tile : tiles) {
            for (VoronoiEdge edge : edges) {
                // If the edge is horizontal
                if (edge.getA()[0] == edge.getB()[0]) {
                    // If the
                    if (Math.abs(tile.getCol() - edge.getA()[0]) < MIN_DISTANCE
                            && (Math.abs(tile.getRow() - edge.getA()[1]) < MIN_DISTANCE
                            || Math.abs(tile.getRow() - edge.getB()[1]) < MIN_DISTANCE)) {
                        edge.addTile(tile);
                    }

                } else {
                    double gradient = (edge.getA()[1] - edge.getB()[1]) / (edge.getA()[0] - edge.getB()[0]);
                    // A quantity used to calculate the distance
                    double distanceNumerator = -1 * gradient * tile.getCol() + tile.getRow()
                            + gradient * edge.getB()[0] - edge.getB()[1];
                    // Get the square distance
                    double squareDistance = distanceNumerator * distanceNumerator / (gradient * gradient + 1);
                    if (squareDistance <= MIN_SQUARE_DISTANCE) {
                        //edge.addTile(tile);
                        double dxA = edge.getA()[0] - tile.getCol();
                        double dxB = edge.getB()[0] - tile.getCol();
                        double dyA = edge.getA()[1] - tile.getRow();
                        double dyB = edge.getB()[1] - tile.getRow();
                        if (dxA * dxA + dyA * dyA < edge.getSquareOfLength() + MIN_SQUARE_DISTANCE
                                && dxB * dxB + dyB * dyB < edge.getSquareOfLength() + MIN_SQUARE_DISTANCE) {
                            edge.addTile(tile);
                        }
                    }
                }
            }
        }
    }

    /**
     * Gets the edged that are adjacent to this edge via the given vertex
     * @param vertex
     * @return
     */
    public List<VoronoiEdge> getVertexSharingEdges(double[] vertex) {
        if (Arrays.equals(vertex, this.pointA)) {
            return this.pointANeighbours;
        }
        if (Arrays.equals(vertex, this.pointB)) {
            return this.pointBNeighbours;
        }
        return null;
    }

    public static void assignNeighbours(List<VoronoiEdge> edges) {
        // TODO find a way to do this while assigning node neighbours
        for (int i = 0; i < edges.size(); i++) {

            // Choose an arbitrary edge node
            WorldGenNode node = edges.get(i).getEdgeNodes().get(0);
            // Assign the end nodes of the edge
            for (WorldGenNode neighbour : node.getNeighbours()) {
                // Don't add the other edge node as an end node
                if (edges.get(i).getEdgeNodes().contains(neighbour)) {
                    continue;
                }

                for (double[] nodeVertex : neighbour.getVertices()) {
                    if (Arrays.equals(edges.get(i).getA(), nodeVertex) || Arrays.equals(edges.get(i).getB(), nodeVertex)) {
                        edges.get(i).addEndNode(neighbour);
                        break;
                    }
                }
            }

            for (int j = i + 1; j < edges.size(); j++) {
                VoronoiEdge edgeA = edges.get(i);
                VoronoiEdge edgeB = edges.get(j);
                if (Arrays.equals(edgeA.getA(), edgeB.getA())) {
                    edgeA.addANeighbour(edgeB);
                    edgeB.addANeighbour(edgeA);
                    continue;
                }
                if (Arrays.equals(edgeA.getA(), edgeB.getB())) {
                    edgeA.addANeighbour(edgeB);
                    edgeB.addBNeighbour(edgeA);
                    continue;
                }
                if (Arrays.equals(edgeA.getB(), edgeB.getA())) {
                    edgeA.addBNeighbour(edgeB);
                    edgeB.addANeighbour(edgeA);
                    continue;
                }
                if (Arrays.equals(edgeA.getB(), edgeB.getB())) {
                    edgeA.addBNeighbour(edgeB);
                    edgeB.addBNeighbour(edgeA);
                }
            }
        }
    }

    private double getSquareOfLength() {
        double dx = this.pointA[0] - this.pointB[0];
        double dy = this.pointA[1] - this.pointB[1];
        return dx * dx + dy * dy;
    }

    public void addANeighbour(VoronoiEdge other) {
        this.pointANeighbours.add(other);
    }

    public void addBNeighbour(VoronoiEdge other) {
        this.pointBNeighbours.add(other);
    }

    public void addEdgeNode(WorldGenNode node) {
        this.edgeNodes.add(node);
    }

    public void addEndNode(WorldGenNode node) {
        this.endNodes.add(node);
    }

    public List<WorldGenNode> getEdgeNodes() {
        return this.edgeNodes;
    }

    public List<WorldGenNode> getEndNodes() {
        return this.endNodes;
    }

    public double[] otherVertex(double[] vertex) {
        if (Arrays.equals(this.pointA, vertex)) {
            return this.pointB;
        }
        if (Arrays.equals(this.pointB, vertex)) {
            return this.pointA;
        }
        return null;
    }

    public double[] getA() {
        return this.pointA;
    }

    public double[] getB() {
        return this.pointB;
    }

    public void addTile(Tile tile) {
        this.tiles.add(tile);
    }

    public List<Tile> getTiles() {
        return this.tiles;
    }

}
