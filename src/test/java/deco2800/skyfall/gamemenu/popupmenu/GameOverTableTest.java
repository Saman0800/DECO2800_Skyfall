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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.when;

public class GameOverTableTest extends BaseGDXTest {
    private Stage stage;
    private GameMenuManager gmm;
    private GameOverTable gameOverTable;
    private TextureManager tm;
    private QuestManager qm;

    @Before
    public void setUp () {
        stage = mock(Stage.class);
        gmm = mock(GameMenuManager.class);
        tm = spy(TextureManager.class);
        gmm = mock(GameMenuManager.class);
        qm = mock(QuestManager.class);

        MainCharacter mc = mock(MainCharacter.class);
        Mockito.when(gmm.getMainCharacter()).thenReturn(mc);

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

    @Test
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
