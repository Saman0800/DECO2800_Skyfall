package deco2800.skyfall.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.Box2D;
import deco2800.skyfall.managers.GameManager;

/**
 * An entity that is shot from a weapon.
 * It travels in a straight direction from the angle of the shot.
 *
 * E.g. a bow shoots an arrow.
 */
public class Projectile extends AbstractEntity {

    /**
     * How many game ticks all projectiles survive for before being removed.
     */
    public static final int LIFE_TIME_TICKS = 50;

    /**
     * Collider of this object.
     */
    private Box2D collider;

    /**
     * The amount of damage this projectile deals.
     */
    private int damage;

    /**
     * Which direction this projectile is travelling in.
     */
    private float direction;

    /**
     * Speed in which projectile is travelling.
     */
    protected float speed;

    /**
     * How many game ticks this object has survived.
     */
    private long ticksAliveFor = 0;

    /**
     * Construct a new projectile class.
     * @param damage
     */
    public Projectile(String textureName, String objectName, float col, float row, int damage) {

        super(col,row,3);

        this.damage = damage;

        this.setTexture("slash");
        this.setObjectName(objectName);
    }

    /**
     * Checks how long the projectile has been alive
     * for and deletes after a set number of game ticks.
     * @param tick Current game tick.
     */
    @Override
    public void onTick(long tick) {

        //Each game tick add to counter.
        this.ticksAliveFor++;

        //If this projectile has been alive for longer than the set number of ticks, remove it from the world.
        if (this.ticksAliveFor > LIFE_TIME_TICKS) {
            GameManager.get().getWorld().removeEntity(this);
        }
    }



}
