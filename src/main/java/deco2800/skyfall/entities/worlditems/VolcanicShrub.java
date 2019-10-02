package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;

public class VolcanicShrub extends AbstractShrub {

    private static final String ENTITY_ID_STRING = "volcanic_shrub";

    public VolcanicShrub() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public VolcanicShrub(Tile tile, boolean obstructed) {
        super(tile, "vBush" + VolcanicShrub.nextTexture, obstructed);
        this.setObjectName(ENTITY_ID_STRING);
        VolcanicShrub.nextTexture = randomGen.nextInt(3) + 1;
        this.entityType = "VolcanicShrub";
    }

    public VolcanicShrub(SaveableEntityMemento memento) {
        super(memento);
    }

    /**
     * The newInstance method implemented for the VolcanicShrub class to allow for
     * item dispersal on game start up.
     * 
     * @return Duplicate grass instance with modified position.
     */
    @Override
    public VolcanicShrub newInstance(Tile tile) {
        return new VolcanicShrub(tile, this.isObstructed());
    }

}