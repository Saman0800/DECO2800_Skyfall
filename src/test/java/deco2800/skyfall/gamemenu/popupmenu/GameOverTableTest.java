package deco2800.skyfall.gamemenu.popupmenu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import deco2800.skyfall.BaseGDXTest;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.QuestManager;
import deco2800.skyfall.managers.TextureManager;

public class GameOverTableTest extends BaseGDXTest {
    private Stage stage;
    private GameMenuManager gmm;
    private GameOverTable gameOverTable;
    private TextureManager tm;
    private QuestManager qm;

    @Before
    public void setUp() {
        stage = mock(Stage.class);
        gmm = mock(GameMenuManager.class);
        tm = spy(TextureManager.class);
        gmm = mock(GameMenuManager.class);
        qm = mock(QuestManager.class);

        MainCharacter mc = mock(MainCharacter.class);
        Mockito.when(gmm.getMainCharacter()).thenReturn(mc);

        gameOverTable = new GameOverTable(stage, null, null, tm, gmm);
    }

    @Test
    public void drawTest() {
        verify(stage, times(1)).addActor(any(Actor.class));
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

    @Test
    @Ignore
    public void retryQuestTest() {
        gameOverTable.retryQuest();

        MainCharacter mc = mock(MainCharacter.class);

        Mockito.when(mc.getHealth()).thenReturn(50);
        assertEquals(50, mc.getHealth());
    }

    @After
    public void tearDown() {
        stage = null;
        gmm = null;
        gameOverTable = null;
        tm = null;
    }

}
