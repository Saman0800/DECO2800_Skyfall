package deco2800.skyfall.entities.structures;

/**
 *
 */
public enum BuildingType {

    HOUSE("House", 10, 1, 1, 7, "house1"),
    STORAGE_UNIT("StorageUnit", 5, 2, 2, 6, "storage_unit"),
    TOWNCENTRE("Town Centre", 80, 3, 3, 0, "town_centre"),
    WALL("Wall", 5, 1, 1, 3, "fence_bottom_left");

    private String name;
    private int maxHealth;
    private int sizeX;
    private int sizeY;
    private int buildTime;
    private String texture;

    BuildingType(String name, int maxHealth, int sizeX, int sizeY, int buildTime, String texture) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.buildTime = buildTime;
        this.texture = texture;
    }

    /**
     * Gets the name of the Building Type
     *
     * @return Building Type Name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the Maximum Health of the Building Type
     *
     * @return Building Type Max Health
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Gets the size of the Building Type in respect to its X coordinates
     *
     * @return Building Type size in the x plane
     */
    public int getSizeX() {
        return sizeX;
    }

    /**
     * Gets the size of the Building Type in respect to its Y coordinates
     *
     * @return Building Type size in the y plane
     */
    public int getSizeY() {
        return sizeY;
    }

    /**
     * Gets the time to build the specified Building Type
     *
     * @return Building Type Build Time
     */
    public int getBuildTime() {
        return buildTime;
    }

    /**
     * Gets the texture of the Building Type
     *
     * @return Building Type Texture
     */
    public String getTexture() {
        return texture;
    }
}



