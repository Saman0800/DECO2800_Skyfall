package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;

public class DesertShrub extends AbstractShrub {

    private static final String ENTITY_ID_STRING = "desert_shrub";

    public DesertShrub() {
        super();
        this.setTexture("DBush1");
        setupParams();
    }

    public DesertShrub(Tile tile, boolean obstructed) {
        super(tile, "DBush1", obstructed);
        setupParams();
    }

    public DesertShrub(SaveableEntityMemento memento) {
        super(memento);
        setupParams();
    }

    private void setupParams() {
        this.setObjectName(ENTITY_ID_STRING);
        this.entityType = "DesertShrub";
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