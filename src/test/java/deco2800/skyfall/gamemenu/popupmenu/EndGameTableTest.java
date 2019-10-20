package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.QuestManager;
import deco2800.skyfall.managers.TextureManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class EndGameTableTest {
    private EndGameTable endGameTable;
    private Stage stage;
    private ImageButton exitButton;
    private TextureManager tm;
    private GameMenuManager gameMenuManager;
    private Camera camera;

    @Before
    public void setUp() {
        stage = mock(Stage.class);
        exitButton = mock(ImageButton.class);
        tm = mock(TextureManager.class);
        gameMenuManager = mock(GameMenuManager.class);

        MainCharacter mc = mock(MainCharacter.class);
        Mockito.when(gameMenuManager.getMainCharacter()).thenReturn(mc);

        camera = new OrthographicCamera();

        doReturn(camera).when(stage).getCamera();


        endGameTable = new EndGameTable(stage, exitButton, null, tm, gameMenuManager);

    }

    @Test
    public void drawTest() {
        verify(stage, times(1)).addActor(any(Actor.class));
    }

    @Test
    public void hideTest() {
        endGameTable.hide();
        assertFalse(endGameTable.isVisible());
        assertFalse(exitButton.isVisible());
    }

    @Test
    public void showTest() {
        endGameTable.hide();
        assertFalse(endGameTable.isVisible());
        assertFalse(exitButton.isVisible());

        endGameTable.show();
        assertTrue(endGameTable.isVisible());
    }

    @After
    public void tearDown() {
        endGameTable = null;
        camera = null;
        stage = null;
        exitButton = null;
        tm = null;
        gameMenuManager = null;
    }
}
