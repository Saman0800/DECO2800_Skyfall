package deco2800.skyfall.entities.spells;

import deco2800.skyfall.util.HexVector;

public class SpellFactory {

    /**
     * This class is a utility class and should not be instantiated.
     */
    private SpellFactory() {
        throw new IllegalStateException("Utility class.");
    }

    /**
     * Create a new spell.
     * @param spellType The type of spell to create.
     * @param mousePosition The position to create the spell at.
     * @return A new spell.
     */
    public static Spell createSpell(SpellType spellType, HexVector mousePosition){
        Spell spell = null;

        if(spellType.equals(SpellType.FLAME_WALL)){
            spell = new FlameWall(mousePosition, "flame_wall_placeholder",
                    "spell", mousePosition.getCol(), mousePosition.getRow(),
                    1,
                    0.1f,
                    0);
        } else if(spellType.equals(SpellType.SHIELD)){
            spell = new Shield(mousePosition, "shield_placeholder",
                    "shield_spell", mousePosition.getCol(), mousePosition.getRow(),
                    1,
                    0.1f,
                    0);
        } else if(spellType.equals(SpellType.TORNADO)){
            spell = new Tornado(mousePosition, "tornado_placeholder",
                    "spell", mousePosition.getCol(), mousePosition.getRow(),
                    20,
                    0.1f,
                    10);
        }

        return spell;
    }
}
