package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;

public class VolcanicShrub extends AbstractShrub {

    private static final String ENTITY_ID_STRING = "volcanic_shrub";

    public VolcanicShrub() {
        super();
        this.setTexture("vBush" + VolcanicShrub.nextTexture);
        VolcanicShrub.nextTexture = randomGen.nextInt(3) + 1;
        setupParams();
    }

    public VolcanicShrub(Tile tile, boolean obstructed) {
        super(tile, "vBush" + VolcanicShrub.nextTexture, obstructed);
        VolcanicShrub.nextTexture = randomGen.nextInt(3) + 1;
        setupParams();
    }

    public VolcanicShrub(SaveableEntityMemento memento) {
        super(memento);
        setupParams();
    }

    private void setupParams() {
        this.setObjectName(ENTITY_ID_STRING);
        this.entityType = "VolcanicShrub";
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