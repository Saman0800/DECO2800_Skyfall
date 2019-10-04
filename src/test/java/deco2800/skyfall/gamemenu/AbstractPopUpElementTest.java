package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.managers.SoundManager;
import deco2800.skyfall.managers.TextureManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sun.applet.Main;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Mockito.*;

public class AbstractPopUpElementTest {
    AbstractPopUpElement element;
    private TextureManager tm;
    private InventoryManager im;
    private Stage stage;
    private ImageButton exitButton;
    private GameMenuManager gmm;

    @Before
    public void setUp() {
        tm = mock(TextureManager.class);
        im = mock(InventoryManager.class);
        stage = mock(Stage.class);
        exitButton = mock(ImageButton.class);
        gmm = mock(GameMenuManager.class);
        MainCharacter mc = mock(MainCharacter.class);
        when(gmm.getMainCharacter()).thenReturn(mc);
        element = new AbstractPopUpElement(stage, exitButton, null, tm,  gmm);
    }

    @Test
    public void showTest() {
        element.show();
        assertTrue(element.isVisible());
        verify(exitButton).setVisible(true);
    }

    @Test
    public void hideTest() {
        element.hide();
        assertFalse(element.isVisible());
        verify(exitButton).setVisible(false);
    }

    @Test
    public void updatePosition() {
        Camera c = new OrthographicCamera();
        c.viewportHeight = 100;
        c.viewportWidth = 100;
        c.position.x = 100;
        c.position.y = 100;

        doReturn(c).when(stage).getCamera();

        element.updatePosition();
        verify(exitButton).setPosition(anyFloat(), anyFloat());
    }

    @Test
    public void drawVerification() {
        Camera c = new OrthographicCamera();
        c.viewportHeight = 100;
        c.viewportWidth = 100;
        c.position.x = 100;
        c.position.y = 100;

        doNothing().when(exitButton).setSize(anyFloat(), anyFloat());
        doNothing().when(exitButton).setVisible(false);
        doNothing().when(stage).addActor(exitButton);
        doReturn(c).when(stage).getCamera();

        element.draw();

        verify(exitButton).setSize(anyFloat(), anyFloat());
        verify(exitButton).setVisible(false);
        verify(stage).addActor(exitButton);

    }

    @After
    public void tearDown() {
        tm = null;
        im = null;
        stage = null;
        exitButton = null;
        gmm = null;
        element = null;
    }



}