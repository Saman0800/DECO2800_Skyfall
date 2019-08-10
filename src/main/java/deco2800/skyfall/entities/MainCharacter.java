package deco2800.skyfall.entities;

import com.badlogic.gdx.*;
import deco2800.skyfall.managers.*;
import deco2800.skyfall.observers.*;
import deco2800.skyfall.tasks.*;
import deco2800.skyfall.util.*;

import java.util.*;

/**
 * Main character in the game
 */
public class MainCharacter extends Peon implements TouchDownObserver {

    // List of the player's inventories
    // TODO need to replace List<String> with List<InventoryClass>
    private List<String> inventories;

    // Hotbar of inventories
    private List<String> hotbar;

    // Number of equipped_items
    private int equipped_item;

    /*
    Potential future implementations

    // This is equipped items like rings, armour etc.
    private List<InventoryItem> misc;

    // These are status effects (ie. poison, regen, weakness)
    private List<StatusEffect> statusEffects;

    // These are player attributes ie. combat strength
    private List<Attributes> attributes;
    */

    // Level/point system for the Main Character to be recorded as game goes on
    private int level;

    // Textures for all 6 directions to correspond to movement of character
    private String[] textures;

    /**
     * Private helper method to instantiate inventory for Main Character
     * constructor
     */
    private void instantiateInventory() {
        this.inventories = new ArrayList<>();
        this.hotbar = new ArrayList<>();
        this.hotbar.add("Rusty Sword");
        this.equipped_item = 1;
    }

    /**
     * Basic Main Character constructor
     */
    public MainCharacter(float col, float row, float speed, String name,
                         int health) {
        super(row, col, speed, name, health);

        this.setTexture("main_piece");
        this.setHeight(1);
        this.setObjectName("MainPiece");

        GameManager.getManagerFromInstance(InputManager.class)
                .addTouchDownListener(this);

        this.level = 1;
        this.instantiateInventory();
    }

    /**
     * Constructor with various textures
     * @param textures A array of length 6 with string names corresponding to
     *                different orientation
     *                 0 = North
     *                 1 = North-East
     *                 2 = South-East
     *                 3 = South
     *                 4 = South-West
     *                 5 = North-West
     */
    public MainCharacter(float col, float row, float speed, String name,
                         int health, String[] textures) {
        this(row, col, speed, name, health);

        this.textures = textures;
        this.setTexture(textures[2]);
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
        if (inventories.contains(item)) {
            inventories.remove(item);
        }
    }

    /**
     * Gets the player's inventories, modification of the returned list
     * doesn't impact the internal class
     * @return a list of the player's inventories
     */
    public List<String> getInventories() {
        return new ArrayList<>(inventories);
    }

    /**
     * Equips an item from the inventory list
     * Max no of equipped items is 5
     * @param item inventory being equipped
     */
    public void equipItem(String item) {
        if (inventories.contains(item) && equipped_item < 5) {
            hotbar.add(item);
            equipped_item += 1;
        }
    }

    /**
     * Unequips an item
     * @param item inventory being unequipped
     */
    public void unequipItem(String item) {
        if (hotbar.contains(item)) {
            hotbar.remove(item);
            equipped_item -= 1;
        }
    }

    /**
     * Gets the player's currently equipped inventory, modification of the
     * returned list doesn't impact the internal class
     * @return a list of the player's equipped inventories
     */
    public List<String> getEquippedItems() {
        return new ArrayList<>(hotbar);
    }

    /**
     * Gets the number of items equipped
     * @return the number of items equipped
     */
    public int equippedItems() {
        return this.equipped_item;
    }

    /**
     * Change current level of character
     * @param change amount being added or subtracted
     */
    public void changeLevel(int change) {
        if (level + change >= 1) {
            this.level += change;
        }
    }

    /**
     * Gets the current level of character
     * @return level of character
     */
    public int getLevel() {
        return this.level;
    }

    /*
    Potential more methods and related attributes:
    -record killed enemies
    -interaction with worlds
    -interaction with movement
    */

    @Override
    /**
     * Handles click of mousepad for Main Character (linked with movement)
     */
    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
        // only allow left clicks to move player
        if (button != 0) {
            return;
        }

        float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(),
                Gdx.input.getY());
        float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0],
                mouse[1]);

        this.task = new MovementTask(this,
                new HexVector(clickedPosition[0], clickedPosition[1]));
    }

    @Override
    /**
     * Handles tick based stuff, e.g. movement
     */
    public void onTick(long i) {
        if (task != null && task.isAlive()) {
            task.onTick(i);

            if (task.isComplete()) {
                this.task = null;
            }
        }
    }
}