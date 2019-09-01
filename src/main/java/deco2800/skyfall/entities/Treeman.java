package deco2800.skyfall.entities;

import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;

public class Treeman extends EnemyEntity{

    private static final transient int HEALTH = 15;
    private static final transient float ATTACK_RANGE = 1f;
    private static final transient int ATTACK_SPEED = 1000;
    private static final transient String BIOME = "forest";
    private boolean moving = false;
    private float originalCol;
    private float originalRow;
    private boolean moved = false;
    private static final transient String ENEMY_TYPE = "treeman";
    private MainCharacter mc;
    private Direction movingDirection;

    public Treeman(float col, float row, MainCharacter mc) {
        super(col, row);
        this.originalCol = col;
        this.originalRow = row;
        this.setTexture("enemyTreeman");
        this.setObjectName("enemyTreeman");
        this.setHeight(5);
        this.setHealth(HEALTH);
        this.setLevel(2);
        this.setSpeed(2);
        this.setArmour(5);
        this.mc=mc;
        this.configureAnimations();
        this.setDirectionTextures();
    }

    public Treeman(float row, float col, String texturename, int health, int armour, int damage) {
        super(row, col, texturename, health, armour, damage);
    }

    public String getEnemyType() {
        return ENEMY_TYPE;
    }

    public boolean getMoving() {
        return moving;
    }

    public String getBiome() {
        return BIOME;
    }

    //Override the getHealth in Treeman class
    @Override
    public int getHealth(){
        return HEALTH;
    }



    public void configureAnimations() {
        this.addAnimations(
                AnimationRole.DEFENCE,
                Direction.DEFAULT,
                new AnimationLinker("treemanAttack5",
                        AnimationRole.MOVE, Direction.DEFAULT,
                        true, true));
    }


    public void setDirectionTextures() {
    }



    @Override
    public String toString() {
        return String.format("%s at (%d, %d) %s biome", getEnemyType(), (int) getCol(), (int) getRow(), getBiome());
    }

}
