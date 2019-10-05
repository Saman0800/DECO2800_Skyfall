package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.worlds.Tile;

import java.util.HashMap;
import java.util.Map;

public class Spear extends Weapon implements Item, IWeapon {

    public Spear(Tile tile, boolean obstructed) {
        super(tile, "spear_tex", obstructed, "spear",
                "range", "splash",
                4, 5, 7);
    }

    public Spear() {
        super("spear");
    }

    /**
     * @return a new instance of sword
     */
    @Override
    public Spear newInstance(Tile tile) {
        return new Spear(tile, this.isObstructed());
    }


    /**
     * Returns the number of wood required for the item.
     *
     * @return The name of the item
     */
    @Override
    public int getRequiredWood() {
        return 20;
    }

    /**
     * Returns the number of stones required for the item.
     *
     * @return The name of the item
     */
    @Override
    public int getRequiredStone() {
        return 20;
    }

    /**
     * Returns the number of metal required for the item.
     *
     * @return The name of the item
     */
    @Override
    public int getRequiredMetal() {
        return 20;
    }

    /**
     * Returns a map of the name of the required resource and
     * the required number of each resource to create the item.
     *
     * @return a hashamp of the required resources and their number.
     */
    @Override
    public Map<String, Integer> getAllRequirements() {

        Map<String, Integer> allRequirements = new HashMap<>();
        allRequirements.put("Wood", 20);
        allRequirements.put("Stone", 20);
        allRequirements.put("Metal", 20);

        return allRequirements;    }

    /**
     * a getter method to check if a player has learned the blueprint
     *
     * @return true if the player has learned the blueprint.
     */
    @Override
    public boolean isBlueprintLearned() {
        return false;
    }

    /**
     * @return - cost of building the building
     */
    @Override
    public int getCost() {
        return 10;
    }
}
