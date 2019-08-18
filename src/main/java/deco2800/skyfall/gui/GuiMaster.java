package deco2800.skyfall.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;

public class GuiMaster {
    static Map<String, AbstractGui> guiElementsMap = new HashMap<String, AbstractGui>();
    static List<AbstractGui> guiElementsList = new LinkedList<AbstractGui>();

    private static GuiMaster singleInstance = null;

    private GuiMaster() {}

    private static <T extends AbstractGui> T add(T guiElement) {
        guiElementsMap.put(guiElement.hash, guiElement);
        guiElementsList.add(guiElement);

        return guiElement;
    }

    public static ScrollingTextBox ScrollingTextBox(String hash) {
        if (guiElementsMap.containsKey(hash)) {
            AbstractGui item = guiElementsMap.get(hash);

            if (item instanceof ScrollingTextBox) {
                return (ScrollingTextBox)item;
            } else {
                return null;
            }
        }

        return add(new ScrollingTextBox(hash));
    }

    public static void updateAll(long timeDelta) {
        for (AbstractGui guiElement : guiElementsList) {
            guiElement.update(timeDelta);
        }
    }

    public static <T extends AbstractGui> void renderAll(BitmapFont font,
            SpriteBatch batch, OrthographicCamera camera,
            ShapeRenderer shapeRenderer) {
        for (AbstractGui guiElement : guiElementsList) {
            guiElement.render(font, batch, camera, shapeRenderer);
        }
    }
}
