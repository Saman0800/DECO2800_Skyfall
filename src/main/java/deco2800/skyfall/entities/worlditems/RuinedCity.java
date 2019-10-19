package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;

public class RuinedCity extends AbstractEnvironment {

    /**
     * Entity ID
     *
     */
    private static final String ENTITY_ID_STRING = "ruined_city";
    private static final String ruinedCityString = "ruinedCity";

    /**
     * Set the entity ID of ruined city to the object name
     *
     */
    public RuinedCity() {
        super();
        setupParams();
        this.setTexture(ruinedCityString);
    }

    /**
     * Initialises
     *
     * @param tile - The tile it spawns on
     * @param obstructed - Whether the entity is obstructed by something
     */
    public RuinedCity(Tile tile, boolean obstructed) {
        super(tile, ruinedCityString, obstructed);
        setupParams();
    }

    /**
     * Loads a static entity from a memento
     *
     * @param memento the static ruined city to add
     */
    public RuinedCity(SaveableEntityMemento memento) {
        super(memento);
        setupParams();
    }

    private void setupParams() {
        this.setObjectName(ENTITY_ID_STRING);
        this.entityType = ruinedCityString;
    }

    /**
     * Initialises ruined city
     *
     * @param tile - The tile it spawns on
     */
    @Override
    public RuinedCity newInstance(Tile tile) {
        return new RuinedCity(tile, this.isObstructed());
    }
}
