package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;

public class ForestShrub extends AbstractShrub {

    private static final String ENTITY_ID_STRING = "forest_shrub";

    public ForestShrub() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public ForestShrub(Tile tile, boolean obstructed) {
        super(tile, "bush" + ForestShrub.nextTexture, obstructed);
        this.setObjectName(ENTITY_ID_STRING);
        ForestShrub.nextTexture = randomGen.nextInt(3) + 1;
        this.entityType = "ForestShrub";
    }

    public ForestShrub(SaveableEntityMemento memento) {
        super(memento);
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