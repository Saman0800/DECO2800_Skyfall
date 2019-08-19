package deco2800.skyfall.gui;

import static deco2800.skyfall.managers.GameManager.get;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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

public class ScrollingTextBox extends AbstractGui {
    private static final long timePerChar = 20;
    private String printedString;

    private Label guiLabel;
    private Group guiGroup;
    private Image guiImage;
    private Image guiPortrait;

    private long lastTime = 0;
    private boolean started = false;
    private int currentIndex;
    private int residueTime;
    private String subString;
    private boolean finished = false;

    ScrollingTextBox(String hash) {
        super(hash);
        Label.LabelStyle guiLabelStyle = new Label.LabelStyle();
        guiLabelStyle.font = new BitmapFont();
        guiLabelStyle.fontColor = Color.WHITE;

        guiGroup = new Group();
        guiGroup.setPosition(0, 100); // VERY BAD MAGIC NUMBER PLS FIX
        guiGroup.setWidth(1280); // VERY BAD MAGIC NUMBER PLS FIX
        guiGroup.setHeight(100);

        guiImage = new Image(GameManager.get().getManager(TextureManager.class).getTexture("dialogue_text_background"));
        guiImage.setWidth(1280);
        guiImage.setPosition(100, -guiGroup.getHeight());
        guiImage.setHeight(guiGroup.getHeight());
        guiImage.setScaling(Scaling.stretch);

        guiPortrait =
                new Image(GameManager.get().getManager(TextureManager.class).getTexture("Karen(replace)"));
        guiPortrait.setWidth(100);
        guiPortrait.setPosition(0, -guiGroup.getHeight());
        guiPortrait.setHeight(guiGroup.getHeight());
        guiPortrait.setScaling(Scaling.stretch);

        guiLabel = new Label("", guiLabelStyle);
        guiLabel.setWrap(true);
        guiLabel.setWidth(guiGroup.getWidth() - 120);
        guiLabel.setAlignment(Align.topLeft);
        guiLabel.setPosition(100, 0);

        guiGroup.addActor(guiImage);
        guiGroup.addActor(guiLabel);
        guiGroup.addActor(guiPortrait);

        GameManager.get().getStage().addActor(guiGroup);
    }

    ScrollingTextBox(String hash, AbstractGui parent) {
        super(hash, parent);
    }

    public void setString(String printedString) {
        if (!started) {
            this.printedString = printedString;
        }
    }

    public void start() {
        started = true;
        finished = false;
        lastTime = System.currentTimeMillis();
        residueTime = 0;
        currentIndex = 0;
    }

    public boolean isFinished() {
        return finished;
    }

    public void reset() {
        started = false;
    }

    @Override
    public void update(long timeDelta) {
        if (started && !finished) {
            residueTime += (int)(System.currentTimeMillis() - lastTime);

            if (printedString.length()
                    < currentIndex + (int)(residueTime / timePerChar)) {
                finished = true;
            }

            currentIndex = Math.min(printedString.length(),
                    currentIndex + (int)(residueTime / timePerChar));

            residueTime %= timePerChar;

            lastTime = System.currentTimeMillis();
        }

        guiLabel.setText(printedString.substring(0, currentIndex));

        updateChildren(timeDelta);
    }

    @Override
    public void render(BitmapFont font, SpriteBatch batch,
            OrthographicCamera camera, ShapeRenderer shapeRenderer) {
            //font.draw(batch, printedString.substring(0, currentIndex),
            //        camera.position.x - camera.viewportWidth / 2 + 10,
            //        camera.position.y - camera.viewportHeight / 2 + 100);
        renderChildren(font, batch, camera, shapeRenderer);
    }

    @Override
    public void destroy(){
        guiGroup.remove();
        super.destroy();
    }
}
