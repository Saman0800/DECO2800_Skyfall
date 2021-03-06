package deco2800.skyfall.entities;
import deco2800.skyfall.worlds.Tile;

/**
 * An blueprint shop entity that spawns in the game.
 */
public class BlueprintShop extends StaticEntity implements HasHealth {
    private int health = 100;
    private static final String ENTITY_ID_STRING = "blueprintShop";

    /**
     * constructs a blueprint shop to be put into the game
     * @param tile tile to place on
     * @param obstructed is obstructed or not
     */
    public BlueprintShop(Tile tile, boolean obstructed) {
        super(tile, 2, ENTITY_ID_STRING, obstructed);
        this.setObjectName(ENTITY_ID_STRING);
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
    public BlueprintShop newInstance(Tile tile) {
        return new BlueprintShop(tile, this.isObstructed());
    }
}
