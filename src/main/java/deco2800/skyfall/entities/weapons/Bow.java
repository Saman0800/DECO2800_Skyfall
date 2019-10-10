package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.worlds.Tile;

import java.util.HashMap;
import java.util.Map;

public class Bow extends Weapon implements Item, IWeapon {

    public Bow(Tile tile, boolean obstructed) {
        super(tile, "bow_tex", obstructed, "bow",
                "range", "splash",
                3, 4, 10);
    }

    public Bow() {
        super("bow");
    }

    /**
     * @return a new instance of sword
     */
    @Override
    public Bow newInstance(Tile tile) {
        return new Bow(tile, this.isObstructed());
    }


    /**
     * Returns the number of wood required for the item.
     *
     * @return The name of the item
     */
    @Override
    public int getRequiredWood() {
        return 40;
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
        return 15;
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
        allRequirements.put("Wood", 40);
        allRequirements.put("Stone", 20);
        allRequirements.put("Metal", 15);

        return allRequirements;
    }

    /**
     * @return - cost of building the building
     */
    @Override
    public int getCost() {
        return 30;
    }
}
