package deco2800.skyfall;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * DesktopLauncher
 * @author Tim Hadwen
 */
public class GameLauncher {

	/**
	 * Private constructor to hide the implicit constructor
	 */
	private GameLauncher () {
		//Private constructor to hide the implicit one.
	}

	/**
	 * Main function for the game
	 * @param arg Command line arguments (we wont use these)
	 */
	@SuppressWarnings("unused") //app
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 1000;
		config.title = "DECO2800 2018: Skyfall";
		LwjglApplication app = new LwjglApplication(new SkyfallGame(), config);
	}
}