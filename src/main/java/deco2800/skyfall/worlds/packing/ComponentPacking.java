package deco2800.skyfall.worlds.packing;

import deco2800.skyfall.worlds.world.World;

/**
 * This class is used to separate the huge environment packing into
 * different specific small parts/components to do the packing. And
 * it should be added into the packing queue from a Environment Packer.
 *
 */
public abstract class ComponentPacking {

    public EnvironmentPacker packer;

    public ComponentPacking(EnvironmentPacker packer) {
        if (packer == null) {
            throw new NullPointerException("Invalid environment packer.");
        }
        this.packer = packer;
    }

    /**
     * @return which environment packer the packing component is in.
     */
    public EnvironmentPacker getPacker() {
        return packer;
    }

    /**
     * Packing a small specific part of the environment packing.
     */
    public abstract void packing(World world);
}
