package deco2800.skyfall.entities.spells;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.EnemyEntity;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.util.HexVector;

import java.util.ArrayList;
import java.util.List;

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

        if (this.ticksSinceAttacked > ATTACK_TIME_CD) {
            ///deal a damage to entities on this tile.

            //Loop through enemies.
            List<AbstractEntity> entities =  GameManager.get().getWorld().getEntities();

            //TODO can add a kd tree or similar to only select enemies in the target area.
            for (AbstractEntity entity : entities) {
                if (entity instanceof EnemyEntity) {
                    //If close enough, deal damage to the enemy over time.
                    if (this.position.isCloseEnoughToBeTheSameByDistance(entity.getPosition(),1)) {
                        ((EnemyEntity) entity).takeDamage(this.getDamage());
                    }
                }
            }
            //if(this.position.isCloseEnoughToBeTheSameByDistance()
            this.ticksSinceAttacked = 0;
        }

    }
}
