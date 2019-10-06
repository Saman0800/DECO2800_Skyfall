package deco2800.skyfall.entities.vehicle;

import deco2800.skyfall.entities.Horse;
import deco2800.skyfall.entities.MainCharacter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BikeTest {
    // A test for Horse class
        private Bike bike;

        private MainCharacter mainCharacter;
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
         * To test robot enemy type
         */
        @Test
        public void getNameTest() {
            Assert.assertEquals("bike", bike.getTexture());

        }

        /**
         * To test whether moving
         */
        @Test
        public void getMovingTest() {
            Assert.assertEquals(false, horse.getMove());

        }


        /**
         * Check whether the vehicle is available for this biome
         */
        @Test
        public void isAvailableTest() {
            Assert.assertTrue(horse.isAvailable());

        }
    }


