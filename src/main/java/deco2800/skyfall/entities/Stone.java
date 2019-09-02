package deco2800.skyfall.entities;

import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.SoundManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;

public class Stone extends EnemyEntity implements Animatable {
    private static final transient int HEALTH = 30;

    //the speed in normal situation
    private static final transient float NORMALSPEED = 0.01f;

    //the movement speed in angry situation
    private static final transient float ARGRYSPEED = 0.03f;

    //combat range
    private static final transient float ATTACK_RANGE = 3f;

    //frequency of attack
    private static final transient int ATTACK_FREQUENCY = 50;
    private static final transient String BIOME = "desert";
    private boolean moving = false;
    private float originalCol;
    private float orriginalRow;
    private Direction movingDirection;

    private static final transient String ENEMY_TYPE = "stone";
    private boolean attacking = false;
    private MainCharacter mc;

    //if the enemy is attacked by player or the player closed enough to the enemy
    //than the enemy my will be in angry situation
    private int angerTimeAccount = 0;

    //a routine for destination
    private HexVector destination = null;

    //target position
    private float[] targetPosition = null;

    //world coordinate of this enemy
    private float[] orginalPosition = WorldUtil.colRowToWorldCords(this.getCol(), this.getRow());

    //Insert SoundManager class
    private SoundManager sound = new SoundManager();

    //To indicate whether the enemy arrives player's location
    private boolean complete = false;

    public Stone(float col, float row, MainCharacter mc) {
        super(col, row);
        this.originalCol = col;
        this.orriginalRow = row;
        this.setTexture("enemyStone");
        this.setObjectName("enemyStone");
        this.setHeight(1);
        this.setHealth(HEALTH);
        this.setLevel(2);
        this.setSpeed(NORMALSPEED);
        this.setArmour(1);
        this.setDamage(3);
        this.mc = mc;
        this.configureAnimations();
        this.setDirectionTextures();
    }


    public Stone(float row, float col, String texturename, int health, int armour, int damage) {
        super(row, col, texturename, health, armour, damage);
    }


    /**
     * get enemy type
     *
     * @return enemy type
     */
    public String getEnemyType() {
        return ENEMY_TYPE;
    }


    /**
     * To get biome
     *
     * @return biome
     */
    public String getBiome() {
        return BIOME;
    }

    // to account attack time
    private int period = 0;


    /**
     * Handles tick based stuff, e.g. movement
     */
    @Override
    public void onTick(long i) {
        this.setCollider();
        this.randomMoving();
        this.resetFeeling();
        if (isDead() == true) {
            this.stoneDead();
        } else {
            this.angryAttacking();

        }
    }


    /**
     * Setting the enemy to attack model
     *
     * @param attacking
     */
    private void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }


    public void angryAttacking() {
        float colDistance = mc.getCol() - this.getCol();
        float rowDistance = mc.getRow() - this.getRow();
        //if the player in angry distance or the enemy is attacked by player then turning to angry model
        if ((colDistance * colDistance + rowDistance * rowDistance) < 4 || this.isAttacked() == true) {
            this.setAttacking(true);
            this.attackPlayer(mc);
            //because we have not north and north-east combat animation.....
            //if the moving direction is north or north-east then do movement animation
            if (this.getMovingDirection() == Direction.NORTH || this.getMovingDirection() == Direction.NORTH_EAST
                    && complete == false) {
                setCurrentDirection(movementDirection(this.position.getAngle()));
                setCurrentState(AnimationRole.MOVE);
            } else {
                setCurrentDirection(movingDirection);
                setCurrentState(AnimationRole.MELEE);
            }
        } else {
            //if player out of angry distance then the enemy turns to normal model
            this.setAttacking(false);
            this.setSpeed(NORMALSPEED);
            setCurrentDirection(movementDirection(this.position.getAngle()));
            setCurrentState(AnimationRole.MOVE);
        }
    }

    /**
     * if the player is close enough then the enemy will attack player
     *
     * @param player Main character
     */
    public void attackPlayer(MainCharacter player) {
        this.setSpeed(ARGRYSPEED);
        destination = new HexVector(player.getCol(), player.getRow());
        this.position.moveToward(destination, this.getSpeed());
        //when enemy arrive player location turn it face to player and do attack animation
        if (destination.getCol() == this.getCol() && destination.getRow() == this.getRow()) {
            if (movingDirection == Direction.NORTH_EAST) {
                complete = true;
                movingDirection = Direction.SOUTH_WEST;
                setCurrentDirection(movingDirection);
            } else if (movingDirection == Direction.NORTH) {
                complete = true;
                movingDirection = Direction.SOUTH;
                setCurrentDirection(movingDirection);
            } else if (movingDirection == Direction.NORTH_WEST) {
                complete = true;
                movingDirection = Direction.SOUTH_WEST;
                setCurrentDirection(movingDirection);
            } else if (movingDirection == Direction.SOUTH_EAST) {
                complete = true;
                movingDirection = Direction.SOUTH_WEST;
                setCurrentDirection(movingDirection);
            }
        } else {
            complete = false;
            movingDirection = movementDirection(this.position.getAngle());
        }
        //if the player in attack range then attack player
        if (this.position.isCloseEnoughToBeTheSameByDistance(destination, ATTACK_RANGE)) {
            if (period <= ATTACK_FREQUENCY) {
                period++;
            } else {
                period = 0;
                player.setHurt(true);
                player.hurt(this.getDamage());
            }

        }
    }


    /**
     * Turing attack model to normal model
     */
    public void resetFeeling() {
        if (angerTimeAccount < 10) {
            angerTimeAccount++;
        } else {
            angerTimeAccount = 0;
            this.setAttacked(false);
        }
    }

    /**
     * under normal situation the enemy will random wandering in 100 radius circle
     */
    public void randomMoving() {
        if (!attacking) {
            movingDirection = movementDirection(this.position.getAngle());

            if (moving == false) {
                sound.loopSound("stoneWalk");
                targetPosition = new float[2];
                //random movement range
                targetPosition[0] = (float) (Math.random() * 100 + orginalPosition[0]);
                targetPosition[1] = (float) (Math.random() * 100 + orginalPosition[1]);
                float[] randomPositionWorld = WorldUtil.worldCoordinatesToColRow(targetPosition[0], targetPosition[1]);
                destination = new HexVector(randomPositionWorld[0], randomPositionWorld[1]);
                moving = true;
            }

            if (destination.getCol() == this.getCol() && destination.getRow() == this.getRow()) {
                moving = false;
                sound.stopSound("stoneWalk");
            }
            this.position.moveToward(destination, this.getSpeed());
        }
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
     * @return string representation of this class including its enemy type, biome and x,y coordinates
     */
    @Override
    public String toString() {
        return String.format("%s at (%d, %d) %s biome", getEnemyType(), (int) getCol(), (int) getRow(), getBiome());
    }

    public Direction getMovingDirection() {
        return movingDirection;
    }

    //to count dead time
    private int time = 0;

    /**
     * if this enemy is dead then will show dead texture for a while
     */
    private void stoneDead() {
        sound.stopSound("stoneWalk");
        this.moving = true;
        this.destination = new HexVector(this.getCol(), this.getRow());
        if (time <= 100) {
            sound.loopSound("stoneDie");
            time++;
            setCurrentState(AnimationRole.NULL);
            this.setTexture("stoneDead");
            this.setObjectName("stoneDead");
        } else {
            GameManager.get().getWorld().removeEntity(this);
            sound.stopSound("stoneDie");
        }

    }


    /**
     * loading stone animations
     */
    @Override
    public void configureAnimations() {
        this.addAnimations(
                AnimationRole.MOVE, Direction.NORTH, new AnimationLinker("stoneJN", AnimationRole.MOVE, Direction.NORTH,
                        true, true));

        this.addAnimations(
                AnimationRole.MOVE, Direction.NORTH_EAST, new AnimationLinker("stoneJNE", AnimationRole.MOVE, Direction.NORTH_EAST,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.NORTH_WEST, new AnimationLinker("stoneJNW", AnimationRole.MOVE, Direction.NORTH_WEST,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.SOUTH, new AnimationLinker("stoneJS", AnimationRole.MOVE, Direction.SOUTH,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.SOUTH_EAST, new AnimationLinker("stoneJSE", AnimationRole.MOVE, Direction.SOUTH_EAST,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.SOUTH_WEST, new AnimationLinker("stoneJSW", AnimationRole.MOVE, Direction.SOUTH_WEST,
                        true, true));

        this.addAnimations(
                AnimationRole.MELEE, Direction.NORTH_WEST, new AnimationLinker("stoneANW", AnimationRole.MELEE, Direction.NORTH_WEST,
                        true, true));
        this.addAnimations(
                AnimationRole.MELEE, Direction.SOUTH, new AnimationLinker("stoneAS", AnimationRole.MELEE, Direction.SOUTH,
                        true, true));
        this.addAnimations(
                AnimationRole.MELEE, Direction.SOUTH_EAST, new AnimationLinker("stoneASE", AnimationRole.MELEE, Direction.SOUTH_EAST,
                        true, true));
        this.addAnimations(
                AnimationRole.MELEE, Direction.SOUTH_WEST, new AnimationLinker("stoneASW", AnimationRole.MELEE, Direction.SOUTH_WEST,
                        true, true));

    }

    @Override
    public void setDirectionTextures() {
    }
}

