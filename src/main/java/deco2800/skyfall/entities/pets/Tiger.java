package deco2800.skyfall.entities.pets;

import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.enemies.Enemy;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;

public class Tiger extends Enemy implements Animatable {
    // The health of tiger
    private static final int HEALTH = 10;
    // The attack speed of tiger
    private static final float RUNAWAYSPEED = 5f;
    // The normal speed of tiger, if it is not in attack
    private static final float NORMALSPEED = 0.01f;
    // The speed of tiger, if it get injure
    private static final float INJURESPEED = 0.00001f;
    // The biome of tiger
    private static final String BIOME = "forest";
    // Moving direction
    private Direction movingDirection = Direction.SOUTH;
    // Set boolean moving
    private boolean moving = false;

    // Set the type
    private static final String PET_TYPE = "tiger";

    // Texture name
    private static final String TEXTURENAME = "petTiger";

    // savage animation
    private MainCharacter mc;

    // a routine for destination
    private HexVector destination = null;

    // target position
    private float[] targetPosition = null;

    // world coordinate of this pet
    private float[] orginalPosition = WorldUtil.colRowToWorldCords(this.getCol(), this.getRow());

    /**
     * Initialization value of pet tiger, and set the initial image in the game
     */
    public Tiger(float col, float row, MainCharacter mc) {
        super(col, row);
        this.setTexture(TEXTURENAME);
        this.setObjectName(TEXTURENAME);
        this.setHeight(5);
        this.setHealth(HEALTH);
        this.setSpeed(2);
        this.setStrength(1);
        this.mc = mc;
        this.setDirectionTextures();
        this.configureAnimations();
    }

    /**
     * Initialization value of pet tiger
     */
    public Tiger(float col, float row) {
        super(col, row);
        this.setTexture(TEXTURENAME);
        this.setObjectName(TEXTURENAME);
        this.setHeight(5);
        this.setHealth(HEALTH);
        this.setSpeed(2);
    }

    /**
     * get pet type
     * 
     * @return pet type
     */
    public String getPetType() {
        return PET_TYPE;
    }

    /**
     * get pet moving
     * 
     * @return boolean moving
     */
    public boolean getMoving() {
        return moving;
    }

    /**
     * get biome
     * 
     * @return string of biome
     */
    @Override
    public String getBiome() {
        return BIOME;
    }

    /**
     * Return true, if the pet tiger get injure. Otherwise return false
     * 
     * @return True if get injure, false otherwise
     */
    public boolean getInjure() {
        return this.getHealth() < 5;
    }

    /**
     * Return the string
     * 
     * @return string representation of this class including its pet type, biome and
     *         x,y coordinates
     */
    @Override
    public String toString() {
        return String.format("%s at (%d, %d) %s biome", getPetType(), (int) getCol(), (int) getRow(), getBiome());
    }

    /**
     * If the character is not close to the pet tiger, this pet will does the random
     * movement. If the the character is closing to the tiger, the tiger will run
     * away, and if the character attack and capture the tiger, this tiger will be a
     * pet of master
     *
     */
    @Override
    public void onTick(long i) {
        randomMoving();
        setCurrentState(AnimationRole.MOVE);
        if (isDead()) {
            this.tigerDead();
        } else {
            float colDistance = mc.getCol() - this.getCol();
            float rowDistance = mc.getRow() - this.getRow();
            if (getHealth() == 10) {
                if ((colDistance * colDistance + rowDistance * rowDistance) < 4) {
                    runAway();
                } else {
                    randomMoving();
                }
            } else {
                followPlayer(mc);
                setCurrentState(AnimationRole.MOVE);
                if (getInjure()) {
                    this.position.moveToward(destination, Tiger.INJURESPEED);

                } else {
                    this.position.moveToward(destination, Tiger.NORMALSPEED);
                }
            }
        }
    }

    /**
     * Give a location to the pet tiger, if it wants to run away
     */
    public void runAway() {
        targetPosition = new float[2];
        targetPosition[0] = (float) (Math.random() * 800 + orginalPosition[0]);
        targetPosition[1] = (float) (Math.random() * 800 + orginalPosition[1]);
        float[] randomPositionWorld = WorldUtil.worldCoordinatesToColRow(targetPosition[0], targetPosition[1]);
        destination = new HexVector(randomPositionWorld[0], randomPositionWorld[1]);
        this.position.moveToward(destination, Tiger.RUNAWAYSPEED);
    }

    /**
     * get the moving direction
     * 
     * @return moving direction
     */
    public Direction getMovingDirection() {
        return movingDirection;
    }

    /**
     * Make the pet tiger do the random movement
     *
     */
    public void randomMoving() {
        if (!moving) {
            targetPosition = new float[2];
            targetPosition[0] = (float) (Math.random() * 200 + orginalPosition[0]);
            targetPosition[1] = (float) (Math.random() * 200 + orginalPosition[1]);
            float[] randomPositionWorld = WorldUtil.worldCoordinatesToColRow(targetPosition[0], targetPosition[1]);
            destination = new HexVector(randomPositionWorld[0], randomPositionWorld[1]);
            moving = true;
        }
        if (destination.getCol() == this.getCol() && destination.getRow() == this.getRow()) {
            moving = false;
        }
        if (getInjure()) {
            this.position.moveToward(destination, Tiger.INJURESPEED);
        }
        this.position.moveToward(destination, Tiger.NORMALSPEED);

    }

    /**
     * The tiger will follow the player
     *
     */
    public void followPlayer(MainCharacter player) {
        destination = new HexVector(player.getCol(), player.getRow());
        this.position.moveToward(destination, this.getSpeed());

    }

    /**
     * if this pet is dead then will show dead texture for a while
     */
    int time = 0;

    private void tigerDead() {
        if (time <= 100) {
            time++;
            this.setTexture("tigerDead");
            this.setObjectName("tigerDead");
            setCurrentState(AnimationRole.NULL);
        } else {
            GameManager.get().getWorld().removeEntity(this);

        }

    }

    /**
     * add pet tiger animations
     */
    @Override
    public void configureAnimations() {
        this.addAnimations(AnimationRole.MOVE, Direction.DEFAULT,
                new AnimationLinker("tigerFront", AnimationRole.MOVE, Direction.DEFAULT, true, true));

    }

    /**
     * Set the direction textures of the pet tiger
     */
    @Override
    public void setDirectionTextures() {
        // Do nothing for now.
    }
}