package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;

public class DesertEnvironment extends AbstractEnvironment {
    private static final String ENTITY_ID_STRING = "Desert_Environment";

    public DesertEnvironment() {
        super();
        this.setTexture("DEnvironment" + ForestShrub.nextTexture);
        ForestShrub.nextTexture = randomGen.nextInt(2) + 1;
        setupParams();
    }

    public DesertEnvironment(Tile tile, boolean obstructed) {
        super(tile, "DEnvironment" + ForestShrub.nextTexture, obstructed);
        ForestShrub.nextTexture = randomGen.nextInt(2) + 1;
        setupParams();
    }

    public DesertEnvironment(SaveableEntityMemento memento) {
        super(memento);
        setupParams();
    }

    private void setupParams() {
        this.setObjectName(ENTITY_ID_STRING);
        this.entityType = ENTITY_ID_STRING;
    }

    /**
     * The newInstance method implemented for the Desert_Environment class to allow for
     * item dispersal on game start up.
     *
     */
    @Override
    public DesertEnvironment newInstance(Tile tile) {
        return new DesertEnvironment(tile, this.isObstructed());
    }
}
