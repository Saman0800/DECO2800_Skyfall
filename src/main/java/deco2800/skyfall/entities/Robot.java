package deco2800.skyfall.entities;



import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.SoundManager;

public class Robot extends EnemyEntity implements Animatable {
    private static final transient int HEALTH = 10;
    private static final transient float ATTACK_RANGE = 1f;
    private static final transient int ATTACK_SPEED = 1000;
    private static final transient String BIOME="forest";
    private boolean moving=false;
    private static final transient String ENEMY_TYPE="robot";
    //savage animation
    private MainCharacter mc;

    //Insert SoundManager class
    private SoundManager sound = new SoundManager();
    public Robot(float row, float col, String texturename, int health, int armour, int damage) {
        super(row, col, texturename, health, armour, damage);
    }

    public Robot(float col, float row, MainCharacter mc) {
        super(col,row);
        this.setTexture("robot");
        this.setObjectName("robot");
        this.setHeight(1);
        this.setHealth(HEALTH);
        this.setLevel(2);
        this.setSpeed(1);
        this.setArmour(2);
        this.mc = mc;
        this.configureAnimations();
        this.setDirectionTextures();
    }

    public Robot(float col, float row) {
        super(col,row);
        this.setTexture("robot");
        this.setObjectName("robot");
        this.setHeight(1);
        this.setHealth(HEALTH);
        this.setLevel(2);
        this.setSpeed(1);
        this.setArmour(2);
    }

    public String getEnemyType(){
        return ENEMY_TYPE;
    }



    public boolean getMoving(){
        return moving;
    }

    public String getBiome(){
        return BIOME;
    }



    /**
     * @return string representation of this class including its enemy type, biome and x,y coordinates
     */
    @Override
    public String toString() {
        return String.format("%s at (%d, %d) %s biome", getEnemyType(), (int)getCol(), (int)getRow(),getBiome());
    }

    @Override
    public void onTick(long i) {
        if(this.isDead()){
            GameManager.get().getWorld().removeEntity(this);
            sound.stopSound("robot");

        }else {
            defence();
        }
        super.onTick(i);


    }

    private void defence(){
        if (mc != null) {
            float colDistance = mc.getCol() - this.getCol();
            float rowDistance = mc.getRow() - this.getRow();

            if ((colDistance * colDistance + rowDistance * rowDistance) < 4) {
                sound.loopSound("robot");
                this.setCurrentState(AnimationRole.DEFENCE);
            } else {
                sound.stopSound("robot");
                this.setCurrentState(AnimationRole.NULL);
            }
        } else {
            System.out.println("Mc is null");
        }
    }

    @Override
    public void configureAnimations() {
        this.addAnimations(
            AnimationRole.DEFENCE,
            Direction.DEFAULT,
            new AnimationLinker("robot_defence",
                    AnimationRole.MOVE, Direction.DEFAULT,
                    true, true));
    }

    @Override
    public void setDirectionTextures() {

    }
}
