package deco2800.skyfall.entities.spells;

import deco2800.skyfall.util.HexVector;

public class Tornado extends Spell {

    public Tornado(HexVector movementPosition, String textureName, String objectName,
                  float col, float row, int damage, float speed, int range) {

        super(movementPosition, textureName, objectName, col, row, damage, speed, range);

        this.manaCost = 10;

    }


    @Override
    public void onTick(long tick) {
        super.onTick(tick);

    }
}
