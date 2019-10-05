package deco2800.skyfall.entities.spells;

import deco2800.skyfall.entities.MainCharacter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listens for keystrokes in sequential order to cast a spell.
 */
public class SpellCaster {

    //Combo of keystrokes recorded.
    protected int index = 0;

    //Record keystrokes here.
    protected int[] keyLog = new int[5];

    //Reference to the mainCharacter so a spell can be selected.
    private MainCharacter mainCharacter;

    //Logger.
    private final Logger logger = LoggerFactory.getLogger(SpellCaster.class);

    public SpellCaster(MainCharacter mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    /**
     * Receive a keyPressed event to log it.
     * @param keyCode The keycode that has been pressed.
     */
    public void onKeyPressed(int keyCode) {

        logger.trace("Key has been pressed.");

        //Save this keystroke in the keylog.
        keyLog[index] = keyCode;

        //Check if we are hitting a spell sequence.
        this.checkSpellSequence();
    }

    /**
     * Check the spell sequence of the keylog.
     */
    private void checkSpellSequence() {

        SpellType spellSequenceIn = null;
        boolean endOfSpellSequence = false;

        //Iterate through all the spell types.
        for (SpellType type : SpellType.values()) {
            int[] keySequence = this.getKeySequence(type);

            //If there is no key sequence, continue.
            if (keySequence == null) continue;

            // If the key sequence is the current keylog.
            if (keySequence[index] == keyLog[index]) {
                spellSequenceIn = type;
                //If at the end of the spell sequence.
                if (index == keySequence.length-1) {
                    endOfSpellSequence = true;
                }
            }
        }

        //If in no spell sequence. Reset.
        if (spellSequenceIn == null) {
            logger.trace("Note in a spell sequence.");
            this.resetSpellSequence();
        }
        //Else if at the end of the spell sequence
        else if (endOfSpellSequence) {
            logger.trace("At the end of this spell sequence, casting spell.");
            this.castSpell(spellSequenceIn);
        }
        //Else, we are in a spell sequence and not finished.
        else {
            logger.trace("In a spell sequence " + index);
            index++;
        }
    }

    /**
     * Cast a spell.
     * @param type The type of spell to cast.
     */
    private void castSpell(SpellType type) {
        mainCharacter.selectSpell(type);
        this.resetSpellSequence();
    }

    /**
     * Reset the spell sequence.
     */
    private void resetSpellSequence() {
        //Reset keylog and index.
        this.keyLog = new int[5];
        index = 0;
    }

    /**
     * Get a key sequence for a SpellType.
     * @param type The SpellType to get the key sequence for.
     * @return The keySequence to cast the spell.
     */
    private int[] getKeySequence(SpellType type) {

      int[] sequence = null;

      if (type == SpellType.FLAME_WALL)
          sequence = FlameWall.keySequence;
      else if (type == SpellType.SHIELD)
          sequence = Shield.keySequence;
      else if (type == SpellType.TORNADO)
          sequence = Tornado.keySequence;

      return sequence;
    }
}
