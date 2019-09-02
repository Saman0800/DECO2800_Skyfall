package deco2800.skyfall.buildings;

import deco2800.skyfall.entities.structures.BuildingType;

/**
 *  A BuildingFactory is a class to handle the creating process of all building entity classes,
 *  including both BuildingEntity based class and BuildingEntity subclasses.
 */
public class BuildingFactory {

    /**
     * Get the number of buildings that a factory could build.
     * @return the number of building entities
     */
    public int getCount() {
        return BuildingType.values().length;
    }

    /**
     * Create a house based on BuildingEntity class with defined default renderOrder.
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a house building object
     */
    public BuildingEntity createHouse(float col, float row) {
        BuildingEntity house = new BuildingEntity(col, row, 2);
        BuildingType textures = BuildingType.HOUSE;
        house.setObjectName("House" + house.getEntityID());
        house.setTexture(textures.getMainTexture());
        house.setBuildTime(7);
        house.addBuildCost("", 0);
        house.addBuildCost("", 0);
        house.setInitialHealth(10);
        house.setWidth(2);
        house.setLength(2);
        return house;
    }

    /**
     * Create a storage unit based on BuildingEntity class with defined default renderOrder.
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a storage unit building object
     */
    public BuildingEntity createStorageUnit(float col, float row) {
        BuildingEntity storage = new BuildingEntity(col, row, 2);
        BuildingType textures = BuildingType.STORAGE_UNIT;
        storage.setObjectName("StorageUnit" + storage.getEntityID());
        storage.setTexture(textures.getMainTexture());
        storage.setBuildTime(6);
        storage.addBuildCost("", 0);
        storage.addBuildCost("", 0);
        storage.setInitialHealth(5);
        storage.setWidth(2);
        storage.setLength(2);
        return storage;
    }

    /**
     * Create a town centre building based on BuildingEntity class with defined default renderOrder.
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a town centre object
     */
    public BuildingEntity createTownCentreBuilding(float col, float row) {
        BuildingEntity town = new BuildingEntity(col, row, 2);
        BuildingType textures = BuildingType.TOWNCENTRE;
        town.setObjectName("TownCentre" + town.getEntityID());
        town.setTexture(textures.getMainTexture());
        town.setBuildTime(6);
        town.addBuildCost("", 0);
        town.addBuildCost("", 0);
        town.setInitialHealth(80);
        town.setWidth(3);
        town.setLength(3);
        return town;
    }

    /**
     * Create fence building based on BuildingEntity class with defined default renderOrder.
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a fence object
     */
    public BuildingEntity createFenceBuilding(float col, float row) {
        BuildingEntity fence = new BuildingEntity(col, row, 2);
        BuildingType textures = BuildingType.WALL;
        fence.setObjectName("Fence" + fence.getEntityID());
        fence.setTexture(textures.getMainTexture());
        fence.setBuildTime(3);
        fence.addBuildCost("", 0);
        fence.addBuildCost("", 0);
        fence.setInitialHealth(80);
        fence.setWidth(1);
        fence.setLength(1);
        return fence;
    }

    /*
     *  Following contents are in BuildingType class
     *  Each enum constant is one building type and field values relate to texture facing direction.
     *  Texture facing directions and field values order are following
     *      0 as south,
     *      1 as west,
     *      2 as north,
     *      3 as east.
     */
/*    enum BuildingTextures {
        House("cabin_0", "", "", ""),
        StorageUnit("storage_unit", "", "", ""),
        TownCentre("town_centre", "", "", ""),
        Fence("fence_left_bottom", "", "", "");

        private final String south;
        private final String west;
        private final String north;
        private final String east;

        BuildingTextures(String south, String west, String north, String east) {
            this.south = south;
            this.west = west;
            this.north = north;
            this.east = east;
        }

        // default is south facing direction
        public String getTexture(int index) {
            switch (index) {
                case 0:
                    return south;
                case 1:
                    return west;
                case 2:
                    return north;
                case 3:
                    return east;
                default:
                    return south;
            }
        }
    }
*/
}
