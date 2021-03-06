package deco2800.skyfall.worlds;

import com.badlogic.gdx.graphics.Texture;
import com.google.gson.annotations.Expose;
import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.TextureManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.biomes.BeachBiome;
import deco2800.skyfall.worlds.biomes.RiverBiome;
import deco2800.skyfall.worlds.generation.VoronoiEdge;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode;
import deco2800.skyfall.worlds.generation.perlinnoise.NoiseGenerator;
import deco2800.skyfall.worlds.world.World;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class Tile {
    private static int nextID = 0;
    private double perlinValue;

    private static int getNextID() {
        return nextID++;
    }

    public static void resetID() {
        nextID = 0;
    }

    // String Constant
    private static final String WATER = "water";

    @Expose
    private String texture;
    private HexVector coords;
    // Class that stores the tiles biome and its texture
    private StaticEntity parent;
    // The world the tile is in
    private World world;
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

    private Map<Integer, Tile> neighbours;

    @Expose
    private int index = -1;

    @Expose
    private int tileID = 0;

    private WorldGenNode node;
    private VoronoiEdge edge;

    public Tile(World world) {
        this(world, 0, 0);
    }

    public Tile(World world, float col, float row) {
        coords = new HexVector(col, row);
        this.neighbours = new HashMap<>();
        this.tileID = Tile.getNextID();
        this.node = null;
        this.world = world;
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

    /**
     * Returns a integer to represent the tile type
     * 
     * @param tileType Name of tile texture
     * @return int representation of the tile type
     */
    public static int getTileType(String tileType) {
        if (tileType.contains("ice")) {
            return 0;
        } else if (tileType.contains("desert")) {
            return 1;
        } else if (tileType.contains("mountain")) {
            return 2;
        } else if (tileType.contains(WATER) || tileType.contains("lake") || tileType.contains("ocean")) {
            return 3;
        } else if (tileType.contains("snow")) {
            return 4;
        } else if (tileType.contains("volcanic")) {
            return 5;
        } else {
            return 6;
        }
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
     * 
     * @param tileType The type of the tile
     * @return The friction value for the tile
     */
    public static float getFriction(String tileType) {
        // Gets the friction map for the world
        Map<String, Float> frictionMap = GameManager.get().getWorld().getfrictionMap();
        // Checks the type of the tile
        switch (Tile.getTileType(tileType)) {
        case 0:
            return frictionMap.get("ice");
        case 1:
            return frictionMap.get("desert");
        case 2:
            return frictionMap.get("mountain");
        case 3:
            return frictionMap.get(WATER);
        case 4:
            return frictionMap.get("snow");
        case 5:
            return frictionMap.get("volcanic");
        default:
            return frictionMap.get("grass");
        }
    }

    /**
     * Gets a {@link NoiseGenerator} that is appropriate for generating tile offset
     * noise for biome determination.
     *
     * @param random      the instance of {@link Random} for the world
     * @param nodeSpacing the node spacing for the world
     *
     * @return the offset noise generator
     */
    public static NoiseGenerator getOffsetNoiseGenerator(Random random, int nodeSpacing) {
        int startPeriod = nodeSpacing * 2;
        int octaves = Math.max((int) Math.ceil(Math.log(startPeriod) / Math.log(2)) - 1, 1);
        double attenuation = Math.pow(0.9, 1d / octaves);

        return new NoiseGenerator(random.nextLong(), octaves, startPeriod, attenuation);
    }

    /**
     * Gets the location of the tile with noise added.
     * 
     * @param noiseFactor the amplitude of the noise
     * @return the new column coordinate
     */
    private double getNoisyCol(double noiseFactor) {
        return getCol() + world.getTileOffsetNoiseGeneratorX().getOctavedPerlinValue(getCol(), getRow()) * noiseFactor
                - noiseFactor / 2d;
    }

    /**
     * Gets the location of the tile with noise added.
     *
     * @param noiseFactor the amplitude of the noise
     *
     * @return the new row coordinate
     */
    private double getNoisyRow(double noiseFactor) {
        return getRow() + world.getTileOffsetNoiseGeneratorY().getOctavedPerlinValue(getCol(), getRow()) * noiseFactor
                - noiseFactor / 2d;
    }

    /**
     * Assigns this tile to the nearest node
     *
     * @param nodes A List of nodes to choose from sorted by y value
     */
    public void assignNode(List<WorldGenNode> nodes, int nodeSpacing) {
        // Offset the position of tiles used to calculate the nodes using
        // Perlin noise to add noise to the edges.
        double tileX = getNoisyCol(nodeSpacing);
        double tileY = getNoisyRow(nodeSpacing);

        int minDistanceIndex = WorldGenNode.findNearestNodeIndex(nodes, tileX, tileY);
        // Assign node to the tile
        node = nodes.get(minDistanceIndex);
        // Assign tile to the node
        node.getBiome().addTile(this);
    }

    private VoronoiEdge findNearestEdge(VoronoiEdge currentEdge, List<VoronoiEdge> edges, double maxDistance,
            int nodeSpacing) {
        double tileX = getNoisyCol(nodeSpacing);
        double tileY = getNoisyRow(nodeSpacing);

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
                if (tileY <= bigY && tileY >= smallY) {
                    squareDistance = Math.abs(tileX - ax);
                    squareDistance *= squareDistance;
                } else if (tileY > bigY) {
                    squareDistance = this.squareDistanceTo(ax, bigY);
                } else {
                    squareDistance = this.squareDistanceTo(ax, smallY);
                }
            } else {
                squareDistance = getSquareDistanceInvalidY(tileX, tileY, voronoiEdge, ax, ay, bx, by);
            }

            if (squareDistance < closestDistance && squareDistance < maxDistance * maxDistance) {
                closestDistance = squareDistance;
                closestEdge = voronoiEdge;
            }
        }
        return closestEdge;
    }

    private double getSquareDistanceInvalidY(double tileX, double tileY, VoronoiEdge voronoiEdge, double ax, double ay,
            double bx, double by) {
        double squareDistance;
        double dxA = tileX - ax;
        double dxB = tileX - bx;
        double dyA = tileY - ay;
        double dyB = tileY - by;

        double edgeLength = voronoiEdge.getSquareOfLength();
        double dotProduct = (dxA * (bx - ax) + dyA * (by - ay));

        if (dotProduct < 0 || dotProduct > edgeLength) {
            double squareDistanceToA = dxA * dxA + dyA * dyA;
            double squareDistanceToB = dxB * dxB + dyB * dyB;
            squareDistance = Math.min(squareDistanceToA, squareDistanceToB);
        } else {
            double gradient = (ay - by) / (ax - bx);
            // A quantity used to calculate the distance
            double distanceNumerator = -1 * gradient * tileX + tileY + gradient * bx - by;
            // Get the square distance
            squareDistance = distanceNumerator * distanceNumerator / (gradient * gradient + 1);
        }
        return squareDistance;
    }

    /**
     * Assigns the nearest river or beach edge to this node, giving priority to
     * rivers. It will only assign for tiles within a certain distance of an edge
     *
     * @param riverEdges A list of edges that are in rivers
     * @param beachEdges A list of edges that are in beaches
     * @param riverWidth The maximum distance away for rivers
     * @param beachWidth The maximum distance away for edges
     */
    public void assignEdge(Map<VoronoiEdge, RiverBiome> riverEdges, Map<VoronoiEdge, BeachBiome> beachEdges,
            int nodeSpacing, double riverWidth, double beachWidth) {

        if (getBiome().getBiomeName().equals("ocean")) {
            return;
        }
        VoronoiEdge closestEdge = findNearestEdge(null, new ArrayList<>(beachEdges.keySet()), beachWidth, nodeSpacing);
        if (!(Math.abs(getCol()) < riverWidth && Math.abs(getRow()) < riverWidth)) {
            closestEdge = findNearestEdge(closestEdge, new ArrayList<>(riverEdges.keySet()), riverWidth, nodeSpacing);
        }
        this.edge = closestEdge;
        // Add the tile to the biome for the beach/river
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
        obstructables.add(WATER);
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
        buildables.add(WATER);
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