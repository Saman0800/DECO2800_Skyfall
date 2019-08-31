package deco2800.skyfall.managers;

import deco2800.skyfall.entities.PlayerPeon;
import deco2800.skyfall.worlds.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EnvironmentManagerTest {

    //Test manager
    EnvironmentManager manager = new EnvironmentManager();

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
}