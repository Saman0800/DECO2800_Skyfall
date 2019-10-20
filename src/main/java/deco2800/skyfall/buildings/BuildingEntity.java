package deco2800.skyfall.buildings;

import com.badlogic.gdx.graphics.Texture;
import com.google.gson.annotations.Expose;

import deco2800.skyfall.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.entities.weapons.Weapon;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.util.Collider;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * A BuildingEntity is an base class for all building entity subclass, including
 * basic information that a building object should contains.
 */
public class BuildingEntity extends SaveableEntity implements ICombatEntity {

    // a logger
    private final Logger log = LoggerFactory.getLogger(BuildingEntity.class);
    // a building object name
    private static final String ENTITY_ID_STRING = "buildingEntityID";
    private Collider collider;

    // health
    private int health;
    // The type of building to be created
    private BuildingType buildingType;

    // consistent information for a specific building
    private int buildTime;
    private Map<String, Integer> buildCost;
    private Map<String, String> buildingTextures;
    @Expose
    private int maxHealth;

    @Expose
    private int length;

    private int width;
    private int level;
    private boolean upgradable;
    private int currentHealth;

    private InventoryManager inventoryManager;

    /**
     * Item the Building is currently equipped with/holding.
     */
    private Item equippedItem;

    // default value
    private int itemSlotSelected = 0;

    @Override
    public void takeDamage(int damage) {
        this.health -= damage;

        // In Peon.class, when the health = 0, isDead will be set true automatically.
        if (this.health <= 0) {
            destroy();
        }
    }

    private void destroy() {
        GameManager.get().getWorld().removeEntity(this);
    }

    @Override
    public void dealDamage(MainCharacter mc) {
        // Do nothing for now
    }

    @Override
    public boolean canDealDamage() {
        return false;
    }

    @Override
    public int getDamage() {
        switch (buildingType) {
            case CABIN:
                return 0;
            case CASTLE:
                return 1;
            case TOWNCENTRE:
                return 2;
            case FENCE:
                return 0;
            case SAFEHOUSE:
                return 1;
            case WATCHTOWER:
                return 2;
            case STORAGE_UNIT:
                return 0;
            default:
                return 0;
        }
    }

    @Override
    public int[] getResistanceAttributes() {
        return new int[0];
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;

    }

    enum AttackLevel {
        LOW, MEDIUM, HIGH
    }

    enum DefendLevel {
        LOW, MEDIUM, HIGH
    }

    /**
     * Constructor for an building entity with normal rendering size.
     *
     * @param col          the col position on the world
     * @param row          the row position on the world
     * @param renderOrder  the height position on the world
     * @param buildingType specific building information container
     */
    public BuildingEntity(float col, float row, int renderOrder, BuildingType buildingType) {
        this(col, row, renderOrder, buildingType, 1, 1);

        if (!WorldUtil.validColRow(new HexVector(col, row))) {
            log.debug("Invalid position");
        }
    }

    /**
     * Constructor for an building entity with customized scaling factors.
     *
     * @param col             the col position on the world
     * @param row             the row position on the world
     * @param renderOrder     the height position on the world
     * @param buildingType    specific building information container
     * @param colRenderLength factor to scale the texture length
     * @param rowRenderLength factor to scale the texture width
     */
    public BuildingEntity(float col, float row, int renderOrder, BuildingType buildingType, float colRenderLength,
                          float rowRenderLength) {
        super(col, row, renderOrder, colRenderLength, rowRenderLength);
        this.setObjectName(ENTITY_ID_STRING);
        this.setRenderOrder(renderOrder);
        this.animations = new HashMap<>();
        this.buildingType = buildingType;
        this.buildCost = buildingType.getBuildCost();
        this.setObjectName(buildingType.getName());
        this.setTexture(buildingType.getMainTexture());
        this.setBuildTime(buildingType.getBuildTime());
        this.setInitialHealth(buildingType.getMaxHealth());
        this.setWidth(buildingType.getSizeY());
        this.setLength(buildingType.getSizeX());
        this.setBuildingLevel(1);
        this.setCollider();

        if (!WorldUtil.validColRow(new HexVector(col, row))) {
            log.debug("Invalid position");
        }
    }

    /**
     * Creates a new Collider object at (x,y) coordinates with size xLength x
     * yLength. Called by building factory before returning a building such that no
     * building in the game has a Collider set to null.
     */
    public void setCollider() {
        float[] cords = WorldUtil.colRowToWorldCords(position.getCol(), position.getRow());

        // preferred way as setting a collider based on texture size
        try {
            Texture texture = new Texture(getTexture());
            collider = new Collider(cords[0], cords[1], texture.getWidth(), texture.getHeight());
            return;
        } catch (Exception e) {
            log.info("Building {} can't set a collider with its texture", getObjectName() + getEntityID());
        }

        // preferred way is blocked, setting a collider based on tile
        try {
            float tileSize = 100;
            collider = new Collider(cords[0], cords[1], tileSize * getLength(), tileSize * getWidth());
        } catch (Exception e2) {
            log.info("Building {} has a null collider", getObjectName() + getEntityID());
        }
    }

    /**
     * @return The collider for the building entity
     */
    public Collider getCollider() {
        return collider;
    }

    @Override
    public void onTick(long i) {
        // run building utility method here if provided (e.g. add 1 health per second)
        // run building animation here if provided (e.g. show build time left)
        // do nothing so far
    }

    /**
     * @param x      - X coordinate
     * @param y      - Y coordinate
     * @param height - Render height
     * @param world  - World to place building in
     */
    public void placeBuilding(float x, float y, int height, World world) {
        setPosition(x, y, height);
        world.addEntity(this);
    }

    /**
     * @param world - World to remove building from
     */
    public void removeBuilding(World world) {
        world.removeEntity(this);
    }

    /**
     * Get the type of building
     *
     * @return building type
     */
    public BuildingType getBuildingType() {
        return this.buildingType;
    }

    /**
     * Set the time needed to build a building entity.
     *
     * @param time time cost
     */
    public void setBuildTime(int time) {
        buildTime = time;
    }

    /**
     * Get the time needed to build a building entity.
     *
     * @return time cost
     */
    public int getBuildTime() {
        return buildTime;
    }

    /**
     * Set the resources needed to build a building entity.
     *
     * @param resource resource name
     * @param cost     number of the resource cost
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
    public Map<String, Integer> getCost() {
        return buildCost;
    }

    /**
     * Adds a texture to the buildings list of textures.
     *
     * @param name    the name of the texture
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
    public Map<String, String> getTextures() {
        return buildingTextures;
    }

    /**
     * Set the initial health to a building entity.
     *
     * @param health a building's initial health
     */
    private void setInitialHealth(int health) {
        maxHealth = health;
        this.currentHealth = maxHealth;
    }

    /**
     * Get the initial health to a building entity.
     *
     * @return a building's initial health
     */
    public int getInitialHealth() {
        return maxHealth;
    }

    /**
     * Set a building entity length related to number of tile in terms of column.
     *
     * @param length a building's length (x length)
     */
    public void setLength(int length) {
        if (length != 0) {
            this.length = length;
        }
    }

    /**
     * Get a building entity length related to number of tile in terms of column.
     *
     * @return a building's length (x length)
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Set a building entity width related to number of tile in terms of row.
     *
     * @param width a building's width (y length)
     */
    public void setWidth(int width) {
        if (width != 0) {
            this.width = width;
        }
    }

    /**
     * Get a building entity width related to number of tile in terms of row.
     *
     * @return a building's width (y length)
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Set a building entity upgrade state.
     *
     * @param upgradable by boolean value
     */
    public void setUpgradable(boolean upgradable) {
        this.upgradable = upgradable;
    }

    /**
     * Get a building entity current upgrade state.
     *
     * @return boolean value for current upgrade state
     */
    public boolean isUpgradable() {
        return this.upgradable;
    }

    /**
     * Set the level to a building entity.
     *
     * @param level building level
     */
    public void setBuildingLevel(int level) {
        this.level = level;
    }

    /**
     * Get a building entity current level.
     *
     * @return current building level
     */
    public int getBuildingLevel() {
        return this.level;
    }

    /**
     * Updates the health of a Building
     *
     * @param amount - Amount of heath to update
     */
    public void updateHealth(int amount) {
        if (amount < 0 && (currentHealth + amount) > 0) {
            currentHealth += amount;
        }
        if (amount >= 1 && currentHealth < maxHealth) {
            currentHealth += amount;
        }
    }

    /**
     * Get the current health of a building entity.
     *
     * @return current building health
     */
    public int getCurrentHealth() {
        return this.currentHealth;
    }

    /**
     * Returns the number of wood required for the item.
     *
     * @return The name of the item
     */
    // @Override
    public int getRequiredWood() {
        return 1;
    }

    /**
     * Returns the number of stones required for the item.
     *
     * @return The name of the item
     */
    // @Override
    public int getRequiredStone() {
        return 30;
    }

    /**
     * Returns the number of metal required for the item.
     *
     * @return The name of the item
     */
    // @Override
    public int getRequiredMetal() {
        return 10;
    }

    /**
     * Returns a map of the name of the required resource and the required number of
     * each resource to create the item.
     *
     * @return a hashamp of the required resources and their number.
     */
    // @Override
    public Map<String, Integer> getAllRequirements() {

        buildCost.put("Wood", 50);
        buildCost.put("Stone", 30);
        buildCost.put("Metal", 10);
        return buildCost;
    }

    // @Override
    public String getName() {
        return null;
    }

    /**
     * Returns the number of metal required for the item.
     *
     * @return The name of the item
     */
    // @Override
    public boolean isBlueprintLearned() {
        // do nothing
        return true;
    }

    public void cabinInteract() {
        // Resting at the cabin restores a players health.
        MainCharacter player = GameManager.getManagerFromInstance(GameMenuManager.class).getMainCharacter();
        player.changeHealth(+player.getMaxHealth());

    }

    public void fenceInteract() {
        // Do nothing for now.
    }

    public void castleInteract() {
        // Do nothing for now.
    }

    public void safehouseInteract() {
        // Do nothing for now.
    }

    public void towncentreInteract() {
        // Do nothing for now.
    }

    public void watchtowerInteract() {
        // Do nothing for now.
    }

    /**
     * getter method
     *
     * @return InventoryManager of the Building
     */
    public InventoryManager getInventoryManager() {
        return this.inventoryManager;
    }

    /**
     * setter method
     *
     * @param inventoryManager InventoryManager of the Building
     */
    public void setInventoryManager(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    /**
     * Add item into inventory of the building
     *
     * @param item the item added into inventory of the building
     * @return true if added successfully, otherwise false
     */
    public boolean AddInventory(Item item) {
        return this.inventoryManager.add(item);
    }

    /**
     * @param item
     */
    public void quickAccessRemove(Item item) {
        this.inventoryManager.quickAccessRemove(item.getName());
    }

    /**
     * Fire a projectile in the position that the mouse is in.
     *
     * @param enemyPosition The position of the enemy.
     */
    protected void fireProjectile(HexVector enemyPosition) {
        HexVector unitDirection = enemyPosition.subtract(this.getPosition()).normalized();

        setCurrentState(AnimationRole.ATTACK);

        // Make projectile move toward the angle
        // Spawn projectile in front of character
        Projectile projectile;
        if (this.itemSlotSelected == 1)
            projectile = new Projectile(enemyPosition, ((Weapon) equippedItem).getTexture("attack"), "hitbox",
                    new HexVector(position.getCol() + 0.5f + 1.5f * unitDirection.getCol(),
                            position.getRow() + 0.5f + 1.5f * unitDirection.getRow()), ((Weapon) equippedItem).getDamage(), 1,
                    equippedItem.getName().equals("bow") ? 10 : 0);
        else
            projectile = new Projectile(enemyPosition, ((Weapon) equippedItem).getTexture("attack"), "hitbox",
                    new HexVector(position.getCol() + 0.5f + 1.5f * unitDirection.getCol(),
                            position.getRow() + 0.5f + 1.5f * unitDirection.getRow()), ((Weapon) equippedItem).getDamage(), 1,
                    0);

        // Add the projectile entity to the game world.
        GameManager.get().getWorld().addEntity(projectile);

    }

}
