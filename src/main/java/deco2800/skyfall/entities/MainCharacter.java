package deco2800.skyfall.entities;

import com.badlogic.gdx.*;
import com.badlogic.gdx.math.Vector2;
import deco2800.skyfall.Tickable;
import deco2800.skyfall.animation.*;
import deco2800.skyfall.managers.*;
import deco2800.skyfall.observers.*;
import deco2800.skyfall.resources.GoldPiece;
import deco2800.skyfall.resources.HealthResources;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.util.*;

import java.util.*;

/**
 * Main character in the game
 */
public class MainCharacter extends Peon implements KeyDownObserver,
        KeyUpObserver,TouchDownObserver, Tickable {

    // Combat manager for MainCharacter
    // TODO should be ok once merged with combat
    // private CombatManager combatManager;

    // List of weapons for MainCharacter
    // TODO could probably turn this into a Map for next sprint for easier
    //  manahement of number of each weapon
    private List<Weapon> weapons;

    //Hitbox of melee.
    private Projectile hitBox;

    // Manager for all of MainCharacter's inventories
    public InventoryManager inventories; // maybe could be public?

    // Hotbar of inventories
    private List<Item> hotbar;

    // The index of the item selected to be used in the hotbar
    // ie. [sword][gun][apple]
    // if selecting sword then equipped_item = 0,
    // if selecting gun the equipped_item = 1
    private int equipped_item;
    private final int INVENTORY_MAX_CAPACITY = 20;
    private final int HOTBAR_MAX_CAPACITY = 5;

    // The "wallet" which stores the character's gold pieces
    private HashMap<Integer, Integer> goldPouch;

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

    /* food is from 100 to 0 and goes down as the Player does actions such as:
     - Walking
     - Combat
     - Resource Collecting
     Once the food level reaches 0, the Player begins to starve, and starts to
     lose health points. Still unsure if I should implement time based starvation
     where as time goes on, the Player loses hunger.
     */
    private int foodLevel;

    // Textures for all 6 directions to correspond to movement of character
    private String[] textures;

    /**
     * The direction and speed of the MainCharacter
     */
    protected Vector2 direction;
    protected float currentSpeed;

    /**
     * Helper bools to tell which direction the player intends to move
     */
    private boolean MOVE_UP = false;
    private boolean MOVE_LEFT = false;
    private boolean MOVE_RIGHT = false;
    private boolean MOVE_DOWN = false;

    /**
     * Private helper method to instantiate inventory for Main Character
     * constructor
     */
    private void instantiateInventory() {
        this.inventories = new InventoryManager();

        this.hotbar = new ArrayList<>();
        this.equipped_item = 0;
    }

    /**
     * Base Main Character constructor
     */
    public MainCharacter(float col, float row, float speed, String name,
                         int health) {
        super(row, col, speed, name, health);
        //TODO: Change this to properly.
        this.setTexture("main_piece");
        this.setHeight(1);
        this.setObjectName("MainPiece");

        GameManager.getManagerFromInstance(InputManager.class)
                .addKeyDownListener(this);
        GameManager.getManagerFromInstance(InputManager.class)
                .addKeyUpListener(this);
        GameManager.getManagerFromInstance(InputManager.class)
                .addTouchDownListener(this);

        this.direction = new Vector2(row, col);
        this.direction.limit2(0.05f);

        this.weapons = new ArrayList<>();

        instantiateInventory();

        this.level = 1;
        this.foodLevel = 100;

        // create a new goldPouch object
        this.goldPouch = new HashMap<>();
        GoldPiece initialPiece = new GoldPiece(100);
    }

    /**
     * Attack with the weapon the character has equip.
     */
    public void attack() {
        //TODO: Need to calculate an angle that the character is facing.
        HexVector position = this.getPosition();

        //Spawn projectile in front of character for now.
        this.hitBox = new Projectile("slash",
                "test hitbox",
                position.getCol() + 1,
                position.getRow(),
                1, 1);

        //Get AbstractWorld from static class GameManager.
        GameManager manager = GameManager.get();

        //Add the projectile entity to the game world.
        manager.getWorld().addEntity(this.hitBox);
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
     * Add weapon to weapons list
     * @param item weapon to be added
     */
    public void pickUpWeapon(Weapon item) {
        weapons.add(item);
    }

    /**
     * Removes items from player's collection
     * @param item weapon being removed
     */
    public void dropWeapon(Weapon item) {
        if (weapons.contains(item)) {
            weapons.remove(item);
        }
    }

    /**
     * Get the weapons for the player
     * @return weapons
     */
    public List<Weapon> getWeapons() {
        return new ArrayList<>(weapons);
    }

    /**
     * Deals damage to character from combat
     * @param item weapon character is being hit by
     */
    public void weaponEffect(Weapon item) {
        this.changeHealth(item.getDamage().intValue() * -1);
    }

    /**
     * Add weapon to weapons list
     * @param item weapon to be added
     */
    public void pickUpInventory(Item item) {
        this.inventories.inventoryAdd(item);
    }

    /**
     * Attempts to drop given item from inventory
     * @param item item to be dropped from inventory
     */
    public void dropInventory(String item) {
        this.inventories.inventoryDrop(item);
    }

    /**
     * Change the hunger points value for the player
     * (+ve amount increases hunger points)
     * (-ve amount decreases hunger points)
     * @param amount the amount to change it by
     */
    public void change_food(int amount){
        this.foodLevel += amount;
        if (foodLevel > 100) {
            foodLevel = 100;
        }
        if (foodLevel < 0) {
            foodLevel = 0;
        }
    }

    /**
     * Get how many hunger points the player has
     * @return The number of hunger points the player has
     */
    public int getFoodLevel(){
        return foodLevel;
    }

    /**
     * Method for the MainCharacter to eat food and restore/decrease hunger level
     * TODO: add hunger values to food items
     * @param item the item to eat
     */
    public void eatFood(Item item) {
        int amount = inventories.getAmount(item.getName());
        if (amount > 0) {
            if (item instanceof HealthResources) {
                int hungerValue = ((HealthResources) item).getFoodValue();
                int healthValue = ((HealthResources) item).getHealthValue();
                change_food(hungerValue);
                changeHealth(healthValue);
                dropInventory(item.getName());
            } else {
                System.out.println("Given item (" + item.getName() + ") is " +
                        "not edible!");
            }
        } else {
            System.out.println("You don't have enough of the given item");
        }
    }

    /**
     * Gets the player's weapons, modification of the returned list
     * doesn't impact the internal class
     * @return a list of the player's weapons
     * See if the player is starving
     * @return true if hunger points is <= 0, else false
     */
    public boolean isStarving() {
        return foodLevel <= 0;
    }

    /**
     * Set the players inventory to a predefined inventory
     * e.g for loading player saves
     * @param inventoryContents the save for the inventory
     */
    public void setInventory(Map<String, List<Item>> inventoryContents,
                             List<String> quickAccessContent) {
        this.inventories = new InventoryManager(inventoryContents,
                quickAccessContent);
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

    /**
     * Change the player's appearance to the set texture
     * @param texture the texture to set
     */
    public void changeTexture(String texture){
        this.setTexture(texture);
    }

    /**
     * Handles tick based stuff, e.g. movement
     */
    private void updateMoveVector() {

        if (MOVE_UP){this.direction.add(0.0f, speed);}
        if (MOVE_LEFT){this.direction.sub(speed, 0.0f);}
        if (MOVE_DOWN){this.direction.sub(0.0f, speed);}
        if (MOVE_RIGHT){this.direction.add(speed, 0.0f);}
    }

    public void notifyTouchDown(int screenX, int screenY, int pointer,
                                int button) {
        // only allow left clicks to move player

        if (button == 0) {
            this.attack();
        }
    }

    /**
     * Handles tick based stuff, e.g. movement
     */
    @Override
    public void onTick(long i) {
        updateMoveVector();
        this.updateCollider();
        this.setCurrentSpeed(this.direction.len());
        this.moveTowards(new HexVector(this.direction.x, this.direction.y));
//        System.out.printf("(%s : %s) diff: (%s, %s)%n", this.direction,
//         this.getPosition(), this.direction.x - this.getCol(),
//         this.direction.y - this.getRow());
//        System.out.printf("%s%n", this.currentSpeed);
//        TODO: Check direction for animation here

        //Displays or hides the build menu when "b" is clicked
        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            GameManager.getManagerFromInstance(ConstructionManager.class).displayWindow();
        }
    }

    @Override
    public void moveTowards(HexVector destination) {
        position.moveToward(destination, this.currentSpeed);
    }

    /**
     * Sets the Player's current movement speed.
     * @param cSpeed the speed for the player to currently move at.
     */
    private void setCurrentSpeed(float cSpeed){
        this.currentSpeed = cSpeed;
    }

    /**
     * Sets the appropriate movement flags to true on keyDown
     * @param keycode the key being pressed
     */
    @Override
    public void notifyKeyDown(int keycode) {
        //player cant move when paused
        if (GameManager.getPaused()) {
            return;
        }
        switch (keycode) {
            case Input.Keys.W:
                MOVE_UP = true;
                break;
            case Input.Keys.A:
                MOVE_LEFT = true;
                break;
            case Input.Keys.S:
                MOVE_DOWN = true;
                break;
            case Input.Keys.D:
                MOVE_RIGHT = true;
                break;
        }
    }

    /**
     * Sets the appropriate movement flags to false on keyUp
     * @param keycode the key being released
     */
    @Override
    public void notifyKeyUp(int keycode) {
        movingAnimation = AnimationRole.NULL;
        switch(keycode){
            case Input.Keys.W:
                MOVE_UP = false;
                break;
            case Input.Keys.A:
                MOVE_LEFT = false;
                break;
            case Input.Keys.S:
                MOVE_DOWN = false;
                break;
            case Input.Keys.D:
                MOVE_RIGHT = false;
                break;
        }
    }

    /*
    Potential more methods and related attributes for future sprints:
    -record killed enemies
    -interaction with worlds
    -effects on MainCharacter with different Inventory and Weapon items
    */

    /**
     * Adds a piece of gold to the Gold Pouch
     * @param gold The piece of gold to be added to the pouch
     * @param count How many of that piece of gold should be added
     */
    public void addGold(GoldPiece gold, Integer count){

        // store the gold's value (5G, 10G etc) as a variable
        Integer goldValue = gold.getValue();

        // if this gold value already exists in the pouch
        if (goldPouch.containsKey(goldValue)){
            // add this piece to the already existing list of pieces
            goldPouch.put(goldValue, goldPouch.get(goldValue) + count);
        } else {
            goldPouch.put(goldValue, count);
        }

    }

}
