package deco2800.skyfall.entities;

import com.badlogic.gdx.graphics.Texture;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;

import java.util.Map;

/**
 * A generic type of weapon that can be extended to
 * MeleeWeapon or RangeWeapon that will contain more specific logic.
 *
 * Created by Christopher Poli on 10/08/2019
 */
public class Weapon extends StaticEntity implements Item, IWeapon {

    private String name;
    private String weaponType;
    private String damageType;
    private float attackRate;
    private float damage;
    private float durability;

    public Weapon() {
        //TODO: Decide if want to have setters for instance variables.
    }

    public Weapon(Tile tile, boolean obstructed, String name, String weaponType, String damageType,
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

//    public Map<HexVector, String>
}
