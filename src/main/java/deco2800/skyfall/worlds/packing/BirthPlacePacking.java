package deco2800.skyfall.worlds.packing;

import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.world.World;

public class BirthPlacePacking extends ComponentPacking {

    BirthPlacePacking(EnvironmentPacker packer) {
        super(packer);
    }

    @Override
    public void packing(World world) {
        // change some tiles' biome type for a large region
        AbstractBiome changeBiome = getBiomeFromTile(0f, 0f);
        changeTileBiome(3f, -2f, changeBiome);
        changeTileBiome(4f, 0f, changeBiome);
        changeTileBiome(4f, -1f, changeBiome);

        // arrange entity on the region
        removeEntityOnTile(1f, -1f);
        moveEntityFromTileToTile(-2f, -3f, 8f, 2f);
        moveEntityFromTileToTile(-4f, -3f, -4f, 10f);
    }
}
