package deco2800.skyfall.entities;

import deco2800.skyfall.managers.ChestManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import org.junit.Before;

import java.util.HashMap;
import org.junit.Test;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChestTest {

    private Chest chest;
    private Tile mockTile;

    @Before
    public void setup() throws Exception {
        mockTile = mock(Tile.class);
        when(mockTile.getCoordinates()).thenReturn(new HexVector());
        chest = new Chest(mockTile,false,new HashMap<>());
    }


    @Test
    public void testGetHealth() {
        assertThat("Health must be set to default health.", chest.getHealth(), is(equalTo(100)));
    }

    @Test
    public void testSetHealth() {
        assertThat("", chest.getHealth(), is(equalTo(100)));
        chest.setHealth(50);
        assertThat("", chest.getHealth(), is(equalTo(50)));
        chest.setHealth(-1);
        assertThat("", chest.getHealth(), is(equalTo(-1)));
    }

    @Test
    public void testNewInstance() {
        Chest newChest = chest.newInstance(mockTile);
        assertNotNull(newChest);
    }

    @Test
    public void testGetManager() {
        ChestManager manager = chest.getManager();
        assertNotNull(manager);
    }
}
