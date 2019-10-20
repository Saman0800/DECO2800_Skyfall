package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.HasHealth;
import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.Tile;

public class SnowClump extends StaticEntity implements HasHealth {
    private int health = 100;
    private static final String ENTITY_ID_STRING = "rock";


    private static int nextImage = 1;

    public SnowClump() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public SnowClump(Tile tile, boolean obstructed) {
        super(tile, 2, "MSnow" + nextImage, obstructed);

        this.setObjectName(ENTITY_ID_STRING);
        this.entityType = "SnowClump";
    }

    public SnowClump(SaveableEntityMemento memento) {
        super(memento);
    }

    @Override
    public void onTick(long i) {
        // Do nothing on tick
    }


    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * The newInstance method implemented for the SnowClump class to allow for item
     * dispersal on game start up.
     * 
     * @return Duplicate rock instance with modified position.
     */
    @Override
    public SnowClump newInstance(Tile tile) {
        return new SnowClump(tile, this.isObstructed());
    }

    @Override
    public int getHealth() {
        return health;
    }


}