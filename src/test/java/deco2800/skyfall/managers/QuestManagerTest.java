package deco2800.skyfall.managers;

import deco2800.skyfall.buildings.BuildingEntity;
import deco2800.skyfall.buildings.BuildingType;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.resources.GoldPiece;
import deco2800.skyfall.resources.items.Metal;
import deco2800.skyfall.resources.items.Stone;
import deco2800.skyfall.resources.items.Wood;
import deco2800.skyfall.worlds.world.World;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({BuildingType.class})
public class QuestManagerTest {

    //Test managers
    private QuestManager manager;
    private GameManager gameManager = GameManager.get();

    //Mock objects
    private World mockWorld = mock(World.class);
    private List<AbstractEntity> mockEntities = mock(List.class);
    private BuildingEntity mockCabin = mock(BuildingEntity.class);
    private BuildingEntity mockWatchTower = mock(BuildingEntity.class);
    private BuildingType mockBuilding = mock(BuildingType.class);

    @Before
    public void setUp() {
        manager = new QuestManager();
        gameManager.setWorld(mockWorld);
    }

    @Test
    @Ignore //TODO: @Kausta fix this tomorrow
    public void getQuestLevelTest() {
        assertEquals(0, manager.getQuestLevel());
    }

    @Test
    public void setMilestonesTest() {
        manager.setQuestLevel(2);
        assertTrue( manager.getGoldTotal() > 0);
    }
    @Test
    public void setMilestonesLevel2Test() {
        manager.setQuestLevel(2);
        assertEquals(2, manager.getQuestLevel());

    }

    @Test
    public void level2GoldTotalTest() {
        manager.setQuestLevel(2);
        manager.setGoldTotal(100);
        assertEquals(100, manager.getGoldTotal());

    }

    @Test
    public void level2WoodTotalTest() {
        manager.setQuestLevel(2);
        manager.setWoodTotal(200);
        assertEquals(200, manager.getWoodTotal());

    }

    @Test
    public void level2StoneTotalTest() {
        manager.setQuestLevel(2);
        manager.setStoneTotal(300);
        assertEquals(300, manager.getStoneTotal());

    }

    @Test
    public void level2MetalTotalTest() {
        manager.setQuestLevel(2);
        manager.setMetalTotal(400);
        assertEquals(400, manager.getMetalTotal());

    }

    @Test
    public void level2WeaponTotalTest() {
        manager.setQuestLevel(2);
        manager.setWeaponTotal("bow", 20);
        assertEquals(20, manager.getWeaponsTotal("bow"));
        manager.setWeaponTotal("axe", 30);
        assertEquals(30, manager.getWeaponsTotal("axe"));

    }

    @Test
    public void getQuestLevel3Test() {
        manager.setQuestLevel(3);
        assertEquals(3, manager.getQuestLevel());
    }

    @Test
    public void level3GoldTotalTest() {
        manager.setQuestLevel(3);
        manager.setGoldTotal(400);
        assertEquals(400, manager.getGoldTotal());

    }

    @Test
    public void level3WoodTotalTest() {
        manager.setQuestLevel(3);
        manager.setWoodTotal(300);
        assertEquals(300, manager.getWoodTotal());

    }

    @Test
    public void level3StoneTotalTest() {
        manager.setQuestLevel(3);
        manager.setStoneTotal(200);
        assertEquals(200, manager.getStoneTotal());

    }

    @Test
    public void level3MetalTotalTest() {
        manager.setQuestLevel(3);
        manager.setMetalTotal(100);
        assertEquals(100, manager.getMetalTotal());

    }

    @Test
    public void level3WeaponTotalTest() {
        manager.setQuestLevel(2);
        manager.setWeaponTotal("bow", 40);
        assertEquals(40, manager.getWeaponsTotal("bow"));
        manager.setWeaponTotal("axe", 20);
        assertEquals(20, manager.getWeaponsTotal("axe"));

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
        List<BuildingType> testBuildings = new ArrayList<>();
        testBuildings.add(BuildingType.CABIN);
        testBuildings.add(BuildingType.FENCE);

        manager.setBuildingsTotal(testBuildings);
        assertEquals(testBuildings, manager.getBuildingsTotal());
    }

    @Test
    // TODO: FIX
    public void checkGoldTest() {
        assertFalse(manager.checkGold());
        GoldPiece extraGold = new GoldPiece(100);
        manager.getPlayer().addGold(extraGold, 5);
        assertTrue(manager.checkGold());
    }

    @Test
    public void checkWoodTest() {
        assertFalse(manager.checkWood());
        Wood wood = new Wood();
        while(manager.getPlayer().getInventoryManager().getAmount("Wood") < 50) {
            manager.getPlayer().getInventoryManager().add(wood);
        }
        assertTrue(manager.checkWood());
    }

    @Test
    public void checkStoneTest() {
        assertFalse(manager.checkStone());
        Stone stone = new Stone();
        while(manager.getPlayer().getInventoryManager().getAmount("Stone") < 50) {
            manager.getPlayer().getInventoryManager().add(stone);
        }
        assertTrue(manager.checkStone());
    }

    @Test
    public void checkMetalTest() {
        assertFalse(manager.checkMetal());
        Metal metal = new Metal();
        while(manager.getPlayer().getInventoryManager().getAmount("Metal") < 30) {
            manager.getPlayer().getInventoryManager().add(metal);
        }
        assertTrue(manager.checkMetal());
    }

    @Test
    public void checkBuildingsTestFalse() {
        when(mockWorld.getEntities()).thenReturn(mockEntities);
        when(mockEntities.size()).thenReturn(1);
        when(mockEntities.get(anyInt())).thenReturn(manager.getPlayer());

        assertFalse(manager.checkBuildings());
    }

    @Test @Ignore
    // TODO: FIX
    public void checkBuildingsTestTrue() {
        when(mockWorld.getEntities()).thenReturn(mockEntities);
        when(mockEntities.size()).thenReturn(2);
        when(mockEntities.get(anyInt())).thenReturn(mockCabin, mockWatchTower);
        when(mockCabin.getBuildingType()).thenReturn(mockBuilding);
        when(mockWatchTower.getBuildingType()).thenReturn(mockBuilding);
        when(mockBuilding.getName()).thenReturn("Cabin", "WatchTower");

        assertTrue(manager.checkBuildings());
    }

    @Test @Ignore
    // TODO: FIX
    public void resetQuestTest() {
        manager.setQuestLevel(1);
        manager.checkGold();
        manager.setGoldTotal(100);
        manager.resetQuest();

        assertEquals(0, manager.getPlayer().getInventoryManager()
                .getAmount("Stone"));
        assertEquals(0, manager.getPlayer().getInventoryManager()
                .getAmount("Wood"));
        assertEquals(0, manager.getPlayer().getInventoryManager()
                .getAmount("Metal"));
        //assertEquals(0, manager.getPlayer().getGoldPouchTotalValue());
        assertEquals(300, manager.getGoldTotal());

        GoldPiece extraGold = new GoldPiece(100);


        manager.getPlayer().addGold(extraGold, 2);
        manager.getPlayer().addGold(extraGold, 2);
        manager.getPlayer().addGold(extraGold, 2);

        assertTrue(manager.checkGold());

        manager.resetQuest();

        assertFalse(manager.checkGold());
    }

}