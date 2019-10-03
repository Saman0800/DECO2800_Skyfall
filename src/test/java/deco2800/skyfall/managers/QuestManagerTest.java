package deco2800.skyfall.managers;

import deco2800.skyfall.resources.GoldPiece;
import deco2800.skyfall.worlds.world.World;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class QuestManagerTest {

    //Test manager
    private QuestManager manager;
    private GameManager gameManager = GameManager.get();
    private InputManager mockIM = mock(InputManager.class);
    private PetsManager mockPM = mock(PetsManager.class);
    private InventoryManager mockInvM = mock(InventoryManager.class);
    private AnimationManager mockAM = mock(AnimationManager.class);

    @Before
    public void setUp() {
        manager = new QuestManager();
        gameManager.addManager(mockIM);
        gameManager.addManager(mockPM);
        gameManager.addManager(mockInvM);
        gameManager.addManager(mockAM);
    }

    @Test
    public void getQuestLevelTest() {
        assertEquals(1, manager.getQuestLevel());
    }

    @Test
    public void setMilestonesTest() {
        manager.setQuestLevel(2);
        assertEquals(600, manager.getGoldTotal());
    }

    @Test
    public void setGoldTotalTest() {
        manager.setGoldTotal(500);
        assertEquals(500, manager.getGoldTotal());
    }

    @Test
    public void setWoodTotalTest() {
        manager.setWoodTotal(500);
        assertEquals(500, manager.getWoodTotal());
    }

    @Test
    public void setStoneTotalTest() {
        manager.setStoneTotal(500);
        assertEquals(500, manager.getStoneTotal());
    }

    @Test
    public void setMetalTotalTest() {
        manager.setMetalTotal(500);
        assertEquals(500, manager.getMetalTotal());
    }

    @Test
    public void setBuildingsTotalTest() {
        List<String> testBuildings = new ArrayList<>();
        testBuildings.add("Cabin");
        testBuildings.add("Fence");

        manager.setBuildingsTotal(testBuildings);
        assertEquals(testBuildings, manager.getBuildingsTotal());
    }

    @Test
    public void checkGoldTest() {
        assertFalse(manager.checkGold());
        GoldPiece extraGold = new GoldPiece(100);
        manager.getPlayer().addGold(extraGold, 2);
        assertTrue(manager.checkGold());
    }

}