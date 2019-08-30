package deco2800.skyfall.entities;

import com.badlogic.gdx.*;
import com.badlogic.gdx.math.Vector2;
import deco2800.skyfall.GameScreen;
import deco2800.skyfall.Tickable;
import deco2800.skyfall.animation.*;
import deco2800.skyfall.managers.*;
import deco2800.skyfall.observers.*;
import deco2800.skyfall.resources.GoldPiece;
import deco2800.skyfall.resources.HealthResources;
import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.items.Hatchet;
import deco2800.skyfall.resources.items.PickAxe;
import deco2800.skyfall.util.*;
import deco2800.skyfall.worlds.AbstractWorld;
import deco2800.skyfall.worlds.RocketWorld;
import deco2800.skyfall.worlds.Tile;
import org.lwjgl.Sys;

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

    private int itemSlotSelected;

    //List of blueprints that the player has learned.
    private List<String> blueprintsLearned;

    public static final String WALK_NORMAL = "people_walk_normal";

    private SoundManager soundManager = GameManager.get()
            .getManager(SoundManager.class);

    // The index of the item selected to be used in the hotbar
    // ie. [sword][gun][apple]
    // if selecting sword then equipped_item = 0,
    // if selecting gun the equipped_item = 1
    private int equipped_item;
    private final int INVENTORY_MAX_CAPACITY = 20;
    private final int HOTBAR_MAX_CAPACITY = 5;

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

    // A goldPouch to store the character's gold pieces.
    private HashMap<Integer, Integer> goldPouch;

    /**
     * The direction and speed of the MainCharacter
     */
    //protected Vector2 direction;
    //protected float currentSpeed;

    /**
     * Helper bools to tell which direction the player intends to move
     */
    private int xInput;
    private int yInput;

    private float xVel;
    private float yVel;

    private float acceleration;

    private float maxSpeed;

    private double vel;

    private ArrayList<Integer> velHistoryX;
    private ArrayList<Integer> velHistoryY;

    private boolean isMoving;

    /**
     * Private helper method to instantiate inventory for Main Character
     * constructor
     * Used for combat testing melee/range weapons.
     * What number item slot the player has pressed.
     * TODO: remove or integrate into item system.
     * e.g. 1 = test range weapon
     * 2 = test melee weapon
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

        //this.direction = new Vector2(row, col);
        //this.direction.limit2(0.05f);

        this.weapons = new ArrayList<>();

        instantiateInventory();

        this.level = 1;
        this.foodLevel = 100;

        // create a new goldPouch object
        this.goldPouch = new HashMap<>();
        // create the starting gold pouch with 1 x 100G
        GoldPiece initialPiece = new GoldPiece(100);
        this.addGold(initialPiece, 1);
        //Initialises the players velocity properties
        xInput = 0;
        yInput = 0;
        xVel = 0;
        yVel = 0;
        setAcceleration(0.01f);
        setMaxSpeed(0.7f);
        vel = 0;
        velHistoryX = new ArrayList<>();
        velHistoryY = new ArrayList<>();

        isMoving = false;
    }

    /**
     * Attack with the weapon the character has equip.
     */
    public void attack() {
        //TODO: Need to calculate an angle that the character is facing.
        HexVector position = this.getPosition();

/*        //Spawn projectile in front of character for now.
        this.hitBox = new Projectile("slash",
                "test hitbox",
                position.getCol() + 1,
                position.getRow(),
                1, 1);*/

        //Get AbstractWorld from static class GameManager.
        GameManager manager = GameManager.get();

        //Add the projectile entity to the game world.
        manager.getWorld().addEntity(this.hitBox);
    }


    /**
     * Constructor with various textures
     *
     * @param textures A array of length 6 with string names corresponding to
     *                 different orientation
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
     * Switch the item the MainCharacter has equip.
     *
     * @param keyCode Keycode the player has pressed.
     */
    protected void switchItem(int keyCode) {
        // If key is in range of 1-9, accept the input.
        if (keyCode >= 8 && keyCode <= 16) {
            int keyNumber = Integer.parseInt(Input.Keys.toString(keyCode));
            this.itemSlotSelected = keyNumber;
            System.out.println("Switched to item: " + keyNumber);
        }
    }

    /**
     * Return the currently selected item slot.
     *
     * @return The item slot the MainCharacter has equip.
     */
    public int getItemSlotSelected() {
        return this.itemSlotSelected;
    }

    /**
     * Attack with the weapon the character has equip.
     */
    public void attack(HexVector mousePosition) {
        HexVector position = this.getPosition();

        // Make projectile move toward the angle
        // Spawn projectile in front of character for now.

        this.hitBox = new Projectile(mousePosition,
                this.itemSlotSelected == 1 ? "arcane" : "slash",
                "test hitbox",
                position.getCol() + 1,
                position.getRow(),
                1,
                1,
                this.itemSlotSelected == 1 ? 1 : 0);

        // Get AbstractWorld from static class GameManager.
        GameManager manager = GameManager.get();

        // Add the projectile entity to the game world.
        manager.getWorld().addEntity(this.hitBox);
    }

    /**
     * Add weapon to weapons list
     *
     * @param item weapon to be added
     */
    public void pickUpWeapon(Weapon item) {
        weapons.add(item);
    }

    /**
     * Removes items from player's collection
     *
     * @param item weapon being removed
     */
    public void dropWeapon(Weapon item) {
        if (weapons.contains(item)) {
            weapons.remove(item);
        }
    }





    /**
     * Gets the weapon manager of the character, so it can only be modified
     * this way, prevents having it being a public variable
     *
     * @return the weapon manager of character
     */
    public List<Weapon> getWeaponManager() {
        return this.weapons;
    }

    /**
     * Deals damage to character from combat
     *
     * @param item weapon character is being hit by
     */
    public void weaponEffect(Weapon item) {
        this.changeHealth(item.getDamage().intValue() * -1);
    }

    /**
     * Set the players inventory to a predefined inventory
     * e.g for loading player saves
     *
     * @param inventoryContents the save for the inventory
     */
    public void setInventory(Map<String, List<Item>> inventoryContents,
                             List<String> quickAccessContent) {
        this.inventories = new InventoryManager(inventoryContents,
                quickAccessContent);
    }

    /**
     * Add weapon to weapons list
     *
     * @param item weapon to be added
     */
    public void pickUpInventory(Item item) {
        this.inventories.inventoryAdd(item);
    }

    /**
     * Attempts to drop given item from inventory
     *
     * @param item item to be dropped from inventory
     */
    public void dropInventory(String item) {
        this.inventories.inventoryDrop(item);
    }

    /**
     * Gets the inventory manager of the character, so it can only be modified
     * this way, prevents having it being a public variable
     *
     * @return the inventory manager of character
     */
    public InventoryManager getInventoryManager() {
        return this.inventories;
    }

    /**
     * Change the hunger points value for the player
     * (+ve amount increases hunger points)
     * (-ve amount decreases hunger points)
     *
     * @param amount the amount to change it by
     */
    public void change_food(int amount) {
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
     *
     * @return The number of hunger points the player has
     */
    public int getFoodLevel() {
        return foodLevel;
    }

    /**
     * Method for the MainCharacter to eat food and restore/decrease hunger level
     *
     * @param item the item to eat
     */
    public void eatFood(Item item) {
        int amount = inventories.getAmount(item.getName());
        if (amount > 0) {
            if (item instanceof HealthResources) {
                int hungerValue = ((HealthResources) item).getFoodValue();
                change_food(hungerValue);
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
     *
     * @return true if hunger points is <= 0, else false
     */
    public boolean isStarving() {
        return foodLevel <= 0;
    }

    /**
     * Change current level of character and increases health by 10
     * @param change amount being added or subtracted
     */
    public void changeLevel(int change) {
        if (level + change >= 1) {
            this.level += change;
            this.changeHealth(10);
        }
    }

    /**
     * Gets the current level of character
     *
     * @return level of character
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Change the player's appearance to the set texture
     *
     * @param texture the texture to set
     */
    public void changeTexture(String texture) {
        this.setTexture(texture);
    }

    /**
     * Handles tick based stuff, e.g. movement
     */


    public void notifyTouchDown(int screenX, int screenY, int pointer,
                                int button) {
        // only allow left clicks to move player
        if (GameScreen.isPaused) {
            return;
        }
        if (button == 0) {
            this.attack();
            float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(),
                    Gdx.input.getY());
            float[] clickedPosition =
                    WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);

            HexVector mousePos = new HexVector(clickedPosition[0],
                    clickedPosition[1]);
            this.attack(mousePos);
        }
    }

    /**
     * Handles tick based stuff, e.g. movement
     */
    @Override
    public void onTick(long i) {
        this.updatePosition();
        this.updateCollider();

        this.movementSound();
        //this.setCurrentSpeed(this.direction.len());
        //this.moveTowards(new HexVector(this.direction.x, this.direction.y));
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



    /**
     * Sets the appropriate movement flags to true on keyDown
     *
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
                yInput += 1;
                break;
            case Input.Keys.A:
                xInput += -1;
                break;
            case Input.Keys.S:
                yInput += -1;
                break;
            case Input.Keys.D:
                xInput += 1;
                break;
            case Input.Keys.H:
                useHatchet();
                break;
            case Input.Keys.P:
                usePickAxe();
                break;


        }
    }

    /**
     * Sets the appropriate movement flags to false on keyUp
     *
     * @param keycode the key being released
     */
    @Override
    public void notifyKeyUp(int keycode) {
        movingAnimation = AnimationRole.NULL;

        // Player cant move when paused
        if (GameManager.getPaused()) {
            return;
        }
        switch (keycode) {
            case Input.Keys.W:
                yInput -= 1;
                break;
            case Input.Keys.A:
                xInput -= -1;
                break;
            case Input.Keys.S:
                yInput -= -1;
                break;
            case Input.Keys.D:
                xInput -= 1;
                break;
            case Input.Keys.H:
                break;
            case Input.Keys.P:
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

    /**
     * Removes one instance of a gold piece in the pouch.
     * @param gold The gold piece to be removed from the pouch.
     */
    public void removeGold(GoldPiece gold){
        // store the gold's value (5G, 10G etc) as a variable
        Integer goldValue = gold.getValue();

        // if this gold value does not exist in the pouch
        if (!(goldPouch.containsKey(goldValue))){
            return;
        } else if (goldPouch.get(goldValue) > 1) {
            goldPouch.put(goldValue, goldPouch.get(goldValue) - 1);
        } else {
            goldPouch.remove(goldValue);
        }
    }

    /**
     * Returns the types of GoldPieces in the pouch and how many of each type
     * exist
     * @return The contents of the Main Character's gold pouch
     */
    public HashMap<Integer, Integer> getGoldPouch() {
        return new HashMap<>(goldPouch);
    }

    /**
     * Returns the sum of the gold piece values in the Gold Pouch
     * @return The total value of the Gold Pouch
     */
    public Integer getGoldPouchTotalValue(){
        Integer totalValue = 0;
        for (Integer goldValue : goldPouch.keySet()) {
            totalValue += goldValue * goldPouch.get(goldValue);
        }

        return totalValue;
    }


    /**
     * Moves the player based on current key inputs
     */
    public void updatePosition() {
        // Gets current position
        float xPos = position.getCol();
        float yPos = position.getRow();

        //Returns tile at left arm (our perspective) of the player
        float tileCol = (float) Math.round(xPos);
        float tileRow = (float) Math.round(yPos);
        if (tileCol % 2 != 0){
            tileRow += 0.5f;
        }

        //Determined friction scaling factor to apply based on current tile
        float friction;
        Tile currentTile = GameManager.get().getWorld().getTile(tileCol,tileRow);
        if(currentTile != null && currentTile.getTexture() != null){
            //Tile specific friction
            friction = Tile.getFriction(currentTile.getTextureName());
        }else{
            //Default friction
            friction = 1f;
        }

        // Calculates new x and y positions
        xPos += xVel + xInput * acceleration * 0.5 * friction;
        yPos += yVel + yInput * acceleration * 0.5 * friction;

        // Calculates velocity in x direction
        if (xInput != 0) {
            xVel += xInput * acceleration * friction;
            // Prevents sliding
            if (xVel / Math.abs(xVel) != xInput) {
                xVel = 0;
            }
        } else if (yInput != 0) {
            xVel *= 0.8;
        } else {
            xVel = 0;
        }

        // Calculates velocity in y direction
        if (yInput != 0) {
            yVel += yInput * acceleration * friction;
            // Prevents sliding
            if (yVel / Math.abs(yVel) != yInput) {
                yVel = 0;
            }
        } else if (xInput != 0) {
            yVel *= 0.8;
        } else {
            yVel = 0;
        }

        // caps the velocity
        if (vel > maxSpeed) {
            xVel /= vel;
            yVel /= vel;

            xVel *= maxSpeed;
            yVel *= maxSpeed;
        }

        // Calculates speed to destination
        vel = Math.sqrt((xVel * xVel) + (yVel * yVel));

        // Calculates destination vector
        HexVector destination = new HexVector(xPos, yPos);

        // Moves the player to new location
        position.moveToward(destination, vel);

        //Records velocity history in x direction
        if (velHistoryX.size() < 2 || velHistoryY.size() < 2) {
            velHistoryX.add((int) (xVel * 100));
            velHistoryY.add((int) (yVel * 100));
        } else if (velHistoryX.get(1) != (int) (xVel * 100) ||
                velHistoryY.get(1) != (int) (yVel * 100)) {
            velHistoryX.set(0, velHistoryX.get(1));
            velHistoryX.set(1, (int) (xVel * 100));

            velHistoryY.set(0, velHistoryY.get(1));
            velHistoryY.set(1, (int) (yVel * 100));
        }
    }

    /**
     * Gets the direction the player is currently facing
     * North: 0 deg
     * East: 90 deg
     * South: 180 deg
     * West: 270 deg
     *
     * @return the player direction (units: degrees)
     */
    public double getPlayerDirectionAngle() {
        double val;
        if (xInput != 0 || yInput != 0) {
            val = Math.atan2(yInput, xInput);
        } else {
            val = Math.atan2(velHistoryY.get(0), velHistoryX.get(0));
        }
        val = val * -180 / Math.PI + 90;
        if (val < 0) {
            val += 360;
        }
        return val;
    }

    /**
     * Converts the current players direction into a cardinal direction
     * North, South-West, etc.
     *
     * @return new texture to use
     */
    public String getPlayerDirectionCardinal() {
        double direction = getPlayerDirectionAngle();

        if (direction <= 22.5 || direction >= 337.5) {
            return "North";
        } else if (22.5 <= direction && direction <= 67.5) {
            return "North-East";
        } else if (67.5 <= direction && direction <= 112.5) {
            return "East";
        } else if (112.5 <= direction && direction <= 157.5) {
            return "South-East";
        } else if (157.5 <= direction && direction <= 202.5) {
            return "South";
        } else if (202.5 <= direction && direction <= 247.5) {
            return "South-West";
        } else if (247.5 <= direction && direction <= 292.5) {
            return "West";
        } else if (292.5 <= direction && direction <= 337.5) {
            return "North-West";
        }

        return "Invalid";
    }

    /**
     * Gets a list of the players current velocity
     * 0: x velocity
     * 1: y velocity
     * 2: net velocity
     *
     * @return list of players velocity properties
     */
    public List getVelocity() {
        ArrayList<Float> velocity = new ArrayList<>();
        velocity.add(xVel);
        velocity.add(yVel);
        velocity.add((float) vel);

        return velocity;
    }

    /**
     * Sets the players acceleration
     *
     * @param newAcceleration: the new acceleration for the player
     */
    public void setAcceleration(float newAcceleration) {
        this.acceleration = newAcceleration;
    }

    /**
     * Sets the players max speed
     *
     * @param newMaxSpeed: the new max speed of the player
     */
    public void setMaxSpeed(float newMaxSpeed) {
        this.maxSpeed = newMaxSpeed;
    }

    /**
     * Gets the players current acceleration
     *
     * @return the players acceleration
     */
    public float getAcceleration() {
        return this.acceleration;
    }

    /**
     * Gets the plays current max speed
     *
     * @return the players max speed
     */
    public float getMaxSpeed() {
        return this.maxSpeed;
    }

    public void movementSound() {
        if (!isMoving && vel != 0) {
            //Runs when the player starts moving
            isMoving = true;
            //System.out.println("Start Playing");
            //TODO: Play movement sound
            SoundManager.loopSound(WALK_NORMAL);
        }

        if (isMoving && vel == 0) {
            //Runs when the player stops moving
            isMoving = false;
            //System.out.println("Stop Playing");
            //TODO: Stop Player movement
            SoundManager.stopSound(WALK_NORMAL);
        }
    }


    /***
     * This method enables the Main character to use Hatchet. The player's
     * distance from the tree should not be more than 2.5.Every time a
     * wood is collected a message is printed.
     * This method will be changed later to increase efficiency.
     */
    public void useHatchet(){

        if (this.inventories.getQuickAccess().containsKey("Hatchet")) {
            Hatchet playerHatchet = new Hatchet(this);

            for (AbstractEntity entity : GameManager.get().getWorld().getEntities()) {

                if (entity instanceof Tree) {

                    if ( this.getPosition().distance(entity.getPosition()) <= 2.5 ) {
                        playerHatchet.farmTree((Tree) entity);
                        System.out.println(this.inventories.toString());
                    }
                }
            }

        } else{
            System.out.println("No Hatchet in Quick Access");
        }
    }

    /***
     * This method enables the Main character to use Hatchet. The player's
     * distance from the tree should not be more than 2.5.Every time a
     * wood is collected a message is printed.
     * This method will be changed later to increase efficiency.
     */
    public void usePickAxe(){

        if (this.inventories.getQuickAccess().containsKey("Pick Axe")) {
            PickAxe playerPickAxe = new PickAxe(this);

            for (AbstractEntity entity : GameManager.get().getWorld().getEntities()) {

                if (entity instanceof Rock) {

                    if ( this.getPosition().distance(entity.getPosition()) <= 2.5 ) {
                        playerPickAxe.farmRock((Rock) entity);
                        System.out.println(this.inventories.toString());
                    }
                }
            }

        } else{
            System.out.println("No PickAxe in Quick Access");
        }
    }

    /***
     * A getter method for the blueprints that the player has learned.
     * @return the learned blueprints list
     */
    public List<String> getBlueprintsLearned() {
        return this.blueprintsLearned;
    }


}
