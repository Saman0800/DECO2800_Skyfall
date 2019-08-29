package deco2800.skyfall.entities.structures;

public enum BuildingType {

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

}

