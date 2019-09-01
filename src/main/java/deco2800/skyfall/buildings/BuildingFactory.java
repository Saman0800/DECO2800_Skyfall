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
        house.setObjectName("House" + house.getEntityID());
        house.setTexture("house1");
        house.setInitialHealth(10);
        house.setWidth(1);
        house.setLength(1);
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
        storage.setObjectName("StorageUnit" + storage.getEntityID());
        storage.setTexture("storage_unit");
        storage.setInitialHealth(5);
        storage.setWidth(2);
        storage.setLength(2);
        return storage;
    }

    public BuildingEntity createTownCentreBuilding() {
        return null;
    }

    public BuildingEntity createWallBuilding() {
        return null;
    }
}
