package deco2800.skyfall.entities.enemies;

/**
 * An instance of a Scout (easy level) enemy.
 */
public class Scout extends Enemy {

    /**
     * Constructor to create new Scout enemy.
     * 
     * @param col     the x-coordinate of the enemy.
     * @param row     the y-coordinate of the enemy.
     * @param scaling the factor the enemy's stat is scale in this enemy.
     * @param biome   the biome this enemy is in.
     */
    public Scout(float col, float row, float scaling, String biome) {
        super(col, row, EnemyType.SCOUT, 0.06f, biome, "enemyScout");

        this.setType(EnemyType.SCOUT);

        // Assign values, includes default values
        this.setValues(scaling, 100, 10, 8, 5f, 7f);
    }

    /**
     * Constructor of Scout enemy, used for testing
     *
     * @param col     the x-coordinate of the enemy.
     * @param row     the y-coordinate of the enemy.
     * @param scaling the scaling factor of the enemy's stats.
     */
    public Scout(float col, float row, float scaling) {
        super(col, row);

        this.setValues(scaling, 100, 1, 1, 0.06f, 0.04f);
    }
}
