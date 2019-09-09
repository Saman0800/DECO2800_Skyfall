package deco2800.skyfall.worlds;

import java.util.*;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.Texture;
import com.google.gson.annotations.Expose;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.NetworkManager;
import deco2800.skyfall.managers.TextureManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.biomes.BeachBiome;
import deco2800.skyfall.worlds.biomes.RiverBiome;
import deco2800.skyfall.worlds.generation.VoronoiEdge;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;

public class Tile {
    private static int nextID = 0;
    private double perlinValue;

    private static int getNextID() {
        return nextID++;
    }

    public static void resetID() {
        nextID = 0;
    }

    @Expose
    private String texture;
    private HexVector coords;
    // Class that stores the tiles biome and its texture
    private StaticEntity parent;
    // The Biome the tile is in
    private AbstractBiome biome;
    // Determines whether a tile can be built on, main use is for the construction
    // team
    private boolean isBuildable;

    @Expose
    private boolean obstructed = false;

    public static final int NORTH = 0;
    public static final int NORTH_EAST = 1;
    public static final int SOUTH_EAST = 2;
    public static final int SOUTH = 3;
    public static final int SOUTH_WEST = 4;
    public static final int NORTH_WEST = 5;

    static final int[] NORTHS = { NORTH_WEST, NORTH, NORTH_EAST };
    static final int[] SOUTHS = { SOUTH_WEST, SOUTH, SOUTH_EAST };

    private transient Map<Integer, Tile> neighbours;

    // Noise generators for use in edges and nodes
    private static NoiseGenerator xNoiseGen;
    private static NoiseGenerator yNoiseGen;

    @Expose
    private int index = -1;

    @Expose
    private int tileID = 0;

    private WorldGenNode node;
    private VoronoiEdge edge;

    public Tile() {
        this(0, 0);
    }

    public Tile(float col, float row) {
        coords = new HexVector(col, row);
        this.neighbours = new HashMap<>();
        this.tileID = Tile.getNextID();
        this.node = null;
        this.xNoiseGen = null;
        this.yNoiseGen = null;
    }

    public float getCol() {
        return coords.getCol();
    }

    public float getRow() {
        return coords.getRow();
    }

    public HexVector getCoordinates() {
        return new HexVector(coords);
    }

    public String getTextureName() {
        return this.texture;
    }

    public Texture getTexture() {
        return GameManager.get().getManager(TextureManager.class).getTexture(this.texture);
    }

    public void addNeighbour(int direction, Tile neighbour) {
        neighbours.put(direction, neighbour);
    }

    public static int opposite(int dir) {
        return (dir + 3) % 6;
    }

    public void removeReferanceFromNeighbours() {
        for (Entry<Integer, Tile> neighbourHash : neighbours.entrySet()) {
            neighbourHash.getValue().getNeighbours().remove(Tile.opposite(neighbourHash.getKey()));
        }
    }

    public Tile getNeighbour(int direction) {
        return neighbours.get(direction);
    }

    public void removeNeighbour(int direction) {
        neighbours.remove(direction);
    }

    /**
     * Gets the friction value for the tile
     * @param tileType The type of the tile
     * @return The friction value for the tile
     */
    public static float getFriction(String tileType) {
        //Gets the friction map for the world
        Map<String, Float> frictionMap = GameManager.get().getWorld().frictionMap;
        // Checks the type of the tile
        if (tileType.contains("ice")) {
            return frictionMap.get("ice");
        } else if (tileType.contains("desert")) {
            return frictionMap.get("desert");
        } else if (tileType.contains("mountain")) {
            return frictionMap.get("mountain");
        } else if (tileType.contains("water")) {
            return frictionMap.get("water");
        } else if (tileType.contains("forest")) {
            return frictionMap.get("forest");
        } else if (tileType.contains("lake")) {
            return frictionMap.get("lake");
        } else if (tileType.contains("ocean")) {
            return frictionMap.get("ocean");
        } else if (tileType.contains("snow")) {
            return frictionMap.get("snow");
        } else if (tileType.contains("volcanic")) {
            return frictionMap.get("volcanic");
        } else {
            return frictionMap.get("grass");
        }
    }

    /**
     * Sets up the perlin noise generators for the tiles to be used for nodes
     * and edges
     *
     * @param random The instance of Random for the world
     * @param nodeSpacing The node spacing for the world
     */
    public static void setNoiseGenerators(Random random, int nodeSpacing) {
        int startPeriod = nodeSpacing * 2;
        // TODO Fix possible divide-by-zero.
        int octaves = Math.max((int) Math.ceil(Math.log(startPeriod) / Math.log(2)) - 1, 1);
        double attenuation = Math.pow(0.9, 1d / octaves);

        xNoiseGen = new NoiseGenerator(random, octaves, startPeriod, attenuation);
        yNoiseGen = new NoiseGenerator(random,  octaves, startPeriod, attenuation);
    }

    /**
     * Assigns this tile to the nearest node
     *
     * @param nodes A List of nodes to choose from sorted by y value
     */
    public void assignNode(List<WorldGenNode> nodes, int nodeSpacing) {
        // Offset the position of tiles used to calculate the nodes using
        // Perlin noise to add noise to the edges.
        double tileX =
                this.getCol() + xNoiseGen.getOctavedPerlinValue(this.getCol() , this.getRow()) *
                        (double) nodeSpacing - (double) nodeSpacing / 2;
        double tileY =
                this.getRow() + yNoiseGen.getOctavedPerlinValue(this.getCol() , this.getRow()) *
                        (double) nodeSpacing - (double) nodeSpacing / 2;
        // Find the index of the node with the node with one of the nearest
        // Y values (note, if there is no node with the exact Y value, it)
        // Can choose the node on either side, not the strictly closest one
        int nearestIndex = WorldGenNode.binarySearch(tileY, nodes, 0, nodes.size() - 1);
        boolean lowerLimitFound = false;
        boolean upperLimitFound = false;

        // Store the minimum distance to a node, and the index of that node
        double minDistance = nodes.get(nearestIndex).distanceTo(tileX, tileY);
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
                double distance = nodes.get(lower).distanceTo(tileX, tileY);
                // Update the closest node if necessary
                if (distance < minDistance) {
                    minDistance = distance;
                    minDistanceIndex = lower;
                }
                // As distance to a node is necessarily >= the difference in
                // y value, if the difference in y value is greater than the
                // smallest distance to a node, all future nodes in that
                // direction will be further away
                if (nodes.get(lower).yDistanceTo(tileY) > minDistance) {
                    lowerLimitFound = true;
                }
            }
            if (!upperLimitFound) {
                double distance = nodes.get(upper).distanceTo(tileX, tileY);
                if (distance < minDistance) {
                    minDistance = distance;
                    minDistanceIndex = upper;
                }
                if (nodes.get(upper).yDistanceTo(tileY) > minDistance) {
                    upperLimitFound = true;
                }
            }
            iterations++;
        }
        // Assign tile to the node
        nodes.get(minDistanceIndex).addTile(this);
        // Assign node to the tile
        this.node = nodes.get(minDistanceIndex);
    }

    private VoronoiEdge findNearestEdge(VoronoiEdge currentEdge,
            List<VoronoiEdge> edges, double maxDistance) {
        VoronoiEdge closestEdge = currentEdge;
        double closestDistance = Double.POSITIVE_INFINITY;
        for (VoronoiEdge voronoiEdge : edges) {

            // Get the coordinates of the vertices
            double ax = voronoiEdge.getA()[0];
            double ay = voronoiEdge.getA()[1];
            double bx = voronoiEdge.getB()[0];
            double by = voronoiEdge.getB()[1];

            double squareDistance;

            // If the edge is vertical and has undefined gradient
            if (ax == bx) {
                double smallY = Math.min(ay, by);
                double bigY = Math.max(ay, by);

                // If the tile is within the y values of the edge
                if (this.getRow() <= bigY && this.getRow() >= smallY) {
                    squareDistance = Math.abs(this.getCol() - ax);
                    squareDistance *= squareDistance;
                } else if (this.getRow() > bigY) {
                    squareDistance = this.squareDistanceTo(ax, bigY);
                } else {
                    squareDistance = this.squareDistanceTo(ax, smallY);
                }

            } else {
                double dxA = this.getCol() - ax;
                double dxB = this.getCol() - bx;
                double dyA = this.getRow() - ay;
                double dyB = this.getRow() - by;

                double edgeLength = voronoiEdge.getSquareOfLength();
                double dotProduct = (dxA * (bx - ax) + dyA * (by - ay));

                if (dotProduct < 0 || dotProduct * dotProduct > edgeLength) {
                    double squareDistanceToA = dxA * dxA + dyA * dyA;
                    double squareDistanceToB = dxB * dxB + dyB + dyB;
                    squareDistance = Math.min(squareDistanceToA,
                            squareDistanceToB);
                } else {
                    double gradient = (ay - by) / (ax - bx);
                    // A quantity used to calculate the distance
                    double distanceNumerator = -1 * gradient * this.getCol() + this.getRow()
                            + gradient * bx - by;
                    // Get the square distance
                    squareDistance = distanceNumerator * distanceNumerator / (gradient * gradient + 1);
                }
            }

            if (squareDistance < closestDistance
                    && squareDistance < maxDistance * maxDistance) {
                closestDistance = squareDistance;
                closestEdge = voronoiEdge;
            }
        }
        return closestEdge;
    }

    /**
     * Assigns the nearest river or beach edge to this node, giving priority to
     * rivers. It will only assign for tiles within a certain distance of an
     * edge
     *
     * @param riverEdges A list of edges that are in rivers
     * @param beachEdges A list of edges that are in beaches
     * @param riverWidth The maximum distance away for rivers
     * @param beachWidth The maximum distance away for edges
     */
    public void assignEdge(LinkedHashMap<VoronoiEdge, RiverBiome> riverEdges,
                           LinkedHashMap<VoronoiEdge, BeachBiome> beachEdges,
                           double riverWidth, double beachWidth) {
        VoronoiEdge closestEdge = findNearestEdge(null, new ArrayList<>(beachEdges.keySet()), beachWidth);
        closestEdge = findNearestEdge(closestEdge, new ArrayList<>(riverEdges.keySet()), riverWidth);
        this.edge = closestEdge;
        if (beachEdges.containsKey(closestEdge)) {
            beachEdges.get(closestEdge).addTile(this);
        } else if (riverEdges.containsKey(closestEdge)) {
            riverEdges.get(closestEdge).addTile(this);
        }
        // If here is reached, this.edge will remain null
    }

    /**
     * Gets the square of the distance to a point
     *
     * @param x the x value of the point
     * @param y the y value of the point
     * @return the square of the distance to the point
     */
    private double squareDistanceTo(double x, double y) {
        double dx = this.getCol() - x;
        double dy = this.getRow() - y;
        return dx * dx + dy * dy;
    }

    public Map<Integer, Tile> getNeighbours() {
        return neighbours;
    }

    public StaticEntity getParent() {
        return parent;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public String toString() {
        // return String.format("[%.0f, %.1f: %d]", coords.getCol(), coords.getRow(),
        // index);
//        return String.format("%f", getPerlinValue());
//        return textureBackup;
        return getBiome().getBiomeName();
    }

    public void setParent(StaticEntity parent) {
        this.parent = parent;
    }

    public void setTexture(String texture) {
        setObstructed(checkObstructed(texture));
        setIsBuildable(checkIsBuildable(texture));
        this.texture = texture;
    }

    public void dispose() {
        if (this.hasParent() && this.parent != null) {
            for (HexVector childposn : parent.getChildrenPositions()) {
                Tile child = GameManager.get().getWorld().getTile(childposn);
                if (child != null) {
                    child.setParent(null);
                    child.dispose();
                } else {
                    // Wat
                }
            }
        }

        GameManager.get().getManager(NetworkManager.class).deleteTile(this);

        this.removeReferanceFromNeighbours();
        GameManager.get().getWorld().getTileMap().remove(this);
    }

    public int calculateIndex() {
        if (index != -1) {
            return index;
        }

        int max = index;
        for (int north : NORTHS) {
            if (neighbours.containsKey(north)) {
                max = Math.max(max, neighbours.get(north).calculateIndex());
            }
        }
        this.index = max + 1;
        return index;
    }

    public int getTileID() {
        return tileID;
    }

    public void setTileID(int tileID) {
        this.tileID = tileID;
    }

    public void setIndex(Integer indexValue) {
        this.index = indexValue;
    }

    /**
     * Returns whether the tile obstructs entities.
     *
     * @return whether the tile obstructs entities
     */
    public boolean isObstructed() {
        return obstructed;
    }

    public void setObstructed(boolean b) {
        obstructed = b;
    }

    public boolean checkObstructed(String texture) {
        ArrayList<String> obstructables = new ArrayList<>();
        obstructables.add("water");
        for (String obstructable : obstructables) {
            if (texture.contains(obstructable)) {
                return true;
            }
        }
        return false;
    }

    public void setCol(float col) {
        this.coords.setCol(col);
    }

    public void setRow(float row) {
        this.coords.setRow(row);
    }

    /**
     * Used to determine whether a tile can be built on
     * 
     * @return false if the tile cannot be built on, and true if it can be built on
     */
    public boolean getIsBuildable() {
        return isBuildable;
    }

    /**
     * Sets the isBuildable value
     * 
     * @param isBuildable true if it can be built on, false if not
     */
    public void setIsBuildable(boolean isBuildable) {
        this.isBuildable = isBuildable;
    }

    /**
     * Checks whether a tile is able to built on depending on the texture name
     * 
     * @param texture The texture being checked
     * @return False if the tile can not be built on, and true if it can
     */
    private boolean checkIsBuildable(String texture) {
        ArrayList<String> buildables = new ArrayList<>();
        // List of buildable tiles
        buildables.add("water");
        buildables.add("sand");
        for (String obstructable : buildables) {
            if (texture.contains(obstructable)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the reference to the biome the tile is in
     * 
     * @return An AbstractBiome representing the biome the tile is in
     */
    public AbstractBiome getBiome() {
        return biome;
    }

    public void setBiome(AbstractBiome biome) {
        this.biome = biome;
    }

    /**
     * Sets the perlin noise value
     * 
     * @param perlinValue The noise value
     */
    public void setPerlinValue(double perlinValue) {
        this.perlinValue = perlinValue;
    }

    /**
     * Returns the perlin value
     * 
     * @return The perlin value
     */
    public double getPerlinValue() {
        return perlinValue;
    }

    /**
     * Returns the node this tile is closest to
     *
     * @return the node this tile is closest to
     */
    public WorldGenNode getNode() {
        return this.node;
    }

    /**
     * Returns the VoronoiEdge that this tile is closest to (within a certain
     * distance)
     *
     * @return the VoronoiEdge that this tile is closest to
     */
    public VoronoiEdge getEdge() {
        return this.edge;
    }
}