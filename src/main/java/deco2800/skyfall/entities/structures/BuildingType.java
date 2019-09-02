package deco2800.skyfall.entities.structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A BuildingTime Enum outlines all of the building types available in the skyfall game as well as their set values/attributes
 */
public enum BuildingType {

    CABIN("Cabin", 10, 1, 1, 7, "cabin_0"),
    STORAGE_UNIT("StorageUnit", 5, 2, 2, 6, "storage_unit"),
    TOWNCENTRE("TownCentre", 80, 3, 3, 0, "town_centre"),
    FENCE("Fence", 5, 1, 1, 3, "fenceN-S"),
    SAFEHOUSE("SafeHouse", 5, 5, 5, 7, "safe_house_0"),
    WATCHTOWER("WatchTower", 8, 1, 1, 8, "watchtower_0"),
    CASTLE("Castle", 10, 1, 1, 6, "castle_0");





    private String name;
    private int maxHealth;
    private int sizeX;
    private int sizeY;
    private int buildTime;
    private String mainTexture;
    private List<String> buildingTextures;

    BuildingType(String name, int maxHealth, int sizeX, int sizeY, int buildTime, String mainTexture) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.buildTime = buildTime;
        this.mainTexture = mainTexture;

        // adding all of the possible textures for each building type
        switch(name){
            case "House":
                buildingTextures = initialiseHouseTextures();
                break;
            case "StorageUnit":
                buildingTextures = initialiseStorageUnitTextures();
                break;
            case "TownCentre":
                buildingTextures = initialiseTownCentreTextures();
                break;
            case "Wall":
                buildingTextures = initialiseFenceTextures();
                break;
        }
    }

    /**
     * Initialises the textures for the House Building Tpe
     *
     * @return the House textures
     */
    public List<String> initialiseHouseTextures() {
        List<String> buildingTextures = new ArrayList<String>();
        buildingTextures.add("house1");
        return buildingTextures;
    }

    /**
     * Initialises the textures for the Storage Unit Building Tpe
     *
     * @return the StorageUnit textures
     */
    public List<String> initialiseStorageUnitTextures() {
        List<String> storageUnitTextures = new ArrayList<String>();
        storageUnitTextures.add("storage_unit");
        return storageUnitTextures;
    }

    /**
     * Initialises the textures for the TownCentre Building Tpe
     *
     * @return the TownCentre textures
     */
    public List<String> initialiseTownCentreTextures() {
        List<String> townCentreTextures = new ArrayList<String>();
        townCentreTextures.add("town_centre");
        return townCentreTextures;
    }

    /**
     * Initialises the textures for the Wall Building Tpe
     *
     * @return the Wall textures
     */
    public List<String> initialiseFenceTextures() {
        List<String> fenceTextures = new ArrayList<String>();
        fenceTextures.add("fenceN-S");
        fenceTextures.add("fenceNE-SW");
        fenceTextures.add("fenceNW-SE");
        fenceTextures.add("fenceNE-S");
        fenceTextures.add("fenceNE-SE");
        fenceTextures.add("fenceN-SE");
        fenceTextures.add("fenceN-SW");
        fenceTextures.add("fenceNW-NE");
        fenceTextures.add("fenceSE-SW");
        fenceTextures.add("fenceNW-S");
        return fenceTextures;
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
    public String getMainTexture() {
        return mainTexture;
    }
}



