package deco2800.skyfall.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.tasks.MovementTask;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;

import java.util.Map;

import static deco2800.skyfall.managers.GameManager.get;

public class Spider extends EnemyEntity {
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

    //the animation resource
    private TextureAtlas textureAtlas;

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

    /**
     * To get spider Animation
     * @return the animation of spider
     */
    public Animation getAnimation(){
        return animation;
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
//        if (task != null && task.isAlive()) {
//            task.onTick(i);
//
//            if (task.isComplete()) {
//                this.task = null;
//            }
//        }
//        if(period<=50){
//            period++;
//        }else{
//            period=0;
//            randomMoving();
//        }
    }
//    private void randomMoving(){
//        Map neighbourTiles=GameManager.get().getWorld().getTile(this.originalCol,this.orriginalRow).getNeighbours();
//        Tile targetTile=(Tile) neighbourTiles.get((int)(Math.random()*neighbourTiles.size()));
//        System.out.println(targetTile.getCol()+","+targetTile.getRow());
//        if(moved==false){
//            this.task = new MovementTask(this, new HexVector(targetTile.getCol(),targetTile.getRow()));
//            this.setRow(targetTile.getRow());
//            this.setCol(targetTile.getCol());
//        }else{
//            this.task = new MovementTask(this, new HexVector(originalCol,orriginalRow));
//            this.setRow(this.orriginalRow);
//            this.setCol(this.originalCol);
//        }


    /**
     * @return string representation of this class including its enemy type, biome and x,y coordinates
     */
    @Override
    public String toString() {
        return String.format("%s at (%d, %d) %s biome", getEnemyType(),(int) getCol(),(int) getRow(),getBiome());
    }

}
