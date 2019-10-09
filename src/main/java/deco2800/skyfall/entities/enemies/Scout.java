package deco2800.skyfall.entities.enemies;

/**
 * An instance of a Scout (easy level) enemy.
 */
public class Scout extends Enemy implements Spawnable  {

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
        super(col, row, "Scout", EnemyType.SCOUT,
                0.06f,  biome, "enemyScout");

        this.setName("Scout");

        // Assign values, includes default values
        this.setValues(scaling, 3, 2, 1,0.06f,0.08f);
    }

    /**
     * Constructor of Scout enemy, used for testing
     *
     * @param col the x-coordinate of the enemy.
     * @param row the y-coordinate of the enemy.
     * @param scaling the scaling factor of the enemy's stats.
     */
    public Scout(float col, float row, float scaling) {
        super(col, row);

        this.setValues(scaling, 100, 1, 1,0.06f,0.04f);
    }

    @Override
    public Scout newInstance(float row, float col) {
        return new Scout(col, row, this.getScale(), this.getBiome(), this.getTexture());
    }
}
