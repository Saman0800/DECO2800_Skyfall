package deco2800.skyfall.resources;

import deco2800.skyfall.resources.items.Apple;
import deco2800.skyfall.resources.items.Aloe_Vera;
import deco2800.skyfall.resources.items.Berry;
import deco2800.skyfall.resources.items.PoisonousMushroom;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HealthResourcesTest {
    private HealthResources apple;
    private HealthResources aloe_vera;
    private HealthResources berry;
    private HealthResources poisonousmushroom;

    /**
     * Create a new Apple, Aloe_Vera, Berry, PoisonousMushroom health Resource
     */
    @Before
    public void setUp() throws Exception {
        apple = new Apple();
        aloe_vera = new Aloe_Vera();
        berry = new Berry();
        poisonousmushroom = new PoisonousMushroom();

    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Ensure that the getName method returns the name of the subclass
     * (apple, berry etc.) not just null
     */

    @Test
    public void getName2() {
        assertEquals("Apple", apple.getName());
        assertEquals("Aloe_Vera", aloe_vera.getName());
        assertEquals("Berry", berry.getName());
        assertEquals("PoisonousMushroom", poisonousmushroom.getName());
    }

    /**
     * Ensure that all the items are classified as carryable
     */
    @Test
    public void isCarryable2() {
        assertTrue(apple.isCarryable());
        assertTrue(aloe_vera.isCarryable());
        assertTrue(berry.isCarryable());
        assertTrue(poisonousmushroom.isCarryable());

    }

    /**
     * Ensure that the correct subtype "Health Resource" is returned when
     * getSubtype(); is run
     */
    @Test
    public void getSubtype2() {
        assertEquals("Health Resource", apple.getSubtype());
        assertEquals("Health Resource", aloe_vera.getSubtype());
        assertEquals("Health Resource", berry.getSubtype());
        assertEquals("Health Resource", poisonousmushroom.getSubtype());
    }

    /**
     * Ensure that the health resource class has healing power affected
     * hasHealingPower(); return true
     */
    @Test
    public void hasHealingPower2() {
        assertTrue(apple.hasHealingPower());
        assertTrue(aloe_vera.hasHealingPower());
        assertTrue(berry.hasHealingPower());
        assertTrue(poisonousmushroom.hasHealingPower());
    }


    /**
     * Get the coordinate of items
     */
    @Test
    public void getCoords2() {
        //assertEquals(Tile.getCoordinates(),healthResources.getCoords());
    }

    /**
     * Ensure that the item is considered exchangeable for later use in the game
     */
    @Test
    public void isExchangeable2() {

        assertTrue(apple.isExchangeable());
        assertTrue(aloe_vera.isExchangeable());
        assertTrue(berry.isExchangeable());
        assertTrue(poisonousmushroom.isExchangeable());


    }

    /**
     * Ensure the correct string representation is displayed for each type
     * It should be of the following format:
     *
     * <p>'{Health Resource}:{Name}' </p>
     *
     * <p>without surrounding quotes and with {health resource} replaced by
     * the subtype of the item and {name} replaced with the item name
     * For example: </p>
     *
     * <p>Health Resource:Apple, Aloe_Vera, Berry, PoisonousMushroom </p>
     */
    @Test
    public void toString2() {
        assertEquals("Health Resource:Apple", apple.toString());
        assertEquals("Health Resource:Aloe_Vera", aloe_vera.toString());
        assertEquals("Health Resource:Berry", berry.toString());
        assertEquals("Health Resource:PoisonousMushroom", poisonousmushroom.toString());

    }





}