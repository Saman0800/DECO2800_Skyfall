package deco2800.skyfall.entities.pets;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.PlayerPeon;
import deco2800.skyfall.managers.*;
import deco2800.skyfall.resources.GoldPiece;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GameManager.class, DatabaseManager.class, PlayerPeon.class})
public class LizardTest {

    private World w = null;
    private MainCharacter mc = null;
    private PhysicsManager physics;
    private Lizard lizard = null;
    @Mock
    private GameManager mockGM;

    /**
     * set up world
     */
    @Before
    public void Setup() {
        mc = new MainCharacter(1f, 1f, 1f, "Main Piece", 2);
        lizard = new Lizard(1, 1, mc);
        WorldBuilder worldBuilder = new WorldBuilder();
        WorldDirector.constructTestWorld(worldBuilder);
        w = worldBuilder.getWorld();
        mockGM = mock(GameManager.class);
        mockStatic(GameManager.class);
        when(GameManager.get()).thenReturn(mockGM);
        when(mockGM.getWorld()).thenReturn(w);
        InputManager Im = new InputManager();

        OnScreenMessageManager mockOSMM = mock(OnScreenMessageManager.class);
        when(mockGM.getManager(OnScreenMessageManager.class)).thenReturn(mockOSMM);

        physics = new PhysicsManager();
        when(mockGM.getManager(PhysicsManager.class)).thenReturn(physics);

        when(GameManager.getManagerFromInstance(InputManager.class)).thenReturn(Im);

    }

    @Test
    public void getOutSide() {
        Assert.assertEquals(lizard.getIsOutSide(), false);
    }

    /**
     * To test is out side method
     */
    @Test
    public void setOutSide() {
        lizard.setIsOutSide(true);
        Assert.assertEquals(lizard.getIsOutSide(), true);
    }

    /**
     * To test the pet will find the nearby gold
     */
    @Test
    public void findNearbyGold() {
        GoldPiece goldPiece = new GoldPiece(1);
        w.addEntity(goldPiece);
        w.addEntity(lizard);
        lizard.findNearbyGold();
        Assert.assertEquals((int) mc.getGoldPouchTotalValue(), 100);
    }

    /**
     * To test whether the pet will follow the mian character
     */
    @Test
    public void followingCharacter() {
        mc.setCol(10);
        mc.setRow(10);
        lizard.setDomesticated(true);
        lizard.followingCharacter();
        Assert.assertEquals((int) lizard.getCol(), 1);
        Assert.assertEquals((int) lizard.getRow(), 1);
    }


    /**
     * To test get name method
     * it will always return lizard
     */
    @Test
    public void getName() {
        Lizard lizard = new Lizard(0, 0, mc);
        Assert.assertEquals(lizard.getName(), "lizard");
    }

    /**
     * To test get sub type method
     * it will always return pets
     */
    @Test
    public void getSubtype() {
        Lizard lizard = new Lizard(0, 0, mc);
        Assert.assertEquals(lizard.getSubtype(), "pets");
    }

    /**
     * To test the pet is carryable
     */
    @Test
    public void isCarryable() {
        Lizard lizard = new Lizard(0, 0, mc);
        Assert.assertEquals(lizard.isCarryable(), true);
    }

    /**
     * To get the coordinates of this pet
     */
    @Test
    public void getCoords() {
        Lizard lizard = new Lizard(0, 0, mc);
        Assert.assertEquals(lizard.getCoords(), new HexVector(0, 0));
    }

    /**
     * To test this pet is exchangeable
     */
    @Test
    public void isExchangeable() {
        Lizard lizard = new Lizard(0, 0, mc);
        Assert.assertEquals(lizard.isExchangeable(), true);
    }

    /**
     * To test the description of lizard
     */
    @Test
    public void getDescription() {
        Lizard lizard = new Lizard(0, 0, mc);
        Assert.assertEquals(lizard.getDescription(), "pet lizard");
    }
}