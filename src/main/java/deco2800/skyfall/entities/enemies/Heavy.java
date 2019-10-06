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
        this.setValues(scaling, 100, 1, 1,0.06f,0.03f);
    }

    @Override
    public Enemy newInstance(float row, float col) {
        return new Heavy(col, row, getScale(), getBiome(), getTexture());
    }
}
