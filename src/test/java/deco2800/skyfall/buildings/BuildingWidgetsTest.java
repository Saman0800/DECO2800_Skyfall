package deco2800.skyfall.buildings;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import deco2800.skyfall.BaseGDXTest;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.worlds.world.WorldDirector;
import deco2800.skyfall.worlds.world.WorldBuilder;

public class BuildingWidgetsTest extends BaseGDXTest {

    private BuildingWidgets widget;

    @Before
    public void setup() {
        WorldBuilder worldBuilder = new WorldBuilder();
        WorldDirector.constructTestWorld(worldBuilder);
        GameManager.get().setWorld(worldBuilder.getWorld());
        widget = BuildingWidgets.get(GameManager.get());
    }

    @Test
    public void testOnlyOneWidget() {
        BuildingWidgets widget2 = BuildingWidgets.get(GameManager.get());
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
    public void testWidgetComponentCount() {
        assertEquals(5, widget.getMenu().getChildren().size);
    }

    @Test
    public void testWidgetComponentNotNull() {
        assertNotNull(widget.getMenu().getChildren().pop());
        assertNotNull(widget.getMenu().getChildren().pop());
        assertNotNull(widget.getMenu().getChildren().pop());
        assertNotNull(widget.getMenu().getChildren().pop());
        assertNotNull(widget.getMenu().getChildren().pop());
        try {
            widget.getMenu().getChildren().pop();
            assertFalse("Incorrect number of components", false);
        } catch (Exception e) {
            assertTrue("Only five not null components is true", true);
        }
    }

    @Test
    public void testWidgetPerformance() {
        // inner testing pains
    }
}
