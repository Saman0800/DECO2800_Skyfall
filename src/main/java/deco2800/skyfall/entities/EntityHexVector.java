package deco2800.skyfall.entities;

import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.util.Action;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.world.Chunk;
import org.javatuples.Pair;

/**
 * A variant of {@link HexVector} which stores the location of an {@link AbstractEntity}. When the position stored
 * inside this vector is updated, the chunks of the entity is updated accordingly.
 */
public class EntityHexVector extends HexVector {
    AbstractEntity entity;

    /**
     * Constructs an {@code EntityHexVector} from an entity and a pair of coordinates.
     *
     * @param entity the entity whose position is represented by this vector
     * @param col    the col value for the vector
     * @param row    the row value for the vector
     */
    public EntityHexVector(AbstractEntity entity, float col, float row) {
        super(col, row);
        this.entity = entity;
    }

    /**
     * Constructs an {@code EntityHexVector} from an entity and a vector.
     *
     * @param entity the entity whose position is represented by this vector
     * @param vector the vector to copy
     */
    public EntityHexVector(AbstractEntity entity, HexVector vector) {
        super(vector);
        this.entity = entity;
    }

    /**
     * Constructs an {@code EntityHexVector} from an entity and a string representation of a vector.
     *
     * @param entity the entity whose position is represented by this vector
     * @param vector the string representation of the vector to copy
     */
    public EntityHexVector(AbstractEntity entity, String vector) {
        super(vector);
        this.entity = entity;
    }

    /**
     * Constructs an {@code EntityHexVector} from an entity. The initial value of the vector is (0, 0).
     *
     * @param entity the entity whose position is represented by this vector
     */
    public EntityHexVector(AbstractEntity entity) {
        super();
        this.entity = entity;
    }

    @Override
    public void setCol(float col) {
        checkChunk(() -> super.setCol(col));
    }

    @Override
    public void setRow(float row) {
        checkChunk(() -> super.setRow(row));
    }

    @Override
    public void set(float col, float row) {
        checkChunk(() -> super.set(col, row));
    }

    private void checkChunk(Action action) {
        Pair<Integer, Integer> oldChunk = Chunk.getChunkForCoordinates(getCol(), getRow());
        action.perform();
        Pair<Integer, Integer> newChunk = Chunk.getChunkForCoordinates(getCol(), getRow());

        if (!oldChunk.equals(newChunk)) {
            deco2800.skyfall.worlds.world.World world = GameManager.get().getWorld();
            if (world != null) {
                world.getChunk(oldChunk.getValue0(), oldChunk.getValue1()).removeEntity(entity);
                world.getChunk(newChunk.getValue0(), newChunk.getValue1()).addEntity(entity);
            }
        }
    }
}
