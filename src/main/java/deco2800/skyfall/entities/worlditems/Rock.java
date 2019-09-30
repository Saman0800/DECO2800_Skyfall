package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;
import java.util.Random;

public class Rock extends StaticRock {

    protected static final String ENTITY_ID_STRING = "rock";

    public Rock(Tile tile, boolean obstructed) {
        super(tile, obstructed, "rock" + nextRock);
        nextRock = randomGen.nextInt(3) + 1;
        this.setObjectName(ENTITY_ID_STRING);
        this.metalAmount = 15;
    }

    @Deprecated // This really doesn't make sense anymore.
    public Rock() {
        super(new Tile(null, 0.0f, 0.0f), true, "rock" + Rock.nextRock);
        Rock.nextRock = randomGen.nextInt(3) + 1;
        this.setObjectName(ENTITY_ID_STRING);
        this.metalAmount = 15;
        this.entityType = "Rock";
    }

    public Rock(SaveableEntityMemento memento) {
        super(memento);
        this.metalAmount = 15;
    }

    /**
     * The newInstance method implemented for the Rock class to allow for item
     * dispersal on game start up.
     * 
     * @return Duplicate rock instance with modified position.
     */
    @Override
    public Rock newInstance(Tile tile) {
        return new Rock(tile, this.isObstructed());
    }

}
