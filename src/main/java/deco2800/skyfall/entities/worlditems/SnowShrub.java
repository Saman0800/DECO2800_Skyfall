package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.Tile;
import java.util.Random;

public class SnowShrub extends StaticEntity {

    private static final String ENTITY_ID_STRING = "snow_shrub";
    private static Random randomGen = new Random();
    private static int nextRock = 1;

    public SnowShrub() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public SnowShrub(Tile tile, boolean obstructed) {
        super(tile, 2, "MBush" + nextRock, obstructed);
        this.setObjectName(ENTITY_ID_STRING);
        nextRock = randomGen.nextInt(3) + 1;
        this.entityType = "SnowShrub";
    }


    public SnowShrub (StaticEntityMemento memento){
        super(memento);
    }

    @Override
    public void onTick(long i) {

    }

    /**
     * The newInstance method implemented for the SnowShrub class to allow for item
     * dispersal on game start up.
     * 
     * @return Duplicate grass instance with modified position.
     */
    @Override
    public SnowShrub newInstance(Tile tile) {
        return new SnowShrub(tile, this.isObstructed());
    }

}