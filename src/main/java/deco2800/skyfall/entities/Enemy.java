package deco2800.skyfall.entities;


public class Enemy extends Peon {
    private int health;
    private int level;
    private int speed;


    public Enemy(float row, float col,String texturename,String ObjectName) {
        super(row, col, 0.2f);
        this.setTexture(texturename);
        this.setObjectName(ObjectName);
    }

    public void onTick(long i) {
        if (task != null && task.isAlive()) {
            task.onTick(i);

            if (task.isComplete()) {
                this.task = null;
            }
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

}