package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.Tile;
import java.util.Random;

public class SnowShrub extends AbstractShrub {

    private static final String ENTITY_ID_STRING = "snow_shrub";

    public SnowShrub() {
        this.entityType = "SnowShrub";
        this.setObjectName(ENTITY_ID_STRING);
    }

    public SnowShrub(Tile tile, boolean obstructed) {
        super(tile, "MBush" + SnowShrub.nextTexture, obstructed);
        this.setObjectName(ENTITY_ID_STRING);
        SnowShrub.nextTexture = randomGen.nextInt(3) + 1;
        this.entityType = "SnowShrub";
    }

    public SnowShrub(SaveableEntityMemento memento) {
        super(memento);
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