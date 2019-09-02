package deco2800.skyfall.entities;

/**
 * Class for character stats such as damage output, damage reduction and more..
 */
public class CharacterStats {
    private float attackModifier;
    private float armorModifier;

    public CharacterStats(float attackModifier, float armorModifier) {
        this.attackModifier = attackModifier;
        this.armorModifier = armorModifier;
    }

    public float getAttackModifier() {
        return this.attackModifier;
    }

    public void setAttackModifier(float attackModifier) {
        this.attackModifier = attackModifier;
    }

    public float getArmorModifier() {
        return this.armorModifier;
    }

    public void setArmorModifier(float armorModifier) {
        this.armorModifier = armorModifier;
    }
}
