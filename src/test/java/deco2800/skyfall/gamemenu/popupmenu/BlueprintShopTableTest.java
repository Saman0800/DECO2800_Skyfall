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
import deco2800.skyfall.BaseGDXTest;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.managers.TextureManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class BlueprintShopTableTest extends BaseGDXTest {
    private Stage stage;
    private GameMenuManager gmm;
    private BlueprintShopTable bps;
    private TextureManager tmm;
    private ImageButton exit;
    private Skin skin;
    private StatisticsManager sm;

    @Before
    public void setUp() {
        stage = mock(Stage.class);
        gmm = mock(GameMenuManager.class);
        tmm = mock(TextureManager.class);
        exit = mock(ImageButton.class);
        skin = spy(Skin.class);
        sm = mock(StatisticsManager.class);


        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        skin.add("navy-text", labelStyle);
        skin.add("white-text", labelStyle);
        skin.add("white-label", labelStyle);

        MainCharacter mc = mock(MainCharacter.class);
        Mockito.when(gmm.getMainCharacter()).thenReturn(mc);

        Camera c = new OrthographicCamera();
        c.viewportHeight = 100;
        c.viewportWidth = 100;
        c.position.x = 100;
        c.position.y = 100;

        doReturn(c).when(stage).getCamera();

        bps = new BlueprintShopTable(stage, exit, null, tmm, gmm, sm, skin);
    }

    @Test
    public void hideTest() {
        bps.hide();
        assertFalse(bps.isVisible());
        assertFalse(exit.isVisible());
    }

    @Test
    public void showTest() {
        bps.show();
        assertTrue(bps.isVisible());
        verify(exit).setVisible(true);
    }

}
