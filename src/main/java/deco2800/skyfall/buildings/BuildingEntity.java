package deco2800.skyfall.buildings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.entities.AbstractEntity;

/* This class is a base class for all building entity classes */
public class BuildingEntity extends AbstractEntity {

    private final transient Logger log = LoggerFactory.getLogger(BuildingEntity.class);
    private static final String ENTITY_ID_STRING = "buildingEntityID";

    // consistent information for a specific building
    private int buildTime;
    private Map<String, Integer> buildCost;
    private int initialHealth;

    // changeable information for a specific building
    private int length;
    private int width;
    private int level;
    private boolean updatable;
    private int currentHealth;

    public BuildingEntity(float col, float row, int renderOrder) {
        super(col, row, renderOrder);
        this.setObjectName(ENTITY_ID_STRING);

        if (!WorldUtil.validColRow(new HexVector(col, row))) {
            log.debug("Invalid position");
        }
        setDefault();
    }

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
        // building utility method here (if provided)
        // building animation here (e.g. show build time left)
        // do nothing so far
    }

    /* ------------------------------------------------------------------------
     * 				GETTERS AND SETTERS BELOW THIS COMMENT.
     * ------------------------------------------------------------------------ */

    public void setBuildTime(int time) {
        buildTime = time;
    }

    public int getBuildTime() {
        return buildTime;
    }

    public void addBuildCost(String resource, int cost) {
        if (buildCost == null) {
            buildCost = new TreeMap<>();
        }
        if (!resource.equals("") && cost != 0) {
            buildCost.put(resource, cost);
        }
    }

    public void setInitialHealth(int health) {
        initialHealth = health;
        this.currentHealth = initialHealth;
    }

    public int getInitialHealth() {
        return initialHealth;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return this.length;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return this.width;
    }

    public void setUpdatability(boolean updatable) {
        this.updatable = updatable;
    }

    public boolean getUpdatability() {
        return this.updatable;
    }

    public void setBuildingLevel(int level) {
        this.level = level;
    }

    public int getBuildingLevel() {
        return this.level;
    }

    public void setCurrentHealth(int health) {
        this.currentHealth = health;
    }

    public int getCurrentHealth() {
        return this.currentHealth;
    }
}
