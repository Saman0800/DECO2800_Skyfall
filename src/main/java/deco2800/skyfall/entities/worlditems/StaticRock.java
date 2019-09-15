package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.entities.HasHealth;

public abstract class StaticRock extends StaticEntity implements HasHealth {

    protected static final String ENTITY_ID_STRING = "rock";

    public StaticRock() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public StaticRock(Tile tile, boolean obstructed, String image) {
        super(tile, 2, image, obstructed);
    }

}