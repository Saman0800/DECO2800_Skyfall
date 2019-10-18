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
        this.draw();
    }

    @Override
    public void updatePosition() {
        feedbackBar.setPosition(gmm.getTopLeftX() + stage.getCamera().viewportWidth / 5, gmm.getBottomRightY());
    }

    @Override
    public void draw() {
        feedbackBar = new Table();
        feedbackBar.setBackground(gmm.generateTextureRegionDrawableObject("feedback_bar"));
        feedbackBar.setSize(800, 55);

        feedback = new Label("Click 'HELP' if you get stuck", skin, "white-text");
        feedback.setFontScale(0.8f);
        feedbackBar.add(feedback);
        stage.addActor(feedbackBar);
    }

    @Override
    public void update() {
        super.update();
    }
}
