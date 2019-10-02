package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.Tile;

public class OrganicMound extends StaticEntity {

    protected static final String ENTITY_ID_STRING = "organic_mound";

    public OrganicMound(SaveableEntityMemento memento) {
        super(memento);
    }

    public OrganicMound() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public OrganicMound(Tile tile, boolean obstructed) {
        super(tile, 2, "mound1", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
        this.entityType = "OrganicMound";
    }

    public OrganicMound(Tile tile) {
        this(tile, false);
    }

    @Override
    public void onTick(long i) {
        // Do nothing on tick
    }
}