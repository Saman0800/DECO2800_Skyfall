package deco2800.skyfall.entities.enemies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An instance of a Scout (easy level) enemy.
 */
public class Scout extends Enemy implements Spawnable  {

    // Logger for tracking enemy information
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructor to create new Scout enemy.
     *
     * @param col the x-coordinate of the enemy.
     * @param row the y-coordinate of the enemy.
     * @param scaling the factor the enemy's stat is scale in this enemy.
     * @param biome the biome this enemy is in.
     * @param textureName the name of the texture of the enemy.
     */
    public Scout(float col, float row, float scaling, String biome, String textureName) {
        super(col, row, "Scout", "Scout",0.06f,  biome, textureName);

        // Assign values, includes default values
        this.setValues(scaling, 3, 1, 1,0.06f,0.08f);
    }

    @Override
    public Scout newInstance(float row, float col) {
        return new Scout(col, row, this.getScale(), this.getBiome(), this.getTexture());
    }
}
