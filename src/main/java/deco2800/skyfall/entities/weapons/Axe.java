package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.worlds.Tile;

import java.util.HashMap;
import java.util.Map;

public class Axe extends Weapon implements Item, IWeapon {

    public Axe(Tile tile, boolean obstructed) {
        super(tile, "axe_tex", obstructed, "axe",
                "melee", "slash",
                4, 4, 10);
    }

    public Axe() {
        super("axe");
    }

    /**
     * @return a new instance of axe
     */
    @Override
    public Axe newInstance(Tile tile) {
        return new Axe(tile, this.isObstructed());
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
        return 10;
    }

    /**
     * Returns the number of metal required for the item.
     *
     * @return The name of the item
     */
    @Override
    public int getRequiredMetal() {
        return 10;
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
        allRequirements.put("Stone", 10);
        allRequirements.put("Metal", 10);

        return allRequirements;
    }

    /**
     * @return - cost of building the building
     */
    @Override
    public int getCost() {
        return 5;
    }
}

