package deco2800.skyfall.entities;

import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;

public class Treeman extends AbstractEnemy implements Animatable {
    //The health of treeman
    private static final transient int HEALTH = 10;
    //The attack range of treeman
    private static final transient float ATTACK_RANGE = 1f;
    //The speed of treeman if it is angry and attack
    private static final transient float ANGRYSPEED = 0.01f;
    //The speed of treeman, if it get injure
    private static final transient float INJURESPEED = 0.00001f;
    private static final transient float INJURE_ANGRY_SPEED = 0.00005f;
    //The attack frequency of treeman
    private static final transient int ATTACK_FREQUENCY = 0;
    //The biome of treeman
    private static final transient String BIOME = "forest";
    //Moving direction
    private Direction movingDirection;

    //Set the period equal to zero , to account attack time
    private int period = 0;
    //Set the type
    private static final transient String ENEMY_TYPE = "treeman";
    //savage animation
    private MainCharacter mc;
    private boolean attackStatus = false;
    //if the enemy is attacked by player or the player closed enough to the
    // enemy than the enemy will be in angry situation
    private int angerTimeAccount = 0;
    //To indicate whether the enemy arrives player's location
    private boolean complete = false;
    //a routine for destination
    private HexVector destination = null;

    /**
     * Initialization value of enemy treeman, and set the initial image in
     * the game
     */
    public Treeman(float col, float row, MainCharacter mc) {
        super(col, row, mc);
        this.setTexture("enemyTreeman");
        this.setObjectName("enemyTreeman");
        this.setHeight(5);
        this.setHealth(HEALTH);
        this.setLevel(2);
        this.setSpeed(0.01f);
        //this.setArmour(5);
        this.setDamage(1);
        this.mc = mc;
        this.enemyAnimationName = "treeman";
        this.configureAnimations();
        this.setDirectionTextures();
    }

    public Treeman(float col, float row) {
        super(col,row);
        this.setTexture("enemyTreeman");
        this.setObjectName("enemyTreeman");
        this.setHeight(1);
        this.setHealth(HEALTH);
        this.setLevel(2);
        this.setSpeed(1);
        this.enemyAnimationName = "treeman";
        this.configureAnimations();
        this.setDirectionTextures();
        // this.setArmour(2);
    }

    /**
     * get enemy type
     * @return enemy type
     */
    public String getEnemyType() {
        return ENEMY_TYPE;
    }


    /**
     * get biome
     * @return string of biome
     */
    public String getBiome() {
        return BIOME;
    }

    /**
     * get the attack status of enemy treeman
     * @param  status - boolean value
     */
    public void SetAttackStatus(boolean status) {
        this.attackStatus = status;

    }

    /**
     * Return true, if the enemy treeman get injure. Otherwise return false
     * @return True if get injure, false otherwise
     */
    public boolean getInjure() {
        return this.getHealth() < 5;
    }


    /**
     * Return the string
     * @return string representation of this class including its enemy type,
     * biome and x,y coordinates
     */
    @Override
    public String toString() {
        return String.format("%s at (%d, %d) %s biome", getEnemyType(),
                (int) getCol(), (int) getRow(), getBiome());
    }

    /**
     * The tree man will attack the player in angry speed and chase character as
     * well
     *
     */
    public void attackPlayer(MainCharacter player){
        this.setSpeed(ANGRYSPEED);
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
            } else if (movingDirection == Direction.WEST) {
                complete = true;
                movingDirection = Direction.EAST;
                setCurrentDirection(movingDirection);
            }
        } else {
            complete = false;
            movingDirection = movementDirection(this.position.getAngle());
        }
        if(this.position.isCloseEnoughToBeTheSameByDistance
                (destination,ATTACK_RANGE)){
            if(period <= ATTACK_FREQUENCY){
                period ++;
            }else{
                period = 0;
                if (!(mc.isRecovering())) {
                    player.hurt(this.getDamage());
                }
            }
            if (this.getInjure()) {
                this.position.moveToward(destination,this.INJURE_ANGRY_SPEED);
            }else{
                this.position.moveToward(destination,this.chaseSpeed);
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
     * if this enemy is dead then will show dead texture for a while
     */
    int time =0;
    private void treemanDead(){
        if (time<=100) {
            if (time == 0) {
                this.setTexture("treemanDead");
                this.setObjectName("treemanDead");
                setCurrentState(AnimationRole.DEFENCE);
                destroy();
            }
            time++;
            this.setTexture("treemanDead");
            this.setObjectName("treemanDead");
            setCurrentState(AnimationRole.NULL);
        }else{
            GameManager.get().getWorld().removeEntity(this);
        }

    }

    @Override
    public void dealDamage(ICombatEntity entity) {
        if (entity.canDealDamage()) {
            entity.dealDamage(entity);
        }
    }
}
