package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.NaturalResources;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;

/**
 * A class representing a Wood Natural Resource item
 */
public class Wood extends NaturalResources implements Item {

    // the name of the item
    private String name;

    // the colour of the wood
    private String colour;

    // the biome the wood is in
    private String biome;

    /**
     * Creates a default wood item
     */
    public Wood(){
        this.name = "Wood";
        this.colour = "brown";
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
     * Returns the colour of the wood
     * @return the colour of the wood
     */
    public String getColour(){
        return colour;
    }

    /**
     * Returns the biome the wood is situated in
     * @return the biome the wood is situated in
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


    public static void main(String[] args) {
        NaturalResources n = new Wood();
        System.out.println(n.getName());
        System.out.println(n.getSubtype());
        System.out.println(n.toString());
        Wood w = new Wood();
        System.out.println(w.getName());
        System.out.println(w.getSubtype());
        System.out.println(w.toString());
        System.out.println(w.getColour());
        System.out.println(w.getBiome());
    }

}