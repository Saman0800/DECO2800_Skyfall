//package deco2800.skyfall.entities;
//
//import deco2800.skyfall.animation.Animatable;
//import deco2800.skyfall.animation.AnimationLinker;
//import deco2800.skyfall.animation.AnimationRole;
//import deco2800.skyfall.animation.Direction;
//import deco2800.skyfall.managers.GameManager;
//import deco2800.skyfall.util.HexVector;
//import deco2800.skyfall.util.WorldUtil;
//
//public class Tiger extends EnemyEntity implements Animatable {
//    //The health of tiger
//    private static final transient int HEALTH = 10;
//    //The attack range of tiger
//    private static final transient float ATTACK_RANGE = 1f;
//    //The attack speed of tiger
//    private static final transient float RUNAWAYSPEED = 5f;
//    //The speed of tiger if it is angry and attack
//    private static final transient float ANGRYSPEED = 0.03f;
//    //The normal speed of tiger, if it is not in attack
//    private static final transient float NORMALSPEED = 0.01f;
//    //The speed of tiger, if it get injure
//    private static final transient float INJURESPEED = 0.00001f;
//    private static final transient float INJURE_ANGRY_SPEED = 0.00005f;
//    //The attack frequency of tiger
//    private static final transient int ATTACK_FREQUENCY = 50;
//    //The biome of tiger
//    private static final transient String BIOME = "forest";
//    //Moving direction
//    private Direction movingDirection;
//    //Set boolean moving
//    private boolean moving = false;
//    //Set the period equal to zero , to account attack time
//    private int period = 0;
//    //Set the type
//    private static final transient String PET_TYPE = "tiger";
//    //savage animation
//    private MainCharacter mc;
//    private boolean attackStatus = false;
//    //if the pet is attacked by enemies or the enemy closed enough to the
//    // pet, then the pet will be in angry situation
//    private int angerTimeAccount = 0;
//    //To indicate whether the enemy arrives pet's location
//    private boolean complete = false;
//    //a routine for destination
//    private HexVector destination = null;
//
//    //target position
//    private float[] targetPosition = null;
//
//    //world coordinate of this pet
//    private float[] orginalPosition = WorldUtil.colRowToWorldCords
//            (this.getCol(), this.getRow());
//
//
//    public Tiger(float row, float col, String texturename,
//                   int health, int armour, int damage) {
//        super(row, col, texturename, health, armour, damage);
//    }
//    /**
//     * Initialization value of pet tiger, and set the initial image in
//     * the game
//     */
//    public Tiger(float col, float row, MainCharacter mc) {
//        super(col, row);
//        this.setTexture("petTiger");
//        this.setObjectName("petTiger");
//        this.setHeight(5);
//        this.setHealth(HEALTH);
//        this.setLevel(2);
//        this.setSpeed(2);
//        this.setArmour(5);
//        this.setDamage(1);
//        this.mc = mc;
//        this.setDirectionTextures();
//        this.configureAnimations();
//    }
//
//    /**
//     * Initialization value of pet tiger
//     */
//    public Tiger(float col, float row) {
//        super(col, row);
//        this.setTexture("petTiger");
//        this.setObjectName("petTiger");
//        this.setHeight(5);
//        this.setHealth(HEALTH);
//        this.setLevel(2);
//        this.setSpeed(2);
//        this.setArmour(5);
//    }
//
//
//    /**
//     * get pet type
//     * @return pet type
//     */
//    public String getPetType() {
//        return PET_TYPE;
//    }
//
//    /**
//     * get pet moving
//     * @return boolean moving
//     */
//    public boolean getMoving() {
//        return moving;
//    }
//
//    /**
//     * get biome
//     * @return string of biome
//     */
//    public String getBiome() {
//        return BIOME;
//    }
//
//    /**
//     * get the attack status of pet tiger
//     * @param  status - boolean value
//     */
//    public void SetAttackStatus(boolean status) {
//        this.attackStatus = status;
//
//    }
//
//    /**
//     * Return true, if the pet tiger get injure. Otherwise return false
//     * @return True if get injure, false otherwise
//     */
//    public boolean getInjure() {
//        if (this.getHealth() < 5) {
//            return true;
//        }
//        return false;
//    }
//
//
//    /**
//     * Return the string
//     * @return string representation of this class including its pet type,
//     * biome and x,y coordinates
//     */
//    @Override
//    public String toString() {
//        return String.format("%s at (%d, %d) %s biome", getPetType(),
//                (int) getCol(), (int) getRow(), getBiome());
//    }
//
//
//    /**
//     * If the character is not close to the pet tiger, this pet will does the
//     * random movement. If the the character is closing to the tiger, the tiger
//     * will run away, and if the character attack and capture the tiger, this
//     * tiger will be a pet of master
//     *
//     */
//    @Override
//    public void onTick(long i) {
//        this.setCollider();
//        randomMoving();
//        setCurrentState(AnimationRole.MOVE);
//        if (isDead() == true) {
//            this.tigerDead();
//        } else {
//            float colDistance = mc.getCol() - this.getCol();
//            float rowDistance = mc.getRow() - this.getRow();
//            if(getHealth() == 10){
//                if ((colDistance * colDistance + rowDistance * rowDistance) < 4)
//                {
//                    this.SetAttackStatus(true);
//                    runAway();
//                }else {
//                    randomMoving();
//                }
//            }
//            else{
//                followPlayer(mc);
//                this.SetAttackStatus(false);
//                setCurrentState(AnimationRole.MOVE);
//                if (getInjure() == true) {
//                    this.position.moveToward(destination,this.INJURESPEED);
//
//                } else {
//                    this.position.moveToward(destination,this.NORMALSPEED);
//                }
//            }
//        }
//
//    }
//
//    /**
//     * Give a location to the pet tiger, if it wants to run away
//     */
//    public void runAway(){
//        targetPosition = new float[2];
//        targetPosition[0] = (float)
//                (Math.random() * 700 + orginalPosition[0]);
//        targetPosition[1]=(float)
//                (Math.random() * 700 + orginalPosition[1]);
//        float[] randomPositionWorld = WorldUtil.worldCoordinatesToColRow
//                (targetPosition[0], targetPosition[1]);
//        destination = new HexVector(randomPositionWorld[0],
//                randomPositionWorld[1]);
//        this.position.moveToward(destination,this.RUNAWAYSPEED);
//    }
//
//
//
//
//    /**
//     * get the moving direction
//     * @return moving direction
//     */
//    public Direction getMovingDirection(){
//        return movingDirection;
//    }
//
//    /**
//     * Make the pet tiger do the random movement
//     *
//     */
//    private void randomMoving() {
//        if(moving==false){
//            targetPosition = new float[2];
//            targetPosition[0] = (float)
//                    (Math.random() * 200 + orginalPosition[0]);
//            targetPosition[1]=(float)
//                    (Math.random() * 200 + orginalPosition[1]);
//            float[] randomPositionWorld = WorldUtil.worldCoordinatesToColRow
//                    (targetPosition[0], targetPosition[1]);
//            destination = new HexVector(randomPositionWorld[0],
//                    randomPositionWorld[1]);
//            moving = true;
//        }
//        if(destination.getCol() == this.getCol() &&
//                destination.getRow() == this.getRow()){
//            moving = false;
//        }
//        if (getInjure() == true) {
//            this.position.moveToward(destination,this.INJURESPEED);
//        }
//        this.position.moveToward(destination,this.NORMALSPEED);
//
//    }
//
//    /**
//     * The tiger will follow the player
//     *
//     */
//    public void followPlayer(MainCharacter player){
//        destination = new HexVector(player.getCol(), player.getRow());
//        this.position.moveToward(destination, this.getSpeed());
//
//    }
//
//
//    /**
//     * if this pet is dead then will show dead texture for a while
//     */
//    int time=0;
//    private void tigerDead(){
//        if(time<=100){
//            time++;
//            this.setTexture("tigerDead");
//            this.setObjectName("tigerDead");
//            setCurrentState(AnimationRole.NULL);
//        }else{
//            GameManager.get().getWorld().removeEntity(this);
//
//        }
//
//    }
//
//    /**
//     * add pet tiger animations
//     */
//    @Override
//    public void configureAnimations() {
//        this.addAnimations(
//                AnimationRole.MOVE, Direction.DEFAULT, new AnimationLinker
//                        ("tigerFront", AnimationRole.MOVE, Direction.DEFAULT,
//                                true, true));
//
//    }
//
//    /**
//     * Set the direction textures of the pet tiger
//     */
//    @Override
//    public void setDirectionTextures() {
//
//    }
//
//
//
//}