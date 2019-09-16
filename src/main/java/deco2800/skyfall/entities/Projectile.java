package deco2800.skyfall.entities;

import com.badlogic.gdx.math.Vector2;
import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.util.HexVector;

import java.util.List;

/**
 * An entity that is shot from a weapon.
 * It travels in a straight direction from the angle of the shot.
 *
 * E.g. a bow shoots an arrow.
 */
public class Projectile extends AgentEntity implements Animatable {

    /**
     * How many game ticks all projectiles survive for before being removed.
     */
    public static final int LIFE_TIME_TICKS = 100;

    /**
     * The amount of damage this projectile deals.
     */
    private int damage;

    /**
     * Speed in which projectile is travelling.
     */
    protected float speed;

    /**
     * How many game ticks this object has survived.
     */
    protected long ticksAliveFor = 0;

    /**
     *
     */
    private HexVector movementPosition;

    /**
     *
     */
    private String textureName;

    /**
     * How far this projectile will travel.
     */
    protected int range;

    /**
     * Construct a new projectile.
     * @param textureName The name of the texture to render.
     * @param objectName The name to call this object.
     * @param col The column to spawn this projectile in.
     * @param row The row to spawn this projectile in.
     * @param damage The damage this projectile will deal on hit.
     * @param speed How fast this projectile is travelling.
     */
    public Projectile(HexVector movementPosition,String textureName, String objectName,
                      float col, float row, int damage, float speed, int range) {

        super(col,row,3,speed);

        this.damage = damage;
        this.speed = speed;
        this.movementPosition = movementPosition;
        this.range = range;

        this.textureName = textureName;
        this.setTexture(textureName);
        this.setObjectName(objectName);

        //Position the projectile correctly.
        position.moveToward(movementPosition,speed);

        //fixture.setFilterData()

        configureAnimations();

        //TODO: rotate sprite in angle facing.
    }

    /**
     * Get the damage this projectile will deal.
     * @return The amount of damage this projectile will deal.
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Get the range this projectile will travel.
     * @return The range this projectile will travel.
     */
    public int getRange() {
        return this.range;
    }

    /**
     * Checks how long the projectile has been alive
     * for and deletes after a set number of game ticks.
     * @param tick Current game tick.
     */
    @Override
    public void onTick(long tick) {

        if (toBeDestroyed) {
            destroy();
        }

        //Each game tick add to counter.
        this.ticksAliveFor++;

        //If this projectile has been alive for longer than the set number of ticks, remove it from the world.
        if (this.ticksAliveFor > LIFE_TIME_TICKS) {
            this.destroy();
        }

        if (this.range >= 1) {
            position.moveToward(movementPosition,speed);
            getBody().setTransform(position.getCol(), position.getRow(), getBody().getAngle());
        }

    }

    private boolean toBeDestroyed = false;
    @Override
    public void handleCollision(Object other) {
        if (other instanceof EnemyEntity) {
            ((EnemyEntity) other).takeDamage(this.getDamage());
            ((EnemyEntity) other).setAttacked(true);
            toBeDestroyed = true;
            ((EnemyEntity)other).getBody().setLinearVelocity(
                    (((EnemyEntity)other).getBody().getLinearVelocity()
                    .lerp(new Vector2(0.f, 0.f), 0.5f)));
        }
    }

    /**
     * Remove the projectile from the game world.
     */
    private boolean beenDestroyed = false;
    public void destroy() {
        if (!beenDestroyed) {
            GameManager.get().getWorld().removeEntity(this);
            getBody().getWorld().destroyBody(getBody());
            beenDestroyed = true;
            setCurrentState(AnimationRole.NULL);
        }
    }

    @Override
    public void configureAnimations() {
    }

    @Override
    public void setDirectionTextures() {
    }
}
