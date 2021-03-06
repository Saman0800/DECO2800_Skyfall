package deco2800.skyfall.entities;

import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.entities.enemies.Enemy;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.util.HexVector;
import org.javatuples.Pair;

/**
 * An entity that is shot from a weapon. It travels in a straight direction from
 * the angle of the shot.
 *
 * E.g. a bow shoots an arrow.
 */
public class Projectile extends AgentEntity implements Animatable {

    /**
     * How many game ticks a projectile will survive for before being removed.
     */
    private int lifespan = 40;

    /**
     * The amount of damage this projectile deals.
     */
    private int damage;

    /**
     * How many game ticks this object has survived.
     */
    protected long ticksAliveFor = 0;

    private HexVector movementPosition;

    /**
     * The texture of the projectile.
     */
    private String textureName;

    /**
     * How far this projectile will travel.
     */
    protected float range;

    /**
     * Construct a new projectile.
     * 
     * @param textureName The name of the texture to render.
     * @param objectName  The name to call this object.
     * @param startPosition The position to spawn this projectile in.
     * @param damage      The damage this projectile will deal on hit.
     * @param speed       How fast this projectile is travelling.
     */

    public Projectile(HexVector movementPosition, String textureName, String objectName,
                      HexVector startPosition, int damage, float speed, Pair<Float, Integer> rangeLifespan) {
        super(startPosition.getCol(), startPosition.getRow(), 3, speed);

        this.damage = damage;
        this.speed = speed;
        this.movementPosition = movementPosition;
        this.range = rangeLifespan.getValue0();
        this.lifespan = rangeLifespan.getValue1();
        this.textureName = textureName;

        this.setTexture(textureName);
        this.setObjectName(objectName);

        // Sets the filters so that Projectile doesn't collide with MainCharacter.
        for (Fixture fix : getBody().getFixtureList()) {
            Filter filter = fix.getFilterData();
            filter.categoryBits = (short) 0x4; // Set filter category to 2
            filter.maskBits = (short) (0xFFFF ^ 0x2); // remove mask category 2 (Main Character)
            fix.setFilterData(filter);
        }

        configureAnimations();
    }

    /**
     * Get the damage this projectile will deal.
     * 
     * @return The amount of damage this projectile will deal.
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Get the range this projectile will travel.
     * 
     * @return The range this projectile will travel.
     */
    public float getRange() {
        return this.range;
    }

    public String getTextureName() {
        return textureName;
    }

    /**
     * Checks how long the projectile has been alive for and deletes after a set
     * number of game ticks.
     * 
     * @param tick Current game tick.
     */
    @Override
    public void onTick(long tick) {

        if (toBeDestroyed) {
            destroy();
        }

        // Each game tick add to counter.
        this.ticksAliveFor++;

        // If this projectile has been alive for longer than the set number of ticks,
        // remove it from the world.
        if (this.ticksAliveFor > lifespan) {
            this.destroy();
        }

        if (range > 0.f) {
            if (range < speed) {
                position.moveToward(movementPosition, range);
            } else {
                position.moveToward(movementPosition, speed);
            }

            range = Math.max(range - speed, 0.f);

            getBody().setTransform(position.getCol(), position.getRow(),
            getBody().getAngle());
        }

    }

    private boolean toBeDestroyed = false;

    @Override
    public void handleCollision(Object other) {
        if (other instanceof Enemy) {
            ((Enemy) other).takeDamage(this.getDamage());
            ((Enemy) other).setHurt(true);
            toBeDestroyed = true;
            HexVector knockbackDir = movementPosition.subtract(((Enemy)other).getPosition()).normalized().times(damage);
            ((Enemy) other).getBody().setLinearVelocity(knockbackDir.toVector2());
        }
    }

    /**
     * Remove the projectile from the game world.
     */
    protected boolean beenDestroyed = false;

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
        // Do nothing currently.
    }

    @Override
    public void setDirectionTextures() {
        // Do nothing currently.
    }
}
