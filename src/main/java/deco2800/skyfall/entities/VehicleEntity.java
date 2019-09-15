package deco2800.skyfall.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class VehicleEntity extends Peon {

    private final transient Logger log = LoggerFactory.getLogger(EnemyEntity.class);

    private int health;

    private boolean available = true;

    public VehicleEntity(float col, float row) {
        this.setRow(row);
        this.setCol(col);
    }

    public VehicleEntity(float row, float col, String textureName,int health) {
        super(row, col, 0.2f, textureName, health);
        this.setTexture(textureName);
    }

    public void onTick(long i) {
        // Do nothing on tick
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
