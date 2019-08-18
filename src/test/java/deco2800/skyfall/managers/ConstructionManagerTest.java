package deco2800.skyfall.managers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import deco2800.skyfall.managers.ConstructionManager;
import deco2800.skyfall.worlds.AbstractWorld;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.entities.AbstractEntity;

//Add all tests related to the construction manager
public class ConstructionManagerTest {
    private ConstructionManager cmgr;

    @Before
    public void setup() {
        this.cmgr = new ConstructionManager();
    }

    @Test
    public void verifyNullTest() {
        Assert.assertEquals(false, this.cmgr.verifyTerrain(null));
        Assert.assertEquals(false, this.cmgr.verifyBiome(null));
        Assert.assertEquals(false, this.cmgr.verifyEntity(null, null));
        Assert.assertEquals(false, this.cmgr.isTilesBuildable(null, null));
    }

    @Test
    public void testVerifyEntity(){
        Assert.assertFalse(cmgr.verifyEntity(null, null));
    }

    @After
    public void cleanup() {
        this.cmgr = null;
    }
}
