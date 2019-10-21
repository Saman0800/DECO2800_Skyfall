package deco2800.skyfall.entities;

import deco2800.skyfall.managers.ChestManager;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.worlds.Tile;
import java.util.List;
import java.util.Map;

/**
 * An chest entity that spawns in the game.
 */
public class Chest extends StaticEntity implements HasHealth {
    private int health = 100;
    private static final String ENTITY_ID_STRING = "chestClosed";


    // The chest to be stored
    private ChestManager manager;

    /**
     * Constructs a chest to be put into the game
     * @param tile tile to place chest on
     * @param obstructed does the chest obstruct the player's movement?
     * @param contents contents of the chest
     */
    public Chest(Tile tile, boolean obstructed, Map<String, List<Item>> contents) {
        super(tile, 2, ENTITY_ID_STRING, obstructed);
        this.manager = new ChestManager(contents);
        this.setObjectName(ENTITY_ID_STRING);
        changeCollideability(false);
    }

    @Override
    public void onTick(long i) {
        // Do nothing on tick
    }

    /**
     * The newInstance method implemented for the MountainRock class to allow for
     * item dispersal on game start up.
     * 
     * @return Duplicate rock instance with modified position.
     */
    @Override
    public Chest newInstance(Tile tile) {
        return new Chest(tile, this.isObstructed(), manager.getContents());
    }

    public ChestManager getManager() {
        return manager;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }
}
