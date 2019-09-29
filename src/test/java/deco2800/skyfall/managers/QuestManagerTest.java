package deco2800.skyfall.managers;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class QuestManagerTest {

    //Test manager
    private QuestManager manager;

    @Before
    public void setUp() {
        manager = new QuestManager();
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

}