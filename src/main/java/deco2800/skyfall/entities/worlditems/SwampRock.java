package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;

public class SwampRock extends AbstractRock {

    protected static final String ENTITY_ID_STRING = "swamp_rock";

    public SwampRock(Tile tile, boolean obstructed) {
        super(tile, obstructed, "sRock" + nextRock);
        nextRock = randomGen.nextInt(3) + 1;
        this.setObjectName(ENTITY_ID_STRING);
        this.metalAmount = 15;
    }

    public SwampRock(SaveableEntityMemento memento) {
        super(memento);
        this.metalAmount = 15;
    }

    /**
     * The newInstance method implemented for the SwampRock class to allow for item
     * dispersal on game start up.
     * 
     * @return Duplicate rock instance with modified position.
     */
    @Override
    public SwampRock newInstance(Tile tile) {
        return new SwampRock(tile, this.isObstructed());
    }

}
