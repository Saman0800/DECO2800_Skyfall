package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.NaturalResources;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;

/**
 * A class representing a Vine Natural Resource item
 */
public class Vine extends NaturalResources implements Item {

    // the name of the item
    private String name;

    // the biome the vine is in
    private String biome;

    /**
     * Creates a default vine item
     */
    public Vine(){
        this.name = "Vine";
        this.biome = "Forest";
    }

    /**
     * Returns the name of the natural resource
     * @return The name of the natural resource
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the biome the vine is situated in
     * @return the biome the vine is situated in
     */
    public String getBiome(){
        return biome;
    }

    /**
     * Returns the item description
     * @return the item description
     */
    @Override
    public String getDescription() {
        return "This item can be found in the forest biome and can " +
                "be used to produce rope.";
    }

    /**
     * Creates a string representation of the natural resource in the format:
     *
     * <p>'{Natural Resource}:{Name}' </p>
     *
     * <p>without surrounding quotes and with {natural resource} replaced by
     * the subtype of the item and {name} replaced with the item name
     * For example: </p>
     *
     * <p>Natural Resource:Wood </p>
     *
     * @return A string representation of the natural resource.
     */
    @Override
    public String toString() {
        return "" + subtype + ":" + name;
    }

    @Override
    public void use(){

    }


}
