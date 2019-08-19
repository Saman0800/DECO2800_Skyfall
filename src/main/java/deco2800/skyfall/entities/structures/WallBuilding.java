package deco2800.skyfall.entities.structures;

import com.google.gson.annotations.Expose;
import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.managers.ConstructionManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.AbstractWorld;
import deco2800.skyfall.worlds.Tile;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Walls that the player can place. Walls are stationary buildings that
 * impede all land units. Walls block projectiles.
 */
public class WallBuilding extends AbstractBuilding {

    private final transient Logger log = LoggerFactory.getLogger(StaticEntity.class);

    private static final String ENTITY_ID_STRING = "WallID";
    private int renderOrder;

    private int maxHealth = 5;
    private int currentHealth;

    private HexVector coords;
    private String texture = "fence_bottom_left";

    ConstructionManager permissions = new ConstructionManager();

    @Expose
    public Map<HexVector, String> children;

    /**
     * Tile constructor
     * @param tile - Tile building is one
     * @param renderOrder - Render order of building
     */
    public WallBuilding(Tile tile, int renderOrder) {
        super(tile.getRow(), tile.getCol());
        this.setTexture(texture);

        this.setObjectName(ENTITY_ID_STRING);
        this.renderOrder = renderOrder;
        this.currentHealth = maxHealth;

        children = new HashMap<>();
        children.put(tile.getCoordinates(), texture);
        if (!WorldUtil.validColRow(tile.getCoordinates())) {
            log.debug(tile.getCoordinates() + "%s Is Invalid:");
            return;
        }
    }

    /**
     * Default constructor
     * @param x - X coordinate
     * @param y - Y coordinate
     */
    public WallBuilding(float x, float y) {
        super(x, y);
        this.currentHealth = maxHealth;
        //Ignore that the fence is using a building image.
        this.setTexture(texture);

        this.setObjectName(ENTITY_ID_STRING);
        //this.renderOrder = renderOrder;
        //Build time in seconds.
        int constructionTime = 3;
        //Building size
        int xSize = 1;
        int ySize = 1;
        //Cost of building
        //String: Name of item, can access using item.getName();
        //Integer: The number of that type of item
        TreeMap<String, Integer> constructionCost = new TreeMap<>();

        this.setXSize(xSize);
        this.setYSize(ySize);
        this.setBuildTime(constructionTime);
        this.setCost(constructionCost);

        children = new HashMap<>();
        coords = new HexVector(x, y);
        children.put(coords, texture);
        if (!WorldUtil.validColRow(coords)) {
            log.debug(coords + "%s Is Invalid:");
            return;
        }
    }

    /**
     * @return - Health of the fence
     */
    public int getMaxHealth() {return this.maxHealth;}

    /**
     * @return - Current health
     */
    public int getCurrentHealth() {return this.currentHealth;}


    /**
     * @param newMaxHealth - New max health
     */
    public void setHealth(int newMaxHealth) {this.maxHealth = newMaxHealth;}

    /**
     * @param newCurrentHealth - New current health
     */
    public void setCurrentHealth(int newCurrentHealth) {this.currentHealth = newCurrentHealth;}

    /**
     * @param damage - Amount of damage to take
     */
    public void takeDamage(int damage) {
        if((currentHealth - damage) > 0) {
            currentHealth = currentHealth - damage;
        } else {
            currentHealth = 0;
        }
    }

    @Override
    public void onTick(long i) {
        //Functionality.
    }

}
