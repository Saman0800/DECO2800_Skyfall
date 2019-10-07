package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import deco2800.skyfall.BaseGDXTest;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.gamemenu.GameMenuBar2;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.managers.TextureManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class GameMenuBar2Test extends BaseGDXTest {
    private Stage stage;
    private Skin skin;
    private TextureManager textureManager;
    private GameMenuManager gameMenuManager;
    private InventoryManager inventory;
    private GameMenuBar2 gameMenuBar2;

    @Before
    public void setUp() {
        stage = mock(Stage.class);
        gameMenuManager = mock(GameMenuManager.class);
        textureManager = mock(TextureManager.class);
        skin = spy(Skin.class);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        skin.add("white-text", labelStyle);
        skin.add("white-label", labelStyle);

        inventory = mock(InventoryManager.class);
        when(gameMenuManager.getInventory()).thenReturn(inventory);

        MainCharacter mc = mock(MainCharacter.class);
        Mockito.when(gameMenuManager.getMainCharacter()).thenReturn(mc);

        gameMenuBar2 = new GameMenuBar2(stage, null, textureManager, skin, gameMenuManager);
    }

    @Test
    public void setEquippedTest() {
        // Empty when game starts
        assertEquals("", gameMenuBar2.getEquipped());
        gameMenuBar2.setEquipped("pick axe");
        assertEquals("pick axe", gameMenuBar2.getEquipped());
    }
}
