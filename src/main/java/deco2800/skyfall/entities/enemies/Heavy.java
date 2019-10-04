package deco2800.skyfall.entities.enemies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Heavy extends Enemy {

    // Logger for tracking enemy information
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Heavy(float col, float row, float scaling, String biome, String textureName) {
        super(col, row, "HeavyHitBox", "Heavy", 0.2f,  biome, textureName);

        // Assign values, includes default values
        this.setValues(scaling, 20, 5, 1, 5, 10);
    }
}