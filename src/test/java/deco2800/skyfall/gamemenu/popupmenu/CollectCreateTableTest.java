package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import deco2800.skyfall.BaseGDXTest;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.QuestManager;
import deco2800.skyfall.managers.TextureManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CollectCreateTableTest extends BaseGDXTest {
    private Skin skin;
    private Stage stage;
    private GameMenuManager gmm;
    private QuestManager qm;
    private CollectCreateTable element;
    private TextureManager tm;
    private ImageButton exitButton;

    @Before
    public void setUp() {
        stage = mock(Stage.class);
        skin = spy(Skin.class);
        gmm = mock(GameMenuManager.class);
        qm = mock(QuestManager.class);
        exitButton = mock(ImageButton.class);
        gmm = mock(GameMenuManager.class);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        skin.add("default", new BitmapFont());
        Label.LabelStyle ui = new Label.LabelStyle();
        TextButton.TextButtonStyle uiT = new TextButton.TextButtonStyle();
        ui.font = new BitmapFont();
        uiT.font = new BitmapFont();
        skin.add("blue-pill",ui);
        skin.add("title-pill",ui);
        skin.add("white-text",ui);
        skin.add("green-pill",ui);
        skin.add("default" ,uiT);

        MainCharacter mc = mock(MainCharacter.class);
        when(gmm.getMainCharacter()).thenReturn(mc);
        tm = mock(TextureManager.class);
        Camera c = new OrthographicCamera();
        c.viewportHeight = 100;
        c.viewportWidth = 100;
        c.position.x = 100;
        c.position.y = 100;

        doReturn(c).when(stage).getCamera();
    }


    @Test
    public void setCompleteTest1() {
        when(gmm.getQuestManager()).thenReturn(qm);

        element = new CollectCreateTable(stage, exitButton, null, tm,  gmm, skin, "collect");
        when(qm.checkGold()).thenReturn(true);
        when(qm.checkMetal()).thenReturn(true);
        when(qm.checkStone()).thenReturn(true);
        when(qm.checkWood()).thenReturn(true);
        when(qm.checkWeapons("sword")).thenReturn(true);
        when(qm.checkWeapons("spear")).thenReturn(true);
        when(qm.checkWeapons("bow")).thenReturn(true);
        when(qm.checkWeapons("axe")).thenReturn(true);
        element.setComplete();
        assertTrue(element.getComplete().isVisible());
    }


    @Test
    public void setCompleteTest2() {
        when(gmm.getQuestManager()).thenReturn(qm);

        element = new CollectCreateTable(stage, exitButton, null, tm,  gmm, skin, "collect");

        when(qm.checkGold()).thenReturn(false);
        when(qm.checkMetal()).thenReturn(false);
        element.setComplete();
        assertFalse(element.getComplete().isVisible());
    }

    @Test
    public void updateTextTest() {
        when(gmm.getQuestManager()).thenReturn(qm);

        element = new CollectCreateTable(stage, exitButton, null, tm,  gmm, skin, "collect");

        when(qm.getGoldTotal()).thenReturn(30);
        when(qm.getMetalTotal()).thenReturn(30);
        when(qm.getWoodTotal()).thenReturn(30);
        when(qm.getStoneTotal()).thenReturn(30);

        when(qm.getWeaponsTotal("sword")).thenReturn(10);
        when(qm.getWeaponsTotal("spear")).thenReturn(10);
        when(qm.getWeaponsTotal("bow")).thenReturn(10);
        when(qm.getWeaponsTotal("axe")).thenReturn(10);

        element.updateText();

        assertEquals("30 x Gold" ,element.getLabelGold().getText().toString());
        assertEquals("30 x Metal" ,element.getLabelMetal().getText().toString());
        assertEquals("30 x Wood" ,element.getLabelWood().getText().toString());
        assertEquals("30 x Stone" ,element.getLabelStone().getText().toString());

        assertEquals("10 x Sword" ,element.getLabelSword().getText().toString());
        assertEquals("10 x Spear" ,
                element.getLabelSpear().getText().toString());
        assertEquals("10 x Bow" ,element.getLabelBow().getText().toString());
        assertEquals("10 x Axe" ,
                element.getLabelAxe().getText().toString());

    }

}