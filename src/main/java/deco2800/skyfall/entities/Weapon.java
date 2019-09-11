package deco2800.skyfall.entities;

import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.resources.Item;

/**
 * Weapon to be used by the Main Character and stored as an inventory item
 */
public class Weapon extends StaticEntity implements Item, IWeapon {

    // Weapon attributes
    private String name;
    private String weaponType;
    private String damageType;
    private int attackRate;
    private int damage;
    private int durability;
    private Boolean carryable;
    private HexVector position;
    private Boolean exchangeable;

    /**
     * Weapon constructor used in the game world
     */
    public Weapon(Tile tile, boolean obstructed, String name,
                  String weaponType, String damageType,
                  int damage, int attackRate, int durability) {
        super(tile, 5, name + "_tex", obstructed);

        this.name = name;
        this.weaponType = weaponType;
        this.durability = durability;
        this.damageType = damageType;
        this.damage = damage;
        this.attackRate = attackRate;
        this.carryable = true;
        this.position = tile.getCoordinates();
        this.exchangeable = false;
    }

    /**
     * Weapon constructor used for testing
     */
    public Weapon(String name, String weaponType, String damageType,
                  int damage, int attackRate, int durability) {
        this.name = name;
        this.weaponType = weaponType;
        this.durability = durability;
        this.damageType = damageType;
        this.damage = damage;
        this.attackRate = attackRate;
        this.carryable = true;
        this.exchangeable = false;
    }

    public Weapon(String texture) {
        this.setTexture(texture);
        this.name = "no_weapon";
    }

//    public Weapon(Tile tile, boolean obstructed, String name) {
//        super(tile, 5, name + "_tex", obstructed);
//    }

    /**
     * @return name of weapon
     */
    public String getName() {
        return this.name;
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
     * Returns a description about the weapon
     * @return a description about the weapon
     */
    public String getDescription() {
        return this.toString();
    }

    @Override
    /**
     * A paragraph describing the weapon
     */
    public String toString() {
        return this.getName() + " is a " + this.getSubtype() + " weapon which" +
                " can be used to help the Main Character defeat enemies." +
                " It has deals " + this.getDamage() + " " + this.getDamageType()
                + " damages each time it is used. It also has an attack rate " +
                "of: " + this.getAttackRate() + " and a durability of: " +
                this.getDurability() + " before it become useless. "
                + this.getName() + "is carryable, but exchangeable.";
    }

    @Override
    public void onTick(long i) {
        // Auto-generated method stub
    }

    @Override
    public Weapon newInstance(Tile tile) {
        return new Weapon(tile, this.isObstructed(), this.name, this
                        .weaponType, this.damageType, this.damage, this
                        .attackRate, this.durability);
    }
}
