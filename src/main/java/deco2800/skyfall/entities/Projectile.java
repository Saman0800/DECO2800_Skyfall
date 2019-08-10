package deco2800.skyfall.entities;

import com.badlogic.gdx.physics.box2d.Box2D;

/**
 * An entity that is shot from a weapon.
 * It travels in a straight direction from the angle of the shot.
 *
 * E.g. a bow shoots an arrow.
 */
public class Projectile extends AbstractEntity {

    /**
     * Collider of this object.
     * TODO implement collision events.
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
     * Construct a new projectile class.
     * @param damage
     */
    public Projectile(String textureName, String objectName, float col, float row, int damage) {

        super(col, row,1, 2, 0.2f);

        this.damage = damage;

        this.setTexture(textureName);
        this.setObjectName(objectName);
    }

    /**
     *
     * @param tick Current game tick.
     */
    @Override
    public void onTick(long tick) {
        //TODO implement collision events and movement.
    }



}
