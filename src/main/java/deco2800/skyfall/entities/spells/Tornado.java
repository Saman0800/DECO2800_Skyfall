package deco2800.skyfall.entities.spells;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.util.HexVector;

public class Tornado extends Spell {

    private MainCharacter mc;

    public Tornado(HexVector movementPosition, String textureName, String objectName,
                  float col, float row, int damage, float speed, int range) {

        super(movementPosition, textureName, objectName, col, row, damage, speed, range);

        this.mc = GameManager.getManagerFromInstance(GameMenuManager.class).getMainCharacter();

        this.position = new HexVector(this.mc.getCol(), this.mc.getRow());

        this.manaCost = 10;

    }


    @Override
    public void onTick(long tick) {
        super.onTick(tick);

    }
}
