package deco2800.skyfall.entities;

import deco2800.skyfall.worlds.world.World;

/**
 * Class for the grass of the world
 */
public class GrassTuft extends AbstractEntity {
    private static final String ENTITY_ID_STRING = "GrassTuft";

    /**
     * Constructor for the Grass entity
     *
     * @param world the world the grass in in
     * @param col   the grass' x position in the world
     * @param row   the grass' y position in the world
     */
    public GrassTuft(World world, float col, float row) {
        super(col, row, 0);
        setTexture("grass_tuft");
        setObjectName(ENTITY_ID_STRING);
        changeCollideability(false);
    }

    @Override
    public void onTick(long i) {
        // Do nothing on tick
    }
}
