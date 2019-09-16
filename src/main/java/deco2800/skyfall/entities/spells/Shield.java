package deco2800.skyfall.entities.spells;

import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.util.HexVector;

public class Shield extends Spell implements Animatable {


    //Keep a reference to the maincharacter so this spell can stay on it's position.
    protected MainCharacter mc;

    private int shieldTime = 0;

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
    public Shield(HexVector movementPosition, String textureName, String objectName,
                  float col, float row, int damage, float speed, int range) {

        super(movementPosition, textureName, objectName, col, row, damage, speed, range);

        //Shield must stay in place on the player.
        this.range = 0;
        this.speed = 0;
        this.manaCost = 30;
        this.mc = GameManager.getManagerFromInstance(GameMenuManager.class).getMainCharacter();
        if (this.mc != null) {
            this.mc.setInvincible(true);
        }
        setCurrentState(AnimationRole.STILL);
    }

    @Override
    public void onTick(long tick) {
        super.onTick(tick);
        this.setPosition(this.mc.getCol(),this.mc.getRow(),this.mc.getHeight());

        if (shieldTime < 80) {
            shieldTime += 20;
            setCurrentState(AnimationRole.ATTACK);
        } else {
            setCurrentState(AnimationRole.STILL);
        }
    }

    @Override
    public void destroy() {
        this.mc.setInvincible(false);
        super.destroy();
    }

    @Override
    public void configureAnimations() {
        // Shield spell animation
        addAnimations(AnimationRole.ATTACK, Direction.DEFAULT,
                new AnimationLinker("Spells_Shield_Anim",
                        AnimationRole.ATTACK, Direction.DEFAULT, true, true));

        addAnimations(AnimationRole.STILL, Direction.DEFAULT,
                new AnimationLinker("Spells_Shield_Still",
                        AnimationRole.STILL, Direction.DEFAULT, true, true));
    }
}
