package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;

public class RuinedRobot extends AbstractEnvironment {

    /**
     * Entity ID
     *
     */
    private static final String ENTITY_ID_STRING = "ruined_robot";
    private static final String RuinedRobotString = "ruinedRobot";

    /**
     * Set the entity ID of ruined robot to the object name
     *
     */
    public RuinedRobot() {
        super();
        this.setTexture(RuinedRobotString);
        setupParams();
    }

    /**
     * Initialises
     *
     * @param tile       - The tile it spawns on
     * @param obstructed - Whether the entity is obstructed by something
     */
    public RuinedRobot(Tile tile, boolean obstructed) {
        super(tile, RuinedRobotString, obstructed);
        setupParams();
    }

    /**
     * Loads a static entity from a memento
     *
     * @param memento the static ruined robot to add
     */
    public RuinedRobot(SaveableEntityMemento memento) {
        super(memento);
        setupParams();
    }

    private void setupParams() {
        this.setObjectName(ENTITY_ID_STRING);
        this.entityType = RuinedRobotString;
    }

    /**
     * Initialises ruined robot
     *
     * @param tile - The tile it spawns on
     */
    @Override
    public RuinedRobot newInstance(Tile tile) {
        return new RuinedRobot(tile, this.isObstructed());
    }
}
