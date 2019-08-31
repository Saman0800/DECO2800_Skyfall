package deco2800.skyfall.buildings;

/**
 *  A BuildingFactory is a class to handle the creating process of all building entity classes,
 *  including both BuildingEntity based class and BuildingEntity subclasses.
 */
public class BuildingFactory {

    // building labels that a factory contains so far
    enum Buildings {
        House,
        StorageUnit,
        TownCentreBuilding,
        WallBuilding
    }

    enum BuildingType {

        HOUSE("House", 10, 1, 1,7,"house1"),
        STORAGE_UNIT("StorageUnit", 5,2,2, 6, "storage_unit"),
        TOWNCENTRE("Town Centre", 80,3,3,0, "town_centre"),
        WALL("Wall",5, 1,1,3, "fence_bottom_left");

        private String name;
        private int maxHealth;
        private int sizeX;
        private int sizeY;
        private int buildTime;
        private String texture;

        BuildingType( String name, int maxHealth, int sizeX, int sizeY, int buildTime, String texture) {
            this.name = name;
            this.maxHealth = maxHealth;
            this.sizeX = sizeX;
            this.sizeY = sizeY;
            this.buildTime = buildTime;
            this.texture = texture;
        }

        public String getName() {
            return name;
        }

        public int getMaxHealth() {
            return maxHealth;
        }

        public int getSizeX() {
            return sizeX;
        }

        public int getSizeY() {
            return sizeY;
        }

        public int getBuildTime() {
            return buildTime;
        }

        public String getTexture() {
            return texture;
        }


    }

    /**
     * Get the number of buildings that a factory could build.
     * @return the number of building entities
     */
    public int getCount() {
        return Buildings.values().length;
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
