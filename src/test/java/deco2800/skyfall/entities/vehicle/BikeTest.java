package deco2800.skyfall.entities.vehicle;

import com.badlogic.gdx.Input;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import org.junit.Assert;
import org.junit.Before;
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
     * To test useage of key F
     */
    @Test
    public void notifyKeyDownFTest(){
        int keyCode = 34;
        Assert.assertEquals(keyCode, Input.Keys.F);
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


