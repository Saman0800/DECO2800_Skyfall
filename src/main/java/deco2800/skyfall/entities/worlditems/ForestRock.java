package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;

public class ForestRock extends AbstractRock {

    protected static final String ENTITY_ID_STRING = "forest_rock";

    public ForestRock(Tile tile, boolean obstructed) {
        super(tile, obstructed, "rock" + nextRock);
        nextRock = randomGen.nextInt(3) + 1;
        setupParams();
    }


    public ForestRock() {
        super(new Tile(null, 0.0f, 0.0f), true, "rock" + ForestRock.nextRock);
        ForestRock.nextRock = randomGen.nextInt(3) + 1;
        setupParams();
    }

    public ForestRock(SaveableEntityMemento memento) {
        super(memento);
        setupParams();
    }

    private void setupParams() {
        this.setObjectName(ENTITY_ID_STRING);
        this.metalAmount = 15;
        this.entityType = "ForestRock";
    }

    /**
     * The newInstance method implemented for the ForestRock class to allow for item
     * dispersal on game start up.
     * 
     * @return Duplicate rock instance with modified position.
     */
    @Override
    public ForestRock newInstance(Tile tile) {
        return new ForestRock(tile, this.isObstructed());
    }

}
