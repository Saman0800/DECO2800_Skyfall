package deco2800.skyfall.gamemenu.popupmenu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import deco2800.skyfall.BaseGDXTest;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class PauseTableTest extends BaseGDXTest {
    private Stage stage;
    private GameMenuManager gameMenuManager;
    private PauseTable table;
    private TextureManager textureManager;
    private ImageButton exit;
    private Skin skin;

    @Before
    public void setUp() {
        stage = mock(Stage.class);
        gameMenuManager = mock(GameMenuManager.class);
        textureManager = mock(TextureManager.class);
        exit = mock(ImageButton.class);
        skin = spy(Skin.class);

        MainCharacter mc = mock(MainCharacter.class);
        Mockito.when(gameMenuManager.getMainCharacter()).thenReturn(mc);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        skin.add("navy-text", labelStyle);
        skin.add("white-text", labelStyle);
        Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
        skin.add("default-slider", sliderStyle);

        Camera c = new OrthographicCamera();
        c.viewportHeight = 100;
        c.viewportWidth = 100;
        c.position.x = 100;
        c.position.y = 100;

        doReturn(c).when(stage).getCamera();

        table = new PauseTable(stage, exit, null, textureManager, gameMenuManager, skin);
    }

    @Test
    public void hideTest() {
        table.hide();
        assertFalse(table.isVisible());
        assertFalse(exit.isVisible());
    }

    @Test
    public void showTest() {
        table.show();
        assertTrue(table.isVisible());
        verify(exit).setVisible(true);
    }

    @Test
    public void retryTest() {
        table.retryQuest();
        table.hide();
        assertFalse(table.isVisible());
    }

}
