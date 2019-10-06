package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;

public class ruinedCity extends AbstractEnvironment {
    private static final String ENTITY_ID_STRING = "ruined_city";

    public ruinedCity() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public ruinedCity(Tile tile, boolean obstructed) {
        super(tile, "ruinedCity", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
        this.entityType = "ruinedCity";
    }

    public ruinedCity(SaveableEntityMemento memento) {
        super(memento);
    }

    @Override
    public ruinedCity newInstance(Tile tile) {
        return new ruinedCity(tile, this.isObstructed());
    }
}
