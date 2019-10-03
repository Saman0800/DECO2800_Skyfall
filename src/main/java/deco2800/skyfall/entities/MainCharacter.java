package deco2800.skyfall.entities;

import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import deco2800.skyfall.buildings.BuildingFactory;
import deco2800.skyfall.entities.spells.SpellCaster;
import deco2800.skyfall.entities.spells.SpellFactory;
import deco2800.skyfall.GameScreen;
import deco2800.skyfall.Tickable;
import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.spells.Spell;
import deco2800.skyfall.entities.spells.SpellType;
import deco2800.skyfall.entities.weapons.*;
import deco2800.skyfall.gamemenu.HealthCircle;
import deco2800.skyfall.gamemenu.popupmenu.GameOverTable;
import deco2800.skyfall.gui.ManaBar;
import deco2800.skyfall.managers.*;
import deco2800.skyfall.observers.KeyDownObserver;
import deco2800.skyfall.observers.KeyUpObserver;
import deco2800.skyfall.observers.TouchDownObserver;
import deco2800.skyfall.resources.*;
import deco2800.skyfall.resources.items.Hatchet;
import deco2800.skyfall.resources.items.PickAxe;
import deco2800.skyfall.saving.Save;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.Tile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main character in the game
 */
public class MainCharacter extends Peon implements KeyDownObserver,
        KeyUpObserver, TouchDownObserver, Tickable, Animatable {

    private static MainCharacter mainCharacterInstance = null;
    private boolean residualFromPopUp = false;

    /**
     * Removes the stored main character instance so that the next call to any of the {@code getInstance} methods will
     * create a new {@code MainCharacter}.
     */
    public static void resetInstance() {
        mainCharacterInstance = null;
    }

    public static MainCharacter getInstance(float col, float row, float speed, String name, int health, String[] textures) {
        if (mainCharacterInstance == null) {
            mainCharacterInstance = new MainCharacter(col, row, speed, name,
                    health, textures);
        }
        return mainCharacterInstance;
    }

    public static MainCharacter getInstance(float col, float row, float speed, String name, int health) {
        if (mainCharacterInstance == null) {
            mainCharacterInstance = new MainCharacter(col, row, speed, name,
                    health);
        }
        return mainCharacterInstance;
    }

    public static MainCharacter getInstance() {
        if (mainCharacterInstance == null) {
            mainCharacterInstance = new MainCharacter(0,0,0.05f, "Main " +
                    "Piece", 10);
        }
        return mainCharacterInstance;
    }

    // TODO:dannathan Fix or remove this.
    // public static MainCharacter loadMainCharacter(MainCharacterMemento memento, Save save) {
    //     if (mainCharacterInstance == null) {
    //         mainCharacterInstance = new MainCharacter(memento, save);
    //     } else {
    //         mainCharacterInstance.load(memento);
    //     }
    //     return mainCharacterInstance;
    // }

    // The id of the character for storing in a database
    private long id;

    /**
     * Sets the save of the main character
     *
     * @param save the save of the main character
     */
    public void setSave(Save save) {
        this.save = save;
    }

    // The save file of this character
    private Save save;

    // Logger to show messages
    private final Logger logger = LoggerFactory.getLogger(MainCharacter.class);

    // Manager for all of MainCharacter's inventories
    private InventoryManager inventories;

    //List of blueprints that the player has learned.

    private List<Blueprint> blueprintsLearned;
    private PetsManager petsManager;
    private BuildingFactory tempFactory;

    /**
     * Please feel free to change, this is not accurate as to the stages of
     * the game
     */
    public enum GameStage {
        FOREST,
        MOUNTAIN,
        ICE,
        LAVA,
        RIVER,
        VALLEY,
        GRAVEYARD
    }

    //The name of the item to be created.
    private String itemToCreate;

    // Variables to sound effects
    public static final String WALK_NORMAL = "people_walk_normal";

    public static final String HURT_SOUND_NAME = "player_hurt";
    public static final String DIED_SOUND_NAME = "player_died";

    public static final String BOWATTACK = "bow_and_arrow_attack";
    public static final String AXEATTACK = "axe_attack";
    public static final String SWORDATTACK = "sword_attack";
    public static final String SPEARATTACK = "first_attack";



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

    // The accumulated food tick to tick
    private float foodAccum;

    // Textures for all 6 directions to correspond to movement of character


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
    private float acceleration;
    private float maxSpeed;
    private double vel;
    private ArrayList<Integer> velHistoryX;
    private ArrayList<Integer> velHistoryY;
    private boolean isMoving;
    private boolean canSwim;
    private boolean isSprinting;

    //Is the camera locked onto the main character
    private boolean cameraLock = true;

    /*
        What stage of the game is the player on? Controls what blueprints
        the player can buy and make.
     */
    private GameStage gameStage;

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
     * How long does MainCharacter take to dead before
     * game over screen shows,
     */
    private long deadTime = 500;

    /**
     * Check whether MainCharacter is hurt.
     */
    private boolean isHurt = false;

    /**
     * Check id player is recovering
     */
    private boolean isRecovering = false;
    private boolean isTexChanging = false;



    /**
     * Item player is currently equipped with/holding.
     */
    private Item equippedItem;

    /**
     * The spell the user currently has selected to cast.
     */
    protected SpellType spellSelected = SpellType.NONE;

    /**
     * Used to cast spells.
     */
    protected SpellCaster spellCaster = null;

    /**
     * How much mana the character has available for spellcasting.
     */
    private int mana = 100;

    //Current time in interval to restore mana.
    private int manaCD = 0;

    //Tick interval to restore mana.
    private int totalManaCooldown = 10;

    /**
     * The GUI mana bar that can be updated when mana is restored/lost.
     */
    private ManaBar manaBar;


    /**
     * The GUI health bar for the character.
     */
    private HealthCircle healthBar;



    /**
     *  Game Over screen.
     */
    private GameOverTable gameOverTable;

    // TODO:dannathan Fix or remove this.
    // /**
    //  * Loads a main character from a memento
    //  *
    //  * @param memento the memento to load the character from
    //  */
    // private MainCharacter(MainCharacterMemento memento, Save save) {
    //     this.load(memento);
    //     this.save = save;
    // }

    /**
     * Base Main Character constructor
     */
    public MainCharacter(float col, float row, float speed, String name, int health) {
        super(row, col, speed, name, health, "MainCharacter");
        this.id = System.nanoTime();
        gameStage = GameStage.FOREST;
        this.setTexture("__ANIMATION_MainCharacterE_Anim:0");
        this.setHeight(1);
        this.setObjectName("MainPiece");
        this.setMaxHealth(health);

        GameManager.getManagerFromInstance(InputManager.class)
                .addKeyDownListener(this);
        GameManager.getManagerFromInstance(InputManager.class)
                .addKeyUpListener(this);
        GameManager.getManagerFromInstance(InputManager.class)
                .addTouchDownListener(this);

        this.petsManager = GameManager.getManagerFromInstance(PetsManager.class);

        this.inventories = GameManager.getManagerFromInstance(InventoryManager.class);

        this.level = 1;
        this.foodLevel = 100;
        foodAccum = 0.f;

        // create a new goldPouch object
        this.goldPouch = new HashMap<>();
        // create the starting gold pouch with 1 x 100G
        GoldPiece initialPiece = new GoldPiece(100);
        this.addGold(initialPiece, 1);

        // Initialises the players velocity properties
        xInput = 0;
        yInput = 0;
        setAcceleration(10.f);
        // FIXME:Ontonator Change this back.
        // setMaxSpeed(1.f);
        setMaxSpeed(5.f);
        vel = 0;
        velHistoryX = new ArrayList<>();
        velHistoryY = new ArrayList<>();

        blueprintsLearned = new ArrayList<>();
        tempFactory = new BuildingFactory();

        this.equippedItem = new EmptyItem();
        isMoving = false;

        // Sets the filters so that MainCharacter doesn't collide with projectile.
        for (Fixture fix : getBody().getFixtureList()) {
            Filter filter = fix.getFilterData();
            filter.categoryBits = (short) 0x2; // Set filter category to 2
            filter.maskBits = (short) (0xFFFF
                    ^ 0x4); // remove mask category 4 (projectiles)
            fix.setFilterData(filter);
        }

        isSprinting = false;

        canSwim = false;
        this.scale = 0.4f;
        setDirectionTextures();
        configureAnimations();

        spellCaster = new SpellCaster(this);
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
    private MainCharacter(float col, float row, float speed, String name, int health, String[] textures) {
        this(row, col, speed, name, health);
        this.setTexture(textures[2]);
    }

    /**
     * Setup the character specific gui elements.
     */
    public void setUpGUI() {
        this.setupHealthBar();
        this.setUpManaBar();
        this.setupGameOverScreen();
    }

    /**
     * Set up the mana bar.
     */
    private void setUpManaBar() {
        //Start with 100 mana.
        this.manaBar = new ManaBar(100, "mana_bar_inner", "mana_bar");
    }

    /**
     * Set up the health bar.
     */
    private void setupHealthBar() {
        this.healthBar = (HealthCircle) GameManager.getManagerFromInstance(GameMenuManager.class).
                getUIElement("healthCircle");
    }

    /**
     * Set up the game over screen.
     */
    private void setupGameOverScreen() {
        this.gameOverTable = (GameOverTable) GameManager.get().getManagerFromInstance(GameMenuManager.class).
                getPopUp("gameOverTable");
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
            logger.info("Switched to item: " + keyNumber);
        }
    }

    // FIXME:Ontonator Should this return primitive `boolean`?
    /**
     * Sets the player's equipped item
     *
     * @param item the item to equip
     */
    public Boolean setEquippedItem(Item item) {
        if (item.isEquippable()) {
            this.equippedItem = item;
            return true;
        } else {
            logger.warn("You can't equip " + item.getName() + ".");
            return false;
        }
    }

    /**
     * Sets the equipped item to be null when it runs out of durability
     */
    public void unEquip() {
        // Return item to a tile in the world
        if (equippedItem instanceof Weapon) {
            GameManager.get().getWorld().addEntity((StaticEntity)equippedItem);
        }

        this.equippedItem = new EmptyItem();

    }

    /**
     * Returns the players equipped item
     *
     * @return Item object that player is equipped with
     */
    public Item getEquippedItem() {
        return equippedItem;
    }

    /**
     * Gets pestManager
     *
     * @return
     */
    public PetsManager getPetsManager() {
        return this.petsManager;
    }

    /**
     * Returns string of players equipped item, or "No item equipped" if equippedItem == null
     *
     * @return String of equipped item
     */
    public String displayEquippedItem() {
        if (equippedItem != null) {
            return equippedItem.toString();
        } else {
            return "No item equipped";
        }
    }

    /**
     * Use the function of equipped item
     */
    public void useEquipped() {
        if ((equippedItem instanceof Weapon && !((Weapon) equippedItem).isUsable())
            || (equippedItem instanceof ManufacturedResources
                && !((ManufacturedResources) equippedItem).isUsable())) {
                this.unEquip();
                return;
        }

        equippedItem.use(this.getPosition());
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
        //Animation control
        logger.debug("Attacking");


        setCurrentState(AnimationRole.ATTACK);

        //If there is a spell selected, spawn the spell.
        //else, just fire off a normal projectile.
        if (this.spellSelected != SpellType.NONE) {
            this.castSpell(mousePosition, spellSelected);
        } else {
            this.fireProjectile(mousePosition);
        }
    }

    /**
     * Fire a projectile in the position that the mouse is in.
     *
     * @param mousePosition The position of the user's mouse.
     */
    protected void fireProjectile(HexVector mousePosition) {
        HexVector unitDirection = mousePosition.subtract(this.getPosition()).normalized();

        setCurrentState(AnimationRole.ATTACK);

        // Make projectile move toward the angle
        // Spawn projectile in front of character
        Projectile projectile = new Projectile(mousePosition,
                ((Weapon)equippedItem).getTexture("attack"),
                "hitbox",
                position.getCol() + 0.5f + 1.5f * unitDirection.getCol(),
                position.getRow() + 0.5f + 1.5f * unitDirection.getRow(),
                ((Weapon)equippedItem).getDamage(),
                ((Weapon)equippedItem).getAttackRate(),
                this.itemSlotSelected == 1 ? 1 : 0);

        // Add the projectile entity to the game world.
        GameManager.get().getWorld().addEntity(projectile);

        // Play weapon attack sound
        switch(((Weapon)equippedItem).getName()) {
            case "sword":
                SoundManager.playSound(SWORDATTACK);
                break;
            case "spear":
                SoundManager.playSound(SPEARATTACK);
                break;
            case "bow":
                SoundManager.playSound(BOWATTACK);
                break;
            case "axe":
                SoundManager.playSound(AXEATTACK);
                break;
            default:
                SoundManager.playSound(HURT_SOUND_NAME);
                break;
        }

    }

    /**
     * Cast the spell in the position that the mouse is in.
     *
     * @param mousePosition The position of the user's mouse.
     */
    private void castSpell(HexVector mousePosition, SpellType spellType) {

        // Unselect the spell.
        this.spellSelected = SpellType.NONE;

        //Create the spell using the factory.
        Spell spell = SpellFactory.createSpell(spellType, mousePosition);

        logger.info("Spell Case: " + spellType.toString());

        int manaCost = spell.getManaCost();

        //Check if there is enough mana to attack.
        if (mana < manaCost) {
            return;
        }

        //Subtract some mana, and update the GUI.
        this.mana -= manaCost;
        if (this.manaBar != null) {
            this.manaBar.update(this.mana);
        }

        GameManager.get().getWorld().addEntity(spell);


    }

    /**
     * Set the mana the character has available.
     *
     * @param mana The mana to set for the character.
     */
    public void setMana(int mana) {
        this.mana = mana;
    }

    /**
     * Get the mana the character currently has available.
     *
     * @return The mana the character has available.
     */
    public int getMana() {
        return this.mana;
    }



    /**
     * Lets the player enter a vehicle, by changing there speed and there sprite
     *
     * @param vehicle The vehicle they are entering
     */
    public void enterVehicle(String vehicle) {
        // Determine the vehicle they are entering and set their new speed and
        // texture
        if (vehicle.equals("Camel")) {
            //this.setTexture();
            setAcceleration(0.1f);
            setMaxSpeed(0.8f);
        } else if (vehicle.equals("Dragon")) {
            //this.setTexture();
            setAcceleration(0.125f);
            setMaxSpeed(1f);
        } else if (vehicle.equals("Boat")) {
            //this.setTexture();
            setAcceleration(0.01f);
            setMaxSpeed(0.5f);
            changeSwimming(true);
        } else {
            //this.setTexture();
            setAcceleration(0.03f);
            setMaxSpeed(0.6f);
        }
    }

    /**
     * Lets the player exit the vehicle by setting their speed back to
     * default and changing the texture. Also changing swimming to false in
     * case they were in a boat
     */
    public void exitVehicle() {
        //this.setTexture();
        setAcceleration(0.01f);
        setMaxSpeed(0.4f);
        changeSwimming(false);
    }



    public void pickUpInventory(Item item) {
        this.inventories.add(item);
    }

    /**
     * Attempts to drop given item from inventory
     *
     * @param item item to be dropped from inventory
     */
    public void dropInventory(String item) {
        this.inventories.drop(item);
    }

    /**
     * Player takes damage from other entities/ by starving.
     */
    public void hurt(int damage) {


        if (this.isRecovering) return;

        setHurt(true);
        logger.info("Hurted: " + isHurt);
        changeHealth(-damage);
        updateHealth();

        getBody().setLinearVelocity(getBody().getLinearVelocity()
                        .lerp(new Vector2(0.f, 0.f), 0.5f));

        if (this.getHealth() <= 0) {
            kill();
        } else {
            hurtTime = 0;
            recoverTime = 0;

            /*
            HexVector bounceBack = new HexVector(position.getCol(), position.getRow() - 2);

            switch (getPlayerDirectionCardinal()) {
                case "North":
                    bounceBack = new HexVector(position.getCol(), position.getRow() - 2);
                    break;
                case "North-East":
                    bounceBack = new HexVector(position.getCol() - 2, position.getRow() - 2);
                    break;
                case "East":
                    bounceBack = new HexVector(position.getCol() - 2, position.getRow());
                    break;
                case "South-East":
                    bounceBack = new HexVector(position.getCol() - 2, position.getRow() + 2);
                    break;
                case "South":
                    bounceBack = new HexVector(position.getCol(), position.getRow() + 2);
                    break;
                case "South-West":
                    bounceBack = new HexVector(position.getCol() + 2, position.getRow() + 2);
                    break;
                case "West":
                    bounceBack = new HexVector(position.getCol() - 2, position.getRow());
                    break;
                case "North-West":
                    bounceBack = new HexVector(position.getCol() + 2, position.getRow() - 2);
                    break;
                default:
                    break;
            }
            position.moveToward(bounceBack, 1f);
            */

            SoundManager.playSound(HURT_SOUND_NAME);

            if (hurtTime == 400) {
                setRecovering(true);
            }
        }
    }

    private void checkIfHurtEnded() {
        hurtTime += 20; // hurt for 1 second

        if (hurtTime > 400) {
            logger.info("Hurt ended");
            setHurt(false);
            setRecovering(true);
            hurtTime = 0;
        }
    }

    /**
     * Helper function to update healthBar outside of class.
     */
    public void updateHealth() {
        if (this.healthBar != null) {
            this.healthBar.update();
        }
    }

    /**
     * Player recovers from being attacked. It removes player 's
     * hurt effect (e.g. sprite flashing in red), in hurt().
     */
    public boolean isRecovering() {
        return isRecovering;
    }

    public void setRecovering(boolean isRecovering) {
        this.isRecovering = isRecovering;
    }

    public boolean isTexChanging() {
        return isTexChanging;
    }

    public void setTexChanging(boolean isTexChanging) {
        this.isTexChanging = isTexChanging;
    }

    private void checkIfRecovered() {
        recoverTime += 20;

        this.changeCollideability(false);

        if (recoverTime > 1000) {
            logger.info("Recovered");
            setRecovering(false);
            changeCollideability(true);
            recoverTime = 0;
        }
    }

    /**
     * Kills the player. and notifying the game that the player
     * has died and cannot do any actions in game anymore.
     */
    public void kill() {
        // set health to 0.
        changeHealth(0);
        SoundManager.playSound(DIED_SOUND_NAME);
        setCurrentState(AnimationRole.DEAD);
        deadTime = 0;
        // gameOverTable.show();
    }

    /**
     * @return if player is in the state of "hurt".
     */
    public boolean IsHurt() {
        return isHurt;
    }

    /**
     * @param isHurt the player's "hurt" status
     */
    public void setHurt(boolean isHurt) {
        this.isHurt = isHurt;
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
     * Method for the MainCharacter to eat food and restore/decrease hunger
     * level
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
                logger.info("Given item (" + item.getName() + ") is " + "not edible!");
            }
        } else {
            logger.info("You don't have enough of the given item");
        }
    }

    /**
     * See if the player is starving
     *
     * @return true if hunger points is <= 0, else false
     */
    public boolean isStarving() {
        return foodLevel <= 0;
    }

    public void changeSwimming(boolean swimmingAbility) {
        this.canSwim = swimmingAbility;
    }

    /**
     * Change current level of character and increases health by 10
     *
     * @param change amount being added or subtracted
     */
    public void changeLevel(int change) {
        if (level + change >= 1) {
            this.level += change;
            this.setMaxHealth(getHealth() * 10);
            this.changeHealth(change * 10);
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
     * Handles mouse click events
     *
     * @param screenX the x position the mouse was pressed at
     * @param screenY the y position the mouse was pressed at
     * @param pointer mouse pointer
     * @param button  the button which was pressed
     */
    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
        // only allow left clicks to move player

        logger.info(String.valueOf(button));
        if (GameScreen.isPaused) {
            return;
        }

        //Check if player wants to place a building
        if (button == 0) {

            float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
            float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);

            //Check we have permission to build

            if(GameManager.getManagerFromInstance(ConstructionManager.class).getStatus() == 1) {
            //    System.out.println(clickedPosition[0]);
            //    System.out.println(clickedPosition[1]);
                //cheking inventories
            //    if (GameManager.getManagerFromInstance(ConstructionManager.class).invCheck(inventories)){
            //        GameManager.getManagerFromInstance(ConstructionManager.class).build(GameManager.get().getWorld(),clickedPosition[0], clickedPosition[1]);
            //    }

                // REMOVE THE INVENTORIES
                    //    buildingToBePlaced.placeBuilding(x, y, buildingToBePlaced.getHeight(), world);
                    //    invRemove(buildingToBePlaced,GameManager.getManagerFromInstance(InventoryManager.class));

                GameManager.getManagerFromInstance(ConstructionManager.class).build(GameManager.get().getWorld(),
                        (int) clickedPosition[0], (int) clickedPosition[1]);
            }
        }

    }

    /**
     * Reset the mana cooldown period and restore 1 mana to the MainCharacter.
     */
    private void restoreMana() {

        //Reset the cooldown period.
        this.manaCD = 0;

        //Time interval has passed so restore some mana.
        if (this.mana < 100) {
            this.mana++;
        }
    }

    /**
     * Handles tick based stuff, e.g. movement
     */
    @Override
    public void onTick(long i) {
        if(!GameScreen.isPaused) {
            if (residualFromPopUp) {
                residualInputsFromPopUp();
            }
            this.updatePosition();
        } else {
            SoundManager.stopSound("people_walk_normal");
            getBody().setLinearVelocity(0f, 0f);
            residualFromPopUp = true;
        }
        this.movementSound();
        this.centreCameraAuto();

        this.manaCD++;
        if (this.manaCD > totalManaCooldown) {
            this.restoreMana();
        }

        //this.setCurrentSpeed(this.direction.len());
        //this.moveTowards(new HexVector(this.direction.x, this.direction.y));
        //        System.out.printf("(%s : %s) diff: (%s, %s)%n", this.direction,
        //         this.getPosition(), this.direction.x - this.getCol(),
        //         this.direction.y - this.getRow());
        //        System.out.printf("%s%n", this.currentSpeed);

        if (isHurt) {
            checkIfHurtEnded();
        } else if (isRecovering) {
            checkIfRecovered();
        }
        this.updateAnimation();

        if (isDead()) {
            if (deadTime < 500) {
                deadTime += 20;
            } else {
                GameManager.setPaused(true);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            GameManager.getManagerFromInstance(ConstructionManager.class).displayWindow();
        }
        // Do hunger stuff here
        if (isMoving) {
            if (isSprinting) {
                foodAccum += 0.1f;
            } else {
                foodAccum += 0.01f;
            }
        } else {
            foodAccum += 0.001f;
        }

        while (foodAccum >= 1.f) {
            change_food(-1);
            foodAccum -= 1.f;
        }
    }

    @Override
    public void handleCollision(Object other) {
        //Put specific collision logic here
    }

    public void resetVelocity() {

        xInput = 0;
        yInput = 0;
    }
    /**
     * Sets the Player's current movement speed
     *
     * @param cSpeed the speed for the player to currently move at
     */
    private void setCurrentSpeed(float cSpeed) {
        this.currentSpeed = cSpeed;
    }

    boolean petout = false;

    /**
     * Sets the appropriate movement flags to true on keyDown
     *
     * @param keycode the key being pressed
     */
    @Override
    public void notifyKeyDown(int keycode) {
        //player cant move when paused
        if (GameManager.getPaused()) {
            xInput = 0;
            yInput = 0;
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
            case Input.Keys.V:
                petsManager.replacePet(this);
                break;

            case Input.Keys.SHIFT_LEFT:
                isSprinting = true;
                maxSpeed *= 2.f;
                break;
            case Input.Keys.SPACE:
                useEquipped();

                if (this.equippedItem instanceof Weapon) {
                    float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
                    float[] clickedPosition = WorldUtil.worldCoordinatesToSubColRow(mouse[0], mouse[1]);
                    HexVector mousePosition = new HexVector(clickedPosition[0], clickedPosition[1]);

                    this.attack(mousePosition);
                }
                break;
            case Input.Keys.ALT_LEFT:
                // Attack moved to SPACE
                break;
            case Input.Keys.G:
                addClosestGoldPiece();
                break;
            case Input.Keys.M:
                getGoldPouchTotalValue();
                break;
            case Input.Keys.Z:
                selectSpell(SpellType.FLAME_WALL);
                break;
            case Input.Keys.X:
                selectSpell(SpellType.SHIELD);
                break;
            case Input.Keys.C:
                selectSpell(SpellType.TORNADO);
                break;
            case Input.Keys.L:
                toggleCameraLock();
                break;
            case Input.Keys.K:
                centreCameraManual();
                break;
            default:
                switchItem(keycode);
                break;
        }
        //Let the SpellCaster know a key was pressed.
        spellCaster.onKeyPressed(keycode);
    }

    /**
     * Select the spell that the character is ready to cast.
     * When they next click attack, this spell will cast.
     *
     * @param type The SpellType to cast.
     */
    public void selectSpell(SpellType type) {
        this.spellSelected = type;
    }

    public void residualInputsFromPopUp() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            yInput += 1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            xInput += -1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            yInput += -1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            xInput += 1;
        }

        residualFromPopUp = false;

    }


    /**
     * Sets the appropriate movement flags to false on keyUp
     *
     * @param keycode the key being released
     */
    @Override
    public void notifyKeyUp(int keycode) {
        // Player cant move when paused
        if (GameManager.getPaused()) {
            xInput = 0;
            yInput = 0;
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
            case Input.Keys.SHIFT_LEFT:
                isSprinting = false;
                maxSpeed /= 2.f;
                break;
            case Input.Keys.SPACE:
                break;
            case Input.Keys.G:
                break;
            case Input.Keys.M:
                break;
            default:
                break;
        }
    }

    /**
     * Adds a piece of gold to the Gold Pouch
     *
     * @Param gold The piece of gold to be added to the pouch
     * @Param count How many of that piece of gold should be added
     */
    public void addGold(GoldPiece gold, Integer count) {
        // store the gold's value (5G, 10G etc) as a variable
        Integer goldValue = gold.getValue();

        // if this gold value already exists in the pouch
        if (goldPouch.containsKey(goldValue)) {
            // add this piece to the already existing list of pieces
            goldPouch.put(goldValue, goldPouch.get(goldValue) + count);
        } else {
            goldPouch.put(goldValue, count);
        }
    }


    /**
     * Removes one instance of a gold piece in the pouch with a specific value.
     *
     * @param goldValue The value of the gold piece to be removed from the pouch.
     */
    public void removeGold(Integer goldValue) {
        // if this gold value does not exist in the pouch
        if (!(goldPouch.containsKey(goldValue))) {
            return;
        } else if (goldPouch.get(goldValue) > 1) {
            goldPouch.put(goldValue, goldPouch.get(goldValue) - 1);
        } else {
            goldPouch.remove(goldValue);
        }
    }

    /**
     * Gets the tile at a position.
     *
     * @param xPos The x position
     * @param yPos The y position
     * @return The Tile at that position
     */
    public Tile getTile(float xPos, float yPos) {
        //Returns tile at left arm (our perspective) of the player
        float tileCol = (float) Math.round(xPos);
        float tileRow = (float) Math.round(yPos);
        if (tileCol % 2 != 0) {
            tileRow += 0.5f;
        }
        return GameManager.get().getWorld().getTile(tileCol, tileRow);
    }

    /**
     * Returns the types of GoldPieces in the pouch and how many of each type
     * exist
     *
     * @return The contents of the Main Character's gold pouch
     */
    public Map<Integer, Integer> getGoldPouch() {
        return new HashMap<>(goldPouch);
    }

    /**
     * Returns the sum of the gold piece values in the Gold Pouch
     *
     * @return The total value of the Gold Pouch
     */
    public Integer getGoldPouchTotalValue() {
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
     */
    public void addClosestGoldPiece() {
        for (AbstractEntity entity : GameManager.get().getWorld().getEntities()) {
            if (entity instanceof GoldPiece) {
                if (this.getPosition().distance(entity.getPosition()) <= 2) {
                    this.addGold((GoldPiece) entity, 1);
                    logger.info(this.inventories.toString());
                }
            }
        }
        logger.info("Sorry, you are not close enough to a gold piece!");

    }

    /**
     * Moves the player based on current key inputs
     * Called in onTick method
     */
    private void updatePosition() {
        // Gets the players current position
        float xPos = position.getCol();
        float yPos = position.getRow();

        // Gets the tile the player is standing on
        Tile currentTile = getTile(xPos, yPos);

        // Determined friction scaling factor to apply based on current tile
        float friction;
        if (currentTile != null && currentTile.getTexture() != null) {
            //Tile specific friction
            friction = Tile.getFriction(currentTile.getTextureName());
        } else {
            // Default friction
            friction = 1f;
        }

        // Applies friction to the body
        getBody().setLinearDamping(friction);

        // If the player can move to the next tile process the movement
        if (checkTileMovement()) {
            this.processMovement();
        }

        // Updates the players position based on where their body is located
        position.setCol(getBody().getPosition().x);
        position.setRow(getBody().getPosition().y);
    }

    /**
     * Checks if the player can move onto the tile they are trying to move onto
     *
     * @return boolean: true if can move, false if can't move
     */
    private boolean checkTileMovement() {
        // Gets the next tile
        Tile tile = getTile(position.getCol() + xInput, position.getRow() + yInput);

        if (tile == null) {
            return false;
        } else {
            return (!tile.getTextureName().contains("water")
                    && !tile.getTextureName().contains("lake")
                    && !tile.getTextureName().contains("ocean"))
                    || canSwim;
        }
    }

    /**
     * Process the movement of the player
     * Only called if the player can move onto the next tile
     */
    private void processMovement() {
        // Gets the players current position
        float xVel = getBody().getLinearVelocity().x;
        float yVel = getBody().getLinearVelocity().y;
        recordVelHistory(xVel, yVel);

        preventSliding(xVel, yVel);

        getBody().applyForceToCenter(new Vector2(xInput * getAcceleration(), yInput * getAcceleration()), true);

        getBody().setLinearVelocity(getBody().getLinearVelocity().limit(maxSpeed));

        updateVel();
    }

    /**
     * Updates the players velocity to prevent the player from sliding around the map
     *
     * @param xVel the player's velocity in the x direction
     * @param yVel the player's velocity in the y direction
     */
    private void preventSliding(float xVel, float yVel) {

        if ((!checkDirection(xInput, xVel) && !checkDirection(yInput, yVel))
                || (xInput == 0 && yInput == 0)) {
            getBody().setLinearVelocity(0, 0);
        } else {
            if (!checkDirection(xInput, xVel) || xInput == 0) {
                getBody().setLinearVelocity(0, yVel);
            }

            if (!checkDirection(yInput, yVel) || yInput == 0) {
                getBody().setLinearVelocity(xVel, 0);
            }
        }
    }

    /**
     * Checks if the player is travelling in the same direction as intended
     *
     * @param mainInput the input direction being checked
     * @param vel       the player's velocity in the direction being checked
     * @return true if the player is travelling in the right direction
     */
    private boolean checkDirection(int mainInput, float vel) {
        boolean direction = true;

        if (mainInput != 0 && vel / Math.abs(vel) != mainInput && vel != 0) {
            direction = false;
        }

        return direction;
    }

    /**
     * Records the player velocity history
     *
     * @param xVel The x velocity
     * @param yVel The y velocity
     */
    private void recordVelHistory(float xVel, float yVel) {
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

    private void updateVel() {
        vel = getBody().getLinearVelocity().len();
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
    private double getPlayerDirectionAngle() {
        double val;
        if (xInput != 0 || yInput != 0) {
            val = Math.atan2(yInput, xInput);
        } else if (velHistoryX != null && velHistoryY != null
                && velHistoryX.size() > 1 && velHistoryY.size() > 1) {
            val = Math.atan2(velHistoryY.get(0), velHistoryX.get(0));
        } else {
            val = 0;
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
    private String getPlayerDirectionCardinal() {
        double playerDirectionAngle = getPlayerDirectionAngle();

        int playerDirectionIndex = Math.floorMod((int) Math.floor(((playerDirectionAngle + 22.5) / 45)), 8);

        switch (playerDirectionIndex) {
        case 0:
                setCurrentDirection(Direction.NORTH);
                return "North";
        case 1:
                setCurrentDirection(Direction.NORTH_EAST);
                return "North-East";
        case 2:
                setCurrentDirection(Direction.EAST);
                return "East";
        case 3:
                setCurrentDirection(Direction.SOUTH_EAST);
                return "South-East";
        case 4:
                setCurrentDirection(Direction.SOUTH);
                return "South";
        case 5:
                setCurrentDirection(Direction.SOUTH_WEST);
                return "South-West";
        case 6:
                setCurrentDirection(Direction.WEST);
                return "West";
        case 7:
                setCurrentDirection(Direction.NORTH_WEST);
                return "North-West";
        default:
            return "Invalid";
        }
    }

    /**
     * Sets the players acceleration
     *
     * @param newAcceleration: the new acceleration for the player
     */
    private void setAcceleration(float newAcceleration) {
        this.acceleration = newAcceleration;
    }

    /**
     * Sets the players max speed
     *
     * @param newMaxSpeed: the new max speed of the player
     */
    private void setMaxSpeed(float newMaxSpeed) {
        this.maxSpeed = newMaxSpeed;
    }

    /**
     * Gets a list of the players current velocity
     * 0: x velocity
     * 1: y velocity
     * 2: net velocity
     *
     * @return list of players velocity properties
     */
    public List<Float> getVelocity() {
        ArrayList<Float> velocity = new ArrayList<>();
        velocity.add(getBody().getLinearVelocity().x);
        velocity.add(getBody().getLinearVelocity().y);
        velocity.add((float) vel);
        return velocity;
    }

    /**
     * Gets the players current acceleration
     *
     * @return the players acceleration
     */
    public float getAcceleration() {
        return this.acceleration;
    }

    private void movementSound() {
        if (!isMoving && vel != 0) {
            // Runs when the player starts moving
            isMoving = true;
            SoundManager.loopSound(WALK_NORMAL);
        }

        if (isMoving && vel == 0) {
            // Runs when the player stops moving
            isMoving = false;
            SoundManager.stopSound(WALK_NORMAL);
        }
    }

    public void addBlueprint(Blueprint blueprint) {
        if (blueprint != null) {
            this.blueprintsLearned.add(blueprint);
        }
    }

    public List<Blueprint> getUnlockedBlueprints() {
        List<Blueprint> unlocked = new ArrayList<>();
        switch (gameStage) {
            case GRAVEYARD:
                // e.g. unlocked.add(new Spaceship())
                // fall through
            case VALLEY:
                // e.g. unlocked.add(new Factory())
                // fall through
            case RIVER:
                // fall through
            case LAVA:
                // fall through
            case ICE:
                // fall through
            case MOUNTAIN:
                // fall through
            case FOREST:
                unlocked.add(new Hatchet());
                unlocked.add(new PickAxe());
        }
        return unlocked;

    }

    /***
     * A getter method for the blueprints that the player has learned.
     * @return the learned blueprints list
     */
    public List<Blueprint> getBlueprintsLearned() {
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
     * @param item the item to be created.
     */
    public void setItemToCreate(String item) {
        this.itemToCreate = item;
    }

    /***
     * Creates an item if the player has the blueprint. Checks if required resources
     * are in the inventory. if yes, creates the item, adds it to the player's
     * inventoryand deducts the required resource from inventory
     */
    public void createItem(Blueprint newItem) {

        for (Blueprint blueprint : getBlueprintsLearned()) {
            if (blueprint.getClass() == newItem.getClass()) {

                if (newItem.getRequiredMetal() > this.getInventoryManager().
                        getAmount("Metal")) {
                    logger.info("You don't have enough Metal");

                } else if (newItem.getRequiredWood() > this.getInventoryManager().
                        getAmount("Wood")) {
                    logger.info("You don't have enough Wood");

                } else if (newItem.getRequiredStone() > this.getInventoryManager().
                        getAmount("Stone")) {
                    logger.info("You don't have enough Stone");

                } else {
                    switch (newItem.getName()) {
                        case "Hatchet":
                            this.getInventoryManager().add(new Hatchet());
                            break;

                        case "Pick Axe":
                            this.getInventoryManager().add(new PickAxe());
                            break;

                        //These are only placeholders and will change once coordinated
                        //with Building team
                        case "Cabin":
                            tempFactory.createCabin(this.getCol(), this.getRow());
                            break;

                        case "StorageUnit":
                            tempFactory.createStorageUnit(this.getCol(), this.getRow());
                            break;

                        case "TownCentre":
                            tempFactory.createTownCentreBuilding(this.getCol(), this.getRow());
                            break;

                        case "Fence":
                            tempFactory.createFenceBuilding(this.getCol(), this.getRow());
                            break;

                        case "SafeHouse":
                            tempFactory.createSafeHouse(this.getCol(), this.getRow());
                            break;

                        case "WatchTower":
                            tempFactory.createWatchTower(this.getCol(), this.getRow());
                            break;

                        case "Castle":
                            tempFactory.createCastle(this.getCol(), this.getRow());
                            break;
                        default:
                            logger.info("Invalid Item");
                            break;
                    }

                    this.getInventoryManager().dropMultiple
                            ("Metal", newItem.getRequiredMetal());
                    this.getInventoryManager().dropMultiple
                            ("Stone", newItem.getRequiredStone());
                    this.getInventoryManager().dropMultiple
                            ("Wood", newItem.getRequiredWood());
                }
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
                        AnimationRole.MOVE, Direction.NORTH_WEST, true, true));

        addAnimations(AnimationRole.MOVE, Direction.NORTH_EAST,
                new AnimationLinker("MainCharacterNE_Anim",
                        AnimationRole.MOVE, Direction.NORTH_WEST, true, true));

        addAnimations(AnimationRole.MOVE, Direction.SOUTH_WEST,
                new AnimationLinker("MainCharacterSW_Anim",
                        AnimationRole.MOVE, Direction.SOUTH_WEST, true, true));

        addAnimations(AnimationRole.MOVE, Direction.SOUTH_EAST,
                new AnimationLinker("MainCharacterSE_Anim",
                        AnimationRole.MOVE, Direction.SOUTH_EAST, true, true));

        addAnimations(AnimationRole.MOVE, Direction.EAST,
                new AnimationLinker("MainCharacterE_Anim",
                        AnimationRole.MOVE, Direction.EAST, true, true));

        addAnimations(AnimationRole.MOVE, Direction.NORTH,
                new AnimationLinker("MainCharacterN_Anim",
                        AnimationRole.MOVE, Direction.NORTH, true, true));

        addAnimations(AnimationRole.MOVE, Direction.WEST,
                new AnimationLinker("MainCharacterW_Anim",
                        AnimationRole.MOVE, Direction.WEST, true, true));

        addAnimations(AnimationRole.MOVE, Direction.SOUTH,
                new AnimationLinker("MainCharacterS_Anim",
                        AnimationRole.MOVE, Direction.SOUTH, true, true));

        // Attack animation
        addAnimations(AnimationRole.ATTACK, Direction.DEFAULT,
                new AnimationLinker("MainCharacter_Attack_E_Anim",
                        AnimationRole.ATTACK, Direction.DEFAULT, false, true));

        // Hurt animation
        addAnimations(AnimationRole.HURT, Direction.DEFAULT,
                new AnimationLinker("MainCharacter_Hurt_E_Anim",
                        AnimationRole.HURT, Direction.DEFAULT, true, true));

        // Dead animation
        addAnimations(AnimationRole.DEAD, Direction.DEFAULT,
                new AnimationLinker("MainCharacter_Dead_E_Anim",
                        AnimationRole.DEAD, Direction.DEFAULT, false, true));

        // Dead animation
        addAnimations(AnimationRole.STILL, Direction.DEFAULT,
                new AnimationLinker("MainCharacter_Dead_E_Still",
                        AnimationRole.STILL, Direction.DEFAULT, false, true));
    }

    /**
     * Sets default direction textures uses the get index for Animation feature
     * as described in the animation documentation section 4.
     */
    @Override
    public void setDirectionTextures() {
        defaultDirectionTextures.put(Direction.EAST, "__ANIMATION_MainCharacterE_Anim:0");
        defaultDirectionTextures.put(Direction.NORTH, "__ANIMATION_MainCharacterN_Anim:0");
        defaultDirectionTextures.put(Direction.WEST, "__ANIMATION_MainCharacterW_Anim:0");
        defaultDirectionTextures.put(Direction.SOUTH, "__ANIMATION_MainCharacterS_Anim:0");
        defaultDirectionTextures.put(Direction.NORTH_EAST, "__ANIMATION_MainCharacterNE_Anim:0");
        defaultDirectionTextures.put(Direction.NORTH_WEST, "__ANIMATION_MainCharacterNW_Anim:0");
        defaultDirectionTextures.put(Direction.SOUTH_EAST, "__ANIMATION_MainCharacterSE_Anim:0");
        defaultDirectionTextures.put(Direction.SOUTH_WEST, "__ANIMATION_MainCharacterSW_Anim:0");
    }

    /**
     * If the animation is moving sets the animation state to be Move
     * else NULL. Also sets the direction
     */
    private void updateAnimation() {
        getPlayerDirectionCardinal();



        /* Short Animations */
        if (getToBeRun() != null) {
            if (getToBeRun().getType() == AnimationRole.DEAD) {
                setCurrentState(AnimationRole.STILL);
            } else if (getToBeRun().getType() == AnimationRole.ATTACK) {
                return;
            }
        }

            if (isDead()) {
                setCurrentState(AnimationRole.STILL);
            } else if (isHurt) {
                setCurrentState(AnimationRole.HURT);
            } else {
            if (getVelocity().get(2) == 0f) {
                    setCurrentState(AnimationRole.NULL);
                } else {
                    setCurrentState(AnimationRole.MOVE);
                }
            }
    }


    /**
     * Toggles if the camera should follow the player
     */
    private void toggleCameraLock() {
        if (!cameraLock) {
            cameraLock = true;
            centreCameraManual();
        } else {
            cameraLock = false;
        }
    }

    /**
     * Centres the camera onto the player
     * Designed to called on a loop
     */
    private void centreCameraAuto() {
        if (cameraLock) {
            float[] coords = WorldUtil.colRowToWorldCords(this.getCol(), this.getRow());
            GameManager.get().getCamera().position.set(coords[0], coords[1], 0);

        }
    }

    /**
     * Centres the camera onto the player
     * Not supposed to be called on a loop
     */
    private void centreCameraManual() {
        float[] coords = WorldUtil
                .colRowToWorldCords(this.getCol(), this.getRow());
        GameManager.get().getCamera().position.set(coords[0], coords[1], 0);
    }

     /** Returns the id of this character
     *
     * @return the id of this character
     */
    public long getID() {
        return this.id;
    }

    /**
     * Returns the save this character is for
     *
     * @return the save this character is for
     */
    public Save getSave() {
        return save;
    }

    // FIXME:dannothan Fix or remove this.
//    @Override
//    public MainCharacterMemento save() {
//        return new MainCharacterMemento(this);
//    }
//
//    @Override
//    public void load(MainCharacterMemento memento) {
//        this.id = memento.mainCharacterID;
//        this.equippedItem = memento.equippedItem;
//        this.level = memento.level;
//        this.foodLevel = memento.foodLevel;
//        this.foodAccum = memento.foodAccum;
//        this.goldPouch = memento.goldPouch;
//        this.blueprintsLearned = memento.blueprints;
//        this.inventories = memento.inventory;
//        this.weapons = memento.weapons;
//        this.hotbar = memento.hotbar;
//    }
//
//    public class MainCharacterMemento extends AbstractMemento {
//
//        //TODO:dannathan add stuff for entitiy
//        private long saveID;
//        private long mainCharacterID;
//
//        private int equippedItem;
//        private int level;
//
//        private int foodLevel;
//        private float foodAccum;
//
//        private InventoryManager inventory;
//        private WeaponManager weapons;
//        private HashMap<Integer, Integer> goldPouch;
//        private List<Item> hotbar;
//
//        private List<String> blueprints;
//
//        public MainCharacterMemento(MainCharacter character) {
//            this.saveID = character.save.getSaveID();
//            this.mainCharacterID = character.id;
//            this.equippedItem = character.equippedItem;
//            this.level = character.level;
//            this.foodLevel = character.foodLevel;
//            this.foodAccum = character.foodAccum;
//            this.goldPouch = character.goldPouch;
//            this.blueprints = character.blueprintsLearned;
//            this.inventory = character.inventories;
//            this.weapons = character.weapons;
//            this.hotbar = character.hotbar;
//        }
//    }
}
