package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;

public class ForestShrub extends AbstractShrub {

    private static final String ENTITY_ID_STRING = "forest_shrub";

    public ForestShrub() {
        super();
        this.setTexture("bush" + ForestShrub.nextTexture);
        ForestShrub.nextTexture = randomGen.nextInt(3) + 1;
        setupParams();
    }

    public ForestShrub(Tile tile, boolean obstructed) {
        super(tile, "bush" + ForestShrub.nextTexture, obstructed);
        ForestShrub.nextTexture = randomGen.nextInt(3) + 1;
        setupParams();
    }

    public ForestShrub(SaveableEntityMemento memento) {
        super(memento);
        setupParams();
    }

    private void setupParams() {
        this.setObjectName(ENTITY_ID_STRING);
        this.entityType = "ForestShrub";
    }

    /**
     * The newInstance method implemented for the ForestShrub class to allow for
     * item dispersal on game start up.
     * 
     * @return Duplicate grass instance with modified position.
     */
    @Override
    public ForestShrub newInstance(Tile tile) {
        return new ForestShrub(tile, this.isObstructed());
    }

}