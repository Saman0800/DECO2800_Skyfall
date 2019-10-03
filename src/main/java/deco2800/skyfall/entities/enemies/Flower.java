package deco2800.skyfall.entities.enemies;

import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.AnimationLinker;

/**
 * Instance of a flower enemy.
 */
public class Flower extends AbstractEnemy implements Animatable {



    /**
     * Basic Constructor for creating a flower enemy.
      */
    public Flower(float col, float row, MainCharacter mc) {
        super(col, row);

        this.setSpeed(1);
        this.setRange(2);
        this.setLevel(2);
        this.setHeight(1);
        this.setDamage(4);
        this.setHealth(13);
        this.setCharacter(mc);
        this.setCanMove(false);
        this.setTexture("flower");
        this.setObjectName("flower");

        this.configureAnimations();
        this.setDirectionTextures();
        this.configureSounds();
        this.setAllSpeed(0.04f, 0.05f, 0.02f);
    }



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
    @Override
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

