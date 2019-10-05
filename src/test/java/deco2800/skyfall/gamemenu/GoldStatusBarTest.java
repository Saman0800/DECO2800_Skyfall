package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import deco2800.skyfall.BaseGDXTest;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class GoldStatusBarTest extends BaseGDXTest{

    private Skin skin;
    private Stage stage;
    private GameMenuManager gmm;
    private GoldStatusBar gsb;
    private TextureManager tm;

    @Before
    public void setUp () {
        stage = mock(Stage.class);
        skin = spy(Skin.class);
        gmm = mock(GameMenuManager.class);
        tm = mock(TextureManager.class);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        skin.add("default", new BitmapFont());
        Label.LabelStyle ui = new Label.LabelStyle();


        ui.font = new BitmapFont();
        skin.add("blue-pill",ui);
        gsb = new GoldStatusBar(stage, null, tm , skin, gmm);
    }

    @Test
    public void updateLabelTest() {
        MainCharacter mc = mock(MainCharacter.class);
        when(mc.getGoldPouchTotalValue()).thenReturn(10);
        when(gmm.getMainCharacter()).thenReturn(mc);

        gsb.updateLabel();
        assertEquals(" 10 G   ", gsb.getGoldLabel().getText().toString());
    }

    @After
    public void tearDown() {
        skin = null;
        stage = null;
        gmm = null;
        gsb = null;
        tm = null;
    }
}