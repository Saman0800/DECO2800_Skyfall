package deco2800.thomas.entities;


import deco2800.thomas.worlds.AbstractWorld;

/**
 * Class for the grass of the world
 */
public class GrassTuft extends AbstractEntity {
	private static final String ENTITY_ID_STRING = "GrassTuft";
	

    /**
     * Constructor for the Grass entity
     *
     * @param world the world the grass in in
     * @param col  the grass' x position in the world
     * @param row  the grass' y position in the world
     * @param posZ  the grass' z position in the world
     * @param type  the type of the grass
     */
    public GrassTuft(AbstractWorld world, float col, float row) {
        super(col, row, 0);
        setCollidable(false);
        setTexture("grass_tuft");
        setObjectName(ENTITY_ID_STRING);
    }

    @Override
    public void onTick(long i) {

    }
}
