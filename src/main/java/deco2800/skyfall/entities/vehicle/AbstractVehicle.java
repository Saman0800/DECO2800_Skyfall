package deco2800.skyfall.entities.vehicle;

import deco2800.skyfall.entities.ICombatEntity;
import deco2800.skyfall.entities.Peon;

public abstract class AbstractVehicle extends Peon implements ICombatEntity {
    private int health;
    private boolean isTaken = false;

    public AbstractVehicle(float col, float row) {
        this.setRow(row);
        this.setCol(col);
    }

    public AbstractVehicle(float row, float col, String texturename, int health) {
        super(row, col, 0.2f, texturename, health);
        this.setTexture(texturename);

    }

    /**
     * Get the health of the vehicle
     *
     * @return health of pet
     */
    public int getHealth() {
        return health;
    }

    /**
     * Setting the health to a specific value
     *
     * @param health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    public boolean underUsing(){
        return isTaken;
    }
}
