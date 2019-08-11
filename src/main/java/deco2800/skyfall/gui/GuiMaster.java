package deco2800.skyfall.gui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.LinkedList;
import java.util.List;

public class GuiMaster {
    public List<AbstractGui> guiElements = new LinkedList<AbstractGui>();

    private static GuiMaster singleInstance = null;

    private GuiMaster() {}

    public void updateAll(long timeDelta) {
        for (AbstractGui element : guiElements) {
            element.update(timeDelta);
        }
    }

    public void renderAll(BitmapFont font, SpriteBatch batch, OrthographicCamera camera) {
        for (AbstractGui element : guiElements) {
            element.render(font, batch, camera);
        }
    }

    public static GuiMaster getInstance()
    {
        if (singleInstance == null)
            singleInstance = new GuiMaster();

        return singleInstance;
    }
}
