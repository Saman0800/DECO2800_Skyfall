package deco2800.skyfall.entities.enemies;

/*An interface required for spawning abstract entities
with SpawningManager
 */
public interface Spawnable<E extends Enemy> {
    /**
     * Required for spawning
     * returns a new instance, that is a deep copy
     * should be at specified position
     *
     * @param row the row position of the new enemy
     * @param col the col position of the new enemy
     * @return A new instance of this enemy
     */
    public E newInstance(float row, float col);


}
