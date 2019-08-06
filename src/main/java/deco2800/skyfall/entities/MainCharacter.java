package deco2800.skyfall.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

    // Textures for all 6 directions to correspond to movement of character
    private Texture text1;
    private Texture text2;
    private Texture text3;
    private Texture text4;
    private Texture text5;
    private Texture text6;

    public MainCharacter(float col, float row, float speed, String name,
                         int health) {
        super(row, col, speed, name, health);
        this.setTexture("main_piece");
        this.setHeight(1);
        this.setObjectName("MainPiece");
//        GameManager.getManagerFromInstance(InputManager.class)
//                .addTouchDownListener(this);

        this.inventories = new ArrayList<>();
        this.hotbar = new ArrayList<>();
        this.hotbar.add("Rusty Sword");
        this.equipped_item = 0;
    }

    /**
     * Constructor with various textures
     */
    public MainCharacter(float col, float row, float speed, String name,
                         int health, Texture dir1, Texture dir2, Texture dir3
            , Texture dir4, Texture dir5, Texture dir6) {
        this(row, col, speed, name, health);

        this.text1 = dir1;
        this.text2 = dir2;
        this.text3 = dir3;
        this.text4 = dir4;
        this.text5 = dir5;
        this.text6 = dir6;
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
