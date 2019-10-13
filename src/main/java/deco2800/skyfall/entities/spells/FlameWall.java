package deco2800.skyfall.entities.spells;

import com.badlogic.gdx.Input;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.enemies.Enemy;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.util.HexVector;

import java.util.List;

public class FlameWall extends Spell {

    //How long since the spell last dealt damage.
    protected int ticksSinceAttacked = 0;
    //How long it takes to cool down.
    private int attackCD = 10;

    /**
     * Key sequence required to cast this spell.
     */
    protected static final int[] keySequence = new int[] {
            Input.Keys.UP,
            Input.Keys.DOWN,
            Input.Keys.UP,
            Input.Keys.DOWN,
            Input.Keys.LEFT
    };

    /**
     * Construct a new spell.
     *
     * @param movementPosition The position the spell moves to.
     * @param textureName      The name of the texture to render.
     * @param objectName       The name to call this object.
     * @param startPosition    The position to spawn this projectile in.
     * @param damage           The damage this projectile will deal on hit.
     * @param speed            How fast this projectile is travelling.
     * @param range            How far this spell can be cast.
     */
    public FlameWall(HexVector movementPosition, String textureName, String objectName,
                     HexVector startPosition, int damage, float speed, int range) {

        super(movementPosition, textureName, objectName, startPosition, damage, speed, range);

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
                if (entity instanceof Enemy &&
                        this.position.isCloseEnoughToBeTheSameByDistance(entity.getPosition(),1)) {
                        ((Enemy) entity).takeDamage(this.getDamage());
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

}
