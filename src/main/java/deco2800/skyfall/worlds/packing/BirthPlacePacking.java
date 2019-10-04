package deco2800.skyfall.worlds.packing;

import deco2800.skyfall.worlds.world.World;

public class BirthPlacePacking extends ComponentPacking {

    BirthPlacePacking(EnvironmentPacker packer) {
        super(packer);
    }

    @Override
    public void packing(World world) {
        // do something packing here
        // probably remove some entities on a position added new entites
    }
}
