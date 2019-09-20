package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import deco2800.skyfall.managers.TextureManager;

/**
 * Base UI element on which all game menu elements inherit from.
 */
public abstract class AbstractUIElement {
    protected TextureRegionDrawable[] textures;
    protected Stage stage;
    protected TextureManager tm;

    /**
     * Updates the position of UI elements to dynamically fit different
     * screen sizes
     */
    public abstract void updatePosition();

    /**
     * Effectively the onTick method for ui elements. Updates the state of the
     * element if necessary.
     */
    public void update() {
        updatePosition();
    }

    /**
     * Draws the UI element
     */
    public abstract void draw() ;

    /**
     * Helper method to fill
     * @param textureName Array of string names associated with the texture
     *                    manager to get texture
     * @param textureManager The texture manager instance.
     */
    private void getTextures(String[] textureName, TextureManager textureManager) {
        if (textureManager == null) {
            return;
        }

        for (int i = 0; i < textureName.length; i++) {
            this.textures[i] = new TextureRegionDrawable((new TextureRegion(textureManager.getTexture(textureName[i]))));
        }
    }

    /**
     * Constructor
     * @param stage Stage
     * @param textureNames The array of texture names
     * @param tm Texture Manager instance
     */
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
}
