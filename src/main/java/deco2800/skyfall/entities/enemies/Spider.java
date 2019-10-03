package deco2800.skyfall.entities.enemies;

import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.AnimationLinker;

/**
 * Instance of a spider enemy.
  */
public class Spider extends Enemy {

    // Health of the spider.
    private static final transient int HEALTH = 10;

    private String enemyName = "spider";

    /**
     * Basic constructor for creating a spider enemy.
      */
    public Spider(float col, float row, MainCharacter mc) {
        super(col, row);

        this.setSpeed(1);
        this.setHeight(1);
        this.setHealth(HEALTH);
        this.setTexture(enemyName);
        this.setObjectName(enemyName);

        this.configureAnimations();
    }

    /**
     * Constructor used for testing spider enemy.
      */
    public Spider(float col, float row) {
        super(col, row);

        this.setHeight(1);
        this.setHealth(HEALTH);
        this.setTexture(enemyName);
        this.setObjectName(enemyName);

        this.configureSounds();
        this.configureAnimations();
    }

    /**
     * Set up spider sounds.
      */
    private void configureSounds() {
        chasingSound = "spiderWalk";
        diedSound = "spiderDie";
    }

    /**
     * Set up spider animations.
      */
    public void configureAnimations() {
        this.addAnimations(AnimationRole.DEFENCE, Direction.DEFAULT,
                new AnimationLinker("spider_defence", AnimationRole.MOVE
                        , Direction.DEFAULT, true, true));
    }
}
