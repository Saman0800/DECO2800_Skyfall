package deco2800.skyfall.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Spider extends Enemy {
    //savage animation
    private Animation<TextureRegion> animation;

    //the animation resource
    private TextureAtlas textureAtlas;

    public Spider(float row, float col, String textureName, String ObjectName) {
        super(row, col, textureName, ObjectName);
        textureAtlas=new TextureAtlas(Gdx.files.internal("resources/spiderSheet/SpiderAnimation.atlas"));
        animation=new Animation<TextureRegion>(1f/2f,textureAtlas.getRegions());
    }

    /**
     * To get spider Animation
     * @return the animation of spider
     */
    public Animation getAnimation(){
        return animation;
    }

}
