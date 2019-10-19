package deco2800.skyfall.entities.structures;

import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;

public class HouseTest {

    public House house;
    private Tile mockTile;
    @Before
    public void setup() {
        mockTile = Mockito.mock(Tile.class);
        when(mockTile.getCoordinates()).thenReturn(new HexVector());
        house = new House(mockTile,1);
    }
    @Test
    public void permissions() {
        assertTrue(house.permissions());
    }

    @Test
    public void placeBuilding() {
    }

    @Test
    public void getMaxHealth() {
        assertEquals(10,house.getMaxHealth());
    }

    @Test
    public void getCurrentHealth() {
        assertEquals(10,house.getCurrentHealth());
    }

    @Test
    public void getBuildTime() {
        assertEquals(7,house.getBuildTime());
    }

    @Test
    public void getXSize() {
        assertEquals(1,house.getXSize());
    }

    @Test
    public void getYSize() {
        assertEquals(1,house.getYSize());
    }

    @Test
    public void setHealth() {
        house.setHealth(20);
        assertEquals(20,house.getMaxHealth());
    }

    @Test
    public void setCurrentHealth() {
        house.setCurrentHealth(50);
        assertEquals(50,house.getCurrentHealth());
    }

    @Test
    public void takeDamage() {
        house.setCurrentHealth(20);
        house.takeDamage(14);
        assertEquals(6,house.getCurrentHealth());
        house.takeDamage(21);
        assertEquals(0,house.getCurrentHealth());
    }

    @Test
    public void setXSize() {
        house.setXSize(5);
        assertEquals(5,house.getXSize());
    }

    @Test
    public void setYSize() {
        house.setYSize(4);
        assertEquals(4,house.getYSize());
    }

    @Test
    public void onTick() {
    }
}