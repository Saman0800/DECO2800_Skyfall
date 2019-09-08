package deco2800.skyfall.entities;

import deco2800.skyfall.worlds.Tile;

/**
 * A generic type of weapon to be used in the game
 */
public class Weapon extends StaticEntity implements Item, IWeapon {

    private String name;
    private String weaponType;
    private String damageType;
    private float attackRate;
    private float damage;
    private float durability;

    public Weapon(Tile tile, boolean obstructed, String name,
                  String weaponType, String damageType,
                  float damage, float attackRate, float durability) {
        super(tile, 5, name + "_tex", obstructed);

        System.out.println("Constructing new Weapon class.");

        this.name = name;
        this.weaponType = weaponType;
        this.durability = durability;
        this.damageType = damageType;
        this.damage = damage;
        this.attackRate = attackRate;
    }

    public Weapon(String name, String weaponType, String damageType,
                  float damage, float attackRate, float durability) {
        System.out.println("Constructing new Weapon class.");

        this.name = name;
        this.weaponType = weaponType;
        this.durability = durability;
        this.damageType = damageType;
        this.damage = damage;
        this.attackRate = attackRate;
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
    public String getWeaponType() {
        return this.weaponType;
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
    public Number getDurability() {
        return this.durability;
    }

    /**
     * @return the attack rate of the weapon
     */
    public Number getAttackRate() {
        return this.attackRate;
    }

    /**
     * @return the amount of damage dealt with the weapon
     */
    public Number getDamage() {
        return this.damage;
    }

    @Override
    public void onTick(long i) {
        // Auto-generated method stub
    }
}
