package deco2800.skyfall.entities;

import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;

public class Treeman extends EnemyEntity implements Animatable {
    //The health of treeman
    private static final transient int HEALTH = 3;
    //The attack range of treeman
    private static final transient float ATTACK_RANGE = 1f;
    //The attack speed of treeman
    private static final transient int ATTACK_SPEED = 1000;
    //The speed of treeman if it is angry and attack
    private static final transient float ARGRYSPEED = 0.05f;
    //The normal speed of treeman, if it is not in attack
    private static final transient float NORMALSPEED = 0.03f;
    //The speed of treeman, if it get injure
    private static final transient float INJURESPEED = 0.01f;
    //The attack frequency of treeman
    private static final transient int ATTACK_FREQUENCY = 50;
    //The biome of treeman
    private static final transient String BIOME = "forest";
    //Moving direction
    private Direction movingDirection;
    //Set boolean moving
    private boolean moving = false;
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

    //a routine for destination
    private HexVector destination = null;

    //target position
    private float[] targetPosition = null;

    //world coordinate of this enemy
    private float[] orginalPosition = WorldUtil.colRowToWorldCords
            (this.getCol(), this.getRow());


    public Treeman(float row, float col, String texturename,
                   int health, int armour, int damage) {
        super(row, col, texturename, health, armour, damage);
    }
    /**
     * Initialization value of enemy treeman, and set the initial image in
     * the game
     */
    public Treeman(float col, float row, MainCharacter mc) {
        super(col, row);
        this.setTexture("enemyTreeman");
        this.setObjectName("enemyTreeman");
        this.setHeight(5);
        this.setHealth(HEALTH);
        this.setLevel(2);
        this.setSpeed(2);
        this.setArmour(5);
        this.mc = mc;
        this.setDirectionTextures();
        this.configureAnimations();
    }

    /**
     * Initialization value of enemy treeman
     */
    public Treeman(float col, float row) {
        super(col, row);
        this.setTexture("enemyTreeman");
        this.setObjectName("enemyTreeman");
        this.setHeight(5);
        this.setHealth(HEALTH);
        this.setLevel(2);
        this.setSpeed(2);
        this.setArmour(5);
    }


    /**
     * get enemy type
     * @return enemy type
     */
    public String getEnemyType() {
        return ENEMY_TYPE;
    }

    /**
     * get enemy moving
     * @return boolean moving
     */
    public boolean getMoving() {
        return moving;
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
        if (getHealth() < 5) {
            return true;
        }
        return false;
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
     * If the enemy treem man not attack, it will do the random movement, if
     * the treeman is dead, then it is dead. Otherwise, if the main character
     * close to the treeman, the treeman will attack the character and chase
     * the character at angry speed, if the treeman get injure, it will slow
     * down the speed
     *
     */
    @Override
    public void onTick(long i) {
        getBody().setTransform(position.getCol(), position.getRow(), getBody().getAngle());

        /**if(angerTimeAccount<10){
         angerTimeAccount++;
         }else{
         angerTimeAccount=0;
         this.setAttacked(false);
         }**/
        if (isDead()) {
            this.treemanDead();
            return;
        } else {
            if (this.attackStatus == false) {
                randomMoving();
                setCurrentState(AnimationRole.MOVE);
                //movingDirection=movementDirection(this.position.getAngle());
            }

            float colDistance = mc.getCol() - this.getCol();
            float rowDistance = mc.getRow() - this.getRow();
            if ((colDistance * colDistance + rowDistance * rowDistance) < 4
                    || this.isAttacked() == true) {
                this.SetAttackStatus(true);
                this.attackPlayer(mc);
                setCurrentState(AnimationRole.MELEE);
                //if(this.getMovingDirection()==Direction.NORTH||
                // this.getMovingDirection()==Direction.NORTH_EAST){
                // setCurrentDirection(movementDirection
                // (this.position.getAngle()));
                //setCurrentState(AnimationRole.MOVE);

                //}else {
                // setCurrentDirection(movementDirection
                // (this.position.getAngle()));
                // setCurrentState(AnimationRole.MELEE);
                //}
            } else {
                this.SetAttackStatus(false);
                setCurrentState(AnimationRole.MOVE);
                if (getInjure() == true) {
                    this.setSpeed(INJURESPEED);
                    //setCurrentDirection(movementDirection
                    // (this.position.getAngle()));
                } else {
                    this.setSpeed(NORMALSPEED);
                    //setCurrentDirection(movementDirection
                    // (this.position.getAngle()));

                }


            }
        }
    }




    /**
     * get the moving direction
     * @return moving direction
     */
    public Direction getMovingDirection(){
        return movingDirection;
    }

    /**
     * Make the enemy tree man do the random movement
     *
     */
    private void randomMoving() {
        if(moving==false){
            targetPosition =new float[2];
            targetPosition[0]=(float)
                    (Math.random() * 800 + orginalPosition[0]);
            targetPosition[1]=(float)
                    (Math.random() * 800 + orginalPosition[1]);
            float[] randomPositionWorld = WorldUtil.worldCoordinatesToColRow
                    (targetPosition[0], targetPosition[1]);
            destination=new HexVector(randomPositionWorld[0],
                    randomPositionWorld[1]);
            moving=true;
        }
        if(destination.getCol()==this.getCol() &&
                destination.getRow()==this.getRow()){
            moving=false;
        }
        this.position.moveToward(destination,this.NORMALSPEED);

    }

    /**
     * The tree man will attack the player in angry speed and chase character as
     * well
     *
     */
    public void attackPlayer(MainCharacter player){
        this.setSpeed(ARGRYSPEED);
        destination=new HexVector(player.getCol(),player.getRow());
        this.position.moveToward(destination,this. ARGRYSPEED);
        //movingDirection=movementDirection(this.position.getAngle());
        if(this.position.isCloseEnoughToBeTheSameByDistance
                (destination,ATTACK_RANGE)){
            if(period <=ATTACK_FREQUENCY){
                period++;
            }else{
                period=0;
                player.changeHealth(-this.getDamage());
            }

        }
    }

    /**public Direction movementDirection(double angle){
        angle=Math.toDegrees(angle-Math.PI);
        if (angle<0){
            angle+=360;
        }
        if(angle>=0 && angle<=60){
            return Direction.SOUTH_WEST;
        }else if(angle>60 && angle<=120){
            return Direction.SOUTH;
        }else if (angle>120 && angle<=180){
            return  Direction.SOUTH_EAST;
        }else if (angle>180 && angle<=240){
            return  Direction.NORTH_EAST;
        }else if (angle>240 && angle<=300){
            return  Direction.NORTH;
        }else if (angle>300 && angle <360){
            return Direction.NORTH_WEST;
        }
        return null;

    }**/

    /**
     * if this enemy is dead then will show dead texture for a while
     */
    int time=0;
    private void treemanDead(){
        if (time<=100) {
            if (time == 0) {
                this.setTexture("treemanDead");
                this.setObjectName("treemanDead");
                setCurrentState(AnimationRole.DEFENCE);
                destroy();
            }
            time++;
        }else{
            GameManager.get().getWorld().removeEntity(this);
        }

    }

    /**
     * add treeman animations
     */
    @Override
    public void configureAnimations() {
        this.addAnimations(
                AnimationRole.MELEE,
                Direction.DEFAULT,
                new AnimationLinker("treeman_defence",
                        AnimationRole.MELEE, Direction.DEFAULT,
                        true, true));

        this.addAnimations(
                AnimationRole.MELEE,
                Direction.NORTH,
                new AnimationLinker("treeman_defence",
                        AnimationRole.MELEE, Direction.NORTH,
                        true, true));

        this.addAnimations(
                AnimationRole.MELEE,
                Direction.NORTH_EAST,
                new AnimationLinker("treeman_defence",
                        AnimationRole.MELEE, Direction.NORTH_EAST,
                        true, true));
        this.addAnimations(
                AnimationRole.MELEE,
                Direction.NORTH_WEST,
                new AnimationLinker("treeman_defence",
                        AnimationRole.MELEE, Direction.NORTH_WEST,
                        true, true));

        this.addAnimations(
                AnimationRole.MELEE,
                Direction.WEST,
                new AnimationLinker("treeman_defence",
                        AnimationRole.MELEE, Direction.WEST,
                        true, true));

        this.addAnimations(
                AnimationRole.MELEE,
                Direction.EAST,
                new AnimationLinker("treeman_defence",
                        AnimationRole.MELEE, Direction.EAST,
                        true, true));

        this.addAnimations(
                AnimationRole.MELEE,
                Direction.SOUTH_EAST,
                new AnimationLinker("treeman_defence",
                        AnimationRole.MELEE, Direction.SOUTH_EAST,
                        true, true));

        this.addAnimations(
                AnimationRole.MELEE,
                Direction.SOUTH_WEST,
                new AnimationLinker("treeman_defence",
                        AnimationRole.MELEE, Direction.SOUTH_WEST,
                        true, true));

        this.addAnimations(
                AnimationRole.MOVE,
                Direction.DEFAULT,
                new AnimationLinker("treeman_movement",
                        AnimationRole.MOVE, Direction.DEFAULT,
                        true, true));


        this.addAnimations(
                AnimationRole.MOVE,
                Direction.DEFAULT,
                new AnimationLinker("treeman_movement",
                        AnimationRole.MOVE, Direction.DEFAULT,
                        true, true));

        this.addAnimations(
                AnimationRole.DEFENCE,
                Direction.DEFAULT,
                new AnimationLinker("treeman_dead",
                        AnimationRole.DEFENCE, Direction.DEFAULT,
                        true, true));

    }

    /**
     * Set the direction textures of the enemy treeman
     */
    @Override
    public void setDirectionTextures() {

    }

    @Override
    public void dealDamage(MainCharacter player) {
        if (!player.isRecovering()) {
            player.hurt(getDamage());
        }
    }
}
