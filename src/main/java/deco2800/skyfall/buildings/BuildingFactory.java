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
     * Create cabin building based on BuildingEntity class with defined default renderOrder.
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a cabin object
     */
    public BuildingEntity createCabin(float col, float row) {
        BuildingEntity cabin = new BuildingEntity(col, row, 2);
        BuildingType cabinType = BuildingType.CABIN;
        cabin.setObjectName(cabinType.getName() + cabin.getEntityID());
        cabin.setTexture(cabinType.getMainTexture());
        cabin.setBuildTime(cabinType.getBuildTime());
        cabin.addBuildCost("", 0);
        cabin.addBuildCost("", 0);
        cabin.setInitialHealth(cabinType.getMaxHealth());
        cabin.setWidth(cabinType.getSizeX());
        cabin.setLength(cabinType.getSizeX());
        return cabin;
    }
    /**
     * Create a storage unit based on BuildingEntity class with defined default renderOrder.
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a storage unit building object
     */
    public BuildingEntity createStorageUnit(float col, float row) {
        BuildingEntity storage = new BuildingEntity(col, row, 2);
        BuildingType storageType = BuildingType.STORAGE_UNIT;
        storage.setObjectName("StorageUnit" + storage.getEntityID());
        storage.setTexture(storageType.getMainTexture());
        storage.setBuildTime(storageType.getBuildTime());
        storage.addBuildCost("", 0);
        storage.addBuildCost("", 0);
        storage.setInitialHealth(storageType.getMaxHealth());
        storage.setWidth(storageType.getSizeX());
        storage.setLength(storageType.getSizeY());
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
        BuildingType townType = BuildingType.TOWNCENTRE;
        town.setObjectName("TownCentre" + town.getEntityID());
        town.setTexture(townType.getMainTexture());
        town.setBuildTime(townType.getBuildTime());
        town.addBuildCost("", 0);
        town.addBuildCost("", 0);
        town.setInitialHealth(townType.getMaxHealth());
        town.setWidth(townType.getSizeX());
        town.setLength(townType.getSizeY());
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
        BuildingType fenceType = BuildingType.FENCE;
        fence.setObjectName("Fence" + fence.getEntityID());
        fence.setTexture(fenceType.getMainTexture());
        fence.setBuildTime(fenceType.getBuildTime());
        fence.addBuildCost("", 0);
        fence.addBuildCost("", 0);
        fence.setInitialHealth(fenceType.getMaxHealth());
        fence.setWidth(fenceType.getSizeX());
        fence.setLength(fenceType.getSizeY());
        return fence;
    }

    /**
     * Create safeHouse building based on BuildingEntity class with defined default renderOrder.
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a safeHouse object
     */
    public BuildingEntity createSafeHouse(float col, float row) {
        BuildingEntity safeHouse = new BuildingEntity(col, row, 2);
        BuildingType safeHouseType = BuildingType.SAFEHOUSE;
        safeHouse.setObjectName(safeHouseType.getName() + safeHouse.getEntityID());
        safeHouse.setTexture(safeHouseType.getMainTexture());
        safeHouse.setBuildTime(safeHouseType.getBuildTime());
        safeHouse.addBuildCost("", 0);
        safeHouse.addBuildCost("", 0);
        safeHouse.setInitialHealth(safeHouseType.getMaxHealth());
        safeHouse.setWidth(safeHouseType.getSizeX());
        safeHouse.setLength(safeHouseType.getSizeX());
        return safeHouse;
    }

    /**
     * Create watch tower building based on BuildingEntity class with defined default renderOrder.
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a safeHouse object
     */
    public BuildingEntity createWatchTower(float col, float row) {
        BuildingEntity watchTower = new BuildingEntity(col, row, 2);
        BuildingType watchTowerType = BuildingType.WATCHTOWER;
        watchTower.setObjectName(watchTowerType.getName() + watchTower.getEntityID());
        watchTower.setTexture(watchTowerType.getMainTexture());
        watchTower.setBuildTime(watchTowerType.getBuildTime());
        watchTower.addBuildCost("", 0);
        watchTower.addBuildCost("", 0);
        watchTower.setInitialHealth(watchTowerType.getMaxHealth());
        watchTower.setWidth(watchTowerType.getSizeX());
        watchTower.setLength(watchTowerType.getSizeX());
        return watchTower;
    }

    /**
     * Create Castle building based on BuildingEntity class with defined default renderOrder.
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a safeHouse object
     */
    public BuildingEntity createCastle(float col, float row) {
        BuildingEntity castle = new BuildingEntity(col, row, 2);
        BuildingType castleType = BuildingType.CASTLE;
        castle.setObjectName(castleType.getName() + castle.getEntityID());
        castle.setTexture(castleType.getMainTexture());
        castle.setBuildTime(castleType.getBuildTime());
        castle.addBuildCost("", 0);
        castle.addBuildCost("", 0);
        castle.setInitialHealth(castleType.getMaxHealth());
        castle.setWidth(castleType.getSizeX());
        castle.setLength(castleType.getSizeX());
        return castle;
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
