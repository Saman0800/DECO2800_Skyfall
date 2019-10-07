package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.Tile;

import java.util.Random;

public abstract class AbstractEnvironment extends StaticEntity {
    protected static final String ENTITY_ID_STRING = "environment";
    protected static Random randomGen = new Random();

    public AbstractEnvironment() {
        this.setObjectName(ENTITY_ID_STRING);
    }


    public AbstractEnvironment(Tile tile, String texture, boolean obstructed) {
        super(tile, 2, texture, obstructed);
    }

    public AbstractEnvironment(SaveableEntityMemento memento) {
        super(memento);
    }

    @Override
    public void onTick(long i) {

    }
}
