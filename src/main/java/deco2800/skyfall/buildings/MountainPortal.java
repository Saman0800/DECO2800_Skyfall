package deco2800.skyfall.buildings;

import com.badlogic.gdx.graphics.Texture;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.resources.Blueprint;

import java.util.Map;

public class MountainPortal extends AbstractPortal implements Blueprint {

    public String currentBiome = "mountain";
    public String nextBiome = "volcanic_mountain";
    public String name = "mountainPortal";
    public boolean blueprintLearned = false;
    Texture texture;


    /**
     * Constructor for an building entity with normal rendering size.
     * @param col the col position on the world
     * @param row the row position on the world
     * @param renderOrder the height position on the world
     */
    public MountainPortal(float col, float row, int renderOrder) {
        super(col, row, renderOrder);
        // TODO: @CGulley set texture to the texture of the mountain portal
        // TODO: @Cgulley set the functionality to move to the next biome

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


    public void unlocknext(MainCharacter character) {
        super.unlocknext(character, nextBiome);
    }



}


