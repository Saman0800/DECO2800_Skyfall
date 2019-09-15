package deco2800.skyfall.entities.spells;

import deco2800.skyfall.util.HexVector;
import org.junit.Assert;
import org.junit.Test;

public class SpellFactoryTest {

    /**
     * Test that a Flame Wall can get constructed as the correct type.
     */
    @Test
    public void constructFlameWall() {
        Spell spell = SpellFactory.createSpell(SpellType.FLAME_WALL,new HexVector());
        Assert.assertTrue(spell instanceof FlameWall);
    }

    /**
     * Test that a Shield can get constructed as the correct type.
     */
    @Test
    public void constructShield() {
        Spell spell = SpellFactory.createSpell(SpellType.SHIELD,new HexVector());
        Assert.assertTrue(spell instanceof Shield);
    }

    /**
     * Test that a Tornado can get constructed as the correct type.
     */
    @Test
    public void constructTornado() {
        Spell spell = SpellFactory.createSpell(SpellType.TORNADO,new HexVector());
        Assert.assertTrue(spell instanceof Tornado);
    }


}