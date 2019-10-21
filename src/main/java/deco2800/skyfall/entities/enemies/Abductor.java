package deco2800.skyfall.entities.enemies;

/**
 * An instance of a Abductor enemy. It captures main character to other enemies.
 */
public class Abductor extends Enemy {

    public Abductor(float col, float row, float scaling, String biome) {
        super(col, row, EnemyType.ABDUCTOR, 0.06f, biome, "enemyAbductor");

        this.setType(EnemyType.ABDUCTOR);

        // Assign values, includes default values
        this.setValues(scaling, 100, 1, 4, 4f, 6f);
    }

    /**
     * Constructor of Abductor enemy, used for testing
     *
     * @param col     the x-coordinate of the enemy.
     * @param row     the y-coordinate of the enemy.
     * @param scaling the scaling factor of the enemy's stats.
     */
    public Abductor(float col, float row, float scaling) {
        super(col, row);

        this.setValues(scaling, 100, 0, 0, 0.14f, 0.14f);
    }
}
