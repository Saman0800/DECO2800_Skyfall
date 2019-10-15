package deco2800.skyfall.entities;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CharacterStatsTest {

    private CharacterStats characterStats = new CharacterStats(1f,1f);

    @Test
    public void testGetAttackModifier() {
        assertThat("", characterStats.getAttackModifier(), is(equalTo(1f)));
    }

    @Test
    public void testSetAttackModifier() {
        assertThat("", characterStats.getAttackModifier(), is(equalTo(1f)));
        characterStats.setAttackModifier(2f);
        assertThat("", characterStats.getAttackModifier(), is(equalTo(2f)));
        characterStats.setAttackModifier(-1f);
        assertThat("", characterStats.getAttackModifier(), is(equalTo(-1f)));
    }

    @Test
    public void testGetArmourModifier() {
        assertThat("", characterStats.getArmorModifier(), is(equalTo(1f)));
    }

    @Test
    public void testSetArmourModifier() {
        assertThat("", characterStats.getArmorModifier(), is(equalTo(1f)));
        characterStats.setArmorModifier(2f);
        assertThat("", characterStats.getArmorModifier(), is(equalTo(2f)));
        characterStats.setArmorModifier(-1f);
        assertThat("", characterStats.getArmorModifier(), is(equalTo(-1f)));
    }


}
