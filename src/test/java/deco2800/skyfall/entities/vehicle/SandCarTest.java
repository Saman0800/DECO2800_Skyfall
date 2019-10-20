package deco2800.skyfall.entities.vehicle;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.util.HexVector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class SandCarTest {
    private SandCar sandCar;
    private MainCharacter mc;
    private boolean isOnUse;

    @Before
    public void setup() throws Exception {
        sandCar = new SandCar(5f,4f, mc);
    }

    /**
     * Check the position of sand car
     */
    @Test
    public void positionTest() {
        assertThat("", sandCar.getCol(), is(equalTo(5f)));
        assertThat("", sandCar.getRow(), is(equalTo(4f)));
    }

    /**
     * Check what type of vehicle it was
     */
    @Test
    public void getTypeTest() {
        Assert.assertEquals("sand_car", sandCar.getName());
    }

    /**
     * Test the vehicle belongs to which biome
     */
    @Test
    public void getBIOMETest() {
        Assert.assertEquals("desert", sandCar.getBiome());
    }

    /**
     * To get the coordinates of this vehicle
     */
    @Test
    public void getCoords() {
        Assert.assertEquals(sandCar.getCoords(), new HexVector(5f, 4f));
    }

    /**
     * To test the description of vehicle
     */
    @Test
    public void getDescription() {
        Assert.assertEquals(sandCar.getDescription(), "vehicle sand_car");
    }

    /**
     * To test get sub type method it will always return vehicle
     */
    @Test
    public void getSubtype() {
        Assert.assertEquals(sandCar.getSubtype(), "vehicle");
    }

    @Test
    public void testIsOnUse() {
        Assert.assertEquals(sandCar.isOnUse(), false);
    }

    /**
     * To test distance between bike and main character
     */
    @Test
    public void vehicleOnUseTest(){
        isOnUse = true;
        AbstractEntity ve  = sandCar;
        if(ve.distance(ve) == 4){
            Assert.assertEquals(isOnUse, false);
        }
    }
}
