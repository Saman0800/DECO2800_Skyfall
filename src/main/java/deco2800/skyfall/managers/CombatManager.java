package deco2800.skyfall.managers;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.observers.KeyDownObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Christopher Poli on 10/08/2019
 *
 * This class subscribes to key down events, and in turn causes combat events to happen.
 *
 * When "Z" is detected, swing your equip weapon.
 * When "X" is detected, launch a spell or special attack.
 * ....
 */
public class CombatManager extends AbstractManager implements KeyDownObserver {

    private static final Logger LOGGER = LoggerFactory.getLogger(CombatManager.class);

    public CombatManager() {
        LOGGER.info("Combat manager has been created...");
        GameManager.getManagerFromInstance(InputManager.class)
                .addKeyDownListener(this);
    }

    public void onKeyPressed(int keyCode) {

        LOGGER.info("Received key pressed event: " + keyCode);

        switch (keyCode) {
            case 1:
                //TODO: e.g. make char swing weapon
                break;
            case 2:
                //TODO: e.g. make char use special attack
            default:
                //do nothing
                break;
        }
    }

    @Override
    public void notifyKeyDown(int keycode) {
        this.onKeyPressed(keycode);
    }
}
