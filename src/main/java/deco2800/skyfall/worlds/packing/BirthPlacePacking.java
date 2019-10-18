package deco2800.skyfall.worlds.packing;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.entities.enemies.Enemy;
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
        // limit to enemies that are not appear too close to initial start point
        emptyEntityOnTile(world, Enemy.class, 6);

        // limit to static entities that are not appear too close to initial start point
        emptyEntityOnTile(world, StaticEntity.class, 3);
    }

    /**
     * Empty an specific class of entities on a region that is initial start point with radius.
     *
     * @param world     :   the game world
     * @param type      :   an entity class
     * @param radius    :   radius from the start point
     */
    private <T extends AbstractEntity> void emptyEntityOnTile(World world, Class<T> type, int radius) {
        for (AbstractEntity entity : world.getSortedEntities()) {
            if ((type.isInstance(entity)) && (radius * -1 <= entity.getCol()) && (entity.getCol() <= radius)
                    && (radius * -1 <= entity.getRow()) && (entity.getRow() <= radius)) {
                removeEntityOnTile(entity.getCol(), entity.getRow());
            }
        }
    }
}
