package deco2800.skyfall.buildings;

import com.badlogic.gdx.graphics.Texture;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.resources.Blueprint;

import java.util.Map;

public class DesertPortal extends AbstractPortal implements Blueprint {

    public String currentBiome = "desert";
    public String name = "desertPortal";
    public boolean blueprintLearned = false;
    Texture texture;

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

    }

    @Override
    public void onTick(long i) {
        // do nothing so far
    }

    /**
     * Returns the number of wood required for the item.
     *
     * @return The amount of wood needed
     */
    public int getRequiredWood() {
        return 0;
    }

    /**
     * Returns the number of stones required for the item.
     *
     * @return The amount of stone needed
     */
    public int getRequiredStone() {
        return 0;
    }

    /**
     * Returns the number of metal required for the item.
     *
     * @return The amount of metal needed
     */
    public int getRequiredMetal() {
        return 0;
    }

    /**
     * Returns a map of the name of the required resource and the required number of
     * each resource to create the item.
     *
     * @return a hashamp of the required resources and their number.
     */
    public Map<String, Integer> getAllRequirements() {
        return super.getBuildCost();
    }

    /**
     * Get the name of the Portal
     *
     * @return String - The name of the portal
     */
    public String getName() {
        return this.name;
    }

    public void unlocknext(MainCharacter character) {
        super.unlocknext(character, nextBiome);
    }

    /**
     * @return - cost of building the building
     */
    public int getCost() {
        return 0;
    }

}
