package deco2800.skyfall.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.Box2D;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.util.Collider;

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
    public static final int LIFE_TIME_TICKS = 20;

    /**
     * Collider of this object.
     */
    private Collider collider;

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
    private long ticksAliveFor = 0;

    /**
     * Construct a new projectile.
     * @param textureName The name of the texture to render.
     * @param objectName The name to call this object.
     * @param col The column to spawn this projectile in.
     * @param row The row to spawn this projectile in.
     * @param damage The damage this projectile will deal on hit.
     * @param speed How fast this projectile is travelling.
     */
    public Projectile(String textureName, String objectName, float col, float row, int damage, float speed) {

        super(col,row,3);

        this.damage = damage;
        this.speed = speed;

        this.setTexture(textureName);
        this.setObjectName(objectName);
    }

    /**
     * Get the damage this projectile will deal.
     * @return The amount of damage this projectile will deal.
     */
    public int getDamage() {
        return this.damage;
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

        //TODO add forward movement task on each tick.
        this.setPosition(this.position.getCol()+0.1f,this.position.getRow(),1);
    }



}
