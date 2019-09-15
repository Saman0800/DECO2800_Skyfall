package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import deco2800.skyfall.managers.TextureManager;

public abstract class AbstractUIElement {
    protected TextureRegionDrawable[] textures;
    protected Stage stage;
    protected TextureManager tm;


    public abstract void updatePosition();

    public void update() {
        updatePosition();
    }

    public abstract void draw() ;

    //public abstract void hideElement();

    private void getTextures(String[] textureName, TextureManager textureManager) {
        if (textureManager == null) {
//            System.out.println("Texture manager is null (fine for testing)");
            return;
        }

        for (int i = 0; i < textureName.length; i++) {
            this.textures[i] = new TextureRegionDrawable((new TextureRegion(textureManager.getTexture(textureName[i]))));
        }
    }

    public AbstractUIElement(Stage stage, String[] textureNames, TextureManager tm) {
        this.tm = tm;
        if (textureNames != null) {
            textures = new TextureRegionDrawable[textureNames.length];
            this.getTextures(textureNames, tm);
        }
        this.stage = stage;
    }

    public AbstractUIElement() {

    }

    //Gets centre of the screen
    //TODO: Implement this.
    protected void getCentre(int[] centreCoords) {

    }

}
