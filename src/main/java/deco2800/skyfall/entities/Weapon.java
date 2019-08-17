package deco2800.skyfall.entities;

/**
 * A generic type of weapon that can be extended to
 * MeleeWeapon or RangeWeapon that will contain more specific logic.
 *
 * Created by Christopher Poli on 10/08/2019
 */
public class Weapon implements Item, IWeapon {

    private String name, weaponType, damageType;
    private float attackRate, damage, durability;


    public Weapon() {
        //TODO: Decide if want to have setters for instance variables.
    }

    public Weapon(String name, String weaponType, String damageType, float damage, float attackRate, float durability) {

        System.out.println("Constructing new Weapon class.");

        this.name = name;
        this.weaponType = weaponType;
        this.durability = durability;
        this.damageType = damageType;
        this.damage = damage;
        this.attackRate = attackRate;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getWeaponType() {
        return this.weaponType;
    }

    @Override
    public String getDamageType() {
        return this.damageType;
    }

    @Override
    public Number getDurability() {
        return this.durability;
    }

    @Override
    public Number getAttackRate() {
        return this.attackRate;
    }

    @Override
    public Number getDamage() {
        return this.damage;
    }


}
