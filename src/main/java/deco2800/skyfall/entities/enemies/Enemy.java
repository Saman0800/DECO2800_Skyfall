package deco2800.skyfall.entities.enemies;

import deco2800.skyfall.Tickable;
import deco2800.skyfall.animation.AnimationLinker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import deco2800.skyfall.entities.Peon;


import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.entities.ICombatEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.animation.AnimationRole;

/**
 * An instance to abstract the basic variables and  methods of an enemy.
 */
public class Enemy extends Peon implements Animatable, ICombatEntity, Tickable {

    // Logger for tracking enemy information
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // animation timings
    private long hurtTime;
    private long attackTime;
    private long deadTime;
    // Booleans to check whether Enemy is in a state.
    private boolean isMoving;
    private boolean isAttacking;
    private boolean isHurt = false;
    // walking speeds
    private float walkingSpeed;
    private float chasingSpeed;
    // sound effects
    private String chaseSound;
    private String attackSound;
    private String deadSound;
    // default value
    private float strength;
    private float attackRange;
    // names
    private String biome;

    private MainCharacter mainCharacter;

    public Enemy(float col, float row, String hitBoxPath, String name, String biome, String textureName) {
        this.setPosition(col, row);
        this.initialiseBox2D(col, row, hitBoxPath);
        this.setCollidable(true);

        this.setName(name);
        this.setBiome(biome);

        this.setMainCharacter(MainCharacter.getInstance());

        this.setTexture(textureName);
        this.setDirectionTextures();
        this.configureAnimations();
        this.configureSounds();
    }

    public Enemy(float col, float row) {
        this.setPosition(col, row);
    }

    private void attackPlayer() {
        // TODO: write code for attacking player
    }

    private void moveToPlayer() {
        // TODO: write code for moving to player
    }

    public void takeDamage(int damage) {
        // TODO: write code for taking damage
        // Destroys the enemy if it has taken too much damage
    }

    @Override
    public void dealDamage(MainCharacter mc) {
        // TODO: write code for dealing damage
    }

    @Override
    public boolean canDealDamage() {
        // TODO: write code for checking if damage can be dealt
        return false;
    }

    @Override
    public int getDamage() {
        // TODO: write code for how much damage should be dealt
        return 0;
    }

    @Override
    public int[] getResistanceAttributes() {
        // TODO: write code for this
        // Nothing else uses this and I'm not sure what it's for
        return new int[0];
    }

    public void updateAnimation() {
        // TODO: write code for updating the enemy animation
        // Uses current direction
        // May need to move angle code from mainCharacter to peon
    }

    public void onTick(int i) {
        // TODO: write all code to be called on each tick
    }

    @Override
    public void setHealth(int health) {
        this.changeHealth(this.getHealth() - health);
    }


    public void setValues(float scaling, int health, float strength, float attackRange, float walkingSpeed, float chasingSpeed) {
        this.setMaxHealth((int) (health * scaling));
        this.setStrength(strength * scaling);
        this.setAttackRange(attackRange * scaling);
        this.setWalkingSpeed(walkingSpeed * scaling);
        this.setChasingSpeed(chasingSpeed * scaling);
    }

    @Override
    public void setDirectionTextures() {
        String animationNameStart = "__ANIMATION_" + this.getName();
        defaultDirectionTextures.put(Direction.EAST, animationNameStart + "E_Anim:0");
        defaultDirectionTextures.put(Direction.NORTH, animationNameStart + "N_Anim:0");
        defaultDirectionTextures.put(Direction.WEST, animationNameStart + "W_Anim:0");
        defaultDirectionTextures.put(Direction.SOUTH, animationNameStart + "S_Anim:0");
        defaultDirectionTextures.put(Direction.NORTH_EAST, animationNameStart + "NE_Anim:0");
        defaultDirectionTextures.put(Direction.NORTH_WEST, animationNameStart + "NW_Anim:0");
        defaultDirectionTextures.put(Direction.SOUTH_EAST, animationNameStart + "SE_Anim:0");
        defaultDirectionTextures.put(Direction.SOUTH_WEST, animationNameStart + "SW_Anim:0");
    }


    public void configureSounds() {
        String name = this.getName();
        this.chaseSound = name + "Walk";
        this.attackSound = name + "Attack";
        this.deadSound = name + "Dead";
    }

    @Override
    public void configureAnimations() {
        String name_biome = this.getName() + this.getBiome();
        this.addAnimations(
                AnimationRole.MOVE, Direction.NORTH, new AnimationLinker(name_biome + "MN", AnimationRole.MOVE, Direction.NORTH,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.NORTH_EAST, new AnimationLinker(name_biome + "ME", AnimationRole.MOVE, Direction.NORTH_EAST,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.NORTH_WEST, new AnimationLinker(name_biome + "MW", AnimationRole.MOVE, Direction.NORTH_WEST,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.EAST, new AnimationLinker(name_biome + "ME", AnimationRole.MOVE, Direction.EAST,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.WEST, new AnimationLinker(name_biome + "MW", AnimationRole.MOVE, Direction.WEST,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.SOUTH, new AnimationLinker(name_biome + "MS", AnimationRole.MOVE, Direction.SOUTH,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.SOUTH_EAST, new AnimationLinker(name_biome + "MSE", AnimationRole.MOVE, Direction.SOUTH_EAST,
                        true, true));
        this.addAnimations(
                AnimationRole.MOVE, Direction.SOUTH_WEST, new AnimationLinker(name_biome + "MSW", AnimationRole.MOVE, Direction.SOUTH_WEST,
                        true, true));

        this.addAnimations(
                AnimationRole.ATTACK, Direction.EAST, new AnimationLinker(name_biome + "AE", AnimationRole.ATTACK, Direction.EAST,
                        true, true));
        this.addAnimations(
                AnimationRole.ATTACK, Direction.NORTH, new AnimationLinker(name_biome + "AN", AnimationRole.ATTACK, Direction.NORTH,
                        true, true));
        this.addAnimations(
                AnimationRole.ATTACK, Direction.SOUTH, new AnimationLinker(name_biome + "AS", AnimationRole.ATTACK, Direction.SOUTH,
                        true, true));
        this.addAnimations(
                AnimationRole.ATTACK, Direction.SOUTH_EAST, new AnimationLinker(name_biome + "ASE", AnimationRole.ATTACK, Direction.SOUTH_EAST,
                        true, true));
        this.addAnimations(
                AnimationRole.ATTACK, Direction.SOUTH_WEST, new AnimationLinker(name_biome + "ASW", AnimationRole.ATTACK, Direction.SOUTH_WEST,
                        true, true));
        this.addAnimations(
                AnimationRole.ATTACK, Direction.WEST, new AnimationLinker(name_biome + "AW", AnimationRole.ATTACK, Direction.WEST,
                        true, true));
        this.addAnimations(
                AnimationRole.NULL, Direction.DEFAULT, new AnimationLinker(name_biome + "Dead", AnimationRole.DEFENCE, Direction.DEFAULT,
                        true, true));

    }

    /**
     *  Get whether enemy is hurt.
     */
    public boolean isHurt() {
        return isHurt;
    }

    /**
     *  Set whether enemy is hurt.
     * @param isHurt the player's "hurt" status
     */
    public void setHurt(boolean isHurt) {
        this.isHurt = isHurt;
    }

    // Getter and setters
    public void setHurtTime(long hurtTime) {
        this.hurtTime = hurtTime;
    }

    public long getHurtTime() {
        return hurtTime;
    }

    public void setAttackTime(long attackTime) {
        this.attackTime = attackTime;
    }

    public long getAttackTime() {
        return attackTime;
    }

    public void setDeadTime(long deadTime) {
        this.deadTime = deadTime;
    }

    public long getDeadTime() {
        return deadTime;
    }

    public void setWalkingSpeed(float walkingSpeed) {
        this.walkingSpeed = walkingSpeed;
    }

    public float getWalkingSpeed() {
        return this.walkingSpeed;
    }

    public void setChasingSpeed(float chasingSpeed) {
        this.chasingSpeed = chasingSpeed;
    }

    public float getChasingSpeed() {
        return this.chasingSpeed;
    }

    public void setChaseSound(String chaseSound) {
        this.chaseSound = chaseSound;
    }

    public String getChaseSound() {
        return this.chaseSound;
    }

    public void setAttackSound(String attackSound) {
        this.attackSound = attackSound;
    }

    public String getAttackSound() {
        return this.attackSound;
    }

    public void setDeadSound(String deadSound) {
        this.deadSound = deadSound;
    }

    public String getDeadSound() {
        return this.deadSound;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }

    public float getStrength() {
        return this.strength;
    }

    public void setBiome(String biome) {
        this.biome = biome;
    }

    public String getBiome() {
        return this.biome;
    }

    public void setMainCharacter(MainCharacter mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    public MainCharacter getMainCharacter() {
        return this.mainCharacter;
    }

    public void setAttackRange(float attackRange) {
        this.attackRange = attackRange;
    }

    public float getAttackRange() {
        return this.attackRange;
    }
}
