package deco2800.skyfall.resources.items;

import deco2800.skyfall.resources.Item;
import deco2800.skyfall.resources.NaturalResources;


/**
 * A class representing a Metal Natural Resource item
 */
public class Metal extends NaturalResources implements Item {

    // the name of the item
    private String name;

    // the biome the metal is in
    private String biome;

    /**
     * Creates a default metal item
     */
    public Metal(){
        this.name = "Metal";
        this.biome = "Ruined City";

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
     * Returns the biome the metal is situated in
     * @return the biome the metal is situated in
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
        NaturalResources n = new Metal();
        System.out.println(n.getName());
        System.out.println(n.getSubtype());
        System.out.println(n.toString());
        Metal w = new Metal();
        System.out.println(w.getName());
        System.out.println(w.getSubtype());
        System.out.println(w.toString());
        System.out.println(w.getBiome());
    }

}