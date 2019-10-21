package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.HasHealth;
import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.Tile;
import java.util.Random;

public abstract class AbstractRock extends StaticEntity implements HasHealth {

    protected int health = 100;
    protected static Random randomGen = new Random();
    protected static int nextRock = 1;
    protected int metalAmount;

    protected static final String ENTITY_ID_STRING = "rock";

    public AbstractRock(SaveableEntityMemento memento) {
        super(memento);
    }

    public AbstractRock() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public AbstractRock(Tile tile, boolean obstructed, String image) {
        super(tile, 2, image, obstructed);
    }

    @Override
    public void onTick(long i) {
        // Do nothing on tick
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }
}