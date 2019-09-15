package deco2800.skyfall.entities.spells;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.Projectile;
import deco2800.skyfall.util.HexVector;



public class Spell extends Projectile {

    //Default mana cost.
    protected int manaCost = 20;

    /**
     * Construct a new spell.
     *
     * @param movementPosition The position to move to.
     * @param textureName      The name of the texture to render.
     * @param objectName       The name to call this object.
     * @param col              The column to spawn this projectile in.
     * @param row              The row to spawn this projectile in.
     * @param damage           The damage this projectile will deal on hit.
     * @param speed            How fast this projectile is travelling.
     * @param range            How far the projectile can move.
     */
    public Spell(HexVector movementPosition, String textureName, String objectName, float col,
                 float row, int damage, float speed, int range) {
        super(movementPosition, textureName, objectName, col, row, damage, speed, range);
    }

    public int getManaCost() {
        return this.manaCost;
    }

}
