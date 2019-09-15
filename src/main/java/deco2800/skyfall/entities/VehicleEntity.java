package deco2800.skyfall.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class VehicleEntity extends Peon {

    private final transient Logger log = LoggerFactory.getLogger(EnemyEntity.class);

    private int damage;

    private boolean available = true;

    public VehicleEntity(float col, float row) {
        this.setRow(row);
        this.setCol(col);
    }

    public VehicleEntity(float row, float col, String textureName,int damage) {
        super(row, col, 0.2f, textureName, damage);
        this.setTexture(textureName);
    }

    public void onTick(long i) {
        // Do nothing on tick
    }

    public int getDamage() {
        return damage;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
