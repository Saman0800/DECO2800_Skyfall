package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import deco2800.skyfall.BaseGDXTest;
import deco2800.skyfall.managers.EnvironmentManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.managers.TextureManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;

public class HealthCircleTest extends BaseGDXTest{
    private Skin skin;
    private Stage stage;
    private GameMenuManager gmm;
    private HealthCircle healthCircle;
    private TextureManager tm;
    private StatisticsManager sm;

    @Before
    public void setUp () {
        stage = mock(Stage.class);
        skin = spy(Skin.class);
        gmm = mock(GameMenuManager.class);
        tm = spy(TextureManager.class);
        sm = mock(StatisticsManager.class);
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        skin.add("default", new BitmapFont());
        Label.LabelStyle ui = new Label.LabelStyle();


        ui.font = new BitmapFont();
        skin.add("blue-pill",ui);
        healthCircle = new HealthCircle(stage, new String[]{"inner_circle", "big_circle"}, tm, sm, skin, gmm);
    }

    @Test
    public void drawTest() {
        verify(stage,times(3)).addActor(any(Actor.class));
    }

    @Test
    public void updateInnerCircle1() {
        Camera c = new OrthographicCamera();
        c.viewportHeight = 100;
        c.viewportWidth = 100;
        c.position.x = 100;
        c.position.y = 100;

        doReturn(c).when(stage).getCamera();

        when(gmm.getTopRightX()).thenReturn(0f);
        when(gmm.getTopRightY()).thenReturn(0f);

        when(sm.getHealth()).thenReturn(50);
        healthCircle.update();

        assertEquals(100, (int) healthCircle.getInnerCircle().getWidth());
    }

    @Test
    public void updateInnerCircle2() {
        Camera c = new OrthographicCamera();
        c.viewportHeight = 100;
        c.viewportWidth = 100;
        c.position.x = 100;
        c.position.y = 100;

        doReturn(c).when(stage).getCamera();

        when(gmm.getTopRightX()).thenReturn(0f);
        when(gmm.getTopRightY()).thenReturn(0f);

        when(sm.getHealth()).thenReturn(0);
        healthCircle.update();

        assertEquals(0, (int) healthCircle.getInnerCircle().getWidth());
    }

    @After
    public void tearDown() {
        skin = null;
        stage = null;
        gmm = null;
        healthCircle = null;
        tm = null;
        sm = null;
    }

}
