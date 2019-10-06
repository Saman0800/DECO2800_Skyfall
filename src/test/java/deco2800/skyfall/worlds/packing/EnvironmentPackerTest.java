package deco2800.skyfall.worlds.packing;

import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.biomes.ForestBiome;
import deco2800.skyfall.worlds.world.World;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EnvironmentPackerTest {

    private GameManager gm;
    private World world;
    private EnvironmentPacker packer;

    @Before
    public void setup() {
        gm = GameManager.get();
        world = mock(World.class);   // using mock world
        gm.setWorld(world);
        packer = new EnvironmentPacker(world);
    }

    @Test
    public void packerPackingListTest() {
        AbstractPacking packing = new BirthPlacePacking(packer);
        packer.doPackings();
        assertFalse(packer.addPackingComponent(null));
        assertTrue(packer.addPackingComponent(packing));
        assertFalse(packer.removePackingComponent((AbstractPacking) null));
        assertTrue(packer.removePackingComponent(packing));
        assertFalse(packer.removePackingComponent(BirthPlacePacking.class));
    }

    @Test
    public void changeTileBiomeTest() {
        BirthPlacePacking packing = new BirthPlacePacking(packer);
        assertFalse(packing.changeTileBiome(0f, 0f, null));
        ForestBiome biome = mock(ForestBiome.class);
        Tile tile = mock(Tile.class);
        when(world.getTile(-32f, 0f)).thenReturn(tile);
        assertFalse(packing.changeTileBiome(-32f, 0f, biome));
    }

    @Test
    public void removeEntityOnTileTest() {
        BirthPlacePacking packing = new BirthPlacePacking(packer);
        Tile tile = new Tile(world, 0f, 0f);
        when(world.getTile(0f, 0f)).thenReturn(tile);
        assertTrue(packing.removeEntityOnTile(0f, 0f));
        when(world.getTile(1f, 2f)).thenReturn(null);
        assertFalse(packing.removeEntityOnTile(1f, 2f));
    }

    @Test
    public void moveEntityFromTileToTileTest() {
        BirthPlacePacking packing = new BirthPlacePacking(packer);
        Tile tile1 = new Tile(world, 0f, 0f);
        Tile tile2 = new Tile(world, 4f, 2f);
        when(world.getTile(0f, 0f)).thenReturn(tile1);
        when(world.getTile(4f, 2f)).thenReturn(tile2);
        when(world.getTile(3f, 2f)).thenReturn(null);
        assertTrue(packing.moveEntityFromTileToTile(0f, 0f, 0f, 0f));
        assertTrue(packing.moveEntityFromTileToTile(0f, 0f, 4f, 2f));
        assertFalse(packing.moveEntityFromTileToTile(0f, 0f, 3f, 2f));
        assertFalse(packing.moveEntityFromTileToTile(3f, 2f, 4f, 2f));
    }

    @After
    public void clean() {
        gm = null;
        world = null;
        packer = null;
    }
}
