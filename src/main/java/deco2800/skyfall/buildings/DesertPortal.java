package deco2800.skyfall.buildings;

import deco2800.skyfall.resources.Blueprint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DesertPortal extends AbstractPortal implements Blueprint {

    // a logger
    private final transient Logger logger = LoggerFactory.getLogger(DesertPortal.class);
    /**
     * Constructor for an building entity with normal rendering size.
     *
     * @param col         the col position on the world
     * @param row         the row position on the world
     * @param renderOrder the height position on the world
     */
    public DesertPortal(float col, float row, int renderOrder) {
        super(col, row, renderOrder);
        this.setTexture("portal_desert");
        this.setNext("mountain");
        this.currentBiome = "desert";
        this.name = "desertPortal";
        this.blueprintLearned = false;
    }

}
