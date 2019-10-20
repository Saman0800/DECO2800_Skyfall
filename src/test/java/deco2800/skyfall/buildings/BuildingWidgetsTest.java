package deco2800.skyfall.buildings;

import com.badlogic.gdx.scenes.scene2d.Actor;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.worlds.world.World;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import deco2800.skyfall.BaseGDXTest;
import org.junit.Before;
import org.junit.Test;

public class BuildingWidgetsTest extends BaseGDXTest {

    private GameManager gm;
    private World world;
    private BuildingWidgets widget;

    @Before
    public void setup() {
        gm = GameManager.get();
        world = mock(World.class);   // using mock world
        gm.setWorld(world);
        widget = BuildingWidgets.get(GameManager.get());
    }

    @Test
    public void testOnlyOneWidget() {
        BuildingWidgets widget2 = BuildingWidgets.get(gm);
        assertEquals(widget, widget2);
    }

    @Test
    public void testWidgetVisible() {
        assertFalse(widget.getMenu().isVisible());
        widget = mock(BuildingWidgets.class);
        Table table = mock(Table.class);
        when(widget.getMenu()).thenReturn(table);
        when(table.isVisible()).thenReturn(false);
        widget.getMenu().setVisible(true);
        assertFalse(widget.getMenu().isVisible());
    }

    @Test
    public void testWidgetComponent() {
        assertEquals(5, widget.getMenu().getChildren().size);
        for (Actor actor : widget.getMenu().getChildren()) {
            if ((actor instanceof Label) || (actor instanceof TextButton) || (actor instanceof ProgressBar)) {
                continue;
            }
            fail("Unknown components");
        }
    }
}
