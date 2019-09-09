package deco2800.skyfall.buildings;

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
        BuildingEntity cabin = new BuildingEntity(col, row, 2, BuildingType.CABIN);
        BuildingType cabinType = BuildingType.CABIN;
        return cabin;
    }

    /**
     * Create a storage unit based on BuildingEntity class with defined default renderOrder.
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a storage unit building object
     */
    public BuildingEntity createStorageUnit(float col, float row) {
        BuildingEntity storage = new BuildingEntity(col, row, 2, BuildingType.STORAGE_UNIT);
        return storage;
    }

    /**
     * Create a town centre building based on BuildingEntity class with defined default renderOrder.
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a town centre object
     */
    public BuildingEntity createTownCentreBuilding(float col, float row) {
        BuildingEntity town = new BuildingEntity(col, row, 2, BuildingType.TOWNCENTRE);
        return town;
    }

    /**
     * Create fence building based on BuildingEntity class with defined default renderOrder.
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a fence object
     */
    public BuildingEntity createFenceBuilding(float col, float row) {
        BuildingEntity fence = new BuildingEntity(col, row, 2, BuildingType.FENCE);
        return fence;
    }

    /**
     * Create safeHouse building based on BuildingEntity class with defined default renderOrder.
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a safeHouse object
     */
    public BuildingEntity createSafeHouse(float col, float row) {
        BuildingEntity safeHouse = new BuildingEntity(col, row, 2, BuildingType.SAFEHOUSE);
        return safeHouse;
    }

    /**
     * Create watch tower building based on BuildingEntity class with defined default renderOrder.
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a safeHouse object
     */
    public BuildingEntity createWatchTower(float col, float row) {
        BuildingEntity watchTower = new BuildingEntity(col, row, 2, BuildingType.WATCHTOWER);
        return watchTower;
    }

    /**
     * Create Castle building based on BuildingEntity class with defined default renderOrder.
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a safeHouse object
     */
    public BuildingEntity createCastle(float col, float row) {
        BuildingEntity castle = new BuildingEntity(col, row, 2, BuildingType.CASTLE);
        return castle;
    }
}
