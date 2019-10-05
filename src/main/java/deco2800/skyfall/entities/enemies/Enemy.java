package deco2800.skyfall.entities.enemies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import deco2800.skyfall.Tickable;
import deco2800.skyfall.entities.Peon;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.graphics.types.vec2;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.SoundManager;
import deco2800.skyfall.entities.ICombatEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.AnimationLinker;

/**
 * An instance to abstract the basic variables and  methods of an enemy.
 */
public class Enemy extends Peon
        implements Animatable, ICombatEntity, Tickable {

    // Logger for tracking enemy information
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Basic enemy stats
    private int health;
    private int damage;
    private float attackRange;
    private float walkingSpeed;
    private float chasingSpeed;

    // Name of the biome the enemy is in.
    private String biome;

    // Enemy types
    public enum EnemyType {
        ABDUCTOR,
        FLOWER,
        HEAVY,
        ROBOT,
        SCOUT,
        SPIDER,
        STONE,
        TREEMAN
    }

    // type this enemy is
    private Enemy.EnemyType enemy;

    // Animation timings
    private long hurtTime = 0;
    private long deadTime = 0;
    // Booleans to check whether Enemy is in a state.
    private boolean isMoving = false;
    private boolean isAttacking = false;
    private boolean isHurt = false;

    // sound effects
    @SuppressWarnings("WeakerAccess")
    protected String chasingSound;
    @SuppressWarnings("WeakerAccess")
    protected String attackingSound;
    @SuppressWarnings("WeakerAccess")
    protected String diedSound;

    // Main character instance in the game.
    private MainCharacter mainCharacter;

    // A routine for destination
    private HexVector destination = null;
    // World coordinates of this enemy
    private float[] originalPosition = WorldUtil.colRowToWorldCords(this.getCol(), this.getRow());

    public Enemy(float col, float row, String hitBoxPath, String name,
                  float speed, String biome, String textureName) {
        super(row, col, speed, textureName, 10);

        // Sets the spawning location and all the collision
        this.setPosition(col, row);
        //this.initialiseBox2D(col, row, hitBoxPath);
        this.setCollidable(true);

        // Sets the main character in the game.
        this.setMainCharacter(MainCharacter.getInstance());

        // Sets the type of the enemy, its name and the biome it is from
        this.setType(name);
        this.setName(name);
        this.setBiome(biome);
        this.setHealth(health);
        this.setMaxHealth(health);

        // Sets the texture for this enemy and the animations
        this.setTexture(textureName);
        this.setDirectionTextures();
        this.configureAnimations();
        this.configureSounds();
    }

    /**
     *This constructor is written for testing.
     *
     * @param col the x-coordinate of the enemy.
     * @param row the y-coordinate of the enemy.
     */
    public Enemy(float col, float row) {
        this.setRow(row);
        this.setCol(col);
    }

    /**
     * Damage taken
     * @param damage hero damage
     */
    @Override
    public void takeDamage(int damage) {
        hurtTime = 0;
        setHurt(true);
        changeHealth(-damage);
        health -= damage;

        // In Peon.class, when the health = 0, isDead will be set true automatically.
        if (health <= 0) {
            enemyDied();
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

    /**
     * Check whether the hurt time is within 2 seconds,
     * therefore casting hurt effects on enemy.
     */
    private void checkIfHurtEnded() {
        hurtTime += 20; // hurt for 1 second
        if (hurtTime > 400) {
            logger.info("Hurt ended");
            setHurt(false);
            hurtTime = 0;
        }
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
     * Remove this enemy from the game world.
     */
    private void enemyDied() {
        if (isDead()) {
            if(getChaseSound() != null) {
                SoundManager.stopSound(getChaseSound());
            }
            if(getDeadSound() != null) {
                SoundManager.playSound(getDeadSound());

                isMoving = false;
                this.destination = new HexVector(this.getCol(), this.getRow());
                this.setDead(true);
                logger.info("Enemy destroyed.");}

            GameManager.get().getWorld().removeEntity(this);
            setCurrentState(AnimationRole.NULL);
        }
    }

    /**
     * under normal situation the enemy will random wandering in 100 radius circle
     */
    private void randomMoving() {
        if ((!isAttacking || mainCharacter.isRecovering() ||
                mainCharacter.isDead()) && !isMoving) {

            float[] targetPosition = new float[2];
            targetPosition[0] = (float) (Math.random() * 800 + originalPosition[0]);
            targetPosition[1] = (float) (Math.random() * 800 + originalPosition[1]);

            float[] randomPositionWorld = WorldUtil.worldCoordinatesToColRow(targetPosition[0], targetPosition[1]);
            destination = new HexVector(randomPositionWorld[0], randomPositionWorld[1]);
            isMoving = true;
            this.position.moveToward(destination, getWalkingSpeed());
            movementDirection(this.position.getAngle());

            SoundManager.stopSound(chasingSound);
            if (destination.getCol() == this.getCol() && destination.getRow() == this.getRow()) {
                isMoving = false;
            }
        }
    }

    /**
     * Enemy chase the player, if player position is in range,
     * enemy attacks player.
     */
    private void chasePlayer() {
        if(isAttacking && !(this.mainCharacter.isRecovering() ||
                this.mainCharacter.isDead() || this.mainCharacter.isHurt())) {
            this.setSpeed(getChasingSpeed());
            this.destination = new HexVector(mainCharacter.getCol(), mainCharacter.getRow());
            this.position.moveToward(destination, this.getChasingSpeed());

            //if the player in attack range then attack player
            if (distance(mainCharacter) < getAttackRange()) {
                dealDamage(mainCharacter);
            }
        }
    }

    /**
     * Deals damage to the main character by lowering their health
     * @param mc The main character
     */
    @Override
    public void dealDamage(MainCharacter mc) {
        setAttacking(false);
        setCurrentState(AnimationRole.ATTACK);
        mc.hurt(this.getDamage());
        mc.setRecovering(true);
    }

    /**
     * Determines whether the enemy can deal damage
     * @return True if they can deal damage, false otherwise
     */
    @Override
    public boolean canDealDamage() {
        return this.getName().matches("Abductor");
    }

    /**
     *  Handles the action of the enemy per time tick in game.
     * @param tick number of second tin the game.
     */
    @Override
    public void onTick(long tick) {
        if (isDead()) {
            if (deadTime < 500) {
                deadTime += 20;
            } else {
                enemyDied();
            }
        } else {
            movementDirection(this.position.getAngle());

            //if the player in angry distance or the enemy is attacked by player then turning to angry model
            if (distance(mainCharacter) < 2 && !(mainCharacter.isDead() ||
                    mainCharacter.isRecovering() || mainCharacter.isHurt())) {
                setAttacking(true);
                setCurrentState(AnimationRole.ATTACK);
                chasePlayer();
                SoundManager.loopSound(getChaseSound());

            } else {
                isMoving = false;
                randomMoving();
                setAttacking(false);
                setSpeed(getWalkingSpeed());
                setCurrentState(AnimationRole.MOVE);
                SoundManager.stopSound(getChaseSound());
            }
            if (isHurt) {
                checkIfHurtEnded();
            }
            this.updateAnimation();
        }
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
     * get movement direction
     *
     * @param angle the angle between to tile
     */
    private void movementDirection(double angle) {
        angle = Math.toDegrees(angle - Math.PI);

        System.out.println(angle);
        switch ((int) angle) {
            case 0:
                setCurrentDirection(Direction.NORTH);
                break;
            case 1:
                setCurrentDirection(Direction.NORTH_EAST);
                break;
            case 2:
                setCurrentDirection(Direction.EAST);
                break;
            case 3:
                setCurrentDirection(Direction.SOUTH_EAST);
                break;
            case 4:
                setCurrentDirection(Direction.SOUTH);
                break;
            case 5:
                setCurrentDirection(Direction.SOUTH_WEST);
                break;
            case 6:
                setCurrentDirection(Direction.WEST);
                break;
            case 7:
                setCurrentDirection(Direction.NORTH_WEST);
                break;
            default:
                break;
        }
    }

    /**
     * Sets the type of the enemy from all available types
     * @param name The name of the enemy
     */
    private void setType(String name) {
        if (name.matches("Abductor")) {
            enemy = Enemy.EnemyType.ABDUCTOR;
        } else if (name.matches("Flower")) {
            enemy = Enemy.EnemyType.FLOWER;
        } else if (name.matches("Heavy")) {
            enemy = Enemy.EnemyType.HEAVY;
        } else if (name.matches("Robot")) {
            enemy = Enemy.EnemyType.ROBOT;
        } else if (name.matches("Scout")) {
            enemy = Enemy.EnemyType.SCOUT;
        } else if (name.matches("Spider")) {
            enemy = Enemy.EnemyType.SPIDER;
        } else if (name.matches("Stone")) {
            enemy = Enemy.EnemyType.STONE;
        } else {
            enemy = Enemy.EnemyType.TREEMAN;
        }
    }

    void setValues(float scaling, int health, int damage, float attackRange, float walkingSpeed, float chasingSpeed) {
        this.setMaxHealth((int) (health * scaling));
        this.setHealth((int) (health * scaling));
        this.setDamage((int) ((float) damage * scaling));
        this.setAttackRange(attackRange * scaling);
        this.setWalkingSpeed(walkingSpeed * scaling);
        this.setChasingSpeed(chasingSpeed * scaling);
    }

    /**
     * Deploy the sound of the enemy into the game.
     */
    private void configureSounds() {
        String name = this.getName();
        this.chasingSound = name + "Walk";
        this.attackingSound = name + "Attack";
        this.diedSound = name + "Dead";
    }

    /**
     * Deploy the animation of the enemy in 8 different directions,
     * and for moving, hurting attacking and dead animations.
     */
    @Override
    public void configureAnimations() {
        String enemyName = this.getName();

        this.addAnimations(
                AnimationRole.MOVE, Direction.NORTH, new AnimationLinker(
                        enemyName + "MoveN", AnimationRole.MOVE, Direction.NORTH,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.NORTH_EAST, new AnimationLinker(
                        enemyName + "MoveNE", AnimationRole.MOVE, Direction.NORTH_EAST,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.NORTH_WEST, new AnimationLinker(
                        enemyName + "MoveNW", AnimationRole.MOVE, Direction.NORTH_WEST,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.EAST, new AnimationLinker(
                        enemyName + "MoveE", AnimationRole.MOVE, Direction.EAST,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.WEST, new AnimationLinker(
                        enemyName + "MoveW", AnimationRole.MOVE, Direction.WEST,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.SOUTH, new AnimationLinker(
                        enemyName + "MoveS", AnimationRole.MOVE, Direction.SOUTH,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.SOUTH_EAST, new AnimationLinker(
                        enemyName + "MoveSE", AnimationRole.MOVE, Direction.SOUTH_EAST,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.SOUTH_WEST, new AnimationLinker(
                        enemyName + "MoveSW", AnimationRole.MOVE, Direction.SOUTH_WEST,
                        true, true));

        /*
        this.addAnimations(
                AnimationRole.ATTACK, Direction.EAST, new AnimationLinker(
                enemyName + "AttackE", AnimationRole.ATTACK, Direction.EAST,
                        true, true));
        this.addAnimations(
                AnimationRole.ATTACK, Direction.NORTH, new AnimationLinker(
                enemyName + "AttackN", AnimationRole.ATTACK, Direction.NORTH,
                        true, true));
        this.addAnimations(
                AnimationRole.ATTACK, Direction.SOUTH, new AnimationLinker(
                enemyName + "AttackS", AnimationRole.ATTACK, Direction.SOUTH,
                        true, true));
        this.addAnimations(
                AnimationRole.ATTACK, Direction.SOUTH_EAST, new AnimationLinker(
                enemyName + "AttackSE", AnimationRole.ATTACK, Direction.SOUTH_EAST,
                        true, true));
        this.addAnimations(
                AnimationRole.ATTACK, Direction.SOUTH_WEST, new AnimationLinker(
                enemyName + "AttackSW", AnimationRole.ATTACK, Direction.SOUTH_WEST,
                        true, true));
        this.addAnimations(
                AnimationRole.ATTACK, Direction.WEST, new AnimationLinker(
                enemyName + "AttackW", AnimationRole.ATTACK, Direction.WEST,
                        true, true));
        this.addAnimations(
                AnimationRole.DEAD, Direction.DEFAULT, new AnimationLinker(
                enemyName + "Dead", AnimationRole.DEAD, Direction.DEFAULT,
                        true, true));
        */
    }

    /**
     * Set default texture of the enemy in 8 different directions.
     */
    @Override
    public void setDirectionTextures() {
        String animationNameStart = "__ANIMATION_" + this.getName();
        //defaultDirectionTextures.put(Direction.EAST, animationNameStart + "MoveE_Anim:0")
        defaultDirectionTextures.put(Direction.WEST, animationNameStart + "MoveW_Anim:0");
        defaultDirectionTextures.put(Direction.SOUTH, animationNameStart + "MoveS_Anim:0");
        defaultDirectionTextures.put(Direction.NORTH, animationNameStart + "MoveN_Anim:0");
        defaultDirectionTextures.put(Direction.NORTH_EAST, animationNameStart + "MoveNE_Anim:0");
        defaultDirectionTextures.put(Direction.NORTH_WEST, animationNameStart + "MoveNW_Anim:0");
        defaultDirectionTextures.put(Direction.SOUTH_EAST, animationNameStart + "MoveSE_Anim:0");
        defaultDirectionTextures.put(Direction.SOUTH_WEST, animationNameStart + "MoveSW_Anim:0");
    }

    /**
     * Setter of the enemy's speed when walking,
     * will be used in {@link #setValues(float, int, int, float, float, float)}.
     *
     * @param walkingSpeed the enemy's walking speed.
     */
    private void setWalkingSpeed(float walkingSpeed) {
        this.walkingSpeed = walkingSpeed;
    }

    /**
     * Getter of the enemy's speed when walking.
     * @return the walking speed of this enemy.
     */
    private float getWalkingSpeed() {
        return this.walkingSpeed;
    }

    /**
     * Setter of the enemy's speed when chasing the player.
     *
     * @param chasingSpeed the enemy's chasing speed.
     */
    private void setChasingSpeed(float chasingSpeed) {
        this.chasingSpeed = chasingSpeed;
    }

    /**
     * Getter of the enemy's speed when chasing the player.
     *
     * @return the chasing speed of this enemy.
     */
    private float getChasingSpeed() {
        return this.chasingSpeed;
    }

    /**
     * Getter if the enemy's sound when chasing the player.
     *
     * @return the name of the chasing sound
     *          (defined in {@link SoundManager}) of this enemy.
     */
    private String getChaseSound() {
        return this.chasingSound;
    }

    /**
     * Getter of the enemy's sound when enemy's dead.
     *
     * @return the name of the dead sound
     *               (defined in {@link SoundManager}) of this enemy.
     */
    private String getDeadSound() {
        return this.diedSound;
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
     * Get enemy's current strength
     * @return enemy's current strength.
     */
    @Override
    public int getDamage() {
        return this.damage;
    }

    /**
     * Set the amount of damage for this enemy.
     * @param damage the new amount of damage to be set.
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Set the biome the enemy is located at,
     * will be used in {@link #setValues(float, int, int, float, float, float)}.
     *
     * @param biome the name of the biome the enemy is located at.
     */
    public void setBiome(String biome) {
        this.biome = biome;
    }

    /**
     * Get the name of the biome this enemy is located at.
     * @return the name of the biome this enemy is located.
     */
    public String getBiome() {
        return this.biome;
    }

    /**
     * Getter and setter of the main character object in the game.
     * @param mainCharacter the main character in the game.
     */
    private void setMainCharacter(MainCharacter mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    /**
     * Get the main character instance in the game
     * @return the main character object in the game.
     */
    public MainCharacter getMainCharacter() {
        return this.mainCharacter;
    }

    /**
     * Set the attack range for the enemy,
     * will be called in {@link #setValues(float, int, int, float, float, float)}.
     *
     * @param attackRange the attack range of the enemy.
     */
    @SuppressWarnings("WeakerAccess")
    public void setAttackRange(float attackRange) {
        this.attackRange = attackRange;
    }

    /**
     * Get the attack range of the enemy.
     * @return the attack range of the enemy.
     */
    @SuppressWarnings("WeakerAccess")
    public float getAttackRange() {
        return this.attackRange;
    }

    /**
     *  A cheat method to get player position, used by spawning
     *  to work out where to place an enemy
     * @return returns a vec2 -> (row, col) of player location
     */
    public vec2 getPlayerLocation() {
        return new vec2(mainCharacter.getRow(),
                mainCharacter.getCol());
    }

    /**
     * Return a list of resistance attributes.
     * @return A list of resistance attributes.
     */
    @Override
    public int[] getResistanceAttributes() {
        return new int[0];
    }

    /**
     * @return string representation of this class including its
     *  enemy type, biome and x,y coordinates
     */
    @Override
    public String toString() {
        return String.format("%s at (%d, %d) %s biome", getObjectName(), (int)getCol(), (int)getRow(), getBiome());
    }

    /**
     * Check whether the object equals to this enemy instance.
     *
     * @param obj the object to be checked
     * @return true if the objects equals, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Enemy &&
                this.hashCode() == obj.hashCode();
    }

    /**
     * The hashcode of the enemy based on {@link #toString()}.
     * It will be used in {@link #equals(Object)} for comparing
     * objects.
     *
     * @return the hashcode of the enemy intstance.
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
