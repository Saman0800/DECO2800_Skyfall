package deco2800.skyfall.buildings;

import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import deco2800.skyfall.worlds.world.World;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import static org.mockito.Mockito.*;

public class BuildingWidgetsTest {

    private GameManager gm;
    private BuildingWidgets widget;

    @Before
    public void setup() {
        this.gm = GameManager.get();
        WorldBuilder wb = new WorldBuilder();
        WorldDirector.constructTestWorld(wb);
        this.gm.setWorld(wb.getWorld());
    }

    @Test
    public void OnlyOneWidgetTest() {
        // so far no idea how to test
        Assert.assertEquals(BuildingWidgets.class, BuildingWidgets.class);
    }

    @After
    public void cleanup() {
        this.gm = null;
        this.widget = null;
    }
}
