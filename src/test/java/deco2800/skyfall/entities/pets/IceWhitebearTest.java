package deco2800.skyfall.entities.pets;

import static org.junit.Assert.assertEquals;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.worlds.Tile;
import org.junit.Before;
import org.junit.Test;

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

    /**
     * Tempt to destory ice
     */
    @Test
    public void destoryice() {
        iceWhitebear.setHealth(3);
        iceWhitebear.destoryice();
        assertEquals(2,iceWhitebear.getHealth());
    }
    
}