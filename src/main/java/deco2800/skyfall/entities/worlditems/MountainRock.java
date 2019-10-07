package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;

public class MountainRock extends AbstractRock {

    private static final String ENTITY_ID_STRING = "mountain_rock";

    public MountainRock() {
        super();
        this.setTexture("MRock" + MountainRock.nextRock);
        MountainRock.nextRock = randomGen.nextInt(3) + 1;
        setupParams();
    }

    public MountainRock(Tile tile, boolean obstructed) {
        super(tile, obstructed, "MRock" + MountainRock.nextRock);
        MountainRock.nextRock = randomGen.nextInt(3) + 1;
        setupParams();
    }

    public MountainRock(SaveableEntityMemento memento) {
        super(memento);
        setupParams();
    }

    private void setupParams() {
        this.setObjectName(ENTITY_ID_STRING);
        this.metalAmount = 15;
        this.entityType = "MountainRock";
    }

    /**
     * The newInstance method implemented for the MountainRock class to allow for
     * item dispersal on game start up.
     * 
     * @return Duplicate rock instance with modified position.
     */
    @Override
    public MountainRock newInstance(Tile tile) {
        return new MountainRock(tile, this.isObstructed());
    }

}
