package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;

public class ruinedRobot extends AbstractEnvironment {

    /**
     *Entity ID
     *
     */
    private static final String ENTITY_ID_STRING = "ruined_robot";

    /**
     *Set the entity ID of ruined robot to the object name
     *
     */
    public ruinedRobot() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    /**
     *Initialises
     *
     *@param tile - The tile it spawns on
     *@param obstructed - Whether the entity is obstructed by something
     */
    public ruinedRobot(Tile tile, boolean obstructed) {
        super(tile, "ruinedCity", obstructed);
        this.setObjectName(ENTITY_ID_STRING);
        this.entityType = "ruinedCity";
    }


    /**
     * Loads a static entity from a memento
     *
     * @param memento the static ruined robot to add
     */
    public ruinedRobot(SaveableEntityMemento memento) {
        super(memento);
    }

    /**
     * Initialises ruined robot
     *
     * @param tile - The tile it spawns on
     */
    @Override
    public ruinedRobot newInstance(Tile tile) {
        return new ruinedRobot(tile, this.isObstructed());
    }
}
