package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.NaturalResources;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;

/**
 * A class representing a Sand Natural Resource item
 */
public class Sand extends NaturalResources implements Item {

    // the name of the item
    private String name;

    // the biome the sand is in
    private String biome;

    /**
     * Creates a default sand item
     */
    public Sand(){
        this.name = "Sand";
        this.biome = "Beach";
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
     * Returns the biome the sand is situated in
     * @return the biome the sand is situated in
     */
    public String getBiome(){
        return biome;
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

    /**
     * Returns the item description
     * @return the item description
     */
    @Override
    public String getDescription() {
        return "This resource can be found in the" + "\n" + " Desert or Beach biomes.";
    }

    @Override
    public void use(HexVector position){

    }
}
