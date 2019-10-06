package deco2800.skyfall.entities.enemies;

import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.AnimationLinker;

/**
 * Instance of a flower enemy.
 */
public class Flower extends Enemy implements Spawnable {

    /**
     * Basic Constructor for creating a flower enemy.
      */
    public Flower(float col, float row, MainCharacter mc) {
        super(col, row);

        this.setHeight(1);
        this.setHealth(13);
        this.setTexture("flower");
        this.setObjectName("flower");

        this.configureAnimations();
        this.configureSounds();
    }

    @Override
    public Flower newInstance(float row, float col) {
        return new Flower(col, row, MainCharacter.getInstance());
    }

    /*
    /**
     * Constructor foe testing flower enemy.
      */
    /*
    public Flower(float col, float row) {
        super(col,row);

        this.setLevel(2);
        this.setRange(4);
        this.setSpeed(1);
        this.setDamage(4);
        this.setHeight(1);
        this.setHealth(13);
        this.setCanMove(false);
        this.setTexture("flower");
        this.setObjectName("flower");
        this.setAllSpeed(0.04f, 0.05f, 0.02f);

        this.configureAnimations();
        this.setDirectionTextures();
        this.configureSounds();
    }
    */

    /**
     * Set up flower sounds.
      */
    private void configureSounds() {
        chasingSound = "flowerWalk";
        diedSound = "flowerDie";
    }

    /**
     * Set up flower animations.
      */
    public void configureAnimations() {
        this.addAnimations(
                AnimationRole.MOVE, Direction.DEFAULT,
                new AnimationLinker("flower_defence",
                        AnimationRole.MOVE, Direction.DEFAULT, true, true));
        this.addAnimations(
                AnimationRole.ATTACK, Direction.DEFAULT,
                new AnimationLinker("flower_melee",
                        AnimationRole.ATTACK, Direction.DEFAULT, true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.DEFAULT,
                new AnimationLinker("flower_close",
                        AnimationRole.MOVE, Direction.DEFAULT, true, true));
    }
}

