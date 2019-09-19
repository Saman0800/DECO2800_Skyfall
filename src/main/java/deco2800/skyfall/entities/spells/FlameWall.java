package deco2800.skyfall.entities.spells;

import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.AbstractEnemy;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.EnemyEntity;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.util.HexVector;

import java.util.List;

public class FlameWall extends Spell {

    //How long since the spell last dealt damage.
    protected int ticksSinceAttacked = 0;
    //How long it takes to cooldown.
    private int attackCD = 10;

    /**
     * Construct a new spell.
     *
     * @param movementPosition The position the spell moves to.
     * @param textureName      The name of the texture to render.
     * @param objectName       The name to call this object.
     * @param col              The column to spawn this projectile in.
     * @param row              The row to spawn this projectile in.
     * @param damage           The damage this projectile will deal on hit.
     * @param speed            How fast this projectile is travelling.
     * @param range            How far this spell can be cast.
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

        setCurrentState(AnimationRole.ATTACK);

        if (this.ticksSinceAttacked > attackCD) {
            //Loop through enemies.
            List<AbstractEntity> entities =  GameManager.get().getWorld().getEntities();

            for (AbstractEntity entity : entities) {
                //If close enough, deal damage to the enemy over time.
                if (entity instanceof AbstractEnemy &&
                        this.position.isCloseEnoughToBeTheSameByDistance(entity.getPosition(),1)) {
                        ((AbstractEnemy) entity).takeDamage(this.getDamage());
                    }
                }
            this.ticksSinceAttacked = 0;
        }

    }

    @Override
    public void configureAnimations() {
        // Fire spell animation
        addAnimations(AnimationRole.ATTACK, Direction.DEFAULT,
                new AnimationLinker("Spells_Fire_Anim",
                        AnimationRole.ATTACK, Direction.DEFAULT, true, true));
    }

    @Override
    public void setDirectionTextures() {
    }
}
