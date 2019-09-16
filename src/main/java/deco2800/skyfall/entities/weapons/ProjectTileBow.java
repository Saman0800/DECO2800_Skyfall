package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.entities.Projectile;
import deco2800.skyfall.util.HexVector;

public class ProjectTileBow extends Projectile implements IWeapon {

    private int durability =100;
    /**
     * Construct a new projectile.
     *
     * @param movementPosition
     * @param textureName      The name of the texture to render.
     * @param objectName       The name to call this object.
     * @param col              The column to spawn this projectile in.
     * @param row              The row to spawn this projectile in.
     * @param damage           The damage this projectile will deal on hit.
     * @param speed            How fast this projectile is travelling.
     * @param range
     */
    public ProjectTileBow(HexVector movementPosition, String textureName, String objectName, float col, float row, int damage, float speed, int range) {
        super(movementPosition, textureName, objectName, col, row, damage, speed, range);
    }



    /**
     * Get weapon type
     *
     * @return the type of a weapon
     */
    @Override
    public String getWeaponType() {
        return "range";
    }

    /**
     * Get durability of the weapon
     *
     * @return a number represent the durability of the weapon
     * on the scale of 0 to 10
     */
    @Override
    public int getDurability() {
        return this.durability;
    }

    /**
     * Get attack rate of the weapon
     *
     * @return a number represent the attack rate of the weapon
     * on the scale of 1 to 5
     */
    @Override
    public int getAttackRate() {
        return 1;
    }

    /**
     * Get damage type of the weapon
     *
     * @return a String represent the type of damage
     * the type of damage will be for specific weapon such as
     * sword and axe weapon will have slash effect
     * bow and spear will have splash effect
     */
    @Override
    public String getDamageType() {
        return "splash";
    }
}

