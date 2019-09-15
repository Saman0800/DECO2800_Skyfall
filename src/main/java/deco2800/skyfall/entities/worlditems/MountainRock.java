package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;
import java.util.Random;

public class MountainRock extends StaticRock {
    private int health = 100;
    private static final String ENTITY_ID_STRING = "mountain_rock";

    // TODO Remove this and replace the Random instance with the seeded Random as a
    // parameter.

    private static Random randomGen = new Random();
    private static int nextRock = 1;
    private int metalAmount;

    public MountainRock() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public MountainRock(Tile tile, boolean obstructed) {
        super(tile, obstructed, "MRock" + MountainRock.nextRock);
        MountainRock.nextRock = randomGen.nextInt(3) + 1;
        this.setObjectName(ENTITY_ID_STRING);
        this.metalAmount = 15;
        this.entityType = "MountainRock";
    }

    public MountainRock (StaticEntityMemento memento){
        super(memento);
        this.metalAmount = 15;
    }

    @Override
    public void onTick(long i) {
        // Do nothing on tick
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
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
