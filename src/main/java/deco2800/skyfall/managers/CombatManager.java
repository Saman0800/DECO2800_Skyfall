package deco2800.skyfall.managers;

/**
 * Created by Christopher Poli on 10/08/2019
 *
 * This class subscribes to key down events, and in turn causes combat events to happen.
 *
 * When "Z" is detected, swing your equip weapon.
 * When "X" is detected, launch a spell or special attack.
 * ....
 */
public class CombatManager extends AbstractManager {

    public CombatManager() {
        System.out.println("Combat manager has been created...");
    }

    public void onKeyPressed(int keyCode) {

        System.out.println("Received key pressed event...");
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

}
