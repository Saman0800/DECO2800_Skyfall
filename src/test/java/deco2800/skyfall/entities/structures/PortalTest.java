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
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.*;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WorldBuilder.class, WorldDirector.class, DatabaseManager.class, DataBaseConnector.class,
        GameManager.class })
public class PortalTest {

    public World world;
    private Save save;


    @Before
    public void setup() {
        world = mock(World.class);
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
    public void initialiselockedBiomes() {
        MainCharacter character = new MainCharacter(1,1,1, "character", 10);
        ArrayList<String> lockedBiomes = new ArrayList<>();

        lockedBiomes.add("desert");
        lockedBiomes.add("mountain");
        lockedBiomes.add("volcanic_mountains");

        assertEquals(lockedBiomes, character.getlockedBiomes());

    }

    @Test
    public void unlockBiome() {
        MainCharacter character = new MainCharacter(1,1,1, "character", 10);
        ArrayList<String> lockedBiomes = new ArrayList<>();

        lockedBiomes.add("mountain");
        lockedBiomes.add("volcanic_mountains");

        assertNotEquals(lockedBiomes, character.getlockedBiomes());

        character.unlockBiome("desert");
        assertEquals(lockedBiomes, character.getlockedBiomes());

    }


    @Test
    public void teleportUnlockBiomes() {
        MainCharacter character = new MainCharacter(1,1,1, "character", 10);
        when(save.getMainCharacter()).thenReturn(character);
        save.setMainCharacter(character);
        character.setSave(save);
        ArrayList<String> lockedBiomes = new ArrayList<>();


        lockedBiomes.add("mountain");
        lockedBiomes.add("volcanic_mountains");

        assertNotEquals(lockedBiomes, character.getlockedBiomes());

        ForestPortal forestPortal = new ForestPortal(2,2,1);
        forestPortal.teleport(save);

        assertEquals(lockedBiomes, character.getlockedBiomes());

        lockedBiomes.remove("mountain");
        assertNotEquals(lockedBiomes, character.getlockedBiomes());

        DesertPortal desertPortal = new DesertPortal(2,2,1);
        desertPortal.teleport(save);

        assertEquals(lockedBiomes, character.getlockedBiomes());

        lockedBiomes.remove("volcanic_mountains");
        assertNotEquals(lockedBiomes, character.getlockedBiomes());

        MountainPortal mountainPortal = new MountainPortal(2,2,1);
        mountainPortal.teleport(save);

        assertEquals(lockedBiomes, character.getlockedBiomes());
    }

    @Test
    public void testForestPortal() {

        AbstractPortal portal = new ForestPortal(1,1,1);
        assertEquals("desert", portal.getNextBiome());

    }

    @Test
    public void testDesertPortal() {

        AbstractPortal portal = new DesertPortal(1,1,1);
        assertEquals("mountain", portal.getNextBiome());

    }

    @Test
    public void testMountainPortal() {

        AbstractPortal portal = new MountainPortal(1,1,1);
        assertEquals("volcanic_mountains", portal.getNextBiome());

    }

    @Test
    public void testVolcanoPortal() {

        AbstractPortal portal = new VolcanoPortal(1,1,1);
        assertEquals("volcanic_mountains", portal.getNextBiome());

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




}
