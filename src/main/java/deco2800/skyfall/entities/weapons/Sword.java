package deco2800.skyfall.entities.weapons;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.worlds.Tile;

import java.util.HashMap;
import java.util.Map;

public class Sword extends Weapon implements Item, IWeapon {

    public Sword(Tile tile, boolean obstructed) {
        super(tile, "sword_tex", obstructed, "sword",
                "melee", "slash",
                5, 3, 6);
    }

    public Sword() {
        super("sword");
    }

    /**
     * @return a new instance of sword
     */
    @Override
    public Sword newInstance(Tile tile) {
        return new Sword(tile, this.isObstructed());
    }


    /**
     * Returns the number of wood required for the item.
     *
     * @return The name of the item
     */
    @Override
    public int getRequiredWood() {
        return 30;
    }

    /**
     * Returns the number of stones required for the item.
     *
     * @return The name of the item
     */
    @Override
    public int getRequiredStone() {
        return 30;
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
        allRequirements.put("Wood", 30);
        allRequirements.put("Stone", 30);
        allRequirements.put("Metal", 10);

        return allRequirements;
    }

    /**
     * @return - cost of building the building
     */
    @Override
    public int getCost() {
        return 10;
    }
}

