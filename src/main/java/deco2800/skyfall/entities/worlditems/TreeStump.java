package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.Tile;

public class TreeStump extends StaticEntity {

    protected static final String ENTITY_ID_STRING = "tree_stump";

    public TreeStump(SaveableEntityMemento memento) {
        super(memento);
        setupParams();

    }

    public TreeStump() {
        super();
        setupParams();
        this.setTexture("trunk1");
    }

    public TreeStump(Tile tile, boolean obstructed) {
        super(tile, 2, "trunk1", obstructed);
        setupParams();
    }

    public TreeStump(Tile tile) {
        this(tile, true);
    }

    private void setupParams() {
        this.setObjectName(ENTITY_ID_STRING);
        this.entityType = "TreeStump";
    }

    @Override
    public void onTick(long i) {
        // Do nothing on tick
    }
}