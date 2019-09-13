package deco2800.skyfall.managers;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.gamemenu.AbstractUIElement;
import deco2800.skyfall.gamemenu.HealthCircle;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GameMenuManagerTest {
    GameMenuManager gmm;
    TextureManager tm;
    SoundManager sm;
    InventoryManager im;
    Stage stage;
    Skin skin;
    Map<String, AbstractPopUpElement> popUps;
    Map<String, AbstractUIElement> uiElements;


    @Before
    public void setUp() {
        tm = mock(TextureManager.class);
        sm = mock(SoundManager.class);
        im = mock(InventoryManager.class);
        stage = mock(Stage.class);
        skin = mock(Skin.class);
        popUps = mock(Map.class);
        uiElements = mock(Map.class);

        gmm = new GameMenuManager(tm, sm, im, stage, skin, popUps, uiElements);
    }


    public void onTickTest() {
        gmm.onTick(0);
    }


    @Test
    public void getCurrentPopUpTest() {
        when(popUps.get("goldTable")).thenReturn(null);
        gmm.setPopUp("goldTable");

        gmm.getCurrentPopUp();
        verify(popUps).get("goldTable");
    }

    @Test
    public void getPopUpFromTable() {
        when(popUps.get("chestTable")).thenReturn(null);
        gmm.getPopUp("chestTable");
        verify(popUps).get("chestTable");
    }

    @Test
    public void drawAllElementsWithoutStatsManager() {
        gmm.drawAllElements();
        verify(popUps, never()).put(anyString(), any());
        verify(uiElements, never()).put(anyString(), any());
    }

    @Test
    @Ignore
    public void drawAllElementsWithStatsManager() {
        ///TODO : cannot get mocking for this test working
        //when(popUps.put(anyString(), anyObject())).thenReturn(null);
        when(popUps.put(eq("healthCircle"), anyObject())).thenReturn(null);
        when(uiElements.put(anyString(), anyObject())).thenReturn(null);

        StatisticsManager sm = mock(StatisticsManager.class);
        gmm.addStatsManager(sm);
        gmm.drawAllElements();
        verify(popUps, atLeast(5)).put(anyString(),any(AbstractPopUpElement.class) );
        verify(uiElements, atLeast(2)).put(anyString(), any());
    }

    @After()
    public void tearDown() {
        tm = null;
        sm = null;
        im = null;
        stage = null;
        skin = null;
        popUps = null;
        uiElements = null;
        gmm = null;
    }
}