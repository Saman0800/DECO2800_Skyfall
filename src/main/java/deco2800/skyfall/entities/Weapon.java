package deco2800.skyfall.entities;

import com.badlogic.gdx.graphics.Texture;

/**
 * A generic type of weapon that can be extended to
 * MeleeWeapon or RangeWeapon that will contain more specific logic.
 *
 * Created by Christopher Poli on 10/08/2019
 */
public class Weapon extends AbstractEntity implements Item, IWeapon {

    private String name;
    private String weaponType;
    private String damageType;
    private float attackRate;
    private float damage;
    private float durability;

    public Weapon() {
        //TODO: Decide if want to have setters for instance variables.
    }

    public Weapon(float col, float row, String name, String weaponType, String damageType,
                  float damage, float attackRate, float durability) {
        super(col, row, 5);
        System.out.println("Constructing new Weapon class.");

        this.name = name;
        this.weaponType = weaponType;
        this.durability = durability;
        this.damageType = damageType;
        this.damage = damage;
        this.attackRate = attackRate;

        this.setTexture("wood_sword_small");
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

    @Override
    public void onTick(long i) {
    }
}
