package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.worlds.Tile;

public class Shipwrecks extends AbstractEnvironment {
    private static final String ENTITY_ID_STRING = "ship_wrecks";

    public Shipwrecks() {
        super();
        setupParams();
        this.setTexture("shipwrecks");
    }

    public Shipwrecks(Tile tile, boolean obstructed) {
        super(tile, "shipwrecks", obstructed);
        setupParams();
    }

    public Shipwrecks(SaveableEntityMemento memento) {
        super(memento);
        setupParams();
    }

    private void setupParams() {
        this.setObjectName(ENTITY_ID_STRING);
        this.entityType = "Shipwrecks";
    }

    @Override
    public Shipwrecks newInstance(Tile tile) {
        return new Shipwrecks(tile, this.isObstructed());
    }
}
