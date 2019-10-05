package deco2800.skyfall.entities.vehicle;

import deco2800.skyfall.entities.ICombatEntity;
import deco2800.skyfall.entities.Peon;
import deco2800.skyfall.resources.Item;

public abstract class AbstractVehicle extends Peon implements ICombatEntity, Item {
    private int health;
    private boolean isTaken;
    private String name;

    public AbstractVehicle(float col, float row, String name) {
        this.setRow(row);
        this.setCol(col);
        this.name = name;
        this.isTaken = false;
    }

    public AbstractVehicle(float row, float col, String texturename, int health) {
        super(row, col, 0.2f, texturename, health);
        this.setTexture(texturename);
        this.isTaken = false;
    }

    public String getName() {
        return this.name;
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
