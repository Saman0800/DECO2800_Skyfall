package deco2800.skyfall.worlds.packing;

import deco2800.skyfall.worlds.world.World;
import java.util.ArrayList;

/**
 * The intention of this class is to accurately pack up the game environment with provided
 * entities and an initialized world based on the contents of the story so that to
 * improve the connection between the game and the story.
 *
 * Notes: This class is used after the WorldBuilder.getWorld() set by a WorldDirector and
 * before saving the world. Also, the current packing process is based on the world set
 * by WorldDirector.constructNBiomeSinglePlayerWorld().
 *
 */
public class EnvironmentPacker {

    // the builder should be loaded by WorldDirector, not new
    private World world;
    // a container of many small parts (components) of the environment packing
    private ArrayList<ComponentPacking> packings;

    public EnvironmentPacker(World world) {
        if (world == null) {
            throw new NullPointerException("Invalid world");
        }
        this.world = world;

        this.packings = new ArrayList<>();
        // add packing components here into the packing queue
        addPackingComponent(new BirthPlacePacking(this));
    }

    /**
     * Adds a packing component into the packing list.
     * @param component a small part of environment packing
     */
    public void addPackingComponent(ComponentPacking component) {
        if (component != null) {
            packings.add(component);
        }
    }

    /**
     * Does all packings from the packing queue which includes many
     * packing components for a full environment packing to the game.
     */
    public void doPackings() {
        for (ComponentPacking component : packings) {
            component.packing(world);
        }
        packings.clear();
    }

    /**
     * @return a world will be packed up its environment.
     */
    public World getPackedWorld() {
        return world;
    }
}
