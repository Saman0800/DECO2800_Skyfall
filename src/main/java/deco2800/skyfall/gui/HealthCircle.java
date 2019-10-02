package deco2800.skyfall.gui;

public class HealthCircle {

//    public HealthCircle(int currentValue, String biggerTextureName, String smallerTextureName) {
//        super(currentValue, "Health", biggerTextureName, smallerTextureName);
//    }
//
//    /**
//     * Keeps the object on the top left of the screen
//     */
//    protected void updateWithViewportChanges() {
//        positionX = (stage.getCamera().position.x + (stage.getCamera().viewportWidth / 2) - 100);
//        positionY = (stage.getCamera().position.y + (stage.getCamera().viewportHeight / 2) - 100);
//        smallerCircle.setPosition(positionX + offset, positionY + offset);
//        biggerCircle.setPosition(positionX, positionY);
//        label.setPosition(positionX + 15, positionY + 40);
//    }
//
//    /**
//     * Updates
//     */
//    protected void updateInnerCircle(int newValue) {
//        float diff = (float)(currentValue - newValue);
//
//        if (smallerCircle == null || biggerCircle == null) {
//            return;
//        }
//
//        smallerCircle.setSize(10 * (float)newValue, 10 * (float)newValue);
//        offset += (diff * 10) / 2;
//        smallerCircle.setPosition(positionX + offset, positionY + offset);
//        currentValue = newValue;
//        if (newValue >= 1) {
//            label.setText("Health: " + newValue);
//        }
//
//        if (newValue < 0) {
//            label.setText("DEAD");
//        }
//    }

}
