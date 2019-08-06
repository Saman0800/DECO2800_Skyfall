package deco2800.skyfall.entities;

import deco2800.skyfall.worlds.Tile;

public class LongGrass extends StaticEntity {

    private static final String ENTITY_ID_STRING = "long_grass";

    public LongGrass() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public LongGrass(Tile tile, boolean obstructed) {
        super(tile, 2, "long_grass", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
    }

    @Override
    public void onTick(long i) {

    }

    /**
     * The newInstance method implemented for the LongGrass class to allow for item
     * dispersal on game start up.
     * 
     * @return Duplicate grass instance with modified position.
     */
    @Override
    public LongGrass newInstance(Tile tile) {
        return new LongGrass(tile, this.getObstructed());
    }

}