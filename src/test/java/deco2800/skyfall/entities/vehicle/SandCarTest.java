package deco2800.skyfall.entities.vehicle;

import com.badlogic.gdx.audio.Sound;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.SoundManager;
import deco2800.skyfall.util.HexVector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class SandCarTest {
    private SandCar sandCar;
    private MainCharacter mc;
    private boolean isOnUse;
    private SoundManager sm;

    @Before
    public void setup() throws Exception {
        mc = MainCharacter.getInstance();
        sandCar = new SandCar(5f,4f, mc);
        sm = GameManager.getManagerFromInstance(SoundManager.class);
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
     * Test the animation is correct
     */
    @Test
    public void testAnimations() {
        mc.setCurrentState(AnimationRole.VEHICLE_MOVE);
        mc.setCurrentDirection(Direction.NORTH);
        AnimationLinker linked = mc.getToBeRun();
        Assert.assertEquals(linked.getAnimationName(), "sandcarN");
        Assert.assertEquals(linked.getType(), AnimationRole.VEHICLE_MOVE);
    }

    /**
     * Test the direction texture for vehicle
     */
    @Test
    public void testDirectionTexture() {
        mc.vehicleTexture("sand_car");
        mc.setCurrentDirection(Direction.NORTH);
        Assert.assertEquals(mc.getDefaultTexture(), "sand_car_NORTH");
        mc.setCurrentDirection(Direction.WEST);
        Assert.assertEquals(mc.getDefaultTexture(), "sand_car_WEST");
    }

    /**
     * Test the sound for vehicle, when vehicle stop the sound stop
     * and when vehicle moving the sound play.
     */
    @Test
    @Ignore
    public void testSandCarSound(){
        isOnUse = false;
        Assert.assertFalse(sm.stopSound("sand_car_animation"));
        mc.resetVelocity();
        Assert.assertFalse(sm.playSound("sand_car_animation"));
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
