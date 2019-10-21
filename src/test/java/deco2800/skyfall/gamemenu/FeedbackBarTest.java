package deco2800.skyfall.gamemenu;

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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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

    @After
    public void tearDown() throws Exception {
        fm = null;
        feedbackBar = null;
    }
}