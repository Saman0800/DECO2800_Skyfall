package deco2800.skyfall.entities.enemies;

/**
 * An instance of a Abductor enemy.
 * It captures main character to other enemies.
 */
public class Abductor extends Enemy implements Spawnable {

    public Abductor(float col, float row, float scaling, String biome, String textureName) {
        super(col, row, "Abductor", "Abductor",0.06f,  biome, textureName);

        // Assign values, includes default values
        this.setValues(scaling, 100, 1, 1,0.06f,0.06f);
    }

    /**
     * Constructor of Abductor enemy, used for testing
     *
     * @param col the x-coordinate of the enemy.
     * @param row the y-coordinate of the enemy.
     * @param scaling the scaling factor of the enemy's stats.
     */
    public Abductor(float col, float row, float scaling) {
        super(col, row);

        this.setValues(scaling, 100, 1, 1,0.06f,0.04f);
    }

    @Override
    public Enemy newInstance(float row, float col) {
        return null;
    }
}
