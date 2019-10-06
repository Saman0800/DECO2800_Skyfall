package deco2800.skyfall.entities.enemies;

import deco2800.skyfall.entities.MainCharacter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An instance of a Heavy (hard level) enemy.
 */
public class Heavy extends Enemy implements Spawnable {

    // Logger for tracking enemy information
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Heavy(float col, float row, float scaling, String biome, String textureName) {
        super(col, row, "Heavy", "Heavy", 0.06f,  biome, textureName);

        // Assign values, includes default values
        this.setValues(scaling, 10, 3, 2,0.06f,0.03f);
    }

    /**
     * Constructor of Heavy enemy, used for testing
     *
     * @param col the x-coordinate of the enemy.
     * @param row the y-coordinate of the enemy.
     * @param scaling the scaling factor of the enemy's stats.
     */
    public Heavy(float col, float row, float scaling) {
        super(col, row);

        this.setValues(scaling, 100, 1, 1,0.06f,0.04f);
    }

    @Override
    public Enemy newInstance(float row, float col) {
        return new Heavy(col, row, getScale(), getBiome(), getTexture());
    }
}
