package deco2800.skyfall.resources;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.resources.items.Apple;
import deco2800.skyfall.resources.items.AloeVera;
import deco2800.skyfall.resources.items.Berry;
import deco2800.skyfall.resources.items.PoisonousMushroom;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

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
    public void setUp() {
        apple = new Apple();
        aloe_vera = new AloeVera();
        berry = new Berry();
        poisonousmushroom = new PoisonousMushroom();
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
     * Tests the correct biomes are returned for each health resource
     */
    @Test
    public void getBiomeTest(){
        String forestWord = "Forest";
        assertEquals(forestWord, apple.getBiome());
        assertEquals(forestWord, berry.getBiome());
        assertEquals(forestWord, poisonousmushroom.getBiome());
        assertEquals("Desert", aloe_vera.getBiome());

    }

    /**
     * Tests the correct colours are returned for each health resource
     */
    @Test
    public void getColourTest(){
        assertEquals("red", apple.getColour());
        assertEquals("wine red", berry.getColour());
        assertEquals("black white", poisonousmushroom.getColour());
        assertEquals("green", aloe_vera.getColour());

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
        String healthResource = "Health Resource";
        assertEquals(healthResource, apple.getSubtype());
        assertEquals(healthResource, aloe_vera.getSubtype());
        assertEquals(healthResource, berry.getSubtype());
        assertEquals(healthResource, poisonousmushroom.getSubtype());
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
        MainCharacter main = mock(MainCharacter.class);
        main.setEquippedItem(apple);
        assertEquals(apple.getCoords(), main.getPosition());
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

    @Test
    public void useTest() {
        MainCharacter main = mock(MainCharacter.class);
        apple.use(main.getPosition());
        when(main.getHealth()).thenReturn(10);
        assertEquals(10, main.getHealth());
    }

    @Test
    public void getDescriptionTest() {
        assertEquals("This item can be used to heal\n the Main Character.", apple.getDescription());
        assertEquals("This item can be used to heal\n the Main Character.", aloe_vera.getDescription());
        assertEquals("This item can be used to heal\n the Main Character.", berry.getDescription());
        assertEquals("This item hurts the Main Character.", poisonousmushroom.getDescription());
    }

    @Test
    public void isEquippableTest() {
        assertTrue(apple.isEquippable());
        assertTrue(aloe_vera.isEquippable());
        assertTrue(berry.isEquippable());
        assertTrue(poisonousmushroom.isEquippable());
    }
}