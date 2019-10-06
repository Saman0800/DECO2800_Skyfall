package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;

public class VolcanicRock extends AbstractRock {

    protected static final String ENTITY_ID_STRING = "volcanic_rock";

    public VolcanicRock(Tile tile, boolean obstructed) {
        super(tile, obstructed, "vRock" + nextRock);
        setupParams();
    }

    public VolcanicRock(SaveableEntityMemento memento) {
        super(memento);
        setupParams();
    }

    private void setupParams() {
        this.setObjectName(ENTITY_ID_STRING);
        this.metalAmount = 15;
        this.entityType = "VolcanicRock";
    }

    /**
     * The newInstance method implemented for the VolcanicRock class to allow for
     * item dispersal on game start up.
     * 
     * @return Duplicate rock instance with modified position.
     */
    @Override
    public VolcanicRock newInstance(Tile tile) {
        return new VolcanicRock(tile, this.isObstructed());
    }

}
