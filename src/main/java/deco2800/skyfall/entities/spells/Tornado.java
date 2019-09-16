package deco2800.skyfall.entities.spells;

import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.EnemyEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.util.HexVector;

import java.util.List;

public class Tornado extends Spell {

    //MainCharacter instance.
    private MainCharacter mc;

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
    public Tornado(HexVector movementPosition, String textureName, String objectName,
                  float col, float row, int damage, float speed, int range) {

        super(movementPosition, textureName, objectName, col, row, damage, speed, range);

        this.mc = GameManager.getManagerFromInstance(GameMenuManager.class).getMainCharacter();

        if (this.mc != null) {
            this.position = new HexVector(this.mc.getCol(), this.mc.getRow());
        }

        this.manaCost = 10;
    }

    @Override
    public void onTick(long tick) {
        super.onTick(tick);

        setCurrentState(AnimationRole.ATTACK);

        //Loop through enemies.
        List<AbstractEntity> entities =  GameManager.get().getWorld().getEntities();

        for (AbstractEntity entity : entities) {
            if (entity instanceof EnemyEntity
                    && this.position.isCloseEnoughToBeTheSameByDistance(entity.getPosition(),1)) {
                //If close enough, deal damage to the enemy over time.
                ((EnemyEntity) entity).takeDamage(this.getDamage());
                this.destroy();
            }
        }

    }

    @Override
    public void configureAnimations() {
        // Tornado spell animation
        addAnimations(AnimationRole.ATTACK, Direction.DEFAULT,
                new AnimationLinker("Spells_Tornado_Anim",
                        AnimationRole.ATTACK, Direction.DEFAULT, true, true));
    }

    @Override
    public void setDirectionTextures() {
    }
}
