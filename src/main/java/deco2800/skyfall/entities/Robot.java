package deco2800.skyfall.entities;


import com.badlogic.gdx.graphics.g2d.Animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.TextureManager;


public class Robot extends EnemyEntity {
    private TextureManager textureManager = GameManager.getManagerFromInstance(TextureManager.class);
    private static final transient float HEALTH = 20f;
    private static final transient float ATTACK_RANGE = 1f;
    private static final transient int ATTACK_SPEED = 1000;
    private static final transient String BIOME="";
    private boolean moving=false;
    private static final transient String ENEMY_TYPE="robot";
    private String [] directions={"S","SE","NE","N","NW","SW"};
    //savage animation

    public Robot(float row, float col, String texturename, int health, int armour, int damage) {
        super(row, col, texturename, health, armour, damage);
    }

    public Robot(float col, float row) {
        super(col,row);
        this.setTexture("robot");
        this.setObjectName("robot");
        this.setHeight(1);
        this.setHealth((int)HEALTH);
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
        return String.format("%s at (%d, %d) %s biome", getEnemyType(), getCol(), getRow(),getBiome());
    }



}
