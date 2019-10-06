package deco2800.skyfall.worlds.packing;

import deco2800.skyfall.managers.GameManager;
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
        world = mock(World.class);
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
    public void birthPlacePackingTest() {
        assertEquals(1, 1);
    }

    @After
    public void clean() {
        gm = null;
        world = null;
        packer = null;
    }
}
