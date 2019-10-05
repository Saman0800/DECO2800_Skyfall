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
        return new BuildingEntity(col, row, 2, BuildingType.STORAGE_UNIT);
    }

    /**
     * Create a town centre building based on BuildingEntity class with defined default renderOrder.
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a town centre object
     */
    public BuildingEntity createTownCentreBuilding(float col, float row) {
        return new BuildingEntity(col, row, 2, BuildingType.TOWNCENTRE);
    }

    /**
     * Create fence building based on BuildingEntity class with defined default renderOrder.
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a fence object
     */
    public BuildingEntity createFenceBuilding(float col, float row) {
        return new BuildingEntity(col, row, 2, BuildingType.FENCE);
    }

    /**
     * Create safeHouse building based on BuildingEntity class with defined default renderOrder.
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a safeHouse object
     */
    public BuildingEntity createSafeHouse(float col, float row) {
        return new BuildingEntity(col, row, 2, BuildingType.SAFEHOUSE);
    }

    /**
     * Create watch tower building based on BuildingEntity class with defined default renderOrder.
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a safeHouse object
     */
    public BuildingEntity createWatchTower(float col, float row) {
        return new BuildingEntity(col, row, 2, BuildingType.WATCHTOWER);
    }

    /**
     * Create Castle building based on BuildingEntity class with defined default renderOrder.
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a safeHouse object
     */
    public BuildingEntity createCastle(float col, float row) {
        return new BuildingEntity(col, row, 2, BuildingType.CASTLE);
    }

    public BuildingEntity createForestPortal(float col, float row) {
        return new BuildingEntity(col, row, 2, BuildingType.FORESTPORTAL);
    }

    public BuildingEntity createMountainPortal(float col, float row) {
        return new BuildingEntity(col, row, 2, BuildingType.MOUNTAINPORTAL);
    }

    public BuildingEntity createDesertPortal(float col, float row) {
        return new BuildingEntity(col, row, 2, BuildingType.DESERTPORTAL);
    }

    public BuildingEntity createVolcanoPortal(float col, float row) {
        return new BuildingEntity(col, row, 2, BuildingType.VOLCANOPORTAL);
    }
}
