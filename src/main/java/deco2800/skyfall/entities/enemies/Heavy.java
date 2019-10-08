package deco2800.skyfall.entities.enemies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Heavy extends Enemy {

    // Logger for tracking enemy information
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Heavy(float col, float row, float scaling, String biome, EnemyType enemyType) {
        super(col, row, "HeavyHitBox", EnemyType.HEAVY,
                0.2f, biome, "HeavyTexture");

        // Assign values, includes default values
        this.setValues(scaling, 10, 5, 1, 5, 10);
    }
}
