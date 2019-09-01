package deco2800.skyfall.managers;

import deco2800.skyfall.entities.PlayerPeon;
import deco2800.skyfall.worlds.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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

    @Test
    public void setMonthTest() {
        // Test true
        manager.setMonth(1000000000);
        assertEquals(10, manager.getMonth());

        // Test false
        manager.setMonth(300000000);
        assertFalse(manager.getMonth()==5);

        // Test 0
        manager.setMonth(10);
        assertEquals(0, manager.getMonth());
    }

    @Test
    public void getSeasonTest() {
        // won't work because didn't use setMonth correctly
        ArrayList<Integer> monthList = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            monthList.add(i);
        }

        for (int i = 3; i < 6; i++) {
            manager.month = monthList.get(i);
            assertEquals("Autumn", manager.getSeason());
        }

        for (int i = 6; i < 9; i++) {
            manager.month = monthList.get(i);
            assertEquals("Winter", manager.getSeason());
        }

        for (int i = 9; i < 12; i++) {
            manager.month = monthList.get(i);
            assertEquals("Spring", manager.getSeason());
        }

        // Testing Summer:
        manager.month = 12;
        assertEquals("Summer", manager.getSeason());

        for (int i = 0; i < 3; i++) {
            manager.month = monthList.get(i);
            assertEquals("Summer", manager.getSeason());
        }

        // Check an index not within 0 to 12

    }
}