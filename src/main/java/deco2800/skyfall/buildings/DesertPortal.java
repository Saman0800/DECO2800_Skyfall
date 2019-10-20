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
        super(col, row, renderOrder);
        this.setTexture("portal_desert");
        this.setNext("mountain");
        this.currentBiome = "desert";
        this.name = "desertPortal";
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
        allRequirements.put("Wood", 50);
        allRequirements.put("Stone", 20);
        allRequirements.put("Metal", 10);

        return allRequirements;
    }


    @Override
    public int getRequiredWood() {
        return 50;
    }

    @Override
    public int getRequiredStone() {
        return 20;
    }

    @Override
    public int getRequiredMetal() {
        return 10;
    }
}
