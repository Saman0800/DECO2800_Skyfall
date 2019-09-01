package deco2800.skyfall.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.SoundManager;
import deco2800.skyfall.tasks.MovementTask;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.Tile;

import java.util.Map;

import static deco2800.skyfall.managers.GameManager.get;

public class Spider extends EnemyEntity implements Animatable {
    private static final transient int HEALTH = 10;
    private static final transient float ATTACK_RANGE = 0.5f;
    private static final transient int ATTACK_SPEED = 2000;
    private static final transient String BIOME="forest";
    private boolean moving=false;
    private float originalCol;
    private float orriginalRow;
    private boolean moved=false;
    private static final transient String ENEMY_TYPE="spider";

    //savage animation
    private Animation<TextureRegion> animation;

    //Insert SoundManager class
    private SoundManager sound = new SoundManager();

    //the animation resource
    private TextureAtlas textureAtlas;
    private MainCharacter mc;
    public Spider(float col, float row, MainCharacter mc) {
        super(col, row);
        this.originalCol=col;
        this.orriginalRow=row;
        this.setTexture("spider");
        this.setObjectName("spider");
        this.setHeight(1);
        this.setHealth(HEALTH);
        this.setLevel(1);
        this.setSpeed(1);
        this.setArmour(1);
        this.mc = mc;
        this.configureAnimations();
        this.setDirectionTextures();
    }

    public Spider(float col, float row) {
        super(col, row);
        this.originalCol=col;
        this.orriginalRow=row;
        this.setTexture("spider");
        this.setObjectName("spider");
        this.setHeight(1);
        this.setHealth(HEALTH);
        this.setLevel(1);
        this.setSpeed(1);
        this.setArmour(1);
    }
    public Spider(float row, float col, String texturename, int health, int armour, int damage) {
        super(row, col, texturename, health, armour, damage);
    }



    public String getEnemyType(){
        return ENEMY_TYPE;
    }

    /**
     * To determine whether this enemy can move
     * @return boolean value moving
     */
    public boolean getMoving(){
        return moving;
    }

    public String getBiome(){
        return BIOME;
    }

    int period=0;
    /**
     * Handles tick based stuff, e.g. movement
     */
    @Override
    public void onTick(long i) {
        if(this.isDead()==true){
            GameManager.get().getWorld().removeEntity(this);
        }
        super.onTick(i);
        if (mc != null) {
            float colDistance = mc.getCol() - this.getCol();
            float rowDistance = mc.getRow() - this.getRow();

            if ((colDistance * colDistance + rowDistance * rowDistance) < 4) {
                sound.loopSound("spider");
                this.setCurrentState(AnimationRole.DEFENCE);
            } else {
                sound.stopSound("spider");
                this.setCurrentState(AnimationRole.NULL);
            }
        } else {
            System.out.println("MainCharacter is null");
        }
    }

    private void randomMoving() {

        if (this.getCol() == this.originalCol && this.getRow() == this.orriginalRow) {
            float[] currentPosition = WorldUtil.colRowToWorldCords(this.getCol(), this.getRow());
            float[] randomPosition = {(float) (Math.random() * 100 + currentPosition[0]), (float) (Math.random() * 100 + currentPosition[1])};
            float[] randomPositionWorld = WorldUtil.worldCoordinatesToColRow(randomPosition[0], randomPosition[1]);
            this.task = new MovementTask(this, new HexVector(randomPositionWorld[0], randomPositionWorld[1]));
        } else {
            this.task = new MovementTask(this, new HexVector(this.originalCol, this.orriginalRow));
        }


    }


    @Override
    public void configureAnimations() {
        this.addAnimations(
                AnimationRole.DEFENCE,
                Direction.DEFAULT,
                new AnimationLinker("spider_defence",
                        AnimationRole.MOVE, Direction.DEFAULT,
                        true, true));
    }

    @Override
    public void setDirectionTextures() {

    }

    /**
     * @return string representation of this class including its enemy type, biome and x,y coordinates
     */
    @Override
    public String toString() {
        return String.format("%s at (%d, %d) %s biome", getEnemyType(),(int) getCol(),(int) getRow(),getBiome());
    }

}
