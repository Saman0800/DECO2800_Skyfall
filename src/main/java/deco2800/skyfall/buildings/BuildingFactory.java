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
        cabin.setWidth(cabinType.getSizeY());
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
        storage.setWidth(storageType.getSizeY());
        storage.setLength(storageType.getSizeX());
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
        town.setWidth(townType.getSizeY());
        town.setLength(townType.getSizeX());
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
        fence.setWidth(fenceType.getSizeY());
        fence.setLength(fenceType.getSizeX());
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
        safeHouse.setWidth(safeHouseType.getSizeY());
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
        watchTower.setWidth(watchTowerType.getSizeY());
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
        castle.setWidth(castleType.getSizeY());
        castle.setLength(castleType.getSizeX());
        return castle;
    }
}
