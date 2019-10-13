package deco2800.skyfall.buildings;

import com.badlogic.gdx.graphics.Texture;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.resources.Blueprint;
import deco2800.skyfall.saving.Save;
import deco2800.skyfall.worlds.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class VolcanoPortal extends AbstractPortal implements Blueprint {


    public String currentBiome = "mountain";
    public String name = "portal_volcano";
    public boolean blueprintLearned = false;
    private final transient Logger logger = LoggerFactory.getLogger(BuildingEntity.class);


    /**
     * Constructor for an building entity with normal rendering size.
     * @param col the col position on the world
     * @param row the row position on the world
     * @param renderOrder the height position on the world
     */
    public VolcanoPortal(float col, float row, int renderOrder) {
        super(col, row, renderOrder);
        this.setTexture("portal");
        this.setNext("volcanic_mountains");
        this.entityType = "VolcanoPortal";
        currentBiome = "mountain";
        name = "portal_volcano";
        blueprintLearned = false;
    }

    @Override
    public void setTexture(String texture) {
        super.setTexture(texture);
    }

    @Override
    public void onTick(long i) {
        // do nothing so far
    }

    /**
     * Returns the number of wood required for the item.
     * @return The amount of wood needed
     */
    public int getRequiredWood() {
        return 0;
    }

    /**
     * Returns the number of stones required for the item.
     * @return The amount of stone needed
     */
    public int getRequiredStone() {
        return 0;
    }

    /**
     * Returns the number of metal required for the item.
     * @return The amount of metal needed
     */
    public int getRequiredMetal() {
        return 0;
    }

    /**
     * Returns a map of the name of the required resource and
     * the required number of each resource to create the item.
     * @return a hashamp of the required resources and their number.
     */
    public Map<String,Integer> getAllRequirements() {
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

    /**
     * a getter method to check if a player has learned the blueprint
     *
     * @return true if the player has learned the blueprint, false otherwise
     */
    public boolean isBlueprintLearned() {
        return blueprintLearned;
    }

    /**
     * changes the boolean blueprintLearned to true.
     */
    public void toggleBlueprintLearned() {
        if (blueprintLearned == true) {
            blueprintLearned = false;
        } else {
            blueprintLearned = true;
        }

    }

    /**
     * @return - cost of building the building
     */
    public int getCost() {
        return 0;
    }

    @Override
    public void teleport(Save save) {
        logger.info("UNLOCKED ALL BIOMES - END OF GAME");

    }













}
