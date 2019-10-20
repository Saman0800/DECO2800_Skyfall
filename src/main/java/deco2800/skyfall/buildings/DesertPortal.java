package deco2800.skyfall.buildings;

import deco2800.skyfall.resources.Blueprint;

import java.util.HashMap;
import java.util.Map;

public class DesertPortal extends AbstractPortal implements Blueprint {

    /**
     * Constructor for an building entity with normal rendering size.
     *
     * @param col         the col position on the world
     * @param row         the row position on the world
     * @param renderOrder the height position on the world
     */
    public DesertPortal(float col, float row, int renderOrder) {
        super(col, row, renderOrder, 50, 20, 10);
        this.setTexture("portal_desert");
        this.setNext("mountain");
        this.currentBiome = "desert";
        this.name = "desertPortal";
        this.blueprintLearned = false;
    }


}
