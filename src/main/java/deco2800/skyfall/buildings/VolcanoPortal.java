package deco2800.skyfall.buildings;

import deco2800.skyfall.resources.Blueprint;
import deco2800.skyfall.saving.Save;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VolcanoPortal extends AbstractPortal implements Blueprint {
    private final transient Logger logger = LoggerFactory.getLogger(VolcanoPortal.class);

    /**
     * Constructor for an building entity with normal rendering size.
     *
     * @param col         the col position on the world
     * @param row         the row position on the world
     * @param renderOrder the height position on the world
     */
    public VolcanoPortal(float col, float row, int renderOrder) {
        super(col, row, renderOrder, 0, 0, 0);
        this.setTexture("portal");
        this.setNext("volcanic_mountains");
        this.entityType = "VolcanoPortal";
        this.currentBiome = "mountain";
        this.name = "portal_volcano";
        this.blueprintLearned = false;

    }

    @Override
    public void teleport(Save save) {
        logger.info("UNLOCKED ALL BIOMES - END OF GAME");
    }

}
