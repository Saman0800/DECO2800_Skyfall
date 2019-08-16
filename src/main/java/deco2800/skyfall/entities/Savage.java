package deco2800.skyfall.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Savage extends Enemy {
    //savage animation
    private Animation<TextureRegion> animation;

    //the animation resource
    private TextureAtlas textureAtlas;

    public Savage(float row, float col, String texturename, String ObjectName) {
        super(row, col, texturename, ObjectName);
        textureAtlas=new TextureAtlas(Gdx.files.internal("resources/savageSheet/myamimation.atlas"));
        animation=new Animation<TextureRegion>(1f/2f,textureAtlas.getRegions());
    }

    /**
     *  To get the savage animation
     * @return the animation of savage
     */
    public Animation getAnimation(){
        return animation;
    }
}
