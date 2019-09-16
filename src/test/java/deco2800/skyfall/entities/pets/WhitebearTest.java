package deco2800.skyfall.entities.pets;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.PhysicsManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.world.World;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WhitebearTest {

    private MainCharacter mc = null;
    private Whitebear whitebear = null;
    @Before
    public void setUp() throws Exception {
        mc = new MainCharacter(5f, 5f, 2f, "Main Char", 10);
        whitebear = new Whitebear(5, 2, mc);
    }

    /**
     * Test get out side method
     */
    @Test
    public void getOutSide() {
        Assert.assertEquals(whitebear.getOutSide(), false);
    }

    /**
     * Test set out side method
     */
    @Test
    public void setOutSide() {
        whitebear.setOutSide(false);
        Assert.assertEquals(whitebear.getOutSide(),false);
    }

    /**
     * Test whether the pet follow the mc or not
     */
    @Test
    public void followingCharacter() {
        mc.setCol(3);
        mc.setRow(5);
        whitebear.setDomesticated(true);
        whitebear.followingCharacter();
        Assert.assertEquals((int) whitebear.getCol(), 4);
        Assert.assertEquals((int) whitebear.getRow(), 2);
    }

    /**
     * Test the name of whitebear
     * @return texture name of whitebear
     */
    @Test
    public void getName() {
        Assert.assertEquals(whitebear.getName(), "whitebear");
    }

    /**
     * Test the type of whitebear
     * @return type of whitebear
     */
    @Test
    public void getSubtype() {
        Assert.assertEquals(whitebear.getSubtype(), "pets");
    }

    /**
     * Test whether whitebear is carryable or not
     * @return true
     */
    @Test
    public void isCarryable() {
        Assert.assertEquals(whitebear.isCarryable(), true);
    }

    /**
     * test the location of whitebear
     */
    @Test
    public void getCoords() {
        Assert.assertEquals(whitebear.getCoords(), new HexVector(5, 2));
    }

    /**
     * Test whether whitebear is exchangeable or not
     * @return true
     */
    @Test
    public void isExchangeable() {
        Assert.assertEquals(whitebear.isExchangeable(), true);
    }

    /**
     * Test the description of whitebear
     * @return pet whitebear
     */
    @Test
    public void getDescription() {
        Assert.assertEquals(whitebear.getDescription(), "pet whitebear");
    }

}