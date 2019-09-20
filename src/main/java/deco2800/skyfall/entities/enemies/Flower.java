package deco2800.skyfall.entities.enemies;

import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.ICombatEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.SoundManager;
import deco2800.skyfall.util.HexVector;

public class Flower extends AbstractEnemy implements Animatable {
    private static transient int HEALTH = 1;

    //savage animation
    public MainCharacter mc;

    public Flower(float col, float row, MainCharacter mc) {
        super(col, row);
        this.setTexture("flower");
        this.setObjectName("flower");
        this.setHeight(1);
        this.setHealth(HEALTH);
        this.setDamage(4);
        this.setLevel(2);
        this.setSpeed(1);
        this.setAllSpeed(0.04f, 0.05f, 0.02f);
        this.setRange(4);
        this.setCharacter(mc);
        this.enemyType = "flower";
        this.configureAnimations();
        this.setDirectionTextures();
        this.configureSounds();
    }

    public Flower(float col, float row) {
        super(col,row);
        this.setDamage(4);
        this.setTexture("flower");
        this.setObjectName("flower");
        this.setHeight(1);
        this.setHealth(HEALTH);
        this.setLevel(2);
        this.setSpeed(1);
        this.setAllSpeed(0.04f, 0.05f, 0.02f);
        this.setRange(4);
        this.setCharacter(mc);
        this.enemyType = "flower";
        this.configureAnimations();
        this.setDirectionTextures();
        this.configureSounds();
    }

    private void configureSounds() {
        chasingSound = "flowerWalk";
        diedSound = "flowerDie";
    }

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

