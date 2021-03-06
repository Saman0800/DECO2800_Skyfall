package deco2800.skyfall.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import deco2800.skyfall.graphics.ShaderWrapper;
import deco2800.skyfall.graphics.types.Vec3;
import deco2800.skyfall.gui.GuiMaster;
import deco2800.skyfall.managers.EnvironmentManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.OnScreenMessageManager;
import deco2800.skyfall.managers.PathFindingService;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.util.WorldUtil;

/**
 * Renderer that handles the overlay for the game
 */
public class OverlayRenderer implements Renderer {
    BitmapFont font;
    ShapeRenderer shapeRenderer;

    FPSLogger fpsLogger = new FPSLogger();

    long peakRAM = 0;

    ShaderWrapper shader;

    /**
     * Renders onto a batch, given a renderables with entities It is expected that
     * AbstractWorld contains some entities and a Map to read tiles from
     * 
     * @param batch Batch to render onto
     */
    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        if (shapeRenderer == null) {
            shapeRenderer = new ShapeRenderer();
        }

        if (font == null) {
            font = new BitmapFont();
            font.getData().setScale(1f);
        }

        batch.begin();

        if (GameManager.get().getDebugMode()) {
            renderDebugText(batch, camera);
        }

        GuiMaster.updateAll(1);
        GuiMaster.renderAll(font, batch, camera, shapeRenderer);

        int line = GameManager.get().getManager(OnScreenMessageManager.class).getMessages().size();
        for (String message : GameManager.get().getManager(OnScreenMessageManager.class).getMessages()) {
            chatLine(batch, camera, line--, message);
        }

        if (GameManager.get().getManager(OnScreenMessageManager.class).isTyping()) {
            chatLine(batch, camera, 0, GameManager.get().getManager(OnScreenMessageManager.class).getUnsentMessage());
        }

        if (peakRAM < Gdx.app.getJavaHeap()) {
            peakRAM = Gdx.app.getJavaHeap();
        }

        batch.end();
    }

    /**
     * Sets the current shader
     * 
     * @param shader shader to use
     */
    public void setShader(ShaderWrapper shader) {
        this.shader = shader;
    }

    private void debugLine(SpriteBatch batch, Camera camera, int line, String string) {
        font.draw(batch, string, camera.position.x - camera.viewportWidth / 2 + 10,
                camera.position.y + camera.viewportHeight / 2 - line * 20 - 10);
    }

    private void chatLine(SpriteBatch batch, Camera camera, int line, String string) {
        font.draw(batch, string, camera.position.x - camera.viewportWidth / 2 + 10,
                camera.position.y - camera.viewportHeight / 2 + line * 25 + 25);
    }

    /**
     * Renders the debug information in the top left
     * 
     * @param batch  Batch to render onto
     * @param camera Camera to render onto
     */
    private void renderDebugText(SpriteBatch batch, Camera camera) {
        int line = 0; // Set this to set the line number you want to debug message to
        debugLine(batch, camera, line++, "== Game Info ==");
        debugLine(batch, camera, line++,
                String.format("Rendered: %d/%d entities, %d/%d tiles", GameManager.get().getEntitiesRendered(),
                        GameManager.get().getEntitiesCount(), GameManager.get().getTilesRendered(),
                        GameManager.get().getTilesCount()));
        debugLine(batch, camera, line++, String.format("FPS: %d", Gdx.graphics.getFramesPerSecond()));
        debugLine(batch, camera, line++,
                String.format("RAM: %dMB PEAK: %dMB", Gdx.app.getJavaHeap() / 1000000, peakRAM / 1000000));

        float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
        debugLine(batch, camera, line++, String.format("Mouse: X:%d Y:%d", Gdx.input.getX(), Gdx.input.getY()));
        debugLine(batch, camera, line++, String.format("World: X:%.0f Y:%.0f", mouse[0], mouse[1]));

        float[] colRow = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);
        debugLine(batch, camera, line++, String.format("World: X:%.0f Y:%.0f", colRow[0], colRow[1]));

        // Display current time in game
        debugLine(batch, camera, line++,
                String.format("Time: %s", GameManager.get().getManager(EnvironmentManager.class).getTOD()));

        // Display current month in game
        debugLine(batch, camera, line++,
                String.format("Season: %s", GameManager.get().getManager(EnvironmentManager.class).getSeason()));

        // Display current biome in game
        debugLine(batch, camera, line++,
                String.format("Biome: %s", GameManager.get().getManager(EnvironmentManager.class).currentBiome()));

        // Display player's current equipped item
        debugLine(batch, camera, line++, String.format("Equipped Item: %s",
                GameManager.get().getManager(StatisticsManager.class).getCharacter().displayEquippedItem()));

        line++;

        debugLine(batch, camera, line++, "PathfindingService");
        debugLine(batch, camera, line++, GameManager.get().getManager(PathFindingService.class).toString());

        line++;

        debugLine(batch, camera, line++, "== Graphics ==");
        debugLine(batch, camera, line++, String.format("Extended shading: %s", shader.getActive() ? "True" : "False"));
        debugLine(batch, camera, line++, String.format("Ambient Intensity: %f", shader.getAmbientIntensity()));
        Vec3 colour = shader.getAmbientColour();
        debugLine(batch, camera, line++,
                String.format("Ambient Colour: (%f, %f, %f)", colour.getX(), colour.getY(), colour.getZ()));
        debugLine(batch, camera, line++,
                String.format("Point Light Intensity: %f", 1.0f - shader.getAmbientIntensity()));
        debugLine(batch, camera, line, String.format("Point Light Count: %d", shader.getPointLightCount()));
    }
}
