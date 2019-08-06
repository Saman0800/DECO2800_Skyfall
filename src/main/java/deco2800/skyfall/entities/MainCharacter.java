package deco2800.skyfall.entities;

import com.badlogic.gdx.Gdx;
import deco2800.skyfall.tasks.*;
import deco2800.skyfall.util.*;

import java.util.*;

public class MainCharacter extends Peon {

    // List of the player's inventories
    // TODO need to replace List<String> with List<InventoryClass>
    private List<String> inventories;
    private List<String> hotbar;
    private int equipped_item;

    /*
        Potential future implementations

        private List<InventoryItem> misc; // This is equipped items like rings,
                                             armour etc.

        // These are status effects (ie. poison, regen, weakness)
        private List<StatusEffect> statusEffects;
        // These are player attributes ie. combat strength
        private List<Attributes> attributes
     */

    public MainCharacter(float col, float row, float speed, String name,
                         int health) {
        super(row, col, speed, name, health);

        this.inventories = new ArrayList<>();
        this.hotbar = new ArrayList<>();
        hotbar.add("Rusty Sword");
        equipped_item = 0;
    }

    /**
     * Adds item to player's collection
     * @param item inventory being added
     */
    public void pickUpInventory(String item) {
        inventories.add(item);
    }

    /**
     * Removes items from player's collection
     * @param item inventory being removed
     */
    public void dropInventory(String item) {
        inventories.remove(item);
    }

    /**
     * Gets the player's inventories, modification of the returned list
     * doesn't impact the internal class
     * @return a list of the player's inventories
     */
    public List<String> getInventories() {
        return new ArrayList<>(inventories);
    }

    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
        // only allow left clicks to move player
        if (button != 0) {
            return;
        }

        float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
        float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);

        this.task = new MovementTask(this, new HexVector(clickedPosition[0],clickedPosition[1]));
    }
}
