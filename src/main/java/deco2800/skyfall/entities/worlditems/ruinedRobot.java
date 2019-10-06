package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;

public class ruinedRobot extends AbstractEnvironment {
    private static final String ENTITY_ID_STRING = "ruined_robot";

    public ruinedRobot() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public ruinedRobot(Tile tile, boolean obstructed) {
        super(tile, "ruinedCity", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
        this.entityType = "ruinedCity";
    }

    public ruinedRobot(SaveableEntityMemento memento) {
        super(memento);
    }

    @Override
    public ruinedRobot newInstance(Tile tile) {
        return new ruinedRobot(tile, this.isObstructed());
    }
}
