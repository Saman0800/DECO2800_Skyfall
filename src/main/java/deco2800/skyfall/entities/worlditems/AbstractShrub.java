package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.Tile;
import java.util.Random;

public abstract class AbstractShrub extends StaticEntity {

    protected static final String ENTITY_ID_STRING = "shrub";
    protected static Random randomGen = new Random();
    protected static int nextTexture = 1;

    public AbstractShrub() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public AbstractShrub(Tile tile, boolean obstructed) {
        super(tile, 2, "bush" + AbstractShrub.nextTexture, obstructed);
        this.setObjectName(ENTITY_ID_STRING);
        AbstractShrub.nextTexture = randomGen.nextInt(3) + 1;
        this.entityType = "AbstractShrub";
    }

    public AbstractShrub(Tile tile, String texture, boolean obstructed) {
        super(tile, 2, texture, obstructed);
    }

    public AbstractShrub(SaveableEntityMemento memento) {
        super(memento);
    }

    @Override
    public void onTick(long i) {

    }

}