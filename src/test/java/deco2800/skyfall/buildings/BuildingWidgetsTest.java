package deco2800.skyfall.buildings;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

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
    public void testWidgetComponentSize() {
        assertEquals(5, widget.getMenu().getChildren().size);
    }
}
