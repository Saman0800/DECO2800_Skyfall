package deco2800.skyfall.gamemenu;

/**
 * This should be used by the MainCharacter class, and only called when damage
 * is taken in order to update it.
 *
 * Inspired from HealthCircle.java, but this is held in the MainCharacter class
 * and it only needs to be updated when the character takes/restores mana.
 *
 */
public class ManaBar extends StatBar {

    public ManaBar(int currentValue, String biggerTextureName, String smallerTextureName) {
        super(currentValue, "Mana", biggerTextureName, smallerTextureName);
    }

    /**
     * Updates
     */
    protected void updateInnerCircle(int newValue) {

        if (smallerCircle == null) {
            if (biggerCircle == null) {
            }
            return;
        }

        float percentageDiff = (float) newValue / (float) initialValue;
        int height = (int) (100 * percentageDiff);

        smallerCircle.setSize(100, height);
        smallerCircle.setPosition(positionX, positionY);
        currentValue = newValue;
        label.setText("" + newValue);
    }

    /**
     * Keeps the object in the correct position no matter how window is resized.
     * Inspiration for the method was taken from HealthCircle.java.
     */
    protected void updateWithViewportChanges() {
        positionX = (stage.getCamera().position.x + (stage.getCamera().viewportWidth / 2) - 100);
        positionY = (stage.getCamera().position.y + (stage.getCamera().viewportHeight / 2) - 300);
        smallerCircle.setPosition(positionX, positionY);
        biggerCircle.setPosition(positionX, positionY);
        label.setPosition(positionX + 20, positionY + 20);
    }
}
