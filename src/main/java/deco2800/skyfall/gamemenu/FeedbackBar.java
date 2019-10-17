package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;

public class FeedbackBar extends AbstractUIElement {

    //Game menu manager
    private final GameMenuManager gmm;

    //Current skin
    private final Skin skin;

    //Feedback text
    private Label feedback;

    //Feedback bar
    private Table feedbackBar;

    public FeedbackBar(Stage stage, String[] textureNames, TextureManager tm, Skin skin, GameMenuManager gmm) {
        super(stage, textureNames, tm);
        this.gmm = gmm;
        this.skin = skin;
    }

    @Override
    public void updatePosition() {
        feedbackBar.setPosition(gmm.getBottomRightX() - 300, gmm.getBottomRightY());
    }

    @Override
    public void update() {
        //to be implemented
    }

    @Override
    public void draw() {
        feedbackBar = new Table();
        feedbackBar.setBackground(gmm.generateTextureRegionDrawableObject("feedback_bar"));
        feedbackBar.setSize(500, 65);

        feedback = new Label("", skin, "white-text");
        feedback.setFontScale(0.8f);
        feedbackBar.add(feedback).padBottom(10);
        stage.addActor(feedbackBar);
    }
}
