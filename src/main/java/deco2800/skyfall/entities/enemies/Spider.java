package deco2800.skyfall.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.entities.ICombatEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.enemies.AbstractEnemy;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.SoundManager;
import deco2800.skyfall.tasks.MovementTask;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;

public class Spider extends AbstractEnemy implements Animatable {
    private static final transient int HEALTH = 10;
    private float originalCol;
    private float orriginalRow;

    private MainCharacter mc;

    public Spider(float col, float row, MainCharacter mc) {
        super(col, row);
        this.originalCol = col;
        this.orriginalRow = row;
        this.setTexture("spider");
        this.setObjectName("spider");
        this.setHeight(1);
        this.setHealth(HEALTH);
        this.setLevel(1);
        this.setSpeed(1);
        this.setRange(4);
        this.setCanMove(true);
        this.enemyType = "spider";
        this.mc = mc;
        this.configureAnimations();
        this.setDirectionTextures();
    }

    public Spider(float col, float row) {
        super(col, row);
        this.originalCol = col;
        this.orriginalRow = row;
        this.setTexture("spider");
        this.setObjectName("spider");
        this.setHeight(1);
        this.setHealth(HEALTH);
        this.setLevel(1);
        this.setSpeed(1);
        this.setRange(4);
        this.enemyType = "spider";
        this.configureSounds();
        this.configureAnimations();
    }

    private void configureSounds() {
        chasingSound = "spiderWalk";
        diedSound = "spiderDie";
    }

    @Override
    public void configureAnimations() {
        this.addAnimations(AnimationRole.DEFENCE, Direction.DEFAULT,
                new AnimationLinker("spider_defence", AnimationRole.MOVE
                        , Direction.DEFAULT, true, true));
    }
}
