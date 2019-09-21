package deco2800.skyfall.entities.enemies;

import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.AnimationLinker;

/**
 * Instance of a robot enemy.
  */
public class Robot extends AbstractEnemy implements Animatable {

    private String enemyName = "robot";

    /**
     * Basic constructor to create a robot enemy.
      */
    public Robot(float col, float row, MainCharacter mc) {
        super(col,row);

        this.setLevel(2);
        this.setSpeed(1);
        this.setRange(1);
        this.setHeight(1);
        this.setHealth(10);
        this.setCanMove(true);
        this.setCharacter(mc);
        this.setTexture(enemyName);
        this.setObjectName(enemyName);

        this.configureAnimations();
        this.setDirectionTextures();
        this.configureSounds();
    }

    /**
     * Constructor used for testing robot enemy.
      */
    public Robot(float col, float row) {
        super(col,row);

        this.setLevel(2);
        this.setSpeed(1);
        this.setRange(1);
        this.setHeight(1);
        this.setHealth(10);
        this.setCanMove(true);
        this.setTexture(enemyName);
        this.setObjectName(enemyName);

        this.configureAnimations();
        this.setDirectionTextures();
        this.configureSounds();
    }

    /**
     * Set up robot sounds.
      */
    private void configureSounds() {
        chasingSound = "robotWalk";
        diedSound = "robotDie";
    }

    /**
     * Set up robot animations
      */
    @Override
    public void configureAnimations() {
        this.addAnimations(
            AnimationRole.ATTACK, Direction.DEFAULT, new AnimationLinker(
                    "robot_defence", AnimationRole.MOVE,
                        Direction.DEFAULT, true, true));
    }
}
