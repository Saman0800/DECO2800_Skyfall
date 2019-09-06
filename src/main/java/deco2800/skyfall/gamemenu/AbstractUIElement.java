package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import deco2800.skyfall.managers.AnimationManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.managers.TextureManager;

public abstract class AbstractUIElement {
    Texture[] textures;
    Stage stage;
    TextureManager tm;
    public abstract void updatePosition();

    public void updateElement() {
        updatePosition();
    }

    public abstract void draw() ;

    public abstract void hideElement();

    private void getTextures(String[] textureName, TextureManager textureManager) {
        if (textureManager == null) {
            System.out.println("Texture manager is null (fine for testing)");
            return;
        }

        for (int i = 0; i < textureName.length; i++) {
            this.textures[i] = textureManager.getTexture(textureName[i]);
        }
    }

    public AbstractUIElement(Stage stage, String[] textureNames, TextureManager tm) {
        textures = new Texture[textureNames.length];
        this.getTextures(textureNames, tm);
        this.tm = tm;
        this.stage = stage;
    }


}
