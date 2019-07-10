package deco2800.thomas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import deco2800.thomas.mainmenu.MainMenuScreen;
import deco2800.thomas.managers.GameManager;

/**
 * This class provides the game wrapper that different screens are plugged into
 */
public class ThomasGame extends Game {
	/**
	 * The SpriteBatch for the game
	 */
	public SpriteBatch batch;
	public static final String SAVE_ROOT_DIR = "thomas-saves";
	public FileHandle saveRootHandle;
	public MainMenuScreen mainMenuScreen;

	/**
	 * Creates the mainmenu screen
	 */
	public void create() {
		saveRootHandle = Gdx.files.local(SAVE_ROOT_DIR);
		batch = new SpriteBatch();
		initUISkin();
		mainMenuScreen = new MainMenuScreen(this);
		this.setScreen(mainMenuScreen);
	}

	/**
	 * Disposes of the game
	 */
	public void dispose() {
		mainMenuScreen.dispose();
		batch.dispose();
	}

	public void initUISkin() {
		GameManager.get().setSkin(new Skin(Gdx.files.internal("resources/uiskin.skin")));
	}
}
