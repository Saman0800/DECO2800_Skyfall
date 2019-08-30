package deco2800.skyfall.entities;

import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;

public class Stone extends EnemyEntity {
    private static final transient int HEALTH = 30;
    private static final transient float NORMALSPEED = 0.01f;
    private static final transient float ARGRYSPEED = 0.03f;
    private static final transient float ATTACK_RANGE = 3f;
    private static final transient int ATTACK_FREQUENCY = 50;
    private static final transient String BIOME = "forest";
    private boolean moving = false;
    private float originalCol;
    private float orriginalRow;
    private String movingDirection;
    private boolean moved = false;
    private static final transient String ENEMY_TYPE = "stone";
    private boolean attacking=false;




    public Stone(float col, float row) {
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
    }


    public Stone(float row, float col, String texturename, int health, int armour, int damage) {
        super(row, col, texturename, health, armour, damage);
    }


    public String getEnemyType() {
        return ENEMY_TYPE;
    }

    /**
     * To determine whether this enemy can move
     *
     * @return boolean value moving
     */
    public boolean getMoving() {
        return moving;
    }

    public String getBiome() {
        return BIOME;
    }

    int period = 0;

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }



    private int angerTimeAccount=0;
    /**
     * Handles tick based stuff, e.g. movement
     */
    @Override
    public void onTick(long i) {
        if(isDead()==true){
            this.stoneDead();
        }

        if(angerTimeAccount<10){
            angerTimeAccount++;
        }else{
            angerTimeAccount=0;
            this.setAttacked(false);
        }
        if(!attacking){
            randomMoving();
            movingDirection=movementDirection(this.position.getAngle());
        }

    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    HexVector destination=null;
    float[] targetPosition=null;
    float[] orginalPosition = WorldUtil.colRowToWorldCords(this.getCol(), this.getRow());
    private void randomMoving() {
        if(moving==false){
            targetPosition =new float[2];
            targetPosition[0]=(float) (Math.random() * 100 + orginalPosition[0]);
            targetPosition[1]=(float) (Math.random() * 100 + orginalPosition[1]);
            float[] randomPositionWorld = WorldUtil.worldCoordinatesToColRow(targetPosition[0], targetPosition[1]);
            destination=new HexVector(randomPositionWorld[0], randomPositionWorld[1]);
            moving=true;
        }
        if(destination.getCol()==this.getCol() && destination.getRow()==this.getRow()){
            moving=false;
        }
        this.position.moveToward(destination,this.getSpeed());

    }
    public void attackPlayer(MainCharacter player){
        this.setSpeed(ARGRYSPEED);
        destination=new HexVector(player.getCol(),player.getRow());
        this.position.moveToward(destination,this.getSpeed());
        movingDirection=movementDirection(this.position.getAngle());
        if(this.position.isCloseEnoughToBeTheSameByDistance(destination,ATTACK_RANGE)){
            if(period <=ATTACK_FREQUENCY){
                period++;
            }else{
                period=0;
                player.changeHealth(-this.getDamage());
            }

        }
    }

    public String movementDirection(double angle){
        angle=Math.toDegrees(angle-Math.PI);
        if (angle<0){
            angle+=360;
        }
        if(angle>=0 && angle<=60){
            return "SW";
        }else if(angle>60 && angle<=120){
            return "S";
        }else if (angle>120 && angle<=180){
            return  "SE";
        }else if (angle>180 && angle<=240){
            return  "NE";
        }else if (angle>240 && angle<=300){
            return  "N";
        }else if (angle>300 && angle <360){
            return "NW";
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

    public String getMovingDirection(){
        return movingDirection;
    }

    int time=0;
    private void stoneDead(){
        this.moving=true;
        this.destination=new HexVector(this.getCol(),this.getRow());
        if(time<=100){
            time++;
            this.setTexture("stoneDead");
            this.setObjectName("stoneDead");
        }else{
            GameManager.get().getWorld().removeEntity(this);

        }

    }

}
