package deco2800.skyfall.managers;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.gamemenu.AbstractUIElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;


public class GameMenuManagerTest {
    private GameMenuManager gmm;
    private TextureManager tm;
    private InventoryManager im;
    private Stage stage;
    private Skin skin;
    private Map<String, AbstractPopUpElement> popUps;
    private Map<String, AbstractUIElement> uiElements;
    private Camera camera;

    @Before
    public void setUp() {
        tm = mock(TextureManager.class);
        im = mock(InventoryManager.class);
        stage = mock(Stage.class);
        skin = mock(Skin.class);
        popUps = mock(Map.class);
        uiElements = mock(Map.class);
        camera = spy(Camera.class);
        camera.position.x = 100;
        camera.position.y = 100;
        camera.viewportWidth = 100;
        camera.viewportHeight = 100;
        when(stage.getCamera()).thenReturn(camera);

        gmm = new GameMenuManager(tm, im, stage, skin, popUps, uiElements);
    }

    @Test
    public void onTickPopUpTest() {
        AbstractPopUpElement mockPopUp = mock(AbstractPopUpElement.class);
        gmm.setPopUp("mockPopUp");
        when(popUps.get("mockPopUp")).thenReturn(mockPopUp);
        when(mockPopUp.isVisible()).thenReturn(false);
        doNothing().when(mockPopUp).update();
        doNothing().when(mockPopUp).show();



        gmm.onTick(0);
        verify(mockPopUp).update();
        verify(mockPopUp).show();
    }

    @Test
    @Ignore
    public void onTickUpdateTest() {
        gmm.setPopUp(null);
        //AbstractPopUpElement is still
        AbstractUIElement mockPopUp = mock(AbstractPopUpElement.class);

        doNothing().when(mockPopUp).update();


        HashMap<String, AbstractUIElement> actualMap = new HashMap<>();

        actualMap.put("mock1", null);
        actualMap.put("mock2", null);
        actualMap.put("mock3", null);

        when(uiElements.entrySet()).thenReturn(actualMap.entrySet());

        for (Map.Entry<String, AbstractUIElement> key: actualMap.entrySet()) {
            doNothing().when(key).getValue().update();
        }

        doReturn(mockPopUp).when(uiElements).get(anyString());

        gmm.onTick(0);
        verify(mockPopUp, times(3)).update();

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

    @After()
    public void tearDown() {
        tm = null;
        im = null;
        stage = null;
        skin = null;
        popUps = null;
        uiElements = null;
        gmm = null;
        camera = null;
    }
}