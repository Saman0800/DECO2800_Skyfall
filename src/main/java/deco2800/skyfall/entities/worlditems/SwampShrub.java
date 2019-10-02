package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;

public class SwampShrub extends AbstractShrub {

    private static final String ENTITY_ID_STRING = "swamp_shrub";

    public SwampShrub() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public SwampShrub(Tile tile, boolean obstructed) {
        super(tile, "sBush" + SwampShrub.nextTexture, obstructed);
        this.setObjectName(ENTITY_ID_STRING);
        SwampShrub.nextTexture = randomGen.nextInt(3) + 1;
        this.entityType = "SwampShrub";
    }

    public SwampShrub(SaveableEntityMemento memento) {
        super(memento);
    }

    /**
     * The newInstance method implemented for the SwampShrub class to allow for item
     * dispersal on game start up.
     * 
     * @return Duplicate grass instance with modified position.
     */
    @Override
    public SwampShrub newInstance(Tile tile) {
        return new SwampShrub(tile, this.isObstructed());
    }

}