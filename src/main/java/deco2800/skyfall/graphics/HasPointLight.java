package deco2800.skyfall.graphics;

/**
 * An interface for entities which the can implement to gain a point light
 * when rendered in the game.
 */
public interface HasPointLight {

    /**
     * Sets up all the parameters for the point light when the entity is first
     * created.
     */
    public void pointLightSetUp();

    /**
     * Updates all the parameters of a point light.
     */
    public void updatePointLight();

    /**
     * Gets the points light stored in this object.
     * 
     * @return The PointLight corresponding to this entity instance.
     */
    public PointLight getPointLight();
}
