package deco2800.skyfall.entities.structures;

import com.google.gson.annotations.Expose;
import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.managers.ConstructionManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Town centre that defines the middle of the players base/village.
 */
public class TownCentreBuilding extends AbstractBuilding {

    private final transient Logger log = LoggerFactory.getLogger(StaticEntity.class);

    private static final String ENTITY_ID_STRING = "TownCenterID";
    private int renderOrder;

    private int maxHealth = 80;
    private int currentHealth;

    private HexVector coords;
    private String texture = "fence_bottom_left";

    ConstructionManager permissions = new ConstructionManager();

    @Expose
    public Map<HexVector, String> children;

    /**
     *  Tile Constructor
     * @param tile - tile building is on
     * @param renderOrder - Render order of building
     */
    public TownCentreBuilding(Tile tile, int renderOrder) {
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
     * Default constructor for Town Centre Building
     * @param x - X coordinate
     * @param y - Y coordinate
     */
    public TownCentreBuilding(float x, float y) {
        super(x, y);
        this.currentHealth = maxHealth;
        this.setTexture("buildingA");

        int constructionTime = 6;
        int xSize = 3;
        int ySize = 3;

        TreeMap<String, Integer> constructionCost = new TreeMap<>();

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
    public void onTick(long i) {}
}
