package deco2800.skyfall.managers;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EnvironmentManagerTest {

    EnvironmentManager manager;

    @Before
    public void initialize() {
        manager = new EnvironmentManager();
    }


    @Test
    public void setTimeTest() {
        manager.setTime(100000);
        assertEquals(1, manager.getTime());
    }

    @Test
    public void isDayTest() {
        manager.setTime(100000);
        assertTrue(manager.isDay());
        manager.setTime(1000000);
        assertFalse(manager.isDay());
    }

    @Test
    public void amTest() {
        manager.setTime(100000);
        assertTrue(manager.isDay());

        manager.getTOD();
        assertEquals("am", manager.TOD);

        manager.hours = 24;
        assertTrue(manager.isDay());

        manager.getTOD();
        assertEquals("am", manager.TOD);
    }

    @Test
    public void pmTest() {
        manager.setTime(1000000);
        assertFalse(manager.isDay());

        manager.getTOD();
        assertEquals("pm", manager.TOD);

        manager.hours = 12;
        assertFalse(manager.isDay());

        manager.getTOD();
        assertEquals("pm", manager.TOD);
    }

    @Test
    public void displayTODTest() {
        manager.minutes = 9;
        manager.hours = 10;
        manager.isDay();

        assertEquals(Long.toString(10) + ":" + "0" + Long.toString(9) + "am", manager.getTOD());
    }

    @Test
    public void setFilenameTest() {
        manager.hours = 11;

        manager.isDay();

        manager.setFilename();

        assertEquals("resources/sounds/forest_day.wav", manager.file);
    }

    @Test
    public void onTickTest() {
        manager.onTick(1000000);

        long time = (System.currentTimeMillis()/ 60000) %24;

        assertEquals(time, manager.getTime());
    }
}