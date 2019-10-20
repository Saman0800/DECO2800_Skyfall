package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import deco2800.skyfall.BaseGDXTest;
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

public class EndGameTableTest extends BaseGDXTest {
    private Stage stage;
    private GameMenuManager gmm;
    private EndGameTable endGameTable;
    private TextureManager tm;

    @Before
    public void setUp() {
        stage = mock(Stage.class);
        gmm = mock(GameMenuManager.class);
        tm = spy(TextureManager.class);

        MainCharacter mc = mock(MainCharacter.class);
        Mockito.when(gmm.getMainCharacter()).thenReturn(mc);

        endGameTable = new EndGameTable(stage, null, null, tm, gmm);
    }

    @Test
    public void drawTest() {
        verify(stage, times(1)).addActor(any(Actor.class));
    }

    @Test
    public void hideTest() {
        endGameTable.hide();
        assertFalse(endGameTable.isVisible());
    }

    @Test
    public void showTest() {
        endGameTable.hide();
        assertFalse(endGameTable.isVisible());

        endGameTable.show();
        assertTrue(endGameTable.isVisible());
    }

    @After
    public void tearDown() {
        stage = null;
        gmm = null;
        endGameTable = null;
        tm = null;
    }
}
