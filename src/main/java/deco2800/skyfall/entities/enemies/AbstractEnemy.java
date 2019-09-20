package deco2800.skyfall.entities.enemies;

import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.ICombatEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.Peon;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.SoundManager;
import deco2800.skyfall.tasks.AbstractTask;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEnemy extends Peon implements Animatable, ICombatEntity {

    private final transient Logger log = LoggerFactory.getLogger(AbstractEnemy.class);

    // The task this Enemy is set to perform.
    protected transient AbstractTask task;

    // The basic variables of the enemy.
    private int strength;
    protected int health;
    protected int range;
    protected String enemyType;

    // The level of Enemy
    private int level = 0;

    // The biome where the enemy appears
    private String biomeLocated = "forest";

    // Booleans to check whether Enemy is in a state.
    private boolean isMoving;
    private boolean isChasing;
    private boolean isAttacking;
    private boolean isHurt = false;

    // How long does Enemy take to initiate animation.
    protected long attackTime = 0;
    protected long hurtTime = 0;
    protected long deadTime = 0;

    // Speed of the enemy
    protected float normalSpeed = 0.02f;
    protected float chaseSpeed = 0.03f;
    protected float slowSpeed = 0.09f;

    // Sound of the enemy
    protected String chasingSound;
    protected String attackSound;
    protected String diedSound;

    // Nature of the enemy
    private boolean canMove = true;

    // Main character instance
    private MainCharacter mc;
    // Main character location
    private float colDistance;
    private float rowDistance;

    //a routine for destination
    private HexVector destination = null;
    //target position
    private float[] targetPosition = null;
    //world coordinate of this enemy
    private float[] originalPosition = WorldUtil.colRowToWorldCords(this.getCol(), this.getRow());
    // Direction
    private Direction movingDirection;

    public AbstractEnemy(float col, float row, int health, String textureName,
                         float speed, int strength, String hitBoxPath, int level) {
        super(row, col, speed, textureName,health);
        this.setTexture(textureName);
        this.level = level;
    }

    public AbstractEnemy (float col, float row) {
        this.setRow(row);
        this.setCol(col);
    }

    /**
     * The getter of the enemy's strength
     * @return strength the amount the enemy can damage.
     */
    @Override
    public int getDamage() {
        return this.strength;
    }

    /**
     *  The setter of the enemy's strength
     * @param strength the amount the enemy can damage.
     */
    public void setDamage(int strength){
        this.strength = strength;
    }

    /**
     * Setting the enemy to attack model
     *
     * @param isAttacking whether the enemy is attacking.
     */
    private void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

    /**
     * if the player is close enough then the enemy will attack player
     *
     * @param player Main character
     */
    public void attackPlayer(MainCharacter player) {
        if(isAttacking && !(this.mc.isRecovering() ||
                this.mc.isDead() || this.mc.IsHurt())) {
            this.setSpeed(this.chaseSpeed);
            this.destination = new HexVector(player.getCol(), player.getRow());

            if(canMove) {
                this.position.moveToward(destination, this.chaseSpeed);
            }

            movingDirection = movementDirection(this.position.getAngle());
            //if the player in attack range then attack player
            if (distance(mc) < range) {
                setAttacking(false);
                setCurrentState(AnimationRole.ATTACK);
                player.hurt(this.getDamage());
                player.setRecovering(true);
            }
        }
    }

    private void checkIfAttackEnded() {
        setAttacking(false);
        attackTime += 20; // hurt for 1 second
        if (attackTime > 400) {
            log.info("Attack ended");
            setAttacking(false);
            attackTime = 0;
        }
    }

    /**
     * Damage taken
     * @param damage hero damage
     */
    public void takeDamage(int damage) {
        hurtTime = 0;
        setHurt(true);
        changeHealth(-damage);

        // In Peon.class, when the health = 0, isDead will be set true automatically.
        if (health == 0) {
            destroy();
        }
    }

    /**
     *  Get whether enemy is hurt.
     */
    public boolean isHurt() {
        return isHurt;
    }

    /**
     *  Set whether enemy is hurt.
     * @param isHurt the player's "hurt" status
     */
    public void setHurt(boolean isHurt) {
        this.isHurt = isHurt;
    }

    private void checkIfHurtEnded() {
        hurtTime += 20; // hurt for 1 second
        if (hurtTime > 400) {
            log.info("Hurt ended");
            setHurt(false);
            hurtTime = 0;
        }
    }

    /**
     * Remove this enemy from the game world.
     */
    private void destroy() {
        if(chasingSound != null) {
            SoundManager.stopSound(chasingSound);
        }
        if(diedSound != null) {
            SoundManager.playSound(diedSound);
        }

        isMoving = false;
        this.destination = new HexVector(this.getCol(), this.getRow());
        this.setDead(true);
        log.info("Enemy destroyed.");
        GameManager.get().getWorld().removeEntity(this);
    }

    /**
     * Return the health this enemy has.
     * @return The health this enemy has.
     */
    public int getHealth() {
        return health;
    }

    /**
     * To set enemy heal
     * @param health set heal of enemy
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Return the level this enemy has.
     * @return The level this enemy has.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Set Enemy level
     * @param level the level the player is to be set to
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Get the biome the enemy appears
     * @return the name oof the biome.
     */
    public String getBiomeLocated(){
        return biomeLocated;
    }

    public void setRange(int range) {
        this.range = range;
    }

    /**
     * Return whether this enemy can deal damage.
     * @return Can this enemy deal damage.
     */
    public boolean canDealDamage() {
        return !mc.isRecovering();
    }

    @Override
    public void dealDamage(ICombatEntity entity) {
        if (entity.canDealDamage()) {
            entity.dealDamage(entity);
        }
    }

    /**
     * get enemy moving
     * @return boolean moving
     */
    public boolean getMoving() {
        return isMoving;
    }

    public Direction getMovingDirection() {
        return movingDirection;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    /**
     * get movement direction
     *
     * @param angle the angle between to tile
     * @return direction
     */
    public Direction movementDirection(double angle) {
        angle = Math.toDegrees(angle - Math.PI);
        if (angle < 0) {
            angle += 360;
        }
        if (angle >= 0 && angle <= 60) {
            return Direction.SOUTH_WEST;
        } else if (angle > 60 && angle <= 120) {
            return Direction.SOUTH;
        } else if (angle > 120 && angle <= 180) {
            return Direction.SOUTH_EAST;
        } else if (angle > 180 && angle <= 240) {
            return Direction.NORTH_EAST;
        } else if (angle > 240 && angle <= 300) {
            return Direction.NORTH;
        } else if (angle > 300 && angle < 360) {
            return Direction.NORTH_WEST;
        }
        return null;
    }

    /**
     * under normal situation the enemy will random wandering in 100 radius circle
     */
    public void randomMoving() {
        if(enemyType.equals("stone")) {
            if (!isAttacking || isAttacking && (mc.isRecovering() || mc.isDead())) {
                movingDirection = movementDirection(this.position.getAngle());

                if (!isMoving) {
                    targetPosition = new float[2];
                    //random movement range
                    targetPosition[0] = (float) (Math.random() * 100 + originalPosition[0]);
                    targetPosition[1] = (float) (Math.random() * 100 + originalPosition[1]);
                    float[] randomPositionWorld = WorldUtil.worldCoordinatesToColRow(targetPosition[0], targetPosition[1]);
                    destination = new HexVector(randomPositionWorld[0], randomPositionWorld[1]);
                    isMoving = true;
                }

                if (destination.getCol() == this.getCol() && destination.getRow() == this.getRow()) {
                    isMoving = false;
                    SoundManager.stopSound("stoneWalk");
                }
                this.position.moveToward(destination, this.getSpeed());
            }
        } else if(enemyType.equals("treeman")) {
            if (!isAttacking || mc.isRecovering() || mc.isDead()) {
                if (!isMoving) {
                    targetPosition = new float[2];
                    targetPosition[0] = (float)
                            (Math.random() * 800 + originalPosition[0]);
                    targetPosition[1] = (float)
                            (Math.random() * 800 + originalPosition[1]);
                    float[] randomPositionWorld = WorldUtil.worldCoordinatesToColRow
                            (targetPosition[0], targetPosition[1]);
                    destination = new HexVector(randomPositionWorld[0],
                            randomPositionWorld[1]);
                    isMoving = true;
                }
                if (destination.getCol() == this.getCol() &&
                        destination.getRow() == this.getRow()) {
                    isMoving = false;
                }
                if (isHurt) {
                    this.position.moveToward(destination, this.slowSpeed);
                }
                this.position.moveToward(destination, this.normalSpeed);
            }
        }
    }

    public void setCharacter(MainCharacter player) {
        this.mc = player;
    }

    public void setAllSpeed(float normalSpeed,
                            float chasingSpeed, float slowSpeed) {
        this.normalSpeed = normalSpeed;
        this.chaseSpeed = chaseSpeed;
        this.slowSpeed = slowSpeed;
    }

    /**
     * Handles tick based stuff, e.g. movement
     */
    @Override
    public void onTick(long i) {
        if (isDead()) {
            if (deadTime < 500) {
                deadTime += 20;
            } else {
                destroy();
            }
        } else {
            randomMoving();
            updateAnimation();

            //if the player in angry distance or the enemy is attacked by player then turning to angry model
            if (distance(mc) < 2 ||
                    isAttacking && !(mc.isDead() || mc.isRecovering() || mc.IsHurt())) {
                setAttacking(true);
                attackPlayer(mc);
                attackTime = 0;
                SoundManager.loopSound(chasingSound);
                checkIfAttackEnded();

            } else {
                setAttacking(false);
                setSpeed(normalSpeed);
                setCurrentDirection(movementDirection(this.position.getAngle()));
                setCurrentState(AnimationRole.MOVE);
                SoundManager.stopSound(chasingSound);
            }
            if (isHurt) {
                checkIfHurtEnded();
            }
            this.updateAnimation();
        }
    }

    /**
     * Sets default direction textures uses the get index for Animation feature
     * as described in the animation documentation section 4.
     */
    @Override
    public void setDirectionTextures() {
    }

    /**
     * If the animation is moving sets the animation state to be Move
     * else NULL. Also sets the direction
     */
    private void updateAnimation() {
        /* Short Animations */
        if (getToBeRun() != null) {
            if (getToBeRun().getType() == AnimationRole.DEAD) {
                setCurrentState(AnimationRole.STILL);
            } else if (getToBeRun().getType() == AnimationRole.ATTACK) {
                setAttacking(false);
                return;
            }
        } else {
            if (isDead()) {
                setCurrentState(AnimationRole.STILL);
            } else if (isHurt) {
                setCurrentState(AnimationRole.HURT);
            }
        }
    }

    /**
     * Return a list of resistance attributes.
     *
     * @return A list of resistance attributes.
     */
    @Override
    public int[] getResistanceAttributes() {
        return new int[0];
    }

    /**
     * @return string representation of this class including its enemy type, biome and x,y coordinates
     */
    @Override
    public String toString() {
        return String.format("%s at (%d, %d) %s biome", (int)getCol(), (int)getRow(), getBiomeLocated());
    }

}
