package deco2800.skyfall.entities.spells;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.Projectile;
import deco2800.skyfall.util.HexVector;



public class Spell extends Projectile {

    /**
     * Construct a new spell.
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
    public Spell(HexVector movementPosition, String textureName, String objectName, float col,
                 float row, int damage, float speed, int range) {
        super(movementPosition, textureName, objectName, col, row, damage, speed, range);

        this.setTexture("flame_wall_placeholder");
    }

    @Override
    public void onTick(long tick) {
        super.onTick(tick);

        //Check for enemies and deal 1 damage.
    }

    @Override
    public boolean collidesWith(AbstractEntity entity) {
        System.out.println("COLLIDED WITH");
        return false;
    }
}
