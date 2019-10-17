package deco2800.skyfall.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.TextureManager;

/**
 * This is a scrolling text box that scrolls some text across the screen at a
 * set speed.
 */
public class ScrollingTextBox extends AbstractGui {
    private static final long TimePerChar = 20;
    private String printedString;

    // Labels and images to be added to the stage
    private Label guiLabel;
    private Group guiGroup;
    private Image guiImage;

    private long lastTime = 0;
    private boolean started = false;
    private int currentIndex;
    private int residueTime;
    private boolean finished = false;

    /**
     * Creates all the objects to be added to the stage
     * @param hash the hash to be set in the GuiMaster
     */
    ScrollingTextBox(String hash) {
        super(hash);
        Label.LabelStyle guiLabelStyle = new Label.LabelStyle();
        guiLabelStyle.font = new BitmapFont();
        guiLabelStyle.fontColor = Color.WHITE;

        guiGroup = new Group();
        guiGroup.setPosition(0, 100); // VERY BAD MAGIC NUMBER PLS FIX
        guiGroup.setWidth(1280); // VERY BAD MAGIC NUMBER PLS FIX
        guiGroup.setHeight(100);

        // Texture behind scrolling text
        guiImage = new Image(GameManager.get().getManager(TextureManager.class).getTexture("dialogue_text_background"));
        guiImage.setWidth(1280);
        guiImage.setPosition(0, -guiGroup.getHeight());
        guiImage.setHeight(guiGroup.getHeight());
        guiImage.setScaling(Scaling.stretch);

        // Label for the text
        guiLabel = new Label("", guiLabelStyle);
        guiLabel.setWrap(true);
        guiLabel.setWidth(guiGroup.getWidth() - 120);
        guiLabel.setAlignment(Align.topLeft);
        guiLabel.setPosition(100, 0);

        // Add all the gui elements
        guiGroup.addActor(guiImage);
        guiGroup.addActor(guiLabel);

        GameManager.get().getStage().addActor(guiGroup);
    }

    ScrollingTextBox(String hash, AbstractGui parent) {
        super(hash, parent);
    }

    /**
     * Sets the string to be scrolled across the screen
     * @param printedString scrolling string
     */
    public void setString(String printedString) {
        if (!started) {
            this.printedString = printedString;
        }
    }

    /**
     * Starts scrolling the text across the screen
     */
    public void start() {
        started = true;
        finished = false;
        lastTime = System.currentTimeMillis();
        residueTime = 0;
        currentIndex = 0;
    }

    /**
     * Returns whether the scrolling has finished yet
     * @return boolean output
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Resets the text box so it can be started again
     */
    public void reset() {
        started = false;
    }

    @Override
    public void update(long timeDelta) {
        if (started && !finished) {
            residueTime += (int)(System.currentTimeMillis() - lastTime);

            if (printedString.length()
                    < currentIndex + (int)(residueTime / TimePerChar)) {
                finished = true;
            }

            currentIndex = Math.min(printedString.length(),
                    currentIndex + (int)(residueTime / TimePerChar));

            residueTime %= TimePerChar;

            lastTime = System.currentTimeMillis();
        }

        guiLabel.setText(printedString.substring(0, currentIndex));

        updateChildren(timeDelta);
    }

    @Override
    public void render(BitmapFont font, SpriteBatch batch,
            OrthographicCamera camera, ShapeRenderer shapeRenderer) {
        renderChildren(font, batch, camera, shapeRenderer);
    }

    @Override
    public void destroy(){
        guiGroup.remove();
        super.destroy();
    }
}
