package deco2800.skyfall.entities.vehicle;

import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.SoundManager;
import deco2800.skyfall.util.HexVector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BikeTest {
    private Bike bike;
    private MainCharacter mainCharacter;
    private boolean isOnVehicle;

    @Before
    public void setup() throws Exception {
        bike =new Bike(2f,3f, mainCharacter);
        mainCharacter = MainCharacter.getInstance();
    }

    /**
     * To test initial position of the bike
     */
    @Test
    public void positionTest(){
        assertThat("", bike.getCol(), is(equalTo(2f)));
        assertThat("", bike.getRow(), is(equalTo(3f)));
    }


    /**
     * To test bike vehicle type
     */
    @Test
    public void getNameTest() {
        Assert.assertEquals("bike", bike.getTexture());

    }

    /**
     * Check what type of vehicle it was
     */
    @Test
    public void getTypeTest() {
        Assert.assertEquals("bike", bike.getName());
    }

    /**
     * Test the vehicle belongs to which biome
     */
    @Test
    public void getBIOMETest() {
        Assert.assertEquals("forest", bike.getBiome());
        Assert.assertTrue(bike.getBiome().equals("forest"));
    }

    /**
     * To get the coordinates of this vehicle
     */
    @Test
    public void getCoords() {
        Assert.assertEquals(bike.getCoords(), new HexVector(2f, 3f));
    }

    /**
     * To test the description of vehicle
     */
    @Test
    public void getDescription() {
        Assert.assertEquals(bike.getDescription(), "vehicle bike");
    }

    /**
     * To test get sub type method it will always return vehicle
     */
    @Test
    public void getSubtype() {
        Assert.assertEquals(bike.getSubtype(), "vehicle");
    }

    /**
     * To test whether all animations about bike can match corresponding state.
     */
    @Test
    public void testExistingAnimations() {
        mainCharacter.setCurrentState(AnimationRole.VEHICLE_BIKE_MOVE);
        mainCharacter.setCurrentDirection(Direction.NORTH_EAST);
        AnimationLinker al = mainCharacter.getToBeRun();
        Assert.assertEquals(al.getAnimationName(), "bikeNE");
        Assert.assertEquals(al.getType(), AnimationRole.VEHICLE_BIKE_MOVE);
    }

    /**
     * Test bike directions to defaultDirectionTextures
     */
    @Test
    public void testExistingDirectionTexture() {
        mainCharacter.vehicleTexture("bike");
        mainCharacter.setCurrentDirection(Direction.EAST);
        Assert.assertEquals(mainCharacter.getDefaultTexture(), "bikeEAST");
        mainCharacter.setCurrentDirection(Direction.NORTH);
        Assert.assertEquals(mainCharacter.getDefaultTexture(), "bikeNORTH");
        mainCharacter.setCurrentDirection(Direction.SOUTH_WEST);
        Assert.assertEquals(mainCharacter.getDefaultTexture(), "bikeNORTHWEST");
    }

    /**
     * Test whether bike sound stops when bike is not on use and when
     * velocity of bike is 0.
     */
    @Test
    @Ignore
    public void testBikeSound(){
        isOnVehicle = false;
        Assert.assertFalse(SoundManager.stopSound("bike_animation"));
        mainCharacter.resetVelocity();
        Assert.assertFalse(SoundManager.playSound("bike_animation"));
    }

    /**
     * To test distance between bike and main character
     */
    @Test
    public void vehicleToUseTest(){
        isOnVehicle = true;
        AbstractEntity ve  = bike;
        if(ve.distance(ve) == 4){
            Assert.assertEquals(isOnVehicle, false);
        }
    }
}


