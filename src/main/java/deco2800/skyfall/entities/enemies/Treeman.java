package deco2800.skyfall.entities.enemies;

import deco2800.skyfall.Tickable;
import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.MainCharacter;

public class Treeman extends AbstractEnemy implements Animatable, Tickable {
    //The health of treeman
    private static final transient int HEALTH = 1;

    /*
    //The attack range of treeman
    private static final transient float ATTACK_RANGE = 1f;
    //The speed of treeman if it is angry and attack
    private static final transient float ANGRYSPEED = 0.01f;
    //The speed of treeman, if it get injure
    private static final transient float INJURESPEED = 0.00001f;
    private static final transient float INJURE_ANGRY_SPEED = 0.00005f;
    //The attack frequency of treeman
    private static final transient int ATTACK_FREQUENCY = 0;
    //The biome of treeman
    private static final transient String BIOME = "forest";
    //Moving direction
    private Direction movingDirection;


    //Set the period equal to zero , to account attack time
    private int period = 0;
    //Set the type
    private static final transient String ENEMY_TYPE = "treeman";

    private boolean attackStatus = false;
    //if the enemy is attacked by player or the player closed enough to the
    // enemy than the enemy will be in angry situation
    private int angerTimeAccount = 0;
    //To indicate whether the enemy arrives player's location
    private boolean complete = false;
    //a routine for destination
    private HexVector destination = null;
    */

    //savage animation
    private MainCharacter mc;

    /**
     * Initialization value of enemy treeman, and set the initial image in
     * the game
     */
    public Treeman(float col, float row, MainCharacter mc) {
        super(col, row);
        this.setTexture("enemyTreeman");
        this.setObjectName("enemyTreeman");
        this.setHeight(5);
        this.setHealth(HEALTH);
        this.setLevel(2);
        this.setSpeed(0.01f);
        this.setRange(3);
        this.setDamage(1);
        this.setCharacter(mc);
        this.enemyType = "treeman";
        this.configureAnimations();
        this.setDirectionTextures();
        this.configureSounds();
    }


    public Treeman(float col, float row) {
        super(col,row);
        this.setTexture("enemyTreeman");
        this.setObjectName("enemyTreeman");
        this.setHeight(1);
        this.setHealth(HEALTH);
        this.setLevel(2);
        this.setAllSpeed(0.02f, 0.05f, 0.02f);
        this.setRange(1);
        this.enemyType = "treeman";
        this.configureAnimations();
        this.setDirectionTextures();
        this.configureSounds();
    }



    private void configureSounds() {
        chasingSound = "treeWalk";
        diedSound = "treeDie";
    }

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
