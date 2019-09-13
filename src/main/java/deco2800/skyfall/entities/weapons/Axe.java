package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;

public class Axe extends Weapon implements Item, IWeapon {

    // Weapon attributes
    private String name;
    private String weaponType;
    private String damageType;
    private int attackRate;
    private int damage;
    private int durability;

    public Axe(Tile tile, boolean obstructed) {
        super(tile, "axe_tex", obstructed);

        this.name = "axe";
        this.weaponType = "melee";
        this.durability = 10;
        this.damageType = "slash";
        this.damage = 4;
        this.attackRate = 4;
    }

    public Axe() {
        this.name = "axe";
    }

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

    /**
     * @return a new instance of sword
     */
    public Axe newInstance(Tile tile) {
        return new Axe(tile, this.isObstructed());
    }

    @Override
    public void onTick(long i) {
        // Auto-generated method stub
    }

    @Override
    public void use(HexVector position){

    }
}

