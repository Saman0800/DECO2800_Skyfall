package deco2800.skyfall.gamemenu.popupmenu;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

import deco2800.skyfall.buildings.BuildingEntity;
import deco2800.skyfall.buildings.BuildingType;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.StatisticsManager;
import deco2800.skyfall.managers.TextureManager;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;

@PrepareForTest({ WorldBuilder.class, TextureManager.class,
        GameMenuManager.class, StatisticsManager.class })
@Ignore
public class ConstructionTableTest {

    World world;

    @Before
    public void setup() {
        WorldBuilder wb = new WorldBuilder();
        WorldDirector.constructTestWorld(wb, 0);
        world = wb.getWorld();
    }

    @Test
    public void testBuild() {
        ConstructionTable constructionTable = mock(ConstructionTable.class);
        // when(constructionTable.getBuildingID()).thenCallRealMethod();
        doCallRealMethod().when(constructionTable).setBuildingID(
                any(BuildingType.class));
        doCallRealMethod().when(constructionTable).build(any(World.class),
                any(Integer.class), any(Integer.class));
        constructionTable.setBuildingID(BuildingType.CABIN);
        constructionTable.build(world, 0, 0);

        for (AbstractEntity e : world.getEntities()) {
            if (e instanceof BuildingEntity) {
                BuildingEntity entity = (BuildingEntity) e;
                if (entity.getBuildingType() == BuildingType.CABIN) {
                    return;
                }
            }
        }
        fail();
    }
}
