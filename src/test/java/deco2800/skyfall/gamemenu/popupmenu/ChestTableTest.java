package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import deco2800.skyfall.BaseGDXTest;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.managers.TextureManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ChestTableTest extends BaseGDXTest {
    private Stage stage;
    private GameMenuManager gmm;
    private InventoryTable invTable;
    private InventoryManager inv;
    private ChestTable chest;
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

        chest = new ChestTable(stage, exit, null, tmm, gmm, sm, skin);
    }

    @Test
    public void hideTest() {
        chest.hide();
        assertFalse(chest.isVisible());
        assertFalse(exit.isVisible());
    }

    @Test
    public void showTest() {
        chest.show();
        assertTrue(chest.isVisible());
        verify(exit).setVisible(true);
    }

}
