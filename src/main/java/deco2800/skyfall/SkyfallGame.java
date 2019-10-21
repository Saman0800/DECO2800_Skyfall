package deco2800.skyfall;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import deco2800.skyfall.mainmenu.MainMenuScreen;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.GameManager;

/**
 * This class provides the game wrapper that different screens are plugged into
 */
public class SkyfallGame extends Game {
	/**
	 * The SpriteBatch for the game
	 */
	private SpriteBatch batch;
	public static final String SAVE_ROOT_DIR = "skyfall-saves";
	private FileHandle saveRootHandle;
	private MainMenuScreen mainMenuScreen;

	/**
	 * Creates the mainmenu screen
	 */
	public void create() {
		batch = new SpriteBatch();
		initUISkin();
		DatabaseManager.get().startDataBaseConnector();
		mainMenuScreen = new MainMenuScreen(this);
		this.setScreen(mainMenuScreen);
	}

	/**
	 * Disposes of the game
	 */
	@Override
	public void dispose() {
		mainMenuScreen.dispose();
		batch.dispose();
	}

	private void initUISkin() {
		GameManager.get().setSkin(new Skin(Gdx.files.internal("resources/uiskin.skin")));
	}

	/**
	 * Gets the sprite batch for the game.
	 *
	 * @return the sprite batch for the game
	 */
	public SpriteBatch getBatch() {
		return batch;
	}

	/**
	 * Gets the main menu screen for this game.
	 *
	 * @return the main menu screen for this game
	 */
	public MainMenuScreen getMainMenuScreen() {
		return mainMenuScreen;
	}
}
