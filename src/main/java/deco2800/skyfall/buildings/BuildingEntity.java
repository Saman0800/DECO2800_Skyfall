package deco2800.skyfall.buildings;

import deco2800.skyfall.worlds.AbstractWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.entities.AbstractEntity;

/**
 *  A BuildingEntity is an base class for all building entity subclass,
 *  including basic information that a building object should contains.
 */
public class BuildingEntity extends AbstractEntity {

    // a debug logger
    private final transient Logger log = LoggerFactory.getLogger(BuildingEntity.class);
    // a building object name
    private static final String ENTITY_ID_STRING = "buildingEntityID";

    // consistent information for a specific building
    private int buildTime;
    private Map<String, Integer> buildCost;
    private Map<String, String> buildingTextures;
    private int maxHealth;

    // changeable information for a specific building
    private int sizeX;
    private int sizeY;
    private float col;
    private float row;

    private int length;
    private int width;
    private int level;
    private boolean updatable;
    private int currentHealth;

    /**
     * Constructor for an building entity.
     * @param col the col position on the world
     * @param row the row position on the world
     * @param renderOrder the height position on the world
     */
    public BuildingEntity(float col, float row, int renderOrder) {
        super(col, row, renderOrder);
        this.setObjectName(ENTITY_ID_STRING);

        if (!WorldUtil.validColRow(new HexVector(col, row))) {
            log.debug("Invalid position");
        }
        setDefault();
    }

    /**
     * Constructor for an building entity with customized scaling factors.
     * @param col the col position on the world
     * @param row the row position on the world
     * @param renderOrder the height position on the world
     * @param colRenderLength factor to scale the texture length
     * @param rowRenderLength factor to scale the texture width
     */
    public BuildingEntity(float col, float row, int renderOrder, float colRenderLength, float rowRenderLength) {
        super(col, row, renderOrder, colRenderLength, rowRenderLength);
        this.setObjectName(ENTITY_ID_STRING);
        this.setRenderOrder(renderOrder);
        this.animations = new HashMap<>();

        if (!WorldUtil.validColRow(new HexVector(col, row))) {
            log.debug("Invalid position");
        }
        setDefault();
    }

    /**
     * Set default information for a building entity, and it should be overridden inside
     * a building entity subclass for setting different basic information.
     */
    private void setDefault() {
        // default consistent information of the building type
        setTexture("error_build");
        setBuildTime(1);
        addBuildCost("", 0);
        addBuildCost("", 0);
        setInitialHealth(1000);

        // default changeable information of the building type
        length = 1;
        width = 1;
        level = 1;
        updatable = false;
        currentHealth = getInitialHealth();
    }

    @Override
    public void onTick(long i) {
        // run building utility method here if provided (e.g. add 1 health per second)
        // run building animation here if provided (e.g. show build time left)
        // do nothing so far
    }

    /**
     * @param x - X coordinate
     * @param y - Y coordinate
     * @param height - Render height
     * @param world - World to place building in
     */
    public void placeBuilding(float x, float y, int height, AbstractWorld world) {
        setPosition(x, y, height);
        world.addEntity(this);
    }

    /**
     * @param world - World to remove building from
     */
    public void removeBuilding(AbstractWorld world) {
        world.removeEntity(this);
    }


    /**
     * Set the time needed to build a building entity.
     * @param time time cost
     */
    public void setBuildTime(int time) {
        buildTime = time;
    }

    /**
     * Get the time needed to build a building entity.
     * @return time cost
     */
    public int getBuildTime() {
        return buildTime;
    }

    /**
     * Set the resources needed to build a building entity.
     * @param resource resource name
     * @param cost number of the resource cost
     */
    public void addBuildCost(String resource, int cost) {
        if (buildCost == null) {
            buildCost = new TreeMap<>();
        }
        if (!resource.equals("") && cost != 0) {
            buildCost.put(resource, cost);
        }
    }
    /**
     * @return - cost of building the building
     */
    public Map<String, Integer> getCost(){
        return buildCost;
    }

    /**
     * Adds a texture to the buildings list of textures.
     * @param name the name of the texture
     * @param texture the texture
     */
    public void addTexture(String name, String texture) {
        if (name == null) {
            buildingTextures = new HashMap<>();
        }
        if (!texture.equals("")) {
            buildingTextures.put(name, texture);
        }
    }
    /**
     * @return - the list of the building textures
     */
    public Map<String, String> getTextures(){
        return buildingTextures;
    }

    /**
     * Set the initial health to a building entity.
     * @param health a building's initial health
     */
    public void setInitialHealth(int health) {
        maxHealth = health;
        this.currentHealth = maxHealth;
    }

    /**
     * Get the initial health to a building entity.
     * @return a building's initial health
     */
    public int getInitialHealth() {
        return maxHealth;
    }

    /**
     * Set a building entity length related to number of tile in terms of column.
     * @param length a building's length
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Get a building entity length related to number of tile in terms of column.
     * @return a building's length
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Set a building entity width related to number of tile in terms of row.
     * @param width a building's width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Get a building entity width related to number of tile in terms of row.
     * @return a building's width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Set a building entity updatability.
     * @param updatable by boolean value
     */
    public void setUpdatability(boolean updatable) {
        this.updatable = updatable;
    }

    /**
     * Get a building entity current updatability.
     * @return boolean value for current updatability
     */
    public boolean getUpdatability() {
        return this.updatable;
    }

    /**
     * Set the level to a building entity.
     * @param level building level
     */
    public void setBuildingLevel(int level) {
        this.level = level;
    }

    /**
     * Get a building entity current level.
     * @return current building level
     */
    public int getBuildingLevel() {
        return this.level;
    }

    /**
     * Set current health to a building entity.
     * @param health building current health
     */
    public void setCurrentHealth(int health) {
        this.currentHealth = health;
    }

    /**
     * Get the current health of a building entity.
     * @return current building health
     */
    public int getCurrentHealth() {
        return this.currentHealth;
    }









}
