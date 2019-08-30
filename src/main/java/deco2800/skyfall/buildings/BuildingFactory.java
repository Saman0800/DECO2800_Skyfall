package deco2800.skyfall.buildings;

public class BuildingFactory {

    // contains buildings currently buildable
    enum Buildings {
        House,
        StorageUnit,
        TownCentreBuilding,
        WallBuilding
    }

    public int getCount() {
        return Buildings.values().length;
    }

    public BuildingEntity createHouse(float col, float row) {
        BuildingEntity house = new BuildingEntity(col, row, 3);
        house.setObjectName("House" + house.getEntityID());
        house.setTexture("house1");
        house.setInitialHealth(10);
        house.setWidth(1);
        house.setLength(1);
        return house;
    }

    public BuildingEntity createStorageUnit(float col, float row) {
        BuildingEntity storage = new BuildingEntity(col, row, 3);
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
