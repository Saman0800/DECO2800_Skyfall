package deco2800.skyfall.entities.enemies;

import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.AnimationLinker;

/**
 * Instance of a spider enemy.
  */
public class Spider extends AbstractEnemy implements Animatable {

    // Health of the spider.
    private static final transient int HEALTH = 10;

    private String enemyName = "spider";

    /**
     * Basic constructor for creating a spider enemy.
      */
    public Spider(float col, float row, MainCharacter mc) {
        super(col, row);

        this.setLevel(1);
        this.setSpeed(1);
        this.setRange(4);
        this.setHeight(1);
        this.setCanMove(true);
        this.setCharacter(mc);
        this.setHealth(HEALTH);
        this.setTexture(enemyName);
        this.setObjectName(enemyName);

        this.configureAnimations();
        this.setDirectionTextures();
        this.setAllSpeed(0.04f, 0.05f, 0.02f);
    }

    /**
     * Constructor used for testing spider enemy.
      */
    public Spider(float col, float row) {
        super(col, row);

        this.setLevel(1);
        this.setSpeed(1);
        this.setRange(4);
        this.setHeight(1);
        this.setCanMove(true);
        this.setHealth(HEALTH);
        this.setTexture(enemyName);
        this.setObjectName(enemyName);

        this.configureSounds();
        this.configureAnimations();
        this.setAllSpeed(0.04f, 0.05f, 0.02f);
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
    @Override
    public void configureAnimations() {
        this.addAnimations(AnimationRole.DEFENCE, Direction.DEFAULT,
                new AnimationLinker("spider_defence", AnimationRole.MOVE
                        , Direction.DEFAULT, true, true));
    }
}
