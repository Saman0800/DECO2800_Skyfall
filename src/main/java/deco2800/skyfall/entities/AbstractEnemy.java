package deco2800.skyfall.entities;

import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
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
    protected String enemyAnimationName;

    // The level of Enemy
    private int level = 0;
    
    // Booleans to check whether Enemy is in a state.
    private boolean isMoving;
    private boolean isChasing;
    private boolean isAttacking;
    private boolean isHurt = false;

    // How long does Enemy take to initiate animation.
    protected long attackTime = 0;
    protected long hurtTime = 0;
    protected long deadTime = 500;

    // Speed of the enemy
    protected float normalSpeed = 0.03f;
    protected float chaseSpeed = 0.06f;
    protected float slowSpeed = 0.02f;

    // Main character
    private MainCharacter mc;

    //a routine for destination
    private HexVector destination = null;

    //target position
    private float[] targetPosition = null;

    //world coordinate of this enemy
    private float[] originalPosition = WorldUtil.colRowToWorldCords(this.getCol(), this.getRow());

    // Direction
    private Direction movingDirection;


    public AbstractEnemy (float col, float row, MainCharacter mc) {
        this.setRow(row);
        this.setCol(col);
        this.mc = mc;
    }

    public AbstractEnemy(float col, float row, int health, String textureName,
                         float speed, int strength, String hitBoxPath, int level,
                         MainCharacter mc) {
        super(row, col, 0.2f,textureName,health);
        this.setTexture(textureName);

        this.speed = speed;
        this.level = level;
        this.mc = mc;
    }

    public AbstractEnemy (float col, float row) {
        this.setRow(row);
        this.setCol(col);
    }

    /**
     * The getter of the enemy's strength
     * @return strength value
     */
    @Override
    public int getDamage() {
        return this.strength;
    }

    /**
     *  The setter of the enemy's strength
     * @param strength
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
        this.setSpeed(chaseSpeed);
        destination = new HexVector(player.getCol(), player.getRow());
        this.position.moveToward(destination, this.getSpeed());
        /*
        //when enemy arrive player location turn it face to player and do attack animation
        if (destination.getCol() == this.getCol() && destination.getRow() == this.getRow()) {
            complete = true;
            if (movingDirection == Direction.NORTH_EAST) {
                movingDirection = Direction.SOUTH_WEST;
                setCurrentDirection(Direction.SOUTH_WEST);
            } else if (movingDirection == Direction.NORTH) {
                movingDirection = Direction.SOUTH;
                setCurrentDirection(Direction.SOUTH);
            } else if (movingDirection == Direction.NORTH_WEST) {
                movingDirection = Direction.SOUTH_WEST;
                setCurrentDirection(Direction.SOUTH_WEST);
            } else if (movingDirection == Direction.SOUTH_EAST) {
                movingDirection = Direction.SOUTH_WEST;
                setCurrentDirection(Direction.SOUTH_WEST);
            }
        } else {
        */
        // complete = false;
        movingDirection = movementDirection(this.position.getAngle());
        //}
        //if the player in attack range then attack player
        if (this.position.isCloseEnoughToBeTheSameByDistance(destination, range)) {
            if (attackTime <= 100) {
                attackTime++;
            } else {
                attackTime = 0;
                if (!(mc.isRecovering())) {
                    player.hurt(this.getDamage());
                }
            }
        }
    }

    public void angryAttacking() {
        float colDistance = mc.getCol() - this.getCol();
        float rowDistance = mc.getRow() - this.getRow();
        // if the player in angry distance or the enemy is attacked by player then turning to angry model
        if ((colDistance * colDistance + rowDistance * rowDistance) < 4 || this.isHurt) {
            this.setAttacking(true);
            this.attackPlayer(mc);
            //because we have not north and north-east combat animation.....
            //if the moving direction is north or north-east then do movement animation
            if (this.getMovingDirection() == Direction.NORTH || this.getMovingDirection() == Direction.NORTH_EAST
                    ) {
                setCurrentDirection(movementDirection(this.position.getAngle()));
                setCurrentState(AnimationRole.MOVE);
            } else {
                setCurrentDirection(movingDirection);
                setCurrentState(AnimationRole.MELEE);
            }
        } else {
            //if player out of angry distance then the enemy turns to normal model
            this.setAttacking(false);
            this.setSpeed(normalSpeed);
            setCurrentDirection(movementDirection(this.position.getAngle()));
            setCurrentState(AnimationRole.MOVE);
        }
    }

    /**
     * Turing attack model to normal model
     */
    public void resetFeeling() {
        this.setHurt(false);
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
    protected void destroy() {
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
     * Return whether this enemy can deal damage.
     * @return Can this enemy deal damage.
     */
    @Override
    public boolean canDealDamage() {
        if(mc.isRecovering()) {
            return false;
        }
        return true;
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
        if(enemyAnimationName.equals("stone")) {
            if (!isAttacking) {
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
        } else if(enemyAnimationName.equals("treeman")) {
            if(!isMoving){
                targetPosition = new float[2];
                targetPosition[0] = (float)
                        (Math.random() * 800 + originalPosition[0]);
                targetPosition[1]=(float)
                        (Math.random() * 800 + originalPosition[1]);
                float[] randomPositionWorld = WorldUtil.worldCoordinatesToColRow
                        (targetPosition[0], targetPosition[1]);
                destination = new HexVector(randomPositionWorld[0],
                        randomPositionWorld[1]);
                isMoving = true;
            }
            if(destination.getCol() == this.getCol() &&
                    destination.getRow() == this.getRow()){
                isMoving = false;
            }
            if (isHurt) {
                this.position.moveToward(destination,this.slowSpeed);
            }
            this.position.moveToward(destination,this.normalSpeed);
        } else if(enemyAnimationName.equals("flower")) {

        }
    }

    /**
     * Handles tick based stuff, e.g. movement
     */
    @Override
    public void onTick(long i) {
        this.randomMoving();
        this.updateAnimation();

        if (isDead()) {
            destroy();
        }

        float colDistance = mc.getCol() - this.getCol();
        float rowDistance = mc.getRow() - this.getRow();
        if ((colDistance * colDistance + rowDistance * rowDistance) < 4
                && !(mc.isRecovering() || mc.IsHurt())) {
            this.setAttacking(true);
            this.attackPlayer(mc);
            if(this.getMovingDirection() == Direction.NORTH ||
                    this.getMovingDirection() == Direction.NORTH_EAST) {
                setCurrentDirection(movementDirection(this.position.getAngle()));
                setCurrentState(AnimationRole.MOVE);
            } else {
                setCurrentDirection(movingDirection);
                setCurrentState(AnimationRole.ATTACK);
            }
        } else {
            setCurrentState(AnimationRole.MOVE);
            this.position.moveToward(destination,this.normalSpeed);
            setCurrentDirection(movementDirection(this.position.getAngle()));
        }

        if (isHurt) {
            checkIfHurtEnded();
        }
        this.updateAnimation();

        if (isDead()) {
            if (deadTime < 500) {
                deadTime += 20;
            } else {
                GameManager.setPaused(true);
            }
        }
    }

    /**
     * Sets the animations.
     */
    @Override
    public void configureAnimations() {
        String animationMove;
        String animationAttack = "";
        //String animationHurt;
        switch(enemyAnimationName) {
            case "stone":
                animationMove = "stoneJ";
                animationAttack = "stoneA";
                //animationHurt = "stoneH";
                break;
            case "treeman":
                animationMove = "treemanM";
                animationAttack = "treemanA";
                //animationHurt = "stoneH";
                break;
            default:
                animationMove = "stoneJ";
                animationAttack = "stoneA";
                //animationHurt = "stoneH";
        }

        // Walk animation
        addAnimations(AnimationRole.MOVE, Direction.NORTH_WEST,
                new AnimationLinker(animationMove + "NW",
                        AnimationRole.MOVE, Direction.NORTH_WEST, true, true));

        addAnimations(AnimationRole.MOVE, Direction.NORTH_EAST,
                new AnimationLinker(animationMove + "NE",
                        AnimationRole.MOVE, Direction.NORTH_WEST, true, true));

        addAnimations(AnimationRole.MOVE, Direction.SOUTH_WEST,
                new AnimationLinker(animationMove + "SW",
                        AnimationRole.MOVE, Direction.SOUTH_WEST, true, true));

        addAnimations(AnimationRole.MOVE, Direction.SOUTH_EAST,
                new AnimationLinker(animationMove + "SE",
                        AnimationRole.MOVE, Direction.SOUTH_EAST, true, true));

        addAnimations(AnimationRole.MOVE, Direction.EAST,
                new AnimationLinker(animationMove + "E",
                        AnimationRole.MOVE, Direction.EAST, true, true));

        addAnimations(AnimationRole.MOVE, Direction.NORTH,
                new AnimationLinker(animationMove + "N",
                        AnimationRole.MOVE, Direction.NORTH, true, true));

        addAnimations(AnimationRole.MOVE, Direction.WEST,
                new AnimationLinker(animationMove + "W",
                        AnimationRole.MOVE, Direction.WEST, true, true));

        addAnimations(AnimationRole.MOVE, Direction.SOUTH,
                new AnimationLinker(animationMove + "S",
                        AnimationRole.MOVE, Direction.SOUTH, true, true));

        // Attack animation
        addAnimations(AnimationRole.ATTACK, Direction.DEFAULT,
                new AnimationLinker(animationAttack + "NW",
                        AnimationRole.ATTACK, Direction.DEFAULT, false, true));

        addAnimations(AnimationRole.ATTACK, Direction.DEFAULT,
                new AnimationLinker(animationAttack + "S",
                        AnimationRole.ATTACK, Direction.DEFAULT, false, true));

        addAnimations(AnimationRole.ATTACK, Direction.DEFAULT,
                new AnimationLinker(animationAttack + "SE",
                        AnimationRole.ATTACK, Direction.DEFAULT, false, true));

        addAnimations(AnimationRole.ATTACK, Direction.DEFAULT,
                new AnimationLinker(animationAttack + "SW",
                        AnimationRole.ATTACK, Direction.DEFAULT, false, true));
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
            }

            //else if (getToBeRun().getType() == AnimationRole.ATTACK) {
            //}
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
}
