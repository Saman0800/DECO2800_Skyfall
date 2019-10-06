package deco2800.skyfall.worlds.packing;

import deco2800.skyfall.worlds.world.World;
import java.util.ArrayList;

/**
 * The intention of this class is to accurately pack up the game environment with provided
 * entities and an initialized world based on the contents of the story so that to
 * improve the connection between the game and the story.
 *
 * Notes: in order to avoid errors by rewriting messy world codes from previous sprints, the
 * packing process is run based on the initial world that has been set by a WorldDirector and
 * a WorldBuilder.
 *
 */
public class EnvironmentPacker {

    // the builder should be loaded by WorldDirector, not new
    private World world;
    // a container of many small parts (components) of the environment packing
    private ArrayList<AbstractPacking> packings;

    public EnvironmentPacker(World world) {
        if (world == null) {
            throw new NullPointerException("Invalid world");
        }
        this.world = world;
        this.packings = new ArrayList<>();
    }

    /**
     * Does all packings from the packing queue which contains different
     * parts of the environment packing on the world. And the packing
     * queue will be cleared after executed.
     */
    public void doPackings() {
        for (AbstractPacking component : packings) {
            component.packing(world);
        }
        packings.clear();
    }

    /**
     * @return a world will be packed up its environment.
     */
    public World getWorld() {
        return world;
    }

    /**
     * Adds a packing component into the packing list.
     * @param component a small part of environment packing
     * @return true if success, otherwise false
     */
    public boolean addPackingComponent(AbstractPacking component) {
        if (component != null) {
            return packings.add(component);
        }
        return false;
    }

    /**
     * Removes a packing component inside the packing list.
     * @param component a small part of environment packing
     * @return true if success, otherwise false
     */
    public boolean removePackingComponent(AbstractPacking component) {
        if (component != null) {
            return packings.remove(component);
        }
        return false;
    }

    /**
     * Removes a packing component based on type inside the packing list.
     * @param type the type of a packing component
     * @return true if success, otherwise false
     */
    public <T extends AbstractPacking> boolean removePackingComponent(Class<T> type) {
        if (type != null) {
            for (AbstractPacking packing : packings) {
                if (packing.getClass() == type) {
                    return packings.remove(packing);
                }
            }
        }
        return false;
    }
}
