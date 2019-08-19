package deco2800.skyfall.worlds;

import com.badlogic.gdx.Gdx;
import deco2800.skyfall.entities.*;
import deco2800.skyfall.gui.GuiMaster;
import deco2800.skyfall.gui.ScrollingTextBox;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InputManager;
import deco2800.skyfall.observers.TouchDownObserver;
import deco2800.skyfall.util.Cube;
import deco2800.skyfall.util.WorldUtil;

import javax.swing.*;
import java.util.List;
import java.util.Random;

public class TutorialWorld extends RocketWorld implements TouchDownObserver {
    boolean firstTime = true;
    boolean testKilledTree = false;
    Tree testTutorialTree;
    public TutorialWorld(long seed, int worldSize, int nodeSpacing) {
        super(seed, worldSize, nodeSpacing);
    }

    @Override
    public void onTick(long i) {
        super.onTick(i);

        List<AbstractEntity> entityList = GameManager.get().getWorld().getEntities();

        ScrollingTextBox testTutorialBox = GuiMaster.ScrollingTextBox("tutorialScrollingBox");
        if (firstTime) {
            testTutorialBox.setString("Good morning citizen 27720. I am " +
                    "the caretaker AI responsible for this cryopod " +
                    "facility. You may call me Karen. While thousand of " +
                    "years looking after what amounts to vegetables has " +
                    "made me somewhat jaded, in accordance with " +
                    "protocol I must teach you the skills required to " +
                    "function properly. Please go murder that piece of " +
                    "flora over there and take its flesh. You can do " +
                    "this by right clicking on it. If you wish to get " +
                    "closer to it before ending its existence, please " +
                    "use the wasd keys to move.");
            testTutorialBox.start();
        }
        firstTime = false;

            for (AbstractEntity e : entityList) {
                if (e instanceof Tree) {
                    testTutorialTree = (Tree) e;
                }
						/*if(e instanceof SleepingBowMan) {
							testTutorialEnemy = (SleepingBowMan) e;
						}*/
            }

            if (!entityList.contains(testTutorialTree) && !testKilledTree) {
                testKilledTree = true;
                testTutorialBox.reset();
                testTutorialBox.setString("Congratulations. You have " +
                                "successfully ended the life of a harmless, " +
                                "non-sentient life form. You monster. If we had more " +
                                "time I would enjoy testing your current emotional situation."
								/*, however it seems that a still harmless, but " +
								"far more sentient creature is currently immobile to " +
								"your north. Please move your camera up by using " +
								"the w key and end this creature in the same way" +
								" you did the last."*/);
                testTutorialBox.start();
            }





					/*if (!entityList.contains(testTutorialEnemy) && !testKilledEnemy) {
						testKilledEnemy = true;
						testTutorialBox.reset();
						testTutorialBox.setString("Now that nothing, no matter how " +
								"harmless, can hurt your squishy body, please go collect " +
								"the remnants of the first creature you slaughtered. This" +
								" can be done by walking on top of it where it used to " +
								"stand. With these materials you can now create morally" +
								" questionable tools and building. Hooray. Please press " +
								"(inventory key here) to begin this process.");
						testTutorialBox.start();
					}*/
    }
}
