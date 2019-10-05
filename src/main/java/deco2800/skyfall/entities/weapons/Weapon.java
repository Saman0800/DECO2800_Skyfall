package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.resources.Blueprint;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.resources.Item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    // Logger to show messages
    private final Logger logger = LoggerFactory.getLogger(Weapon.class);

    /**
     * Weapon constructor used in the game world
     */
    public Weapon(Tile tile, String texture, boolean obstructed, String name,
                  String weaponType, String damageType, int attackRate,
                  int damage, int durability) {
        super(tile, 5, texture, obstructed);

        changeCollideability(false);

        this.name = name;
        this.texture = texture;
        this.weaponType = weaponType;
        this.durability = durability;
        this.damageType = damageType;
        this.damage = damage;
        this.attackRate = attackRate;
        this.carryable = true;
        this.exchangeable = false;
        this.equippable = true;
        this.position = tile.getCoordinates();
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
        switch(mode) {
            case "attack":
                return this.getName() + "_attack";
            case "display":
                return this.getName() + "_display_inv";
            default:
                return this.texture;
        }
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
     * @return a description about the weapon
     */
    public String getDescription() {
        return this.getName() + " is a " + this.getSubtype() + " weapon which" +
                " can be used to help the Main Character defeat enemies." +
                " It has deals " + this.getDamage() + " " + this.getDamageType()
                + " damages each time it is used. It also has an attack rate " +
                "of: " + this.getAttackRate() + " and a durability of: " +
                this.getDurability() + " before it become useless. "
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
     * @return True if the item can be carried in the inventory, false
     * if it is consumed immediately
     */
    public Boolean isCarryable() {
        return this.carryable;
    }

    /**
     * Returns the co-ordinates of the tile the weapon is on
     * @return the co-ordinates of the tile the weapon is on
     */
    public HexVector getCoords() {
        return this.position;
    }

    /**
     * Returns whether or not the weapon can be exchanged
     * @return True if the weapon can be exchanged, false otherwise
     */
    public Boolean isExchangeable() {
        return this.exchangeable;
    }

    /**
     * Returns whether or not the weapon can be equipped from the inventory
     * @return True if the weapon can be equipped, false otherwise
     */
    public Boolean isEquippable() {
        return this.equippable;
    }

    @Override
    public void onTick(long i) {
        // Auto-generated method stub
    }

    @Override
    public void use(HexVector position) {
        // Use the specific function associated with the item
        logger.warn("Durability: " + this.getDurability());
        this.decreaseDurability();
    }
}
