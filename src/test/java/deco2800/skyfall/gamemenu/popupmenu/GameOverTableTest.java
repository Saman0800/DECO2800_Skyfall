package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import deco2800.skyfall.BaseGDXTest;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.gamemenu.HealthCircle;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.QuestManager;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.managers.TextureManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.when;

public class GameOverTableTest extends BaseGDXTest {
    private Skin skin;
    private Stage stage;
    private GameMenuManager gmm;
    private GameOverTable gameOverTable;
    private TextureManager tm;

    @Before
    public void setUp () {
        stage = mock(Stage.class);
        gmm = mock(GameMenuManager.class);
        tm = spy(TextureManager.class);
        gmm = mock(GameMenuManager.class);

        MainCharacter mc = mock(MainCharacter.class);
        Mockito.when(gmm.getMainCharacter()).thenReturn(mc);
        tm = mock(TextureManager.class);
        Camera c = new OrthographicCamera();
        c.viewportHeight = 100;
        c.viewportWidth = 100;
        c.position.x = 100;
        c.position.y = 100;

        doReturn(c).when(stage).getCamera();

        gameOverTable = new GameOverTable(stage, null, null, tm,  gmm);
    }

    @Test
    public void drawTest() {
        verify(stage,times(1)).addActor(any(Actor.class));
    }

    @Test
    public void hideTest() {
        gameOverTable.hide();
        assertFalse(gameOverTable.isVisible());
    }

    @Test
    public void showTest() {
        gameOverTable.hide();
        assertFalse(gameOverTable.isVisible());

        gameOverTable.show();
        assertTrue(gameOverTable.isVisible());
    }

    @After
    public void tearDown() {
        skin = null;
        stage = null;
        gmm = null;
        gameOverTable = null;
        tm = null;
    }

}
