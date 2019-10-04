package deco2800.skyfall.worlds.packing;

import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.world.World;

/**
 * This class is used to separate the huge environment packing into
 * different specific small parts/components to do the packing. And
 * it should be added into the packing queue from a Environment Packer.
 *
 */
public abstract class ComponentPacking {

    private EnvironmentPacker packer;

    ComponentPacking(EnvironmentPacker packer) {
        if (packer == null) {
            throw new NullPointerException("Invalid environment packer.");
        }
        this.packer = packer;
    }

    /**
     * Packing a small specific part of the environment packing.
     * @param world a world will be packed up by this procedure
     */
    public abstract void packing(World world);

    /**
     * @return which environment packer the packing component is in.
     */
    public EnvironmentPacker getPacker() {
        return packer;
    }

    /**
     * Changes the biome type of an available tile on the world to an available
     * new biome type.
     * @param x tile's X position on the world
     * @param y tile's Y position on the world
     * @param newBiome a biome type object
     * @return true if success, otherwise false
     */
    public boolean changeTileBiome(float x, float y, AbstractBiome newBiome) {
        HexVector tilePos = new HexVector(x, y);
        Tile tile = packer.getPackedWorld().getTile(tilePos);
        if (newBiome != null && tile != null) {
            tile.setBiome(newBiome);
            return true;
        }
        return false;
    }
}
