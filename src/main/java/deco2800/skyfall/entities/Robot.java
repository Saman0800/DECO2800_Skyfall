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
    private Animation<TextureRegion> animation;
    private TextureRegion[] textureRegions;

    public Robot(float col, float row) {
        super(col,row);
        this.setTexture("robot");
        this.setObjectName("robot");
        this.setHeight(1);
        this.setHealth((int)HEALTH);
        this.loadAnimationSource();
        animation=new Animation<TextureRegion>(1/1f,textureRegions);
    }

    /**
     * load png image from resources folder and save those images to TextureRegions array
     */
    private void loadAnimationSource(){
        int index=0;
        textureRegions=new TextureRegion[directions.length];
        for(int i=0;i<directions.length;i++){
            String textureName="robot"+directions[i];
            TextureRegion tmeTextureRegion=new TextureRegion(textureManager.getTexture(textureName));
            textureRegions[index++]=tmeTextureRegion;
        }
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
     *  To get the savage animation
     * @return the animation of savage
     */
    public Animation getAnimation(){
        return animation;
    }

    /**
     * @return string representation of this class including its enemy type, biome and x,y coordinates
     */
    @Override
    public String toString() {
        return String.format("%s at (%d, %d) %s biome", getEnemyType(), getCol(), getRow(),getBiome());
    }



}
