package deco2800.skyfall.entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnemyTest {

    private Enemy enemy;

    @Before
    public void setUp() throws Exception {
        this.enemy = new Enemy(1,1);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getNameTest() {
        assertEquals(1, enemy.getArmour());
    }
}
