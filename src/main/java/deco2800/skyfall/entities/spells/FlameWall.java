package deco2800.skyfall.entities.spells;

import deco2800.skyfall.util.HexVector;

public class FlameWall extends Spell {

    public int ticksSinceAttacked = 0;
    public int ATTACK_TIME_CD = 10;
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
    public FlameWall(HexVector movementPosition, String textureName, String objectName,
                     float col, float row, int damage, float speed, int range) {
        super(movementPosition, textureName, objectName, col, row, damage, speed, range);

        this.manaCost = 20;
    }

    @Override
    public void onTick(long tick) {
        super.onTick(tick);

        //Each game tick add to counter.
        this.ticksSinceAttacked++;

        //If this projectile has been alive for longer than the set number of ticks, remove it from the world.
        if (this.ticksSinceAttacked > LIFE_TIME_TICKS) {
            ///deal a damage to entities on this tile.

        }

    }
}
