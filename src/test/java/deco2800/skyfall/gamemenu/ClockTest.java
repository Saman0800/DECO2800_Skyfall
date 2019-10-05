package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import deco2800.skyfall.BaseGDXTest;
import deco2800.skyfall.managers.EnvironmentManager;
import deco2800.skyfall.managers.GameMenuManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;

public class ClockTest extends BaseGDXTest{
    Skin skin;
    Stage stage;
    GameMenuManager gmm;
    Clock clock;
    private EnvironmentManager em;

    @Before
    public void setUp () {
        stage = mock(Stage.class);
        skin = spy(Skin.class);
        gmm = mock(GameMenuManager.class);
        em = mock(EnvironmentManager.class);
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        skin.add("default", new BitmapFont());
        Label.LabelStyle ui = new Label.LabelStyle();


        ui.font = new BitmapFont();
        skin.add("blue-pill",ui);
        clock = new Clock(stage, skin, gmm, em);
    }

    @Test
    public void drawTest() {
        verify(stage,times(2)).addActor(any(Actor.class));
    }

    @Test
    public void clockUpdateDisplayTest1() {
        when(em.getTime()).thenReturn(10);
        when(em.getMinutes()).thenReturn(10);
        clock.updateDisplay();
        assertEquals("10 : 10", clock.getClockLabel().getText().toString());
    }
    @Test
    public void clockUpdateDisplayTest2() {
        when(em.getTime()).thenReturn(10);
        when(em.getMinutes()).thenReturn(2);
        clock.updateDisplay();
        assertEquals("10 : 02", clock.getClockLabel().getText().toString());
    }

    @Test
    public void clockUpdatePositionTest() {
        when(gmm.getTopLeftX()).thenReturn(0f);
        when(gmm.getTopLeftY()).thenReturn(0f);


        clock.updatePosition();
        verify(gmm, times(2)).getTopLeftX();
        verify(gmm, times(2)).getTopLeftY();

    }


}