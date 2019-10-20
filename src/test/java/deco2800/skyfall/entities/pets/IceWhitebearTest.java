package deco2800.skyfall.entities.pets;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.worlds.Tile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.*;

public class IceWhitebearTest {
    private IceWhitebear iceWhitebear;
    private Tile mockTile;

    @Before
    public void setUp() throws Exception {
        MainCharacter mainCharacter = new MainCharacter(1f,1f,1f,"mc",2);
        iceWhitebear = new IceWhitebear(1,1,mainCharacter);
    }

    @Test
    public void onTick() {
    }

    @Test
    public void configureAnimations() {
    }

    @Test
    public void setDirectionTextures() {
    }

    @Test
    public void destoryice() {
        iceWhitebear.setHealth(3);
        iceWhitebear.destoryice();
        assertEquals(2,iceWhitebear.getHealth());
    }

    @Test
    public void harvest() {
        mockTile = Mockito.mock(Tile.class);
        iceWhitebear.setHealth(1);
        iceWhitebear.destoryice();
        List<AbstractEntity> drops = iceWhitebear.harvest(mockTile);
        assertTrue(drops.get(0) instanceof Whitebear);
    }
}