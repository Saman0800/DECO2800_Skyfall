package deco2800.skyfall.gui;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GuiMaster {
    static Map<String, AbstractGui> guiElementsMap = new HashMap<>();
    static List<AbstractGui> guiElementsList = new LinkedList<>();

    private GuiMaster() {}

    /**
     * Updates all elements currently in the elements list
     * @param timeDelta time between last update
     */
    public static void updateAll(long timeDelta) {
        for (AbstractGui guiElement : guiElementsList) {
            guiElement.update(timeDelta);
        }
    }

    /**
     * Renders all elements currently in the elements list
     * @param font the font to be used
     * @param batch the sprite batch to render to
     * @param camera the camera to be used
     * @param shapeRenderer the shape renderer
     */
    public static void renderAll(BitmapFont font,
            SpriteBatch batch, OrthographicCamera camera,
            ShapeRenderer shapeRenderer) {
        for (AbstractGui guiElement : guiElementsList) {
            guiElement.render(font, batch, camera, shapeRenderer);
        }
    }
}
