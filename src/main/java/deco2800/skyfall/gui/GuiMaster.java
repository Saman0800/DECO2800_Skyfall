package deco2800.skyfall.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.LinkedList;
import java.util.List;

public class GuiMaster {
    List<AbstractGui> guiElements = new LinkedList<AbstractGui>();

    private static GuiMaster singleInstance = null;

    private GuiMaster() {}

    public void updateAll(long timeDelta) {
        for (AbstractGui element : guiElements) {
            element.update(timeDelta);
        }
    }

    public void renderAll(BitmapFont font, SpriteBatch batch, OrthographicCamera camera, ShapeRenderer shapeRenderer) {
        for (AbstractGui element : guiElements) {
            element.render(font, batch, camera, shapeRenderer);
        }
    }

    public static GuiMaster getInstance()
    {
        if (singleInstance == null)
            singleInstance = new GuiMaster();

        return singleInstance;
    }
}
