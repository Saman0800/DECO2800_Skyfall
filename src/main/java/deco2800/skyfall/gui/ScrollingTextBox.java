package deco2800.skyfall.gui;

import static deco2800.skyfall.managers.GameManager.get;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import deco2800.skyfall.managers.GameManager;

public final class ScrollingTextBox extends AbstractGui {
    private static final long timePerChar = 20;
    private String printedString;
    private Label guiLabel;

    private long lastTime = 0;
    private boolean started = false;
    private int currentIndex;
    private int residueTime;
    private String subString;
    private boolean finished = false;

    ScrollingTextBox(String hash) {
        super(hash);
        Label.LabelStyle guiLabelStyle = new Label.LabelStyle();
        BitmapFont myFont = new BitmapFont();
        guiLabelStyle.font = myFont;
        guiLabelStyle.fontColor = Color.RED;

        guiLabel = new Label("", guiLabelStyle);
        guiLabel.setWrap(true);
        guiLabel.setWidth(1280-20); // VERY BAD MAGIC NUMBER PLS FIX
        guiLabel.setAlignment(Align.topLeft);
        guiLabel.setPosition(10, 100); // VERY BAD MAGIC NUMBER PLS FIX

        GameManager.get().getStage().addActor(guiLabel);
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
            batch.end();
            Stage stageInstance = GameManager.get().getStage();
            shapeRenderer.setProjectionMatrix(camera.projection);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 0, 0, 0.25f);
            shapeRenderer.rect(camera.position.x - camera.viewportWidth / 2,
                    camera.position.x - camera.viewportHeight / 2,
                    camera.viewportWidth, 110);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
            batch.begin();

            //font.draw(batch, printedString.substring(0, currentIndex),
            //        camera.position.x - camera.viewportWidth / 2 + 10,
            //        camera.position.y - camera.viewportHeight / 2 + 100);

        renderChildren(font, batch, camera, shapeRenderer);
    }

    @Override
    public void destroy(){
        guiLabel.remove();
        super.destroy();
    }
}
