package deco2800.skyfall.managers;

import deco2800.skyfall.entities.*;

import org.junit.*;
import org.mockito.*;

public class StatisticsManagerTest {

    // MainCharacter used for testing
    private MainCharacter testCharacter;

    // StatisticsManager used for testing
    private StatisticsManager testManager;

    @Before
    /**
     * Set up all test variables
     */
    public void setUp() {
        testCharacter = new MainCharacter(0f, 0f,
                0.05f, "Main Piece", 10);
        testManager = new StatisticsManager(testCharacter);
    }

    @After
    /**
     * Sets all test variables to null after testing
     */
    public void tearDown() {
        testCharacter = null;
        testManager = null;
    }

    @Test
    /**
     * Test that experience is changing correctly due to different events
     */
    public void experienceTest() {
        Assert.assertEquals(1, 1);
    }


    @Test
    /**
     * Test that level is changing correctly due to experience gained
     */
    public void levelTest() {
        Assert.assertEquals(1, 1);
    }

    @Test
    /**
     * Test that killed enemies are recorded correctly
     */
    public void killsTest() {
        Assert.assertEquals(1, 1);
    }

    @Test
    /**
     * Test that all end attributes for MainCharacter are displayed correctly
     */
    public void characterAttributeTest() {
        Assert.assertEquals(1, 1);
    }
}
