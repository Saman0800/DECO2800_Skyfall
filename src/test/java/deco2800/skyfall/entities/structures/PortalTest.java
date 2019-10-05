package deco2800.skyfall.entities.structures;

import deco2800.skyfall.buildings.AbstractPortal;
import deco2800.skyfall.buildings.ForestPortal;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.biomes.DesertBiome;
import deco2800.skyfall.worlds.world.ServerWorld;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class PortalTest {

    public World world;


    @Before
    public void setup() {
        world = mock(World.class);
        GameManager.get().setWorld(world);

    }

    @Test
    public void initialiselockedBiomes() {
        MainCharacter character = new MainCharacter(1,1,1, "character", 10);
        ArrayList<String> lockedBiomes = new ArrayList<>();

        lockedBiomes.add("desert");
        lockedBiomes.add("mountain");
        lockedBiomes.add("volcanic_mountain");

        assertEquals(lockedBiomes, character.getlockedBiomes());

    }

    @Test
    public void unlockBiome() {
        MainCharacter character = new MainCharacter(1,1,1, "character", 10);
        ArrayList<String> lockedBiomes = new ArrayList<>();

        lockedBiomes.add("mountain");
        lockedBiomes.add("volcanic_mountain");

        assertNotEquals(lockedBiomes, character.getlockedBiomes());

        character.unlockBiome("desert");
        assertEquals(lockedBiomes, character.getlockedBiomes());

    }


    @Test
    public void unlockDesert() {
        MainCharacter character = new MainCharacter(1,1,1, "character", 10);
        ArrayList<String> lockedBiomes = new ArrayList<>();


        lockedBiomes.add("mountain");
        lockedBiomes.add("volcanic_mountain");

        assertNotEquals(lockedBiomes, character.getlockedBiomes());

        ForestPortal portal = new ForestPortal(2,2,1);
        portal.teleport(character, world);

        assertEquals(lockedBiomes, character.getlockedBiomes());

    }

    @Test
    public void testForestPortal() {

        AbstractPortal portal = new ForestPortal(1,1,1);
        assertEquals("desert", portal.nextBiome);

    }

    @Test
    public void testTeleportation() {
        MainCharacter character = new MainCharacter(1,1,1, "character", 10);

        //WorldBuilder builder = Mockito.mock(WorldBuilder.class);
        //WorldDirector.constructNBiomeSinglePlayerWorld(builder, 3, false);
        //verify(builder).setType("single_player");
        //verify(builder).setWorldSize(100);
        //verify(builder).setNodeSpacing(20);

        //Tile characterTile1= character.getTile(character.getCol(),character.getRow());
        //assertEquals("desert", characterTile1.getBiome().getBiomeName());

        ForestPortal portal = new ForestPortal(2,2,1);
        portal.teleport(character, world);

        assertNotEquals(1,character.getCol());
        assertNotEquals(1, character.getRow());

        //Tile characterTile2= character.getTile(character.getCol(),character.getRow());

        //assertEquals("desert", characterTile2.getBiome().getBiomeName());

    }




}
