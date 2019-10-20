package deco2800.skyfall.buildings;

import java.util.HashMap;
import java.util.Map;

import deco2800.skyfall.resources.Blueprint;

public class ForestPortal extends AbstractPortal {

    /**
     * Constructor for an building entity with normal rendering size.
     *
     * @param col         the col position on the world
     * @param row         the row position on the world
     * @param renderOrder the height position on the world
     */
    public ForestPortal(float col, float row, int renderOrder) {
        super(col, row, renderOrder, 25, 10, 0);
        this.setTexture("portal_forest");
        this.setNext("desert");
        this.entityType = "ForestPortal";
        this.currentBiome = "forest";
        this.name = "forestPortal";
        this.blueprintLearned = false;

    }

}
