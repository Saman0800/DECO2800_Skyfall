package deco2800.skyfall.gamemenu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import deco2800.skyfall.BaseGDXTest;
import deco2800.skyfall.managers.FeedbackManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.TextureManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FeedbackBarTest extends BaseGDXTest {

    private FeedbackManager fm;
    private GameMenuManager gmm;
    private TextureManager tm;
    private Stage stage;
    private Skin skin;
    private FeedbackBar feedbackBar;
    private ImageButton exit;

    @Before
    public void setUp() throws Exception {
        fm = new FeedbackManager();
        gmm = mock(GameMenuManager.class);
        tm = mock(TextureManager.class);
        stage = mock(Stage.class);
        skin = spy(Skin.class);
        exit = mock(ImageButton.class);

        Camera mockCam = mock(Camera.class);

        when(gmm.getTopLeftX()).thenReturn(10f);
        when(gmm.getBottomRightY()).thenReturn(100f);
        when(stage.getCamera()).thenReturn(mockCam);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        skin.add("white-text", labelStyle);

        feedbackBar = new FeedbackBar(stage, exit, null, tm, skin, gmm, fm);
    }

    //Feedback Manager tests
    @Test
    public void setFeedbackBarUpdateTest() {
        assertFalse(fm.getFeedbackBarUpdate());
        fm.setFeedbackBarUpdate(true);
        assertTrue(fm.getFeedbackBarUpdate());
    }

    @Test
    public void setFeedbackBarTextTest() {
        assertEquals("", fm.getFeedbackText());
        fm.setFeedbackText("test");
        assertEquals("test", fm.getFeedbackText());
    }

    //Feedback bar tests
    @Test
    public void drawTest() {
        verify(stage, times (1)).addActor(any(Actor.class));
    }

    @Test
    public void updateTextTest() {
        fm.setFeedbackText("test");
        feedbackBar.updateText(fm.getFeedbackText());
        assertFalse(fm.getFeedbackBarUpdate());
    }

    @Test
    public void updateTest() {
        fm.setFeedbackText("test");
        fm.setFeedbackBarUpdate(true);
        feedbackBar.update();
        verify(stage).getCamera();
        assertFalse(fm.getFeedbackBarUpdate());
    }

    @After
    public void tearDown() throws Exception {
        fm = null;
        feedbackBar = null;
    }
}