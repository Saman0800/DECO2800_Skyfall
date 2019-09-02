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
import deco2800.skyfall.resources.ManufacturedResources;
import deco2800.skyfall.resources.items.Hatchet;
import deco2800.skyfall.resources.items.PickAxe;
import deco2800.skyfall.util.*;
import deco2800.skyfall.worlds.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Main character in the game
 */
public class MainCharacter extends Peon implements KeyDownObserver,
        KeyUpObserver,TouchDownObserver, Tickable , Animatable {

    private final Logger logger = LoggerFactory.getLogger(MainCharacter.class);

    // Weapon Manager for MainCharacter
    private WeaponManager weapons;

    // Manager for all of MainCharacter's inventories
    private InventoryManager inventories;

    // Hotbar of inventories
    private List<Item> hotbar;

    //List of blueprints that the player has learned.
    private List<String> blueprintsLearned;

    //The name of the item to be created.
    private String itemToCreate;

    // Variables to sound effects
    public static final String WALK_NORMAL = "people_walk_normal";
    private SoundManager soundManager = GameManager.get()
            .getManager(SoundManager.class);

    //The pick Axe that is going to be created
    private Hatchet hatchetToCreate;

    // The index of the item selected to be used in the hotbar
    // ie. [sword][gun][apple]
    // if selecting sword then equipped_item = 0,
    // if selecting gun the equipped_item = 1
    private int equipped_item;
    private static final int INVENTORY_MAX_CAPACITY = 20;
    private static final int HOTBAR_MAX_CAPACITY = 5;

    // Level/point system for the Main Character to be recorded as game goes on
    private int level;

    /* Food is from 100 to 0 and goes down as the Player does actions such as:
     - Walking
     - Combat
     - Resource Collecting
     Once the food level reaches 0, the Player begins to starve, and starts to
     lose health points. Still unsure if I should implement time based
     starvation where as time goes on, the Player loses hunger.
     */
    private int foodLevel;

    // Textures for all 6 directions to correspond to movement of character
    private String[] textures;

    // A goldPouch to store the character's gold pieces.
    private HashMap<Integer, Integer> goldPouch;


    /**
     * The direction and speed of the MainCharacter
     */
    protected Vector2 direction;
    protected float currentSpeed;

    /*
     * Helper vars to tell which direction the player intends to move
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

    /*
     * Used for combat testing melee/range weapons.
     * What number item slot the player has pressed.
     * e.g. 1 = test range weapon
     * 2 = test melee weapon
     */
    private int itemSlotSelected = 1;

    /**
     * How long does MainCharacter hurt status lasts,
     */
    private long hurtTime = 0;

    /**
     * How long does MainCharacter take to recover,
     */
    private long recoverTime = 3000;

    /**
     * Check whether MainCharacter is hurt.
     */
    private boolean isHurt = false;

    /**
     * Check whether MainCharacter is attacking.
     */
    private boolean isAttacking = false;

    /**
     * Private helper method to instantiate inventory and weapon managers for
     * Main Character constructor
     */
    private void instantiateManagers() {
        this.inventories = new InventoryManager();
        this.weapons = new WeaponManager();
    }

    /**
     * Base Main Character constructor
     */
    public MainCharacter(float col, float row, float speed, String name,
                         int health) {
        super(row, col, speed, name, health);
        this.setTexture("__ANIMATION_MainCharacterE_Anim:0");
        this.setHeight(1);
        this.setObjectName("MainPiece");

        GameManager.getManagerFromInstance(InputManager.class)
                .addKeyDownListener(this);
        GameManager.getManagerFromInstance(InputManager.class)
                .addKeyUpListener(this);
        GameManager.getManagerFromInstance(InputManager.class)
                .addTouchDownListener(this);

        this.weapons = GameManager.getManagerFromInstance(WeaponManager.class);
        this.inventories = GameManager.getManagerFromInstance(InventoryManager.class);

        this.equipped_item = 0;
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

        HexVector position = this.getPosition();

/*        //Spawn projectile in front of character for now.
        this.hitBox = new Projectile("slash",
                "test hitbox",
                position.getCol() + 1,
                position.getRow(),
                1, 1);*/


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
        this.scale = 0.4f;
        setDirectionTextures();
        configureAnimations();
    }

    /**
     * Constructor with various textures
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
     * @param keyCode Keycode the player has pressed.
     */
    protected void switchItem(int keyCode) {
        // If key is in range of 1-9, accept the input.
        if (keyCode >= 8 && keyCode <= 16) {
            int keyNumber = Integer.parseInt(Input.Keys.toString(keyCode));
            this.itemSlotSelected = keyNumber;
            logger.info("Switched to item: " + keyNumber);
        }
    }

    /**
     * Return the currently selected item slot.
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
        setAttacking(true);
        setCurrentState(AnimationRole.DEAD);

        // Make projectile move toward the angle
        // Spawn projectile in front of character for now.

        Projectile projectile = new Projectile(mousePosition,
                this.itemSlotSelected == 1 ? "range_test":"melee_test",
                "test hitbox",
                position.getCol() + 1,
                position.getRow(),
                1,
                0.1f,
                this.itemSlotSelected == 1 ? 1 : 0);

        // Get AbstractWorld from static class GameManager.
        GameManager manager = GameManager.get();

        // Add the projectile entity to the game world.
        manager.getWorld().addEntity(projectile);
    }

    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

    /**
     * Player takes damage from other entities/ by starving.
     *
     */
    public void hurt(int damage) {
        this.changeHealth(-damage);

        if (this.getHealth() <= 0) {
            kill();
        } else {
            hurtTime = 3000;
            HexVector bounceBack = new HexVector();
            /*
            switch (getPlayerDirectionCardinal()) {
                case "North":
                    bounceBack = new HexVector(this.direction.getX(), this.direction.getY() - 1);
                    break;
                case "North-East":
                    bounceBack = new HexVector(this.direction.getX() - 1, this.direction.getY() - 1);
                    break;
                case "East":
                    bounceBack = new HexVector(this.direction.getX() - 1, this.direction.getY());
                    break;
                case "South-East":
                    bounceBack = new HexVector(this.direction.getX() - 1, this.direction.getY() + 1);
                    break;
                case "South":
                    bounceBack = new HexVector(this.direction.getX(), this.direction.getY() + 1);
                    break;
                case "South-West":
                    bounceBack = new HexVector(this.direction.getX() + 1, this.direction.getY() + 1);
                    break;
                case "West":
                    bounceBack = new HexVector(this.direction.getX() + 1, this.direction.getY());
                    break;
                case "North-West":
                    bounceBack = new HexVector(this.direction.getX() + 1, this.direction.getY() - 1);
                    break;
            }
            position.moveToward(bounceBack, 0.5f);

            /* AS.PlayOneShot(hurtSound);
            while(hurtTime < System.currentTimeMillis()) {
                vel = 0;
                anim.SetBool("Invincible", true);
                rb2d.AddForce(new com.badlogic.gdx.math.Vector2(hurtForce.x*forceDir,hurtForce.y));
            }
            */
           // recover();
       }
    }

    /**
     * Player recovers from being attacked. It removes player 's
     * hurt effect (e.g. sprite flashing in red), in hurt().
     */
    void recover () {
        setHurt(false);
        // controller.enabled = true;
    }

    /**
     * Kills the player. and notifying the game that the player
     * has died and cannot do any actions in game anymore.
     */
    void kill () {
        // stop player controls
        setMaxSpeed(0);

        // set health to 0.
        changeHealth(0);

        // AS.PlayOneShot(dieSound);
        GameManager.setPaused(true);
    }

    /**
     * @return if player is in the state of "hurt".
     */
    public boolean IsHurt() {
        return isHurt;
    }

    /**
     * @return if player is in the state of "hurt".
     */
    public void setHurt(boolean isHurt) {
        this.isHurt = isHurt;
    }

    /**
     *  Add weapon to weapons list
     * @param item weapon to be added
     *
     */
    public void pickUpWeapon(Weapon item) {
        weapons.pickUpWeapon(item);
    }

    /**
     * Removes items from player's collection
     * @param item weapon being removed
     */
    public void dropWeapon(Weapon item) {
        weapons.dropWeapon(item);
    }

    /**
     * Get the weapons for the player
     * @return weapons
     */
    public Map<Weapon, Integer> getWeapons() {
        return weapons.getWeapons();
    }

    /**
     * Attempts to equip a weapon from the weapons map
     * @param item weapon being equipped
     */
    public void equipWeapon(Weapon item) {
        weapons.equipWeapon(item);
    }

    /**
     * Attempts to unequip a weapon and return it to the weapons map
     * @param item weapon being unequipped
     */
    public void unequipWeapon(Weapon item) {
        weapons.unequipWeapon(item);
    }

    /**
     * Get a copy of the equipped weapons list
     * Modifying the returned list shouldn't affect the internal state of class
     * @return equipped list
     */
    public List<Weapon> getEquipped() {
        return weapons.getEquipped();
    }

    /**
     * Gets the weapon manager of the character, so it can only be modified
     * this way, prevents having it being a public variable
     * @return the weapon manager of character
     */
    public WeaponManager getWeaponManager() {
        return this.weapons;
    }

    /**
     * Deals damage to character from combat
     * @param item weapon character is being hit by
     */
    public void weaponEffect(Weapon item) {
        this.changeHealth(item.getDamage().intValue() * -1);
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
     * Gets the inventory manager of the character, so it can only be modified
     * this way, prevents having it being a public variable
     * @return the inventory manager of character
     */
    public InventoryManager getInventoryManager() {
        return this.inventories;
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
     * Method for the MainCharacter to eat food and restore/decrease hunger
     * level
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
                logger.info("Given item (" + item.getName() + ") is " + "not edible!");
            }
        } else {
           logger.info("You don't have enough of the given item");
        }
    }

    /**
     * See if the player is starving
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
            this.changeHealth(change * 10);
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
     * Handles mouse click events
     * @param screenX the x position the mouse was pressed at
     * @param screenY the y position the mouse was pressed at
     * @param pointer mouse pointer
     * @param button the button which was pressed
     */
    public void notifyTouchDown(int screenX, int screenY, int pointer,
                                int button) {
        // only allow left clicks to move player
        if (GameScreen.isPaused) {
            return;
        }
        if (button == 0) {
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

        this.updateAnimation();
        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            GameManager.getManagerFromInstance(ConstructionManager.class)
                    .displayWindow();
        }

    }

    /**
     * Move character towards a destination
     */
    @Override
    public void moveTowards(HexVector destination) {
        position.moveToward(destination, this.currentSpeed);
    }

    /**
     * Sets the Player's current movement speed
     * @param cSpeed the speed for the player to currently move at
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
        GoldPiece g = new GoldPiece(5);
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
            case Input.Keys.G:
                addClosestGoldPiece();
                break;
            case Input.Keys.M:
                getGoldPouchTotalValue();
                break;
            default:
                switchItem(keycode);
                //xInput += 1;
                break;

        }
    }

    /**
     * Sets the appropriate movement flags to false on keyUp
     * @param keycode the key being released
     */
    @Override
    public void notifyKeyUp(int keycode) {
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
            case Input.Keys.G:
                break;
            case Input.Keys.M:
                break;

        }
    }

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
        logger.info("The total value of your Gold Pouch is: " + totalValue + "G");
        return totalValue;
    }

    /**
     * If the player is within 2m of a gold piece and presses G, it will
     * be added to their Gold Pouch.
     *
     */
    public void addClosestGoldPiece(){
        for (AbstractEntity entity : GameManager.get().getWorld().getEntities()) {
                if (entity instanceof GoldPiece) {
                    if ( this.getPosition().distance(entity.getPosition()) <= 2 ) {
                        this.addGold((GoldPiece) entity, 1);
                        logger.info(this.inventories.toString());
                    }
                }

        }
        logger.info("Sorry, you are not close enough to a gold piece!");

    }

    /**
     * Moves the player based on current key inputs
     */
    public void updatePosition() {
        // Gets current position
        float xPos = position.getCol();
        float yPos = position.getRow();

        // Returns tile at left arm (our perspective) of the player
        float tileCol = (float) Math.round(xPos);
        float tileRow = (float) Math.round(yPos);
        if (tileCol % 2 != 0){
            tileRow += 0.5f;
        }

        // Determined friction scaling factor to apply based on current tile
        float friction;
        Tile currentTile = GameManager.get().getWorld().getTile(tileCol,
                tileRow);
        if (currentTile != null && currentTile.getTexture() != null) {
            // Tile specific friction
            friction = Tile.getFriction(currentTile.getTextureName());
        } else {
            // Default friction
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

        // Records velocity history in x direction
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
     * @return new texture to use
     */
    public String getPlayerDirectionCardinal() {
        double playerDirectionAngle = getPlayerDirectionAngle();
        if (playerDirectionAngle <= 22.5 || playerDirectionAngle >= 337.5) {
            setCurrentDirection(Direction.NORTH);
            return "North";
        } else if (22.5 <= playerDirectionAngle && playerDirectionAngle <= 67.5) {
            setCurrentDirection(Direction.NORTH_EAST);
            return "North-East";
        } else if (67.5 <= playerDirectionAngle && playerDirectionAngle <= 112.5) {
            setCurrentDirection(Direction.EAST);
            return "East";
        } else if (112.5 <= playerDirectionAngle && playerDirectionAngle <= 157.5) {
            setCurrentDirection(Direction.SOUTH_EAST);
            return "South-East";
        } else if (157.5 <= playerDirectionAngle && playerDirectionAngle <= 202.5) {
            setCurrentDirection(Direction.SOUTH);
            return "South";
        } else if (202.5 <= playerDirectionAngle && playerDirectionAngle <= 247.5) {
            setCurrentDirection(Direction.SOUTH_WEST);
            return "South-West";
        } else if (247.5 <= playerDirectionAngle && playerDirectionAngle <= 292.5) {
            setCurrentDirection(Direction.WEST);
            return "West";
        } else if (292.5 <= playerDirectionAngle && playerDirectionAngle <= 337.5) {
            setCurrentDirection(Direction.NORTH_WEST);
            return "North-West";
        }

        return "Invalid";
    }

    /**
     * Gets a list of the players current velocity
     * 0: x velocity
     * 1: y velocity
     * 2: net velocity
     * @return list of players velocity properties
     */
    public List<Float> getVelocity() {
        ArrayList<Float> velocity = new ArrayList<>();
        velocity.add(xVel);
        velocity.add(yVel);
        velocity.add((float) vel);

        return velocity;
    }

    /**
     * Sets the players acceleration
     * @param newAcceleration: the new acceleration for the player
     */
    public void setAcceleration(float newAcceleration) {
        this.acceleration = newAcceleration;
    }

    /**
     * Sets the players max speed
     * @param newMaxSpeed: the new max speed of the player
     */
    public void setMaxSpeed(float newMaxSpeed) {
        this.maxSpeed = newMaxSpeed;
    }

    /**
     * Gets the players current acceleration
     * @return the players acceleration
     */
    public float getAcceleration() {
        return this.acceleration;
    }

    /**
     * Gets the plays current max speed
     * @return the players max speed
     */
    public float getMaxSpeed() {
        return this.maxSpeed;
    }

    public void movementSound() {
        if (!isMoving && vel != 0) {
            // Runs when the player starts moving
            isMoving = true;

            //logger.info("Start Playing");
            //TODO: Play movement sound
            SoundManager.loopSound(WALK_NORMAL);
        }

        if (isMoving && vel == 0) {
            // Runs when the player stops moving
            isMoving = false;

            //logger.info("Stop Playing");
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

                    if ( this.getPosition().distance(entity.getPosition()) <= 0.5 ) {
                        playerHatchet.farmTree((Tree) entity);
                        logger.info(this.inventories.toString());
                    }
                }
            }
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

                    if ( this.getPosition().distance(entity.getPosition()) <= 0.5 ) {
                        playerPickAxe.farmRock((Rock) entity);
                        logger.info(this.inventories.toString());
                    }
                }
            }

        }
    }

    /***
     * A getter method for the blueprints that the player has learned.
     * @return the learned blueprints list
     */
    public List<String> getBlueprintsLearned() {
        blueprintsLearned = new ArrayList<>();

        return this.blueprintsLearned;
    }

    /***
     * A getter method to get the Item to be created.
     * @return the item to create.
     */
    public String getItemToCreate() {
        return this.itemToCreate;
    }

    /***
     * A Setter method to get the Item to be created.
     * @param itemToCreate the item to be created.
     */
    public void setItemToCreate(String item) {
        this.itemToCreate = item;
    }

    /***
     * Creates a manufactured item. Checks if required resources are in the inventory.
     * if yes, creates the item, adds it to the player's inventory
     * and deducts the required resource from inventory
     */
    public void createItem(ManufacturedResources itemToCreate){

        if (getBlueprintsLearned().contains(itemToCreate.getName())) {

            if (itemToCreate.getRequiredMetal()>=
                    this.getInventoryManager().getAmount(itemToCreate.getName())){
                logger.info("You don't have enough Metal");

            } else if (itemToCreate.getRequiredWood()>=
                    this.getInventoryManager().getAmount(itemToCreate.getName())){
                logger.info("You don't have enough Wood");

            } else if (itemToCreate.getRequiredStone()>=
                    this.getInventoryManager().getAmount(itemToCreate.getName())) {
                logger.info("You don't have enough Stone");

            } else {
                this.getInventoryManager().inventoryAdd(itemToCreate);

                this.getInventoryManager().inventoryDropMultiple("Metal",
                        itemToCreate.getRequiredMetal());
                this.getInventoryManager().inventoryDropMultiple("Stone",
                        itemToCreate.getRequiredStone());
                this.getInventoryManager().inventoryDropMultiple("Wood",
                        itemToCreate.getRequiredWood());
            }
        }
    }


    /**
     * Sets the animations.
     */
    @Override
    public void configureAnimations() {

        // Walk animation
        addAnimations(AnimationRole.MOVE, Direction.NORTH_WEST,
                new AnimationLinker("MainCharacterNW_Anim",
                AnimationRole.MOVE, Direction.NORTH_WEST, true,
                        true));

        addAnimations(AnimationRole.MOVE, Direction.NORTH_EAST,
                new AnimationLinker("MainCharacterNE_Anim",
                        AnimationRole.MOVE, Direction.NORTH_WEST, true,
                        true));

        addAnimations(AnimationRole.MOVE, Direction.SOUTH_WEST,
                new AnimationLinker("MainCharacterSW_Anim",
                        AnimationRole.MOVE, Direction.SOUTH_WEST, true,
                        true));

        addAnimations(AnimationRole.MOVE, Direction.SOUTH_EAST,
                new AnimationLinker("MainCharacterSE_Anim",
                        AnimationRole.MOVE, Direction.SOUTH_EAST, true,
                        true));

        addAnimations(AnimationRole.MOVE, Direction.EAST,
                new AnimationLinker("MainCharacterE_Anim",
                        AnimationRole.MOVE, Direction.EAST, true,
                        true));
        addAnimations(AnimationRole.MOVE, Direction.NORTH,
                new AnimationLinker("MainCharacterN_Anim",
                        AnimationRole.MOVE, Direction.NORTH, true,
                        true));

        addAnimations(AnimationRole.MOVE, Direction.WEST,
                new AnimationLinker("MainCharacterW_Anim",
                        AnimationRole.MOVE, Direction.WEST, true,
                        true));

        addAnimations(AnimationRole.MOVE, Direction.SOUTH,
                new AnimationLinker("MainCharacterS_Anim",
                        AnimationRole.MOVE, Direction.SOUTH, true ,true));

        // Attack animation
        addAnimations(AnimationRole.ATTACK, Direction.DEFAULT,
                new AnimationLinker("MainCharacter_Attack_E_Anim",
                        AnimationRole.ATTACK, Direction.DEFAULT, false ,true));

        // Hurt animation
        addAnimations(AnimationRole.HURT, Direction.DEFAULT,
                new AnimationLinker("MainCharacter_Hurt_E_Anim",
                        AnimationRole.HURT, Direction.DEFAULT, false ,true));

        // Dead animation
        addAnimations(AnimationRole.DEAD, Direction.DEFAULT,
                new AnimationLinker("MainCharacter_Dead_E_Anim",
                        AnimationRole.DEAD, Direction.DEFAULT, false ,true));
    }

    /**
     * Sets default direction textures uses the get index for Animation feature
     * as described in the animation documentation section 4.
     */
    @Override
    public void setDirectionTextures() {
        defaultDirectionTextures.put(Direction.EAST,
                "__ANIMATION_MainCharacterE_Anim:0");
        defaultDirectionTextures.put(Direction.NORTH,
                "__ANIMATION_MainCharacterN_Anim:0");
        defaultDirectionTextures.put(Direction.WEST,
                "__ANIMATION_MainCharacterW_Anim:0");
        defaultDirectionTextures.put(Direction.SOUTH,
                "__ANIMATION_MainCharacterS_Anim:0");
        defaultDirectionTextures.put(Direction.NORTH_EAST,
                "__ANIMATION_MainCharacterNE_Anim:0");
        defaultDirectionTextures.put(Direction.NORTH_WEST,
                "__ANIMATION_MainCharacterNW_Anim:0");
        defaultDirectionTextures.put(Direction.SOUTH_EAST,
                "__ANIMATION_MainCharacterSE_Anim:0");
        defaultDirectionTextures.put(Direction.SOUTH_WEST,
                "__ANIMATION_MainCharacterSW_Anim:0");
    }

    /**
     * If the animation is moving sets the animation state to be Move
     * else NULL. Also sets the direction
     */
    private void updateAnimation() {
       getPlayerDirectionCardinal();
       List<Float> vel = getVelocity();

       /*
        if(isAttacking) {
            setCurrentState(AnimationRole.ATTACK);
           // System.out.println(isAttacking);
        }
       */

        if(isDead()) {
            setCurrentState(AnimationRole.DEAD);
        } else if(isHurt) {
            setCurrentState(AnimationRole.HURT);
        } else {
            if (vel.get(2) == 0f) {
                setCurrentState(AnimationRole.NULL);
            } else {
                setCurrentState(AnimationRole.MOVE);
            }
        }
    }
}
