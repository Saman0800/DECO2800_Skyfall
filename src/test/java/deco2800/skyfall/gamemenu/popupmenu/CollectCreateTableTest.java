package deco2800.skyfall.gamemenu.popupmenu;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.gamemenu.AbstractPopUpElement;
import deco2800.skyfall.gamemenu.Clock;

import deco2800.skyfall.managers.*;
import org.junit.Before;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class CollectCreateTableTest {
    private Skin skin;
    private Stage stage;
    private GameMenuManager gmm;
    private QuestManager qm;
    private AbstractPopUpElement element;
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

        MainCharacter mc = mock(MainCharacter.class);
        when(gmm.getMainCharacter()).thenReturn(mc);
        tm = mock(TextureManager.class);

        element = new CollectCreateTable(stage, exitButton, null, tm,  gmm, qm, skin, "collect");

    }


}