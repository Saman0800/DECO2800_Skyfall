package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import deco2800.skyfall.managers.TextureManager;

public class FeedbackBar extends AbstractUIElement {

    //Current skin
    private final Skin skin;

    //Text in feedback bar
    private Label feedback;

    public FeedbackBar(Stage stage, String[] textureNames, TextureManager tm, Skin skin) {
        super(stage, textureNames, tm);
        this.skin = skin;
    }

    @Override
    public void updatePosition() {
        //to be implemented
    }

    @Override
    public void update() {
        //to be implemented
    }

    @Override
    public void draw() {
        //to be implemented
    }
}
