package deco2800.skyfall.buildings;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.skyfall.resources.Blueprint;

/**
 * TODO: [Portal Team] Consider creating an abstract base class for the portal.
 */
public class ForestPortal extends AbstractPortal implements Blueprint {

    public String currentBiome = "forest";
    public boolean blueprintLearned = false;
    public String name = "forestPortal";
    Texture texture;
    // a logger
    private final transient Logger logger = LoggerFactory.getLogger(ForestPortal.class);

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
