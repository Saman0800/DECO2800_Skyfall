package deco2800.skyfall.entities.spells;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.EnemyEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.util.HexVector;

import java.util.List;

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

        //Loop through enemies.
        List<AbstractEntity> entities =  GameManager.get().getWorld().getEntities();

        //TODO can add a kd tree or similar to only select enemies in the target area.
        for (AbstractEntity entity : entities) {
            if (entity instanceof EnemyEntity) {
                //If close enough, deal damage to the enemy over time.
                if (this.position.isCloseEnoughToBeTheSameByDistance(entity.getPosition(),1)) {
                    ((EnemyEntity) entity).takeDamage(this.getDamage());
                    this.destroy();
                    //TODO: add a status indicator.
                    //entity.addStatusIndicator();
                }
            }
        }

    }
}
