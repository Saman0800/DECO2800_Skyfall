package deco2800.skyfall.entities.spells;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.util.HexVector;

public class Shield extends Spell {


    //Keep a reference to the maincharacter so this spell can stay on it's position.
    private MainCharacter mc;

    public Shield(HexVector movementPosition, String textureName, String objectName,
                  float col, float row, int damage, float speed, int range) {

        super(movementPosition, textureName, objectName, col, row, damage, speed, range);

        //Shield must stay in place on the player.
        this.range = 0;
        this.manaCost = 30;
        this.mc = GameManager.getManagerFromInstance(GameMenuManager.class).getMainCharacter();
        if (this.mc != null) {
            this.mc.setInvincible(true);
        }
    }

    @Override
    public void onTick(long tick) {
        super.onTick(tick);
        this.setPosition(this.mc.getCol(),this.mc.getRow(),this.mc.getHeight());
    }

    @Override
    public void destroy() {
        this.mc.setInvincible(false);
        super.destroy();
    }
}
