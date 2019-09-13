package deco2800.skyfall.entities;

import com.badlogic.gdx.audio.Sound;
import deco2800.skyfall.buildings.BuildingFactory;
import deco2800.skyfall.entities.spells.SpellFactory;
import deco2800.skyfall.entities.structures.BuildingType;
import deco2800.skyfall.entities.spells.SpellFactory;
import deco2800.skyfall.entities.weapons.Sword;
import deco2800.skyfall.entities.weapons.Weapon;
import deco2800.skyfall.entities.worlditems.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.Vector2;
import deco2800.skyfall.GameScreen;
import deco2800.skyfall.Tickable;
import deco2800.skyfall.animation.*;
import deco2800.skyfall.entities.spells.Spell;
import deco2800.skyfall.entities.spells.SpellType;
import deco2800.skyfall.gui.HealthCircle;
import deco2800.skyfall.gui.ManaBar;
import deco2800.skyfall.managers.*;
import deco2800.skyfall.observers.*;
import deco2800.skyfall.resources.*;
import deco2800.skyfall.resources.Item;
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
public class MainCharacter extends Peon
        implements KeyDownObserver, KeyUpObserver, TouchDownObserver, Tickable, Animatable {

    private final Logger logger = LoggerFactory.getLogger(MainCharacter.class);

    // Manager for all of MainCharacter's inventories
    private InventoryManager inventories;

    //List of blueprints that the player has learned.

    private List<Blueprint> blueprintsLearned;

    private BuildingFactory tempFactory;




    //The name of the item to be created.
    private String itemToCreate;

    // Variables to sound effects
    public static final String WALK_NORMAL = "people_walk_normal";
    public static final String HURT = "player_hurt";
    public static final String DIED = "player_died";

    private SoundManager soundManager = GameManager.get().getManager(SoundManager.class);

    public static final String BOWATTACK = "bow_and_arrow_attack";

    //The pick Axe that is going to be created
    private Hatchet hatchetToCreate;

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
    private float xVel;
    private float yVel;
    private float acceleration;
    private float maxSpeed;
    private double vel;
    private ArrayList<Integer> velHistoryX;
    private ArrayList<Integer> velHistoryY;
    private boolean isMoving;
    private boolean canSwim;
    private boolean isSprinting;

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
     * Check id player is recovering
     */
    private boolean isRecovering = false;
    private boolean isTexChanging = false;

    /**
     * Check id player is attacking
     */
    private boolean isAttacking = false;

    /**
     * The spell the user currently has selected to cast.
     */
    private SpellType spellSelected = SpellType.NONE;

    /**
     * How much mana the character has available for spellcasting.
     */
    private int mana = 100;

    /**
     * The GUI mana bar that can be updated when mana is restored/lost.
     */
    private ManaBar manaBar;


    /**
     * The GUI health bar for the character.
     */
    private HealthCircle healthBar;

    /**
     * Can this character take damage.
     */
    private boolean isInvincible;

    private String equipped;

    /**
     * Base Main Character constructor
     */
    public MainCharacter(float col, float row, float speed, String name, int health) {
        super(row, col, speed, name, health);
        this.setTexture("__ANIMATION_MainCharacterE_Anim:0");
        this.setHeight(1);
        this.setObjectName("MainPiece");

        GameManager.getManagerFromInstance(InputManager.class).addKeyDownListener(this);
        GameManager.getManagerFromInstance(InputManager.class).addKeyUpListener(this);
        GameManager.getManagerFromInstance(InputManager.class).addTouchDownListener(this);

        this.inventories = GameManager.getManagerFromInstance(InventoryManager.class);

        this.level = 1;
        this.foodLevel = 100;
        foodAccum = 0.f;

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
        blueprintsLearned = new ArrayList<>();
        tempFactory = new BuildingFactory();



        isMoving = false;

        HexVector position = this.getPosition();

        /*        //Spawn projectile in front of character for now.
        this.hitBox = new Projectile("slash",
                "test hitbox",
                position.getCol() + 1,
                position.getRow(),
                1, 1);*/

        equipped = "no_weapon";
        canSwim = true;
        isSprinting = false;
        this.scale = 0.4f;
        setDirectionTextures();
        configureAnimations();
    }

    /**
     * Setup the character specific gui elements.
     */
    public void setUpGUI() {
        this.setupHealthBar();
        this.setUpManaBar();
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
        this.healthBar = new HealthCircle(this.getHealth(), "big_circle", "inner_circle");
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
    public MainCharacter(float col, float row, float speed,
                         String name, int health, String[] textures) {
        this(row, col, speed, name, health);
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
            logger.info("Switched to item: " + keyNumber);
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
        //Animation control
        setAttacking(true);
        setCurrentState(AnimationRole.ATTACK);

        //If there is a spell selected, spawn the spell.
        //else, just fire off a normal projectile.
        if (this.spellSelected != SpellType.NONE) {
            this.castSpell(mousePosition,spellSelected);
        } else {
            this.fireProjectile(mousePosition);
        }
    }

    /**
     * Fire a projectile in the position that the mouse is in.
     *
     * @param mousePosition The position of the user's mouse.
     */
    private void fireProjectile(HexVector mousePosition) {
        //TODO: Call weapon.Attack(); and move this logic to the weapon.
        HexVector position = this.getPosition();


        setCurrentState(AnimationRole.ATTACK);
        SoundManager.playSound(BOWATTACK);
        // Make projectile move toward the angle
        // Spawn projectile in front of character for now.
        Projectile projectile = new Projectile(mousePosition,
                this.itemSlotSelected == 1 ? "range_test" : "melee_test",
                "test hitbox",
                position.getCol() + 1,
                position.getRow(),
                1,
                0.1f,
                this.itemSlotSelected == 1 ? 1 : 0);
        // Get AbstractWorld from static class GameManager.
        GameManager manager = GameManager.get();

        // Add the projectile entity to the game world.
        GameManager.get().getWorld().addEntity(projectile);
    }

    /**
     * Cast the spell in the position that the mouse is in.
     *
     * @param mousePosition The position of the user's mouse.
     */
    private void castSpell(HexVector mousePosition, SpellType spellType) {

        //Unselect the spell.
        this.spellSelected = SpellType.NONE;
        
        //Create the spell using the factory.
        Spell spell = SpellFactory.createSpell(spellType,mousePosition);

        System.out.println(spellType.toString());

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

        setAttacking(false);
    }

    public String getEquipped() {
        return this.equipped;
    }

    public void setEquipped(String item) {
        this.equipped = item;
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

    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

    /**
     * Set if the character is invincible.
     * @param isInvincible Is the character invincible.
     */
    public void setInvincible(boolean isInvincible) {
        this.isInvincible = isInvincible;
    }

    /**
     * Player takes damage from other entities/ by starving.
     */
    public void hurt(int damage) {

        if (this.isInvincible) return;
        if (this.isRecovering) return;

        setHurt(true);
        this.changeHealth(-damage);

        if (this.healthBar != null) {
            this.healthBar.update(this.getHealth());
        }
        System.out.println("Hurted: " + isRecovering);

        if (!isRecovering) {
            setHurt(true);
            this.changeHealth(-damage);

            if (this.healthBar != null) {
                this.healthBar.update(this.getHealth());
            }

            if (this.getHealth() <= 0) {
                kill();
            } else {
                hurtTime = 0;
                recoverTime = 0;
                HexVector bounceBack = new HexVector();

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
                }
                position.moveToward(bounceBack, 1f);

                SoundManager.playSound(HURT);
            }
        }
    }

        private void checkIfHurtEnded () {
            hurtTime += 20; // hurt for 1 second

            if (hurtTime > 400) {
                System.out.println("Hurt ended");
                setHurt(false);
                setRecovering(true);
                hurtTime = 0;
            }
        }

        /**
         * Player recovers from being attacked. It removes player 's
         * hurt effect (e.g. sprite flashing in red), in hurt().
         */
        public boolean isRecovering () {
            return isRecovering;
        }

        public void setRecovering ( boolean isRecovering){
            this.isRecovering = isRecovering;
        }

        public boolean isTexChanging () {
            return isTexChanging;
        }

        public void setTexChanging ( boolean isTexChanging){
            this.isTexChanging = isTexChanging;
        }

        private void checkIfRecovered () {
            recoverTime += 20;
            System.out.println("Character recovering");
            recoverTime += 20;

            this.changeCollideability(false);

            if (recoverTime > 2000) {
                System.out.println("Recovered");
                setRecovering(false);
                changeCollideability(true);
            }
        }

        /**
         * Kills the player. and notifying the game that the player
         * has died and cannot do any actions in game anymore.
         */
        public void kill () {
            // stop player controls
            AnimationManager animationManager = GameManager.getManagerFromInstance(AnimationManager.class);


            // set health to 0.
            changeHealth(0);

            // AS.PlayOneShot(dieSound);
            GameManager.setPaused(true);
        }

        /**
         * @return if player is in the state of "hurt".
         */
        public boolean IsHurt () {
            return isHurt;
        }

        /**
         * @return if player is in the state of "hurt".
         */
        public void setHurt ( boolean isHurt){
            this.isHurt = isHurt;
        }

        /**
         * Set the players inventory to a predefined inventory
         * e.g for loading player saves
         * @param inventoryContents the save for the inventory
         */
        public void setInventory (Map < String, List < Item >> inventoryContents,
                List < String > quickAccessContent){
            this.inventories = new InventoryManager(inventoryContents,
                    quickAccessContent);
        }

        /**
         * Add weapon to weapons list
         * @param item weapon to be added
         */
        public void pickUpInventory (Item item){
            this.inventories.inventoryAdd(item);
        }

        /**
         * Attempts to drop given item from inventory
         * @param item item to be dropped from inventory
         */
        public void dropInventory (String item){
            this.inventories.inventoryDrop(item);
        }

        /**
         * Gets the inventory manager of the character, so it can only be modified
         * this way, prevents having it being a public variable
         * @return the inventory manager of character
         */
        public InventoryManager getInventoryManager () {
            return this.inventories;
        }

        /**
         * Change the hunger points value for the player
         * (+ve amount increases hunger points)
         * (-ve amount decreases hunger points)
         * @param amount the amount to change it by
         */
        public void change_food ( int amount){
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
        public int getFoodLevel () {
            return foodLevel;
        }

        /**
         * Method for the MainCharacter to eat food and restore/decrease hunger
         * level
         * @param item the item to eat
         */
        public void eatFood (Item item){
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
        public boolean isStarving () {
            return foodLevel <= 0;
        }

        public void changeSwimming ( boolean swimmingAbility){
            this.canSwim = swimmingAbility;
        }

        /**
         * Change current level of character and increases health by 10
         * @param change amount being added or subtracted
         */
        public void changeLevel ( int change){
            if (level + change >= 1) {
                this.level += change;
                this.changeHealth(change * 10);
            }
        }

        /**
         * Gets the current level of character
         * @return level of character
         */
        public int getLevel () {
            return this.level;
        }

        /**
         * Change the player's appearance to the set texture
         * @param texture the texture to set
         */
        public void changeTexture (String texture){
            this.setTexture(texture);
        }

        /**
         * Handles mouse click events
         * @param screenX the x position the mouse was pressed at
         * @param screenY the y position the mouse was pressed at
         * @param pointer mouse pointer
         * @param button the button which was pressed
         */
        public void notifyTouchDown ( int screenX, int screenY, int pointer, int button){
            // only allow left clicks to move player
            if (GameScreen.isPaused) {
                return;
            }
            if (button == 0) {
                float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
                float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);

                HexVector mousePos = new HexVector(clickedPosition[0], clickedPosition[1]);
                this.attack(mousePos);
            }
        }


        /**
         * Handles tick based stuff, e.g. movement
         */
        @Override
        public void onTick ( long i){
            this.updatePosition();
            this.movementSound();

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

        /**
         * Move character towards a destination
         */
        @Override
        public void moveTowards (HexVector destination){
            position.moveToward(destination, this.currentSpeed);
        }

        /**
         * Sets the Player's current movement speed
         * @param cSpeed the speed for the player to currently move at
         */
        private void setCurrentSpeed ( float cSpeed){
            this.currentSpeed = cSpeed;
        }

        /**
         * Sets the appropriate movement flags to true on keyDown
         * @param keycode the key being pressed
         */
        @Override
        public void notifyKeyDown ( int keycode){
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
                case Input.Keys.SHIFT_LEFT:
                    isSprinting = true;
                    maxSpeed *= 2.f;
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
                case Input.Keys.Z:
                    selectSpell(SpellType.FLAME_WALL);
                    break;
                case Input.Keys.X:
                    selectSpell(SpellType.SHIELD);
                    break;
                case Input.Keys.C:
                    selectSpell(SpellType.TORNADO);
                    break;
                default:
                    switchItem(keycode);
                    //xInput += 1;
                    break;
            }
        }

        /**
         * Select the spell that the character is ready to cast.
         * When they next click attack, this spell will cast.
         * @param type The SpellType to cast.
         */
        private void selectSpell (SpellType type){
            this.spellSelected = type;
        }

        /**
         * Sets the appropriate movement flags to false on keyUp
         * @param keycode the key being released
         */
        @Override
        public void notifyKeyUp ( int keycode){
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
                case Input.Keys.SHIFT_LEFT:
                    isSprinting = false;
                    maxSpeed /= 2.f;
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
        public void addGold (GoldPiece gold, Integer count){

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
         * Returns the types of GoldPieces in the pouch and how many of each type
         * exist
         * @return The contents of the Main Character's gold pouch
         */
        public HashMap<Integer, Integer> getGoldPouch () {
            return new HashMap<>(goldPouch);
        }

        /**
         * Returns the sum of the gold piece values in the Gold Pouch
         * @return The total value of the Gold Pouch
         */
        public Integer getGoldPouchTotalValue () {
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
        public void addClosestGoldPiece () {
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
         * Gets the tile at a position.
         * @param xPos The x position
         * @param yPos The y position
         * @return The Tile at that position
         */
        public Tile getTile ( float xPos, float yPos){
            //Returns tile at left arm (our perspective) of the player
            float tileCol = (float) Math.round(xPos);
            float tileRow = (float) Math.round(yPos);
            if (tileCol % 2 != 0) {
                tileRow += 0.5f;
            }
            return GameManager.get().getWorld().getTile(tileCol, tileRow);
        }

        /**
         * Records the player velocity history
         * @param xVel The x velocity
         * @param yVel The y velocity
         */
        public void recordVelHistory ( float xVel, float yVel){
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
         * Calculates the velocity of the player
         * @param mainInput Input being checked
         * @param altInput Input not being checked
         * @param vel Velocity to calculate
         * @param friction Friction value
         * @return The new velocity
         */
        public float calVelocity ( int mainInput, int altInput, float vel, float friction){
            if (mainInput != 0) {
                vel += mainInput * acceleration * friction;
                // Prevents sliding
                if (vel / Math.abs(vel) != mainInput) {
                    vel = 0;
                }
            } else if (altInput != 0) {
                vel *= 0.8;
            } else {
                vel = 0;
            }
            return vel;
        }

        /**
         * Finds the next position to move to and moves there
         * @param position The current position
         * @param destination The new position
         * @param nextTile The tile that will be moved to
         */
        public void findNewPosition (HexVector position, HexVector destination, Tile nextTile){
            if (nextTile == null) {
                // Prevents the player from walking into the void
                position.moveToward(destination, 0);
            } else if (nextTile.getTextureName().contains("water") && !canSwim) {
                // Prevents the player back if they try to enter water when they
                // can't swim
                position.moveToward(destination, 0);
            } else {
                position.moveToward(destination, vel);
            }
        }

        /**
         * Moves the player based on current key inputs
         */
        public void updatePosition () {
            // Gets current position
            float xPos = position.getCol();
            float yPos = position.getRow();

            // Gets the tile the player is standing on
            Tile currentTile = getTile(xPos, yPos);
            // Returns tile at left arm (our perspective) of the player
            float tileCol = (float) Math.round(xPos);
            float tileRow = (float) Math.round(yPos);
            if (tileCol % 2 != 0) {
                tileRow += 0.5f;
            }

            // Determined friction scaling factor to apply based on current tile
            float friction;
            if (currentTile != null && currentTile.getTexture() != null) {
                //Tile specific friction
                currentTile = GameManager.get().getWorld().getTile(tileCol, tileRow);
                friction = Tile.getFriction(currentTile.getTextureName());
            } else {
                // Default friction
                friction = 1f;
            }

            // Calculates new x and y positions
            xPos += xVel + xInput * acceleration * 0.5 * friction;
            yPos += yVel + yInput * acceleration * 0.5 * friction;

            // Calculates velocity in x and y directions
            xVel = calVelocity(xInput, yInput, xVel, friction);
            yVel = calVelocity(yInput, xInput, yVel, friction);

            // caps the velocity depending on the friction of the current tile
            float maxTileSpeed = maxSpeed * friction;
            if (vel > maxTileSpeed) {
                xVel /= vel;
                yVel /= vel;

                xVel *= maxTileSpeed;
                yVel *= maxTileSpeed;
            }

            // Calculates speed to destination
            vel = friction * Math.sqrt((xVel * xVel) + (yVel * yVel));

            // Calculates destination vector
            HexVector destination = new HexVector(xPos, yPos);

            // Next tile the player will move to
            Tile nextTile = getTile(xPos, yPos);

            // Method to take away the player's ability to swim
            changeSwimming(false);

            // Moves the player to new location
            findNewPosition(position, destination, nextTile);

            //Records velocity history in x direction
            recordVelHistory(xVel, yVel);

            getBody().setTransform(xPos, yPos, getBody().getAngle());
        }

        /**
         * Gets the direction the player is currently facing
         * North: 0 deg
         * East: 90 deg
         * South: 180 deg
         * West: 270 deg
         * @return the player direction (units: degrees)
         */
        public double getPlayerDirectionAngle () {
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
         * @return new texture to use
         */
        public String getPlayerDirectionCardinal () {
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
        public List<Float> getVelocity () {
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
        public void setAcceleration ( float newAcceleration){
            this.acceleration = newAcceleration;
        }

        /**
         * Sets the players max speed
         * @param newMaxSpeed: the new max speed of the player
         */
        public void setMaxSpeed ( float newMaxSpeed){
            this.maxSpeed = newMaxSpeed;
        }

        /**
         * Gets the players current acceleration
         * @return the players acceleration
         */
        public float getAcceleration () {
            return this.acceleration;
        }

        /**
         * Gets the plays current max speed
         * @return the players max speed
         */
        public float getMaxSpeed () {
            return this.maxSpeed;
        }

        public void movementSound () {
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
        public void useHatchet () {

            if (this.inventories.getQuickAccess().containsKey("Hatchet")) {
                Hatchet playerHatchet = new Hatchet(this);

                for (AbstractEntity entity : GameManager.get().getWorld().getEntities()) {

                    if (entity instanceof Tree) {


                        if (this.getPosition().distance(entity.getPosition()) <= 1) {
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
        public void usePickAxe () {

            if (this.inventories.getQuickAccess().containsKey("Pick Axe")) {
                PickAxe playerPickAxe = new PickAxe(this);

                for (AbstractEntity entity : GameManager.get().getWorld().getEntities()) {

                    if (entity instanceof Rock) {

                        if (this.getPosition().distance(entity.getPosition()) <= 1) {
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
    public List<Blueprint> getBlueprintsLearned () {
            blueprintsLearned.add(new Hatchet());

        return this.blueprintsLearned;
    }

        /***
         * A getter method to get the Item to be created.
         * @return the item to create.
         */
        public String getItemToCreate () {
            return this.itemToCreate;
        }

        /***
         * A Setter method to get the Item to be created.
         * @param item the item to be created.
         */
        public void setItemToCreate (String item){
            this.itemToCreate = item;
        }

        /***
         * Creates an item if the player has the blueprint. Checks if required resources
         * are in the inventory. if yes, creates the item, adds it to the player's
         * inventoryand deducts the required resource from inventory
         */
    public void createItem (Blueprint newItem) {

            for (Blueprint blueprint: getBlueprintsLearned()){
                if (blueprint.getClass()== newItem.getClass()) {

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
                        switch(newItem.getName()){
                            case "Hatchet":
                                this.getInventoryManager().inventoryAdd(new Hatchet());
                            case "Pick Axe":
                                this.getInventoryManager().inventoryAdd(new PickAxe());

                                //These are only placeholders and will change once coordinated
                                //with Building team
                            case "Cabin":
                                tempFactory.createCabin(this.getCol(),this.getRow());
                            case "StorageUnit":
                                tempFactory.createStorageUnit(this.getCol(),this.getRow());
                            case "TownCentre":
                                tempFactory.createTownCentreBuilding(this.getCol(),this.getRow());
                            case "Fence":
                                tempFactory.createFenceBuilding(this.getCol(),this.getRow());
                            case "SafeHouse":
                                tempFactory.createSafeHouse(this.getCol(),this.getRow());
                            case "WatchTower":
                                tempFactory.createWatchTower(this.getCol(),this.getRow());
                            case "Castle":
                                tempFactory.createCastle(this.getCol(),this.getRow());
                        }
                        this.getInventoryManager().inventoryDropMultiple
                                ("Metal", newItem.getRequiredMetal());
                        this.getInventoryManager().inventoryDropMultiple
                                ("Stone", newItem.getRequiredStone());
                        this.getInventoryManager().inventoryDropMultiple
                                ("Wood", newItem.getRequiredWood());
                    }
                }
            }
    }

        /**
         * Sets the animations.
         */
        @Override
        public void configureAnimations () {

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
        }

        /**
         * Sets default direction textures uses the get index for Animation feature
         * as described in the animation documentation section 4.
         */
        @Override
        public void setDirectionTextures () {
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
        private void updateAnimation () {
            getPlayerDirectionCardinal();
            List<Float> vel = getVelocity();

        /*
        if(isAttacking) {
            setCurrentState(AnimationRole.ATTACK);
           // System.out.println(isAttacking);
            setAttacking(false);
        }

        /* Short Animations */
            if (getToBeRun() != null) {
                if (getToBeRun().getType() == AnimationRole.ATTACK) {
                    return;
                } else if (getToBeRun().getType() == AnimationRole.DEAD) {
                    return;
                }
            }

            if (isHurt) {
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

