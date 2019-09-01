package deco2800.skyfall.buildings;

import deco2800.skyfall.entities.structures.BuildingType;

/**
 *  A BuildingFactory is a class to handle the creating process of all building entity classes,
 *  including both BuildingEntity based class and BuildingEntity subclasses.
 */
public class BuildingFactory {

    /**
     *  Each enum constant is one building type and field values relate to texture facing direction.
     *  Texture facing directions and field values order are following
     *      0 as east,
     *      1 as south,
     *      2 as west,
     *      3 as north.
     */
    enum BuildingTextures {
        House("cabin_0", "", "", ""),
        StorageUnit("storage_unit", "", "", ""),
        TownCentre("town_centre", "", "", ""),
        Fence("fence_left_bottom", "", "", "");

        private final String east;
        private final String south;
        private final String west;
        private final String north;

        BuildingTextures(String east, String south, String west, String north) {
            this.east = east;
            this.south = south;
            this.west = west;
            this.north = north;
        }

        // default is south facing direction
        public String getTexture(int index) {
            switch (index) {
                case 0:
                    return east;
                case 1:
                    return south;
                case 2:
                    return west;
                case 3:
                    return north;
                default:
                    return south;
            }
        }
    }

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
     * @param face texture facing direction, details see Enum type of BuildingTextures
     * @return a house building object
     */
    public BuildingEntity createHouse(float col, float row, int face) {
        BuildingEntity house = new BuildingEntity(col, row, 2);
        BuildingTextures textures = BuildingTextures.House;
        house.setObjectName("House" + house.getEntityID());
        house.setTexture(textures.getTexture(face));
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
     * @param face texture facing direction, details see Enum type of BuildingTextures
     * @return a storage unit building object
     */
    public BuildingEntity createStorageUnit(float col, float row, int face) {
        BuildingEntity storage = new BuildingEntity(col, row, 2);
        BuildingTextures textures = BuildingTextures.StorageUnit;
        storage.setObjectName("StorageUnit" + storage.getEntityID());
        storage.setTexture(textures.getTexture(face));
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
     * @param face texture facing direction, details see Enum type of BuildingTextures
     * @return a town centre object
     */
    public BuildingEntity createTownCentreBuilding(float col, float row, int face) {
        BuildingEntity town = new BuildingEntity(col, row, 2);
        BuildingTextures textures = BuildingTextures.TownCentre;
        town.setObjectName("TownCentre" + town.getEntityID());
        town.setTexture(textures.getTexture(face));
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
     * @param face texture facing direction, details see Enum type of BuildingTextures
     * @return a fence object
     */
    public BuildingEntity createFenceBuilding(float col, float row, int face) {
        BuildingEntity fence = new BuildingEntity(col, row, 2);
        BuildingTextures textures = BuildingTextures.Fence;
        fence.setObjectName("Wall" + fence.getEntityID());
        fence.setTexture(textures.getTexture(face));
        fence.setBuildTime(3);
        fence.addBuildCost("", 0);
        fence.addBuildCost("", 0);
        fence.setInitialHealth(80);
        fence.setWidth(1);
        fence.setLength(1);
        return fence;
    }
}
