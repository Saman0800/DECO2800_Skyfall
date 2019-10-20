package deco2800.skyfall.entities.weapons;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.resources.Blueprint;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;

/**
 * Weapon to be used by the Main Character and stored as an inventory item
 */
public abstract class Weapon extends StaticEntity implements Item, IWeapon, Blueprint {

    // Weapon attributes
    private String name;
    private String weaponType;
    private String damageType;
    private String texture;
    private int attackRate;
    private int damage;
    private int durability;
    private boolean carryable;
    private boolean exchangeable;
    private boolean equippable;
    private HexVector position;

    private int woodRequired;
    private int stoneRequired;
    private int metalRequired;
    private int itemCost;

    // Logger to show messages
    private final Logger logger = LoggerFactory.getLogger(Weapon.class);

    /**
     * Weapon constructor used in the game world
     */
    public Weapon(Tile tile, String texture, boolean obstructed, String name) {
        super(tile, 5, texture, obstructed);

        changeCollideability(false);

        this.name = name;
        this.texture = texture;
        this.carryable = true;
        this.exchangeable = false;
        this.equippable = true;
        this.position = tile.getCoordinates();
    }

    protected void setCostValues(int woodRequired, int stoneRequired, int metalRequired, int itemCost) {

        this.woodRequired = woodRequired;
        this.stoneRequired = stoneRequired;
        this.metalRequired = metalRequired;
        this.itemCost = itemCost;

        return;
    }

    /**
     * Simple constructor with only weapon name, used for testing purposes
     */
    public Weapon(String name) {
        this.name = name;
    }

    /**
     * @return name of weapon
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return texture of weapon
     */
    public String getTexture(String mode) {
        switch (mode) {
        case "attack":
            return this.getName() + "_attack";
        case "display":
            return this.getName() + "_display_inv";
        default:
            return this.texture;
        }
    }

    /**
     * Returns the number of wood required for the item.
     *
     * @return The name of the item
     */
    @Override
    public int getRequiredWood() {
        return this.woodRequired;
    }

    /**
     * Returns the number of stones required for the item.
     *
     * @return The name of the item
     */
    @Override
    public int getRequiredStone() {
        return this.stoneRequired;
    }

    /**
     * Returns the number of metal required for the item.
     *
     * @return The name of the item
     */
    @Override
    public int getRequiredMetal() {
        return this.metalRequired;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeaponType(String weaponType) {
        this.weaponType = weaponType;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    @Override
    public void setTexture(String texture) {
        this.texture = texture;
    }

    public void setAttackRate(int attackRate) {
        this.attackRate = attackRate;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    /**
     * Returns a map of the name of the required resource and the required number of
     * each resource to create the item.
     *
     * @return a hashamp of the required resources and their number.
     */
    @Override
    public Map<String, Integer> getAllRequirements() {

        Map<String, Integer> allRequirements = new HashMap<>();
        allRequirements.put("Wood", this.woodRequired);
        allRequirements.put("Stone", this.stoneRequired);
        allRequirements.put("Metal", this.metalRequired);

        return allRequirements;
    }

    /**
     * @return - cost of building the building
     */
    @Override
    public int getCost() {
        return this.itemCost;
    }

    /**
     * @return type of weapon, melee or range
     */
    public String getSubtype() {
        return this.weaponType;
    }

    /**
     * @return Subtype of weapon
     */
    public String getWeaponType() {
        return this.getSubtype();
    }

    /**
     * @return type of damage, slash or splash
     */
    public String getDamageType() {
        return this.damageType;
    }

    /**
     * @return the durability of the weapon
     */
    public int getDurability() {
        return this.durability;
    }

    /**
     * Reduces durability of weapon by 1
     */
    public void decreaseDurability() {
        this.durability -= 1;
    }

    /**
     * If the durability of the weapon have durability bigger than 0
     * 
     * @return whether to weapon is still usable
     */
    public boolean isUsable() {
        return this.getDurability() > 0;
    }

    /**
     * @return the attack rate of the weapon
     */
    public int getAttackRate() {
        return this.attackRate;
    }

    /**
     * @return the amount of damage dealt with the weapon
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Returns a description about the weapon
     * 
     * @return a description about the weapon
     */
    public String getDescription() {
        return this.getName() + " is a " + this.getSubtype() + " weapon which"
                + " can be used to help the Main Character defeat enemies." + " It has deals " + this.getDamage() + " "
                + this.getDamageType() + " damages each time it is used. It also has an attack rate " + "of: "
                + this.getAttackRate() + " and a durability of: " + this.getDurability() + " before it become useless. "
                + this.getName() + "is carryable, but exchangeable.";
    }

    /**
     * A sentence describing the weapon (type:name)
     */
    @Override
    public String toString() {
        return this.getSubtype() + ":" + this.getName();
    }

    /**
     * Returns whether or not the weapon can be carried
     * 
     * @return True if the item can be carried in the inventory, false if it is
     *         consumed immediately
     */
    public boolean isCarryable() {
        return this.carryable;
    }

    /**
     * Returns the co-ordinates of the tile the weapon is on
     * 
     * @return the co-ordinates of the tile the weapon is on
     */
    public HexVector getCoords() {
        return this.position;
    }

    /**
     * Returns whether or not the weapon can be exchanged
     * 
     * @return True if the weapon can be exchanged, false otherwise
     */
    public boolean isExchangeable() {
        return this.exchangeable;
    }

    /**
     * Returns whether or not the weapon can be equipped from the inventory
     * 
     * @return True if the weapon can be equipped, false otherwise
     */
    public boolean isEquippable() {
        return this.equippable;
    }

    @Override
    public void onTick(long i) {
        // Auto-generated method stub
    }

    @Override
    public void use(HexVector position) {
        // Use the specific function associated with the item
        int dura = this.getDurability();
        logger.warn(String.format("Durability: %d", dura));
        this.decreaseDurability();
    }




}
