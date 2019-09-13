package deco2800.skyfall.resources.items;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.resources.GoldPiece;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.Assert.*;

public class ResearchTableTest {

    private MainCharacter testPlayer;
    private ResearchTable testTable;
    private List<String> expectedCreatables;
    private int goldcost;
    private GoldPiece goldPiece;
    private Map<String, Integer> allRequirements;

    @Before
    public void setUp() {
        testPlayer = new MainCharacter(1f, 1f, 0.05f, "player", 100);
        testTable = new ResearchTable(testPlayer);
        goldcost = 25;
        goldPiece = new GoldPiece(100);
        testPlayer.addGold(goldPiece,2);
        allRequirements = new HashMap<>();
    }

    @After
    public void tearDown()  {
    }

    @Test
    public void getCreatableItems() {
        expectedCreatables = new ArrayList<>();
        expectedCreatables.add("Hatchet");
        expectedCreatables.add("Pick Axe");

        assertEquals(Arrays.asList("Hatchet","Pick Axe"),testTable.getCreatableItems());
    }

    @Test
    public void buyBlueprint() {
        System.out.println(testPlayer.getGoldPouchTotalValue());
        testTable.buyBlueprint();
        assertEquals(Arrays.asList("Pick Axe","Cabin","StorageUnit","TownCentre",
                "Fence","SafeHouse","WatchTower","Castle"),
                testTable.getCreatableItems());
        assertEquals(Arrays.asList("Hatchet"),testPlayer.getBlueprintsLearned());
    }

    @Test
    public void getAllRequirements() {

        allRequirements.put("Wood",100);
        allRequirements.put("Stone",50);
        allRequirements.put("Metal",30);
        assertEquals(testTable.getAllRequirements(),allRequirements);

    }

    @Test
    public void isExchangeable() {
        assertTrue(testTable.isExchangeable());
    }

    @Test
    public void getName() {
        assertEquals("Research Table", testTable.getName());

    }

    @Test
    public void getRequiredWood() {
        assertEquals(100,testTable.getRequiredWood());
    }

    @Test
    public void getRequiredStone() {
        assertEquals(50,testTable.getRequiredStone());

    }

    @Test
    public void getRequiredMetal() {
        assertEquals(30,testTable.getRequiredMetal());

    }

    @Test
    public void getDescription() {
        assertEquals("A research table is used to retrieve blueprints in " +
                "exchange of resources or gold", testTable.getDescription());
    }

}