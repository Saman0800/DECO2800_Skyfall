package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.Tile;
import java.util.Random;

public class LongGrass extends StaticEntity {

    private static final String ENTITY_ID_STRING = "long_grass";
    private static Random randomGen = new Random();
    private static int nextTexture = 1;

    public LongGrass() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public LongGrass(Tile tile, boolean obstructed) {
        super(tile, 2, "bush" + LongGrass.nextTexture, obstructed);
        this.setObjectName(ENTITY_ID_STRING);
        LongGrass.nextTexture = randomGen.nextInt(3) + 1;
        this.entityType = "LongGrass";
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
        return new LongGrass(tile, this.isObstructed());
    }

}