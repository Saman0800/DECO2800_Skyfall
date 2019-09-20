package deco2800.skyfall.entities.enemies;



import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.ICombatEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.SoundManager;

public class Robot extends AbstractEnemy implements Animatable {
    private static final transient int HEALTH = 10;

    public Robot(float col, float row, MainCharacter mc) {
        super(col,row);
        this.setTexture("robot");
        this.setObjectName("robot");
        this.setHeight(1);
        this.setHealth(HEALTH);
        this.setLevel(2);
        this.setSpeed(1);
        this.setRange(1);
        this.setCanMove(true);
        this.setCharacter(mc);
        this.enemyType = "robot";
        this.configureAnimations();
        this.setDirectionTextures();
    }

    public Robot(float col, float row) {
        super(col,row);
        this.setTexture("robot");
        this.setObjectName("robot");
        this.setHeight(1);
        this.setHealth(HEALTH);
        this.setLevel(2);
        this.setSpeed(1);
        this.setRange(1);
        this.enemyType = "robot";
        this.configureAnimations();
        this.setDirectionTextures();
        this.configureSounds();
    }

    private void configureSounds() {
        chasingSound = "robotWalk";
        diedSound = "robotDie";
    }

    @Override
    public void configureAnimations() {
        this.addAnimations(
            AnimationRole.ATTACK, Direction.DEFAULT, new AnimationLinker(
                    "robot_defence", AnimationRole.MOVE,
                        Direction.DEFAULT, true, true));
    }
}
