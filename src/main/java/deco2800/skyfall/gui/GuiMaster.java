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

    private static GuiMaster singleInstance = null;

    private GuiMaster() {}

    /**
     * Adds a gui element to the elements map and list
     * (assumes the hash does not already exist)
     * @param guiElement the element to add
     */
    private static <T extends AbstractGui> T add(T guiElement) {
        guiElementsMap.put(guiElement.hash, guiElement);
        guiElementsList.add(guiElement);

        return guiElement;
    }

    /**
     * Creates a scrolling text box if the hash is not in the map
     * otherwise it grabs the existing scrolling text box in the map
     * @param hash the hash to check for in the elements map
     */
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
    public static <T extends AbstractGui> void renderAll(BitmapFont font,
            SpriteBatch batch, OrthographicCamera camera,
            ShapeRenderer shapeRenderer) {
        for (AbstractGui guiElement : guiElementsList) {
            guiElement.render(font, batch, camera, shapeRenderer);
        }
    }
}
