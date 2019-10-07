package deco2800.skyfall.entities.enemies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scout extends Enemy {

    // Logger for tracking enemy information
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Scout(float col, float row, float scaling, String biome, String textureName) {
        super(col, row, "ScoutHitBox", "Scout", biome, textureName);

        // Assign values, includes default values
        this.setValues(scaling, 10, 1, 1,10,20);
    }
}
