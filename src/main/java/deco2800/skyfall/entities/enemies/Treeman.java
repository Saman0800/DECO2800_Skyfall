package deco2800.skyfall.entities.enemies;

import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.AnimationLinker;

/**
 * Instance of a treeman enemy.
 */
public class Treeman extends AbstractEnemy implements Animatable {

    /**
     * Initialization value of enemy treeman, and set the initial image in
     * the game
     */
    public Treeman(float col, float row, MainCharacter mc) {
        super(col, row);

        this.setLevel(2);
        this.setRange(1);
        this.setDamage(1);
        this.setHeight(5);
        this.setHealth(10);
        this.setCanMove(true);
        this.setCharacter(mc);
        this.setObjectName("treeman");
        this.setTexture("enemyTreeman");
        this.setAllSpeed(0.02f, 0.05f, 0.02f);

        this.configureAnimations();
        this.setDirectionTextures();
        this.configureSounds();
    }

    /**
     * Constrcutor used for testing treeman enemy.
     *
     * @param col x-corrdinatr of the treeman instance.
     * @param row y-corrdinatr of the treeman instance.
     */
    public Treeman(float col, float row) {
        super(col,row);

        this.setLevel(2);
        this.setRange(1);
        this.setHeight(1);
        this.setHealth(10);
        this.setCanMove(true);
        this.setObjectName("treeman");
        this.setTexture("enemyTreeman");
        this.setAllSpeed(0.02f, 0.05f, 0.02f);

        this.configureAnimations();
        this.setDirectionTextures();
        this.configureSounds();
    }

    /**
     * Set up treeman sounds.
     */
    private void configureSounds() {
        chasingSound = "treeWalk";
        diedSound = "treeDie";
    }

    /**
     * Set up treeman animation.
     */
    @Override
    public void configureAnimations() {
        this.addAnimations(
                AnimationRole.MOVE, Direction.NORTH, new AnimationLinker("treemanMN", AnimationRole.MOVE, Direction.NORTH,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.NORTH_EAST, new AnimationLinker("treemanME", AnimationRole.MOVE, Direction.NORTH_EAST,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.NORTH_WEST, new AnimationLinker("treemanMW", AnimationRole.MOVE, Direction.NORTH_WEST,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.EAST, new AnimationLinker("treemanME", AnimationRole.MOVE, Direction.EAST,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.WEST, new AnimationLinker("treemanMW", AnimationRole.MOVE, Direction.WEST,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.SOUTH, new AnimationLinker("treemanMS", AnimationRole.MOVE, Direction.SOUTH,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.SOUTH_EAST, new AnimationLinker("treemanMSE", AnimationRole.MOVE, Direction.SOUTH_EAST,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.SOUTH_WEST, new AnimationLinker("treemanMSW", AnimationRole.MOVE, Direction.SOUTH_WEST,
                        true, true));

        this.addAnimations(
                AnimationRole.MELEE, Direction.EAST, new AnimationLinker("treemanAE", AnimationRole.MELEE, Direction.EAST,
                        true, true));
        this.addAnimations(
                AnimationRole.MELEE, Direction.NORTH, new AnimationLinker("treemanAN", AnimationRole.MELEE, Direction.NORTH,
                        true, true));
        this.addAnimations(
                AnimationRole.MELEE, Direction.SOUTH, new AnimationLinker("treemanAS", AnimationRole.MELEE, Direction.SOUTH,
                        true, true));
        this.addAnimations(
                AnimationRole.MELEE, Direction.SOUTH_EAST, new AnimationLinker("treemanASE", AnimationRole.MELEE, Direction.SOUTH_EAST,
                        true, true));
        this.addAnimations(
                AnimationRole.MELEE, Direction.SOUTH_WEST, new AnimationLinker("treemanASW", AnimationRole.MELEE, Direction.SOUTH_WEST,
                        true, true));
        this.addAnimations(
                AnimationRole.MELEE, Direction.WEST, new AnimationLinker("treemanAW", AnimationRole.MELEE, Direction.WEST,
                        true, true));
        this.addAnimations(
                AnimationRole.NULL, Direction.DEFAULT, new AnimationLinker("treemanDead", AnimationRole.DEFENCE, Direction.DEFAULT,
                        true, true));

    }


}
