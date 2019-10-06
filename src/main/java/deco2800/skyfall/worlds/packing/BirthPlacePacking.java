package deco2800.skyfall.worlds.packing;

import deco2800.skyfall.entities.worlditems.ForestTree;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.world.World;

public class BirthPlacePacking extends AbstractPacking {

    public BirthPlacePacking(EnvironmentPacker packer) {
        super(packer);

        // only one birth place packing process
        do {
            getPacker().removePackingComponent(this.getClass());
        } while (getPacker().removePackingComponent(this.getClass()));
    }

    @Override
    public void packing(World world) {
        // change some tiles' biome type for a large region
        AbstractBiome changeBiome = getBiomeFromTile(0f, 0f);
        changeTileBiome(1f, -0.5f, changeBiome);
        changeTileBiome(0f, -1f, changeBiome);
        changeTileBiome(2f, -1f, changeBiome);
        changeTileBiome(2f, 0f, changeBiome);

        // arrange entity on the region
        removeEntityOnTile(1f, 1.5f);
        removeEntityOnTile(-2f, 0f);
        removeEntityOnTile(-3f, 0.5f);
        removeEntityOnTile(-3f, -0.5f);
        removeEntityOnTile(-1f, -1.5f);
        moveEntityFromTileToTile(0f, 2f, -2f, 0f);
        moveEntityFromTileToTile(-2f, -3f, 4f, 6f);
        moveEntityFromTileToTile(-4f, -3f, -12f, 3f);
        moveEntityFromTileToTile(-4f, -2f, -22f, 3f);

        // add some entity on the region
        //world.addEntity(new ForestTree(world.getTile(-3f, 1f), true));
    }
}
