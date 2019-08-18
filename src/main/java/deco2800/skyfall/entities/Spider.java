package deco2800.skyfall.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Spider extends EnemyEntity {
    private static final transient float HEALTH = 10f;
    private static final transient float ATTACK_RANGE = 0.5f;
    private static final transient int ATTACK_SPEED = 2000;
    private static final transient String BIOME="forest";
    private boolean moving=false;
    private static final transient String ENEMY_TYPE="spider";
    //savage animation
    private Animation<TextureRegion> animation;

    //the animation resource
    private TextureAtlas textureAtlas;

    public Spider(float col, float row) {
        super(col, row);
        this.setTexture("spider");
        this.setObjectName("spider");
        this.setHeight(1);
        this.setHealth((int)HEALTH);
        textureAtlas=new TextureAtlas(Gdx.files.internal("resources/spiderSheet/SpiderAnimation.atlas"));
        animation=new Animation<TextureRegion>(1f/1f,textureAtlas.getRegions());
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

    /**
     * @return string representation of this class including its enemy type, biome and x,y coordinates
     */
    @Override
    public String toString() {
        return String.format("%s at (%d, %d) %s biome", getEnemyType(), getCol(), getRow(),getBiome());
    }

}
