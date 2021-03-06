package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import deco2800.skyfall.managers.FeedbackManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;

/**
 * Feedback bar UI element
 */
public class FeedbackBar extends AbstractUIElement {

    //Game menu manager
    private final GameMenuManager gmm;

    //Feedback manager
    private final FeedbackManager fm;

    //Current skin
    private final Skin skin;

    //Feedback text
    private Label feedback;

    //Feedback bar
    private Table feedbackBarTable;

    //Close button
    private ImageButton exitButton;

    /**
     * Constructor
     *
     * @param stage the stage
     * @param exitButton close button
     * @param textureNames textures
     * @param tm Texture Manager
     * @param skin the skin
     * @param gmm Game Menu Manager
     * @param fm Feedback Manager
     */
    public FeedbackBar(Stage stage, ImageButton exitButton, String[] textureNames,
                       TextureManager tm, Skin skin, GameMenuManager gmm, FeedbackManager fm) {
        super(stage, textureNames, tm);
        this.gmm = gmm;
        this.fm = fm;
        this.exitButton = exitButton;
        this.skin = skin;
        this.draw();
    }

    /**
     * Inherited from super class
     * Sets position of bar in game window
     */
    @Override
    public void updatePosition() {
        feedbackBarTable.setPosition(gmm.getTopLeftX() + stage.getCamera().viewportWidth / 5, gmm.getBottomRightY());
    }

    /**
     * Draws the bar and contained elements
     */
    @Override
    public void draw() {
        feedbackBarTable = new Table();
        feedbackBarTable.setBackground(gmm.generateTextureRegionDrawableObject("feedback_bar"));
        feedbackBarTable.setSize(800, 55);

        feedback = new Label("Click 'HELP' if you get stuck", skin, "white-text");
        feedback.setFontScale(0.8f);
        feedbackBarTable.add(feedback).left().expandX().padLeft(30f);

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideFeedbackBar();
            }
        });
        feedbackBarTable.add(exitButton).size(60f, 60f).right().padRight(30f);
        stage.addActor(feedbackBarTable);
    }

    /**
     * Updates the text displayed in the bar
     * @param text The new text to be displayed
     */
    public void updateText(String text) {
        feedback.setText(text);
        showFeedbackBar();
        fm.setFeedbackBarUpdate(false);
    }

    /**
     * Displays the bar
     */
    private void showFeedbackBar() {
        feedbackBarTable.setVisible(true);
    }

    /**
     * Hides the bar
     */
    private void hideFeedbackBar() {
        feedbackBarTable.setVisible(false);
    }

    /**
     * Called each game tick
     * Checks if text needs updating
     */
    @Override
    public void update() {
        super.update();
        if (fm.getFeedbackBarUpdate()) {
            updateText(fm.getFeedbackText());
        }
    }
}
