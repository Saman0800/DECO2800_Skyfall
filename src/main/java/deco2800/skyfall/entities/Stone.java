package deco2800.skyfall.entities;

import deco2800.skyfall.animation.Animatable;
import deco2800.skyfall.animation.AnimationLinker;
import deco2800.skyfall.animation.AnimationRole;
import deco2800.skyfall.animation.Direction;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.SoundManager;
import deco2800.skyfall.resources.GoldPiece;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Stone extends AbstractEnemy implements Animatable {

    //frequency of attack
    private static final transient int ATTACK_FREQUENCY = 50;
    private static final transient String BIOME = "forest";
    private boolean moving = false;
    private float originalCol;
    private float originalRow;

    private static final transient String ENEMY_TYPE = "stone";
    private MainCharacter mc;

    //if the enemy is attacked by player or the player closed enough to the enemy
    //than the enemy my will be in angry situation
    private int angerTimeAccount = 0;

    //Insert SoundManager class
    private SoundManager sound = new SoundManager();

    //To indicate whether the enemy arrives player's location
    private boolean complete = false;

    public Stone(float col, float row, MainCharacter mc) {
        super(col, row, mc);
        this.originalCol = col;
        this.originalRow = row;
        this.setTexture("enemyStone");
        this.setObjectName("enemyStone");
        this.setHeight(1);
        this.setHealth(10);
        this.setLevel(2);
        this.setSpeed(slowSpeed);
        //this.setArmour(1);
        this.setDamage(3);
        this.mc = mc;
        this.enemyAnimationName = "stone";
        this.configureAnimations();
        this.setDirectionTextures();
    }

    public Stone(float col, float row) {
        super(col,row);
        this.setDamage(4);
        this.setTexture("stone");
        this.setObjectName("stone");
        this.setHeight(1);
        this.setHealth(30);
        this.setLevel(2);
        this.setSpeed(1);
        this.enemyAnimationName = "stone";
        this.configureAnimations();
        this.setDirectionTextures();
        //this.setArmour(2);
    }

    //public Stone(float row, float col, String textureName, int health, int armour, int damage) {
    //    super(row, col, textureName, health, armour, damage);
    //}


    /**
     * get enemy type
     *
     * @return enemy type
     */
    public String getEnemyType() {
        return ENEMY_TYPE;
    }

    /**
     * To get biome
     *
     * @return biome
     */
    public String getBiome() {
        return BIOME;
    }

    /**
     * Handles tick based stuff, e.g. movement
     */
    @Override
    public void onTick(long i) {
        getBody().setTransform(position.getCol(), position.getRow(), getBody().getAngle());
        if (isDead()) {
            this.stoneDead();
        } else {
            this.randomMoving();
            this.resetFeeling();
           // this.angryAttacking();
        }

        // Only make sound if close to Main Character
        if (mc != null) {
            float colDistance = mc.getCol() - this.getCol();
            float rowDistance = mc.getRow() - this.getRow();

            if ((colDistance * colDistance + rowDistance * rowDistance) < 4) {
                sound.loopSound("stoneWalk");
                this.setCurrentState(AnimationRole.DEFENCE);
            } else {
                sound.stopSound("stoneWalk");
                this.setCurrentState(AnimationRole.NULL);
            }
        }
    }

    /**
     * @return string representation of this class including its enemy type, biome and x,y coordinates
     */
    @Override
    public String toString() {
        return String.format("%s at (%d, %d) %s biome", getEnemyType(), (int) getCol(), (int) getRow(), getBiome());
    }

    public List<AbstractEntity> dropGold(float col, float row) {
        Random random = new Random();
        int dropCount = random.nextInt(5);
        System.out.println(dropCount);
        List<AbstractEntity> golds = new ArrayList<>();
        for (int i = 0; i < dropCount; i++) {
//            golds.add(new GoldPiece((int)Math.random()*10+col, (int)Math.random()*10+row));
        }

        return golds;
    }
    //to count dead time
    private int time = 0;

    /**
     * if this enemy is dead then will show dead texture for a while
     */
    private void stoneDead() {
        sound.stopSound("stoneWalk");
        this.moving = true;
        // this.destination = new HexVector(this.getCol(), this.getRow());
        if (time <= 100) {
            sound.loopSound("stoneDie");
            if (time == 0) {
                setCurrentState(AnimationRole.NULL);
                this.setTexture("stoneDead");
                this.setObjectName("stoneDead");
                sound.stopSound("stoneDie");
                destroy();
            }
            time++;
        } else {
            System.out.println((int)this.getCol()+","+(int)this.getRow());
            GameManager.get().getWorld().addEntity(new GoldPiece(5));
            GameManager.get().getWorld().removeEntity(this);
            sound.stopSound("stoneDie");
        }
    }
}

