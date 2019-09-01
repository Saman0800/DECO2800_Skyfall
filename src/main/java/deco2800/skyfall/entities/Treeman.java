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
    private static final transient int HEALTH = 10;
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
    //if the enemy is attacked by player or the player closed enough to the enemy
    //than the enemy my will be in angry situation
    private int angerTimeAccount = 0;

    //a routine for destination
    private HexVector destination = null;

    //target position
    private float[] targetPosition = null;

    //world coordinate of this enemy
    private float[] orginalPosition = WorldUtil.colRowToWorldCords(this.getCol(), this.getRow());


    public Treeman(float row, float col, String texturename, int health, int armour, int damage) {
        super(row, col, texturename, health, armour, damage);
    }

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


    public String getEnemyType() {
        return ENEMY_TYPE;
    }


    public boolean getMoving() {
        return moving;
    }

    public String getBiome() {
        return BIOME;
    }

    public void SetAttackStatus(boolean status) {
        this.attackStatus = status;

    }

    public boolean getInjure() {
        if (getHealth() < 5) {
            return true;
        }
        return false;
    }


    /**
     * @return string representation of this class including its enemy type, biome and x,y coordinates
     */
    @Override
    public String toString() {
        return String.format("%s at (%d, %d) %s biome", getEnemyType(), (int) getCol(), (int) getRow(), getBiome());
    }

    @Override
    public void onTick(long i) {
        this.setCollider();
        if (this.attackStatus == false) {
            randomMoving();
            //movingDirection=movementDirection(this.position.getAngle());
        }

        /**if(angerTimeAccount<10){
         angerTimeAccount++;
         }else{
         angerTimeAccount=0;
         this.setAttacked(false);
         }**/
        if (isDead() == true) {
            this.treemanDead();
        } else {
            float colDistance = mc.getCol() - this.getCol();
            float rowDistance = mc.getRow() - this.getRow();
            if ((colDistance * colDistance + rowDistance * rowDistance) < 4 || this.isAttacked() == true) {
                this.SetAttackStatus(true);
                this.attackPlayer(mc);
                setCurrentState(AnimationRole.MELEE);
                //if(this.getMovingDirection()==Direction.NORTH|| this.getMovingDirection()==Direction.NORTH_EAST){
                // setCurrentDirection(movementDirection(this.position.getAngle()));
                //setCurrentState(AnimationRole.MOVE);

                //}else {
                // setCurrentDirection(movementDirection(this.position.getAngle()));
                // setCurrentState(AnimationRole.MELEE);
                //}
            } else {
                this.SetAttackStatus(false);
                setCurrentState(AnimationRole.MOVE);
                if (getInjure() == true) {
                    this.setSpeed(INJURESPEED);
                    //setCurrentDirection(movementDirection(this.position.getAngle()));
                    setCurrentState(AnimationRole.MOVE);
                } else {
                    this.setSpeed(NORMALSPEED);
                    //setCurrentDirection(movementDirection(this.position.getAngle()));
                    setCurrentState(AnimationRole.MOVE);
                }


            }
        }
    }





    public Direction getMovingDirection(){
        return movingDirection;
    }


    private void randomMoving() {
        if(moving==false){
            targetPosition =new float[2];
            targetPosition[0]=(float) (Math.random() * 800 + orginalPosition[0]);
            targetPosition[1]=(float) (Math.random() * 800 + orginalPosition[1]);
            float[] randomPositionWorld = WorldUtil.worldCoordinatesToColRow(targetPosition[0], targetPosition[1]);
            destination=new HexVector(randomPositionWorld[0], randomPositionWorld[1]);
            moving=true;
        }
        if(destination.getCol()==this.getCol() && destination.getRow()==this.getRow()){
            moving=false;
        }
        this.position.moveToward(destination,this.NORMALSPEED);

    }

    public void attackPlayer(MainCharacter player){
        this.setSpeed(ARGRYSPEED);
        destination=new HexVector(player.getCol(),player.getRow());
        this.position.moveToward(destination,this. ARGRYSPEED);
        //movingDirection=movementDirection(this.position.getAngle());
        if(this.position.isCloseEnoughToBeTheSameByDistance(destination,ATTACK_RANGE)){
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


    int time=0;
    private void treemanDead(){
        if(time<=100){
            time++;
            setCurrentState(AnimationRole.NULL);
            this.setTexture("treemanDead");
            this.setObjectName("treemanDead");
        }else{
            GameManager.get().getWorld().removeEntity(this);

        }

    }

    @Override
    public void configureAnimations() {
        this.addAnimations(
                AnimationRole.MELEE,
                Direction.SOUTH,
                new AnimationLinker("treeman_defence",
                        AnimationRole.MELEE, Direction.SOUTH,
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
                Direction.SOUTH,
                new AnimationLinker("treeman_dead",
                        AnimationRole.MOVE, Direction.SOUTH,
                        true, true));
    }

    @Override
    public void setDirectionTextures() {

    }



}
