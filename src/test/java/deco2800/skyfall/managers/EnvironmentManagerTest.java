package deco2800.skyfall.managers;

import deco2800.skyfall.entities.PlayerPeon;
import deco2800.skyfall.worlds.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EnvironmentManagerTest {

    //Test manager
    EnvironmentManager manager = new EnvironmentManager(0);

    @Test
    public void setTime() {
        manager.setTime(12);
        assertEquals(12, manager.getTime());
    }

    @Test
    public void isDay() {
        manager.setTime(4);
        assertTrue(manager.isDay());
        manager.setTime(15);
        assertFalse(manager.isDay());
    }
}