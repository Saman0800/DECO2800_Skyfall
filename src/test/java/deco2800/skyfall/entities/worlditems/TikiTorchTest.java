package deco2800.skyfall.entities.worlditems;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import deco2800.skyfall.entities.SaveableEntity;
import deco2800.skyfall.graphics.PointLight;
import deco2800.skyfall.graphics.types.Vec2;
import deco2800.skyfall.graphics.types.Vec3;
import deco2800.skyfall.managers.EnvironmentManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GameManager.class })
public class TikiTorchTest {

    private TikiTorch testTikiTorch;
    public EnvironmentManager mockEnvironManager;
    public GameManager mockGameManager;

    @Before
    public void Setup() throws Exception {
        mockEnvironManager = mock(EnvironmentManager.class);
        mockGameManager = mock(GameManager.class);
        when(mockEnvironManager.getHourDecimal()).thenReturn(0.2f).thenReturn(0.3f);
        PowerMockito.mockStatic(GameManager.class);
        BDDMockito.given(GameManager.getManagerFromInstance(EnvironmentManager.class)).willReturn(mockEnvironManager);
    }

    @Test
    public void basicConstructorTest() {

        testTikiTorch = new TikiTorch();
        assertEquals(testTikiTorch.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testTikiTorch.getRenderOrder());
        assertEquals(testTikiTorch.getCol(), 0.0f, 0.001f);
        assertEquals(testTikiTorch.getRow(), 0.0f, 0.001f);
        assertTrue(testTikiTorch.isObstructed());
        assertEquals("tiki_torch", testTikiTorch.getObjectName());
        assertEquals("TikiTorch", testTikiTorch.getEntityType());
        String returnedTexture = testTikiTorch.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("tikitorch"));
    }

    @Test
    public void mementoConstructorTest() {

        TikiTorch toSave = new TikiTorch();
        SaveableEntity.SaveableEntityMemento testTikiTorchMemento = toSave.save();
        testTikiTorch = new TikiTorch(testTikiTorchMemento);
        assertEquals(testTikiTorch.getPosition(), new HexVector(0.0f, 0.0f));
        assertEquals(0, testTikiTorch.getRenderOrder());
        assertEquals(testTikiTorch.getCol(), 0.0f, 0.001f);
        assertEquals(testTikiTorch.getRow(), 0.0f, 0.001f);
        assertTrue(testTikiTorch.isObstructed());
        assertEquals("tiki_torch", testTikiTorch.getObjectName());
        assertEquals("TikiTorch", testTikiTorch.getEntityType());
        String returnedTexture = testTikiTorch.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("tikitorch"));
    }

    @Test
    public void tileConstructorTest() {

        Tile testTile = new Tile(null, 0.5f, 0.5f);

        testTikiTorch = new TikiTorch(testTile, false);
        assertEquals(testTikiTorch.getPosition(), new HexVector(0.5f, 0.5f));
        assertEquals(2, testTikiTorch.getRenderOrder());
        assertEquals(testTikiTorch.getCol(), 0.5f, 0.001f);
        assertEquals(testTikiTorch.getRow(), 0.5f, 0.001f);
        assertFalse(testTikiTorch.isObstructed());
        assertEquals("tiki_torch", testTikiTorch.getObjectName());
        assertEquals("TikiTorch", testTikiTorch.getEntityType());
        String returnedTexture = testTikiTorch.getTexture();
        assertTrue("" + returnedTexture + " is an unexpected texture name", returnedTexture.equals("tikitorch"));
    }

    @Test
    public void getLightTest() {
        // Make sure the same point light is returned between calls.
        testTikiTorch = new TikiTorch();
        testTikiTorch.pointLightSetUp();
        assertEquals(testTikiTorch.getPointLight(), testTikiTorch.getPointLight());

        float kValue = (float) Math.sin(0.5 * 0.2) * 0.15f + 1.2f;

        PointLight testLight = new PointLight(new Vec2(0, 0), new Vec3(1.0f, 0.729f, 0.3372f), kValue, 0.5f);
        PointLight torchLight = testTikiTorch.getPointLight();

        assertEquals(testLight.getColour(), torchLight.getColour());
        assertTrue(((float) Math.sin(100 * 0.2) * 0.15f + 1.2f >= torchLight.getK()));
        assertTrue(((float) Math.sin(250 * 0.2) * 0.15f + 1.2f <= torchLight.getK()));
        assertEquals(testLight.getA(), torchLight.getA(), 0.001);

        testTikiTorch.updatePointLight();

        // Nothing should change from the update
        assertEquals(testLight.getColour(), torchLight.getColour());
        assertTrue(((float) Math.sin(100 * 0.4) * 0.15f + 1.2f >= torchLight.getK()));
        assertTrue(((float) Math.sin(250 * 0.4) * 0.15f + 1.2f <= torchLight.getK()));
        assertEquals(testLight.getA(), torchLight.getA(), 0.001);
    }
}