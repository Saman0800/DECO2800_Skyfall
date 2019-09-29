package deco2800.skyfall.entities.spells;

import com.badlogic.gdx.Input;
import deco2800.skyfall.observers.KeyDownObserver;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * Listens for keystrokes in sequential order to cast a spell.
 */
public class SpellCaster {

    //Combo of keystrokes recorded.
    private int index = 0;

    //Record keystrokes here.
    private int[] keyLog = new int[5];

    public SpellCaster() {

    };

    public void onKeyPressed(int keyCode) {

        //Save this keystroke in the keylog.
        keyLog[index] = keyCode;

        //Check if we are hitting a spell sequence.
        this.checkSpellSequence();
    }

    private void checkSpellSequence() {

        boolean inSpellSequence = false;

        //Iterate through all the spell types.
        for (SpellType type : SpellType.values()) {
            int[] keySequence = this.getKeySequence(type);

            if (keySequence[index] == keyLog[index]) {
                inSpellSequence = true;
            }

        }

        //If there was no spell sequence match.
        if (inSpellSequence) {
            index++;
        } else if (!inSpellSequence) {
            this.resetSpellSequence();
        }


    }

    private void resetSpellSequence() {
        this.keyLog = new int[5];
    }

    /**
     * Get a key sequence for a spelltype.
     * @param type
     * @return
     */
    private int[] getKeySequence(SpellType type) {

      int[] sequence = null;

      if (type == SpellType.FLAME_WALL)
          sequence = FlameWall.keySequence;
      //else if (type == SpellType.SHIELD)
          //sequence = Shield.keySequence;


      return sequence;


    };
}
