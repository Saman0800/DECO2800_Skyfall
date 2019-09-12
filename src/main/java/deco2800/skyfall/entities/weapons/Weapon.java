package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.resources.Item;

/**
 * Weapon to be used by the Main Character and stored as an inventory item
 */
public abstract class Weapon extends StaticEntity implements Item {

    // Weapon attributes
    private Boolean carryable;
    private HexVector position;
    private Boolean exchangeable;

    /**
     * Weapon constructor used in the game world
     */
    public Weapon(Tile tile, String texture, boolean obstructed) {
        super(tile, 5, texture, obstructed);

        this.carryable = true;
        this.position = tile.getCoordinates();
        this.exchangeable = false;
    }

    /**
     * Returns whether or not the weapon can be carried
     * @return True if the item can be carried in the inventory, false
     * if it is consumed immediately
     */
    public Boolean isCarryable() {
        return this.carryable;
    }

    /**
     * Returns the co-ordinates of the tile the weapon is on
     * @return the co-ordinates of the tile the weapon is on
     */
    public HexVector getCoords() {
        return this.position;
    }

    /**
     * Returns whether or not the weapon can be exchanged
     * @return True if the weapon can be exchanged, false otherwise
     */
    public Boolean isExchangeable() {
        return this.exchangeable;
    }

    @Override
    public void onTick(long i) {
        // Auto-generated method stub
    }
}
