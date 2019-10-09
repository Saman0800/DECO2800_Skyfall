package deco2800.skyfall.managers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.observers.DayNightObserver;
import deco2800.skyfall.observers.SeasonObserver;
import deco2800.skyfall.observers.TimeObserver;
import java.util.ArrayList;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.world.World;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class EnvironmentManagerTest {

    EnvironmentManager manager;
    TimeObserver mockTimeObserver = mock(TimeObserver.class);
    DayNightObserver mockDayNightObserver = mock(DayNightObserver.class);
    SeasonObserver mockSeasonObserver = mock(SeasonObserver.class);
    GameManager gm = GameManager.get();
    World mockWorld = mock(World.class);
    List<AbstractEntity> mockEntities = mock(List.class);
    MainCharacter mockPlayer = mock(MainCharacter.class);
    AbstractBiome mockBiome = mock(AbstractBiome.class);
    Tile mockTile = mock(Tile.class);

    @Before
    public void initialize() {
        try {
            manager = new EnvironmentManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
        gm.setWorld(mockWorld);
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
    public void addSeasonListenerTest() {
        manager.addSeasonListener(mockSeasonObserver);
        assertTrue(manager.getSeasonListeners().contains(mockSeasonObserver));
    }

    @Test
    public void removeSeasonListenerTest() {
        manager.addSeasonListener(mockSeasonObserver);
        assertTrue(manager.getSeasonListeners().contains(mockSeasonObserver));
        manager.removeSeasonListener(mockSeasonObserver);
        assertFalse(manager.getSeasonListeners().contains(mockSeasonObserver));
    }

    @Test
    public void updateSeasonListenersTest() {
        doNothing().when(mockSeasonObserver).notifySeasonUpdate("Summer");
        manager.addSeasonListener(mockSeasonObserver);
        manager.updateSeasonListeners("Summer");
        verify(mockSeasonObserver).notifySeasonUpdate("Summer");
    }

    @Test
    public void setTimeTest() {
        manager.setTime(1, 0);
        assertEquals(1, manager.getTime());
    }

    @Test
    public void isDayTest() {
        manager.setTime(6, 0);
        assertTrue(manager.isDay());
        manager.setTime(18, 0);
        assertFalse(manager.isDay());
    }

    @Test
    public void amTest() {
        manager.setTime(6, 0);
        assertTrue(manager.isDay());

        assertEquals("6:00am", manager.getTOD());

        manager.setTime(24,0);
        assertFalse(manager.isDay());

        assertEquals("12:00am", manager.getTOD());
    }

    @Test
    public void pmTest() {
        manager.setTime(18, 0);
        assertFalse(manager.isDay());

        manager.getTOD();
        assertEquals("6:00pm", manager.getTOD());

        manager.setTime(12,0);
        assertEquals("12:00pm", manager.getTOD());
    }

    @Test
    public void displayTODTest() {
        manager.setTime(10,9);
        manager.isDay();

        assertEquals(Long.toString(10) + ":" + "0" + Long.toString(9) + "am", manager.getTOD());
    }

    @Test
    public void setFilenameTest() {
        manager.setTime(12,0);
        manager.isDay();
        manager.biome = "forest";
        manager.setFilename();
        assertEquals("resources/sounds/forest_day.wav", manager.getFilename());

        manager.setTime(19,0);
        manager.isDay();
        manager.biome = "desert";
        manager.setFilename();
        assertEquals("resources/sounds/desert_night.wav", manager.getFilename());

        // Test defaulting ocean and lake biomes
        // Test day
        manager.setTime(10,0);
        manager.isDay();
        manager.biome = "ocean";
        manager.setFilename();
        assertEquals("resources/sounds/forest_day.wav", manager.getFilename());

        // Test night
        manager.setTime(24,0);
        manager.isDay();
        manager.biome = "lake";
        manager.setFilename();
        assertEquals("resources/sounds/forest_night.wav", manager.getFilename());
    }

    @Test
    public void onTickTest() {
        manager.onTick(0);
        verify(mockWorld).getEntities();
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

        // Summer
        for (int i = 0; i < 3; i++) {
            manager.setMonthInt(i);
            assertEquals("Summer", manager.getSeason());
        }

        manager.setMonthInt(12);
        assertEquals("Summer", manager.getSeason());

        // Autumn
        for (int i = 3; i < 6; i++) {
            manager.setMonthInt(i);
            assertEquals("Autumn", manager.getSeason());
        }

        // Winter
        for (int i = 6; i < 9; i++) {
            manager.setMonthInt(i);
            assertEquals("Winter", manager.getSeason());
        }

        // Spring
        for (int i = 9; i < 12; i++) {
            manager.setMonthInt(i);
            assertEquals("Spring", manager.getSeason());
        }

        // Check an index not within 0 to 12
        manager.setMonthInt(13);
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
    public void setDayMusicTest() {

        // test second if statement for file == currentFile
        try {
            manager.setTime(11,0);
            manager.isDay();
            manager.biome = "forest";
            try {
                manager.setFilename();
                manager.setTODMusic();
                assertEquals("resources/sounds/forest_day.wav",manager.getFilename());
            } catch (Exception e) { /* Exception caught, if any */ }
        } catch (Exception e) { /* Exception caught, if any */ }
    }

    @Test
    public void setNightMusicTest() {

        // test second if statement for file == currentFile
        try {
            manager.setTime(20,0);
            manager.isDay();
            manager.biome = "forest";
            try {
                manager.setFilename();
                manager.setTODMusic();
                assertEquals("resources/sounds/forest_night.wav",manager.getFilename());
            } catch (Exception e) { /* Exception caught, if any */ }
        } catch (Exception e) { /* Exception caught, if any */ }
    }

    @Test
    public void setBiomeTest() {
        when(mockWorld.getEntities()).thenReturn(mockEntities);
        when(mockEntities.size()).thenReturn(1);
        when(mockEntities.get(anyInt())).thenReturn(mockPlayer);
        when(mockWorld.getTile(Math.round(mockPlayer.getCol()),
                Math.round(mockPlayer.getRow()))).thenReturn(mockTile);
        when(mockTile.getBiome()).thenReturn(mockBiome);
        when(mockBiome.getBiomeName()).thenReturn("forest");

        manager.setBiome();
        assertEquals("forest", manager.currentBiome());
    }

    @Test
    public void getHourDecimalTest() {
        manager.setTime(24, 80);
        assertEquals(1, manager.getHourDecimal(), 0);
    }
}