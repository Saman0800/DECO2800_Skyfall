package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;

public class SwampShrub extends AbstractShrub {

    private static final String ENTITY_ID_STRING = "swamp_shrub";

    public SwampShrub() {
        super();
        setupParams();
        this.setTexture("sBush" + SwampShrub.nextTexture);
        ForestShrub.nextTexture = randomGen.nextInt(3) + 1;
    }

    public SwampShrub(Tile tile, boolean obstructed) {
        super(tile, "sBush" + SwampShrub.nextTexture, obstructed);
        SwampShrub.nextTexture = randomGen.nextInt(3) + 1;
        setupParams();
    }

    public SwampShrub(SaveableEntityMemento memento) {
        super(memento);
        setupParams();
    }

    private void setupParams() {
        this.setObjectName(ENTITY_ID_STRING);
        this.entityType = "SwampShrub";
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