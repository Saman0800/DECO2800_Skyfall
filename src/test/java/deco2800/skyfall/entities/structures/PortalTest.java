package deco2800.skyfall.entities.structures;

import deco2800.skyfall.buildings.*;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.database.DataBaseConnector;
import deco2800.skyfall.saving.Save;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.world.Chunk;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WorldBuilder.class, WorldDirector.class, DatabaseManager.class, DataBaseConnector.class,
        GameManager.class })
public class PortalTest {

    public World world;
    private Save save;

    @Before
    public void setup() {
        world = WorldDirector.constructTestWorld(new WorldBuilder(), 0).getWorld();
        save = mock(Save.class);
        save.setCurrentWorld(world);
        when(save.getCurrentWorld()).thenReturn(world);
        DataBaseConnector connector = mock(DataBaseConnector.class);
        when(connector.loadChunk(any(World.class), anyInt(), anyInt())).then((Answer<Chunk>) invocation -> {
            Chunk chunk = new Chunk(invocation.getArgumentAt(0, World.class),
                    invocation.getArgumentAt(1, Integer.class), invocation.getArgumentAt(2, Integer.class));
            chunk.generateEntities();
            return chunk;
        });

        DatabaseManager manager = mock(DatabaseManager.class);
        when(manager.getDataBaseConnector()).thenReturn(connector);

        mockStatic(DatabaseManager.class);
        when(DatabaseManager.get()).thenReturn(manager);

        GameManager.get().setWorld(world);
    }

    @Test
    public void testForestPortal() {
        AbstractPortal portal = new ForestPortal(1, 1, 1);
        assertEquals("desert", portal.getNext());
    }

    @Test
    public void testDesertPortal() {
        AbstractPortal portal = new DesertPortal(1, 1, 1);
        assertEquals("mountain", portal.getNext());
    }

    @Test
    public void testMountainPortal() {
        AbstractPortal portal = new MountainPortal(1, 1, 1);
        assertEquals("volcanic_mountains", portal.getNext());
    }

    @Test
    public void testVolcanoPortal() {
        AbstractPortal portal = new VolcanoPortal(1, 1, 1);
        assertEquals("volcanic_mountains", portal.getNext());
    }

    @Test
    public void testTeleportation() {
        MainCharacter character = new MainCharacter(1,1,1, "character", 10);
        when(save.getMainCharacter()).thenReturn(character);
        save.setMainCharacter(character);
        character.setSave(save);

        ForestPortal portal = new ForestPortal(2,2,1);
        portal.teleport(save);

        Tile characterTile1 = character.getTile(character.getCol(),character.getRow());
        assertEquals("desert", characterTile1.getBiome().getBiomeName());
        assertEquals(0, character.getCol(), 0);
        assertEquals(0, character.getRow(), 0);
    }

    @After
    public void cleanup(){
        world = null;
        save = null;
    }
}
