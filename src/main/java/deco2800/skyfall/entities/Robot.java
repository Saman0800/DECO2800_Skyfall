package deco2800.skyfall.entities;


import com.badlogic.gdx.graphics.g2d.Animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.TextureManager;


public class Robot extends Enemy {
    private TextureManager textureManager = GameManager.getManagerFromInstance(TextureManager.class);

    private String [] directions={"S","SE","NE","N","NW","SW"};
    //savage animation
    private Animation<TextureRegion> animation;
    private TextureRegion[] textureRegions;


    public Robot(float row, float col, String texturename, String ObjectName) {
        super(row, col, texturename, ObjectName);
        this.loadAnimationSource();
        this.makeAimation();

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


    /**
     * make an Animation
     */
    private void makeAimation(){
        animation=new Animation<TextureRegion>(1/1f,textureRegions);
    }

    /**
     *  To get the savage animation
     * @return the animation of savage
     */
    public Animation getAnimation(){
        return animation;
    }





}
