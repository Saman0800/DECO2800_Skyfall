package deco2800.skyfall.worlds.world;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.worlditems.ForestTree;
import deco2800.skyfall.gui.GuiMaster;
import deco2800.skyfall.gui.ScrollingTextBox;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.observers.TouchDownObserver;
import java.util.List;

/**
 * This is a tutorial world created to help the player understand the world and
 * the mechanics of the game.
 */
public class TutorialWorld extends World implements TouchDownObserver {
    // Variables used for the event triggers used in the tutorial
    private boolean firstTime = true;
    private boolean testKilledTree = false;
    private ForestTree testTutorialTree;

    /**
     * Constructs a tutorial world using the Rocket World constructor
     * 
     * @param worldParameters Class that contains all the world parameters
     */
    public TutorialWorld(WorldParameters worldParameters) {
        super(worldParameters);
    }

    @Override
    public void onTick(long i) {
        super.onTick(i);

        List<AbstractEntity> entityList = GameManager.get().getWorld().getEntities();

        ScrollingTextBox testTutorialBox = GuiMaster.ScrollingTextBox("tutorialScrollingBox");
        if (firstTime) {
            // Flavour text for the tutorial
            testTutorialBox
                    .setString("Good morning citizen 27720. I am " + "the caretaker AI responsible for this cryopod "
                            + "facility. You may call me Karen. While thousand of "
                            + "years looking after what amounts to vegetables has "
                            + "made me somewhat jaded, in accordance with "
                            + "protocol I must teach you the skills required to "
                            + "function properly. Please go murder that piece of "
                            + "flora over there and take its flesh. You can do "
                            + "this by right clicking on it. If you wish to get "
                            + "closer to it before ending its existence, please " + "use the wasd keys to move.");
            testTutorialBox.start();
        }
        firstTime = false;

        for (AbstractEntity e : entityList) {
            if (e instanceof ForestTree) {
                testTutorialTree = (ForestTree) e;
            }
        }

        if (!entityList.contains(testTutorialTree) && !testKilledTree) {
            testKilledTree = true;
            testTutorialBox.reset();
            testTutorialBox.setString("Congratulations. You have " + "successfully ended the life of a harmless, "
                    + "non-sentient life form. You monster. If we had more "
                    + "time I would enjoy testing your current emotional situation."
            /*
             * , however it seems that a still harmless, but " +
             * "far more sentient creature is currently immobile to " +
             * "your north. Please move your camera up by using " + "the up arrow key and
             * end this creature in the same way you did the last."
             */);
            testTutorialBox.start();
        }


    }
}
