package deco2800.skyfall.managers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import deco2800.skyfall.observers.DayNightObserver;
import deco2800.skyfall.observers.TimeObserver;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class EnvironmentManagerTest {

    EnvironmentManager manager;
    TimeObserver mockTimeObserver = mock(TimeObserver.class);
    DayNightObserver mockDayNightObserver = mock(DayNightObserver.class);

    @Before
    public void initialize() {
        manager = new EnvironmentManager();
    }

    @Test
    public void addTimeListenerTest() {
        manager.addTimeListener(mockTimeObserver);
        assertTrue(manager.getTimeListeners().contains(mockTimeObserver));
    }

    @Test
    public void removeTimeListenerTest() {
        manager.addTimeListener(mockTimeObserver);
        assertTrue(manager.getTimeListeners().contains(mockTimeObserver));
        manager.removeTimeListener(mockTimeObserver);
        assertFalse(manager.getTimeListeners().contains(mockTimeObserver));
    }

    @Test
    public void updateTimeListenersTest() {
        doNothing().when(mockTimeObserver).notifyTimeUpdate(10000);
        manager.addTimeListener(mockTimeObserver);
        manager.updateTimeListeners(10000);
        verify(mockTimeObserver).notifyTimeUpdate(10000);
    }

    @Test
    public void addDayNightListenerTest() {
        manager.addDayNightListener(mockDayNightObserver);
        assertTrue(manager.getDayNightListeners().contains(mockDayNightObserver));
    }

    @Test
    public void removeDayNightListenerTest() {
        manager.addDayNightListener(mockDayNightObserver);
        assertTrue(manager.getDayNightListeners().contains(mockDayNightObserver));
        manager.removeDayNightListener(mockDayNightObserver);
        assertFalse(manager.getDayNightListeners().contains(mockDayNightObserver));
    }

    @Test
    public void updateDayNightListenersTest() {
        doNothing().when(mockDayNightObserver).notifyDayNightUpdate(true);
        manager.addDayNightListener(mockDayNightObserver);
        manager.updateDayNightListeners(true);
        verify(mockDayNightObserver).notifyDayNightUpdate(true);
    }

    @Test
    public void setTimeTest() {
        manager.setTime(100000);
        assertEquals(1, manager.getTime());
    }

    @Test
    public void isDayTest() {
        manager.setTime(1000000);
        assertTrue(manager.isDay());
        manager.setTime(100000);
        assertFalse(manager.isDay());
    }

    @Test
    public void amTest() {
        manager.setTime(500000);
        assertTrue(manager.isDay());

        manager.getTOD();
        assertEquals("am", manager.TOD);

        manager.hours = 24;
        assertFalse(manager.isDay());

        manager.getTOD();
        assertEquals("am", manager.TOD);
    }

    @Test
    public void pmTest() {
        manager.setTime(10000000);
        assertFalse(manager.isDay());

        manager.getTOD();
        assertEquals("pm", manager.TOD);

        manager.hours = 19;
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
        manager.biome = "forest";
        manager.setFilename();
        assertEquals("resources/sounds/forest_day.wav", manager.file);

        manager.hours = 19;
        manager.isDay();
        manager.biome = "desert";
        manager.setFilename();
        assertEquals("resources/sounds/desert_night.wav", manager.file);

        // Test defaulting ocean and lake biomes
        // Test day
        manager.hours = 8;
        manager.isDay();
        manager.biome = "ocean";
        manager.setFilename();
        assertEquals("resources/sounds/forest_day.wav", manager.file);

        // Test night
        manager.hours = 23;
        manager.isDay();
        manager.biome = "lake";
        manager.setFilename();
        assertEquals("resources/sounds/forest_night.wav", manager.file);
    }

    @Test
    public void onTickTest() {
        try {
            manager.onTick(1000000);

            long time = (System.currentTimeMillis() / 60000) % 24;

            assertEquals(time, manager.getTime());

        } catch (Exception e) { /* Exception caught, if any */ }
    }

    @Test
    public void setMonthTest() {
        // Test true
        manager.setMonth(1000000000);
        assertEquals(10, manager.getMonth());

        // Test false
        manager.setMonth(300000000);
        assertFalse(manager.getMonth() == 5);

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
            manager.monthInt = monthList.get(i);
            assertEquals("Autumn", manager.getSeason());
        }

        for (int i = 6; i < 9; i++) {
            manager.monthInt = monthList.get(i);
            assertEquals("Winter", manager.getSeason());
        }

        for (int i = 9; i < 12; i++) {
            manager.monthInt = monthList.get(i);
            assertEquals("Spring", manager.getSeason());
        }

        // Testing Summer:
        manager.monthInt = 12;
        assertEquals("Summer", manager.getSeason());

        for (int i = 0; i < 3; i++) {
            manager.monthInt = monthList.get(i);
            assertEquals("Summer", manager.getSeason());
        }

        // Check an index not within 0 to 12
        manager.monthInt = 13;
        assertEquals("Invalid season", manager.getSeason());
    }

    @Test
    public void currentBiomeTest() {

        ArrayList<String> biomeList = new ArrayList<>();
        biomeList.add("forest");
        biomeList.add("desert");
        biomeList.add("lake");
        biomeList.add("ocean");
        biomeList.add("mountain");

        for (String s : biomeList) {
            manager.biome = s;
            String expected = manager.biome;
            assertEquals(expected, manager.currentBiome());
        }
    }

    @Test
    public void setTODMusicTest() {

        // test first if statement for file == current File
        try {
            manager.file = "resources/sounds/forest_day.wav";
            manager.currentFile = "resources/sounds/forest_day.wav";
            manager.hours = 11;
            manager.isDay();
            manager.biome = "forest";
            manager.setTODMusic();
            assertEquals(manager.file, manager.currentFile);
            assertEquals("resources/sounds/forest_day.wav", manager.file);

        } catch (Exception e) { /* Exception caught */}

        // test second if statement for file != currentFile
        try {
            manager.file = "resources/sounds/forest_day.wav";
            manager.currentFile = "resources/sounds/forest_night.wav";
            manager.hours = 11;
            manager.isDay();
            manager.biome = "forest";
            try {
                manager.setTODMusic();
                assertEquals(manager.file, manager.currentFile);
            } catch (Exception e) { /* Exception caught, if any */ }
        } catch (Exception e) { /* Exception caught, if any */ }
    }

    @Test
    public void setBiomeTest() {
        // This test is not at all comprehensive, will need to be redone in next sprint

        try {
            manager.biome = "forest";
            assertEquals("forest", manager.currentBiome());

        } catch (Exception e) { /* Exception caught, if any */ }
    }

}