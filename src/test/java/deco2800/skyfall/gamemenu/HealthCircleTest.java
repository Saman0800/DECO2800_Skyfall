package deco2800.skyfall.gamemenu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.InventoryManager;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.managers.TextureManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//import static org.junit.Assert.*;

public class HealthCircleTest {
    HealthCircle hc;
    Stage stage;
    TextureManager tm;
    StatisticsManager sm;
    @Before
    public void setUp (){
        tm = mock(TextureManager.class);
        stage = mock(Stage.class);
        sm = mock(StatisticsManager.class);
        Texture t1 = mock(Texture.class);
        Texture t2 = mock(Texture.class);

        when(tm.getTexture("inner_circle")).thenReturn(t1);
        when(tm.getTexture("big_circle")).thenReturn(t2);

        hc = new HealthCircle(stage, new String[]{"inner_circle", "big_circle"}, tm, sm);

    }

    @After
    public void tearDown (){



    }

}