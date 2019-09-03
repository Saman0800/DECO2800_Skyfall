package deco2800.skyfall.worlds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.Texture;
import com.google.gson.annotations.Expose;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.NetworkManager;
import deco2800.skyfall.managers.TextureManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.biomes.AbstractBiome;

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

    @Expose
    private int index = -1;

    @Expose
    private int tileID = 0;

    public Tile() {
        this(0, 0);
    }

    public Tile(float col, float row) {
        coords = new HexVector(col, row);
        this.neighbours = new HashMap<>();
        this.tileID = Tile.getNextID();
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

	public static float getFriction(String tileType){
    	Map<String, Float> frictionMap =
				GameManager.get().getWorld().frictionMap;
    	if(tileType.contains("ice")){
			return frictionMap.get("ice");
		}else if(tileType.contains("sand")){
			return frictionMap.get("sand");
		}else if(tileType.contains("mountain")){
			return frictionMap.get("mountain");
		}else if(tileType.contains("water")){
			return frictionMap.get("water");
		}else{
			return frictionMap.get("grass");
		}
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
     *
     * @deprecated use {@link #isObstructed()}
     */
    @Deprecated
    public boolean getObstructed() {
        return isObstructed();
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
}