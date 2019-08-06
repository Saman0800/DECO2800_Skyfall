package deco2800.skyfall.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InputManager;
import deco2800.skyfall.observers.TouchDownObserver;
import deco2800.skyfall.tasks.*;
import deco2800.skyfall.util.*;

import java.util.*;

public class MainCharacter extends Peon implements TouchDownObserver {

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

    // Textures for all 6 directions to correspond to movement of character
    private String[] textures;

    private void instantiateInventory() {
        this.inventories = new ArrayList<>();
        this.hotbar = new ArrayList<>();
        this.hotbar.add("Rusty Sword");
        this.equipped_item = 0;
    }

    public MainCharacter(float col, float row, float speed, String name,
                         int health) {
        super(row, col, speed, name, health);
        this.setTexture("main_piece");
        this.setHeight(1);
        this.setObjectName("MainPiece");
        GameManager.getManagerFromInstance(InputManager.class)
                .addTouchDownListener(this);

        this.instantiateInventory();

    }

    /**
     * Constructor with various textures
     * @param textures A array of length 6 with string names corresponding to different orientation
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
        this.setHeight(1);
        this.setTexture(textures[2]);

        this.instantiateInventory();
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

    @Override
    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
    // only allow left clicks to move player
        if (button != 0) {
            return;
        }

        float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
        float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);

        this.task = new MovementTask(this, new HexVector(clickedPosition[0],clickedPosition[1]));
    }

    @Override
    public void onTick(long i) {
        if (task != null && task.isAlive()) {
            task.onTick(i);

            if (task.isComplete()) {
                this.task = null;
            }
        }
    }
}
