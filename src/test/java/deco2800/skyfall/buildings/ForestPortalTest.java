package deco2800.skyfall.buildings;

import static org.junit.Assert.assertTrue;

import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ForestPortalTest {

    private ForestPortal forestPortal;

    @Before
    public void setUp() {
        forestPortal = new ForestPortal(0 ,0 ,0);
    }


    @Test
    public void getCost() {
        assertTrue(forestPortal.getCost() > 0);
    }

    @Test
    public void getNextTest() {
        forestPortal.setNext("desert");
        assertTrue(forestPortal.getNext().equals("desert"));
    }

    @Test
    public void getAllRequirements() {
        Map<String, Integer> a = forestPortal.getAllRequirements();
        assertTrue(a.get("Wood") >= 0);
        assertTrue(a.get("Stone") >= 0);
        assertTrue(a.get("Metal") >= 0);
    }


}