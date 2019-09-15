package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.Tile;
import java.util.Random;
import deco2800.skyfall.entities.HasHealth;

public class Rock extends StaticEntity implements HasHealth {
    private int health = 100;
    private static final String ENTITY_ID_STRING = "rock";

    // TODO Remove this and replace the Random instance with the seeded Random as a
    // parameter.

    private static Random randomGen = new Random();
    private static int nextRock = 1;
    private int metalAmount;

    public Rock() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public Rock(Tile tile, boolean obstructed) {
        super(tile, 2, "rock" + nextRock, obstructed);
        nextRock = randomGen.nextInt(3) + 1;
        this.setObjectName(ENTITY_ID_STRING);
        this.metalAmount = 15;
        this.entityType = "Rock";
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
