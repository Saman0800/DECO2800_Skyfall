package deco2800.skyfall.entities.spells;

import deco2800.skyfall.util.HexVector;

public class SpellFactory {

    public static Spell createSpell(SpellType spellType, HexVector mousePosition){
        Spell spell = null;

        if(spellType.equals(SpellType.FLAME_WALL)){
            spell = new FlameWall(mousePosition, "flame_wall_placeholder",
                    "spell", mousePosition.getCol(), mousePosition.getRow(),
                    1,
                    0.1f,
                    0);
        } else if(spellType.equals(SpellType.SHIELD)){
            System.out.println("Spawning shield.");
            spell = new Shield(mousePosition, "shield_placeholder",
                    "shield_spell", mousePosition.getCol(), mousePosition.getRow(),
                    1,
                    0.1f,
                    0);
        } else if(spellType.equals(SpellType.TORNADO)){
            spell = new Tornado(mousePosition, "tornado_placeholder",
                    "spell", mousePosition.getCol(), mousePosition.getRow(),
                    1,
                    0.1f,
                    10);
        }

        return spell;
    }
}
