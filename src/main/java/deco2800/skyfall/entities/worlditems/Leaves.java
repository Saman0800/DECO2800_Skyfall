package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.Tile;

public class Leaves extends StaticEntity {

    protected static final String ENTITY_ID_STRING = "leaves";

    public Leaves(SaveableEntityMemento memento) {
        super(memento);
        setupParams();
    }

    public Leaves() {
        super();
        setupParams();

        this.setTexture("leaves1");
    }

    public Leaves(Tile tile, boolean obstructed) {
        super(tile, 2, "leaves1", obstructed);
        setupParams();
    }

    public Leaves(Tile tile) {
        this(tile, false);
        setupParams();
    }

    private void setupParams() {
        this.setObjectName(ENTITY_ID_STRING);
        this.entityType = "Leaves";
    }

    @Override
    public void onTick(long i) {
        // Do nothing on tick
    }
}