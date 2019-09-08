package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.scenes.scene2d.Stage;
import deco2800.skyfall.managers.TextureManager;

public abstract class AbstractPopUpElement extends AbstractUIElement {
    public abstract void updatePosition();
    public abstract void update();


    public void exit() {

    }


    public AbstractPopUpElement(Stage stage, String[] textureNames, TextureManager tm) {
        super(stage, textureNames, tm);
    }
}
