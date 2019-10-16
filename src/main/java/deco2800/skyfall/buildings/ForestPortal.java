package deco2800.skyfall.buildings;

import java.util.HashMap;
import java.util.Map;

import deco2800.skyfall.resources.Blueprint;

public class ForestPortal extends AbstractPortal implements Blueprint {

    /**
     * Constructor for an building entity with normal rendering size.
     *
     * @param col         the col position on the world
     * @param row         the row position on the world
     * @param renderOrder the height position on the world
     */
    public ForestPortal(float col, float row, int renderOrder) {
        super(col, row, renderOrder);
        this.setTexture("portal_forest");
        this.setNext("desert");
        this.entityType = "ForestPortal";
        this.currentBiome = "forest";
        this.name = "forestPortal";
        this.blueprintLearned = false;

    }

    /**
     * Returns a map of the name of the required resource and the required number of
     * each resource to create the item.
     *
     * @return a hashamp of the required resources and their number.
     */
    public Map<String, Integer> getAllRequirements() {
        Map<String, Integer> allRequirements = new HashMap<>();
        allRequirements.put("Wood", 25);
        allRequirements.put("Stone", 10);
        allRequirements.put("Metal", 0);

        return allRequirements;
    }

}
