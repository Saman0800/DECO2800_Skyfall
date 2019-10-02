package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.Tile;
import java.util.Random;

public class DesertShrub extends AbstractShrub {

    private static final String ENTITY_ID_STRING = "snow_shrub";

    public DesertShrub() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public DesertShrub(Tile tile, boolean obstructed) {
        super(tile, "DBush1", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
        this.entityType = "DesertShrub";
    }

    public DesertShrub(SaveableEntityMemento memento) {
        super(memento);
    }

    /**
     * The newInstance method implemented for the DesertShrub class to allow for
     * item dispersal on game start up.
     * 
     * @return Duplicate grass instance with modified position.
     */
    @Override
    public DesertShrub newInstance(Tile tile) {
        return new DesertShrub(tile, this.isObstructed());
    }

}