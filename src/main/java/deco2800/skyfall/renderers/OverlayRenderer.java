package deco2800.skyfall.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.Tree;
import deco2800.skyfall.gui.GuiMaster;
import deco2800.skyfall.gui.ScrollingTextBox;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.NetworkManager;
import deco2800.skyfall.managers.OnScreenMessageManager;
import deco2800.skyfall.managers.PathFindingService;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.TutorialWorld;
import java.util.List;
import javax.swing.JFrame;

public class OverlayRenderer implements Renderer {
	
    BitmapFont font;
    ShapeRenderer shapeRenderer;
    boolean firstTime = true;
     
    FPSLogger fpsLogger = new FPSLogger();

    long peakRAM = 0;

		ScrollingTextBox testTutorialBox;
		Tree testTutorialTree;
		boolean testKilledTree = false;


    /**
     * Renders onto a batch, given a renderables with entities
     * It is expected that AbstractWorld contains some entities and a Map to read tiles from
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
        

        if( GameManager.get().debugMode) {
        	renderDebugText(batch, camera);
        }

			List<AbstractEntity> entityList = GameManager.get().getWorld().getEntities();

			if (firstTime && GameManager.get().isTutorial) {
					testTutorialBox = new ScrollingTextBox();
					testTutorialBox.setString("hello and welcome to scrolling text as you can see this text is scrolling and that is very cool. Now, what is very epic about this scrolling is that its very easy to make. The issue is that it can not\n"
							+ "be very easily constructed and stored without more code writing and tbh I am kinda tired of doing code at this point I just wanna play games not make one haha anyways why are you still reading\n"
							+ "this haha enjoy refactoring this whoever sees this (probably me) lolololoololololololololollollolololoololololololololollollolololoololololololololollollolololoololololololololollollolololoololololololololollollolololololololollollolololoololololololololollollolololololololollollolololoololololololololollol\n"
							+ "Okay now u gotta left click to move and right click to interact. Go chop down that tree.");
					testTutorialBox.start();
					firstTime = false;

					for (AbstractEntity e : entityList) {
						if (e instanceof Tree) {
							testTutorialTree = (Tree)e;
						}
					}
				}

			if (!entityList.contains(testTutorialTree) && !testKilledTree) {
				testKilledTree = true;
				testTutorialBox.reset();
				testTutorialBox.setString("Well done on killing him, you monster");
				testTutorialBox.start();
			}

				GuiMaster.getInstance().updateAll(1);
        GuiMaster.getInstance().renderAll(font, batch, camera, shapeRenderer);

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

	private void debugLine(SpriteBatch batch, Camera camera, int line, String string) {
		font.draw(batch, string, camera.position.x - camera.viewportWidth / 2 + 10,
				camera.position.y + camera.viewportHeight / 2 - line * 20 - 10);
	}

	private void chatLine(SpriteBatch batch, Camera camera, int line, String string) {
		font.draw(batch, string, camera.position.x - camera.viewportWidth / 2 + 10,
				camera.position.y - camera.viewportHeight / 2 + line * 25 + 25);
	}

	private void renderDebugText(SpriteBatch batch, Camera camera) {
		int line = 0; // Set this to set the line number you want to debug message to
		debugLine(batch, camera, line++, "== Game Info ==");
		debugLine(batch, camera, line++,
				String.format("Rendered: %d/%d entities, %d/%d tiles", GameManager.get().entitiesRendered,
						GameManager.get().entitiesCount, GameManager.get().tilesRendered,
						GameManager.get().tilesCount));
		debugLine(batch, camera, line++, String.format("FPS: %d", Gdx.graphics.getFramesPerSecond()));
		debugLine(batch, camera, line++,
				String.format("RAM: %dMB PEAK: %dMB", Gdx.app.getJavaHeap() / 1000000, peakRAM / 1000000));

		float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
		debugLine(batch, camera, line++, String.format("Mouse: X:%d Y:%d", Gdx.input.getX(), Gdx.input.getY()));
		debugLine(batch, camera, line++, String.format("World: X:%.0f Y:%.0f", mouse[0], mouse[1]));

		float[] ColRow = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);
		debugLine(batch, camera, line++, String.format("World: X:%.0f Y:%.0f", ColRow[0], ColRow[1]));

		line++;

		debugLine(batch, camera, line++, "PathfindingService");
		debugLine(batch, camera, line++, GameManager.get().getManager(PathFindingService.class).toString());

		line++;
		debugLine(batch, camera, line++, "== Networking ==");
		debugLine(batch, camera, line++,
				String.format("ID: %d", GameManager.get().getManager(NetworkManager.class).getID()));
		debugLine(batch, camera, line++, String.format("Messages Received: %d",
				GameManager.get().getManager(NetworkManager.class).getMessagesReceived()));
		debugLine(batch, camera, line++,
				String.format("Messages Sent: %d", GameManager.get().getManager(NetworkManager.class).getMessagesSent()));
		debugLine(batch, camera, line++,
				String.format("Username: %s", GameManager.get().getManager(NetworkManager.class).getUsername()));
	}
}
