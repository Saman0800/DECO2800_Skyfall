package deco2800.skyfall.entities.enemies;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.animation.AnimationLinker;

/**
 * Instance of a stone enemy.
  */

public class Stone extends AbstractEnemy implements Animatable {

    /**
     * Basic constructor for creating a stone enemy.
      */
    public Stone(float col, float row, MainCharacter mc) {
        super(col, row);

        this.setLevel(2);
        this.setRange(1);
        this.setHeight(1);
        this.setDamage(3);
        this.setHealth(10);
        this.setCanMove(true);
        this.setCharacter(mc);
        this.setTexture("enemyStone");
        this.setObjectName("stone");
        this.setAllSpeed(0.008f, 0.009f, 0.007f);

        this.configureSounds();
        this.configureAnimations();
        this.setDirectionTextures();

    }

    /**
     * This constructor is for testing stone enemy.
      */
    public Stone(float col, float row) {
        super(col,row);

        this.setLevel(2);
        this.setSpeed(1);
        this.setRange(1);
        this.setHeight(1);
        this.setHealth(13);
        this.setObjectName("stone");
        this.setTexture("enemyStone");
        this.setAllSpeed(0.01f, 0.02f, 0.02f);

        this.configureAnimations();
        this.setDirectionTextures();
        this.configureSounds();
    }

    /**
     * Drop harvestables after defeated.
     *
     * @param col The x-coordinate the gold is dropped.
     * @param row The y-coordinate the gold is dropped.
     * @return List of items that are dropped.
     */
    public List<AbstractEntity> dropGold(float col, float row) {
        Random random = new Random();
        int dropCount = random.nextInt(5);
        System.out.println(dropCount);
        List<AbstractEntity> golds = new ArrayList<>();
        for (int i = 0; i < dropCount; i++) {
            System.out.println("Item is dropped");
//            golds.add(new GoldPiece((int)Math.random()*10+col, (int)Math.random()*10+row));
        }
        return golds;
    }

    /**
     * Set up stone sounds.
     */
    private void configureSounds() {
        chasingSound = "stoneWalk";
        diedSound = "stoneDie";
    }

    /**
     * loading stone animations.
     */
    @Override
    public void configureAnimations() {
        this.addAnimations(
                AnimationRole.MOVE, Direction.NORTH, new AnimationLinker("stoneJN", AnimationRole.MOVE, Direction.NORTH,
                        true, true));

        this.addAnimations(
                AnimationRole.MOVE, Direction.NORTH_EAST, new AnimationLinker("stoneJNE", AnimationRole.MOVE, Direction.NORTH_EAST,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.NORTH_WEST, new AnimationLinker("stoneJNW", AnimationRole.MOVE, Direction.NORTH_WEST,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.SOUTH, new AnimationLinker("stoneJS", AnimationRole.MOVE, Direction.SOUTH,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.SOUTH_EAST, new AnimationLinker("stoneJSE", AnimationRole.MOVE, Direction.SOUTH_EAST,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.SOUTH_WEST, new AnimationLinker("stoneJSW", AnimationRole.MOVE, Direction.SOUTH_WEST,
                        true, true));

        this.addAnimations(
                AnimationRole.MELEE, Direction.NORTH_WEST, new AnimationLinker("stoneANW", AnimationRole.MELEE, Direction.NORTH_WEST,
                        true, true));
        this.addAnimations(
                AnimationRole.MELEE, Direction.SOUTH, new AnimationLinker("stoneAS", AnimationRole.MELEE, Direction.SOUTH,
                        true, true));
        this.addAnimations(
                AnimationRole.MELEE, Direction.SOUTH_EAST, new AnimationLinker("stoneASE", AnimationRole.MELEE, Direction.SOUTH_EAST,
                        true, true));
        this.addAnimations(
                AnimationRole.MELEE, Direction.SOUTH_WEST, new AnimationLinker("stoneASW", AnimationRole.MELEE, Direction.SOUTH_WEST,
                        true, true));

    }


}

