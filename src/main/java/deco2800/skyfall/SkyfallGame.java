package deco2800.skyfall;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import deco2800.skyfall.mainmenu.MainMenuScreen;
import deco2800.skyfall.managers.GameManager;

/**
 * This class provides the game wrapper that different screens are plugged into
 */
public class SkyfallGame extends Game {
	/**
	 * The SpriteBatch for the game
	 */
	private SpriteBatch batch;
	private MainMenuScreen mainMenuScreen;

	/**
	 * Creates the mainmenu screen
	 */
	public void create() {
		batch = new SpriteBatch();
		initUISkin();
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

    public SpriteBatch getBatch() {
        return batch;
    }

    public MainMenuScreen getMainMenuScreen() {
        return mainMenuScreen;
    }
}
