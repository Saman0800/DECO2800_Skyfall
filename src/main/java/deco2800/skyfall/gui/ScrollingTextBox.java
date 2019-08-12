package deco2800.skyfall.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import deco2800.skyfall.util.Vector2;
import javax.swing.JFrame;

public final class ScrollingTextBox extends AbstractGui {
    private final long timePerChar = 20;
    private long lastTime = 0;
    private boolean started = false;
    private String printedString;
    private int currentIndex;
    private int residueTime;
    private String subString;
    private boolean finished = false;

    public ScrollingTextBox() {
        super();
    }

    public ScrollingTextBox(AbstractGui parent) {
        super(parent);
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

        updateChildren(timeDelta);
    }

    @Override
    public void render(BitmapFont font, SpriteBatch batch,
            OrthographicCamera camera, ShapeRenderer shapeRenderer) {
            batch.end();
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
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

            font.draw(batch, printedString.substring(0, currentIndex),
                    camera.position.x - camera.viewportWidth / 2 + 10,
                    camera.position.y - camera.viewportHeight / 2 + 100);

        renderChildren(font, batch, camera, shapeRenderer);
    }
}
