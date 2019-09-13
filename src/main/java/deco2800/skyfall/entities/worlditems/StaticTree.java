package deco2800.skyfall.entities.worlditems;

import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.entities.Harvestable;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.WoodCube;
import deco2800.skyfall.Tickable;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.entities.HasHealth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class StaticTree extends StaticEntity implements Tickable, Harvestable {

    protected static final String ENTITY_ID_STRING = "tree";

    public StaticTree() {
        this.setObjectName(ENTITY_ID_STRING);
    }

    public StaticTree(float col, float row, int renderOrder, Map<HexVector, String> texture) {
        super(col, row, renderOrder, texture);
    }

    public StaticTree(Tile tile, boolean obstructed, String image) {
        super(tile, 5, image, obstructed);
    }
}