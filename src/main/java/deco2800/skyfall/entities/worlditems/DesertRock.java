package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;

public class DesertRock extends AbstractRock {

    private static final String ENTITY_ID_STRING = "desert_rock";

    public DesertRock() {
        this.setObjectName(ENTITY_ID_STRING);
        setupParams();
        this.setTexture("DRock" + DesertRock.nextRock);
        DesertRock.nextRock = randomGen.nextInt(3) + 1;
    }

    public DesertRock(Tile tile, boolean obstructed) {
        super(tile, obstructed, "DRock" + DesertRock.nextRock);
        DesertRock.nextRock = randomGen.nextInt(3) + 1;
        setupParams();
    }

    public DesertRock(SaveableEntityMemento memento) {
        super(memento);
        setupParams();
    }

    private void setupParams() {
        this.setObjectName(ENTITY_ID_STRING);
        this.metalAmount = 15;
        this.entityType = "DesertRock";
    }

    /**
     * The newInstance method implemented for the DesertRock class to allow for item
     * dispersal on game start up.
     * 
     * @return Duplicate rock instance with modified position.
     */
    @Override
    public DesertRock newInstance(Tile tile) {
        return new DesertRock(tile, this.isObstructed());
    }

}