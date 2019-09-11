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
            case "Cabin":
                buildingTextures = initialiseCabinTextures();
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
     * Initialises the textures for the Cabin Building Tpe
     *
     * @return the Cabin textures
     */
    public List<String> initialiseCabinTextures() {
        List<String> buildingTextures = new ArrayList<String>();
        buildingTextures.add("cabin_0");
        buildingTextures.add("cabin_90");
        buildingTextures.add("cabin_180");
        buildingTextures.add("cabin_270");
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
     * Initialises the textures for the Castle Building Tpe
     *
     * @return the Castle textures
     */
    public List<String> initialiseCastleTextures() {
        List<String> castleCentreTextures = new ArrayList<String>();
        castleCentreTextures.add("castle_0");
        castleCentreTextures.add("castle_90");
        castleCentreTextures.add("castle_180");
        castleCentreTextures.add("castle_270");
        return castleCentreTextures;
    }

    /**
     * Initialises the textures for the Safe House Building Tpe
     *
     * @return the safe house textures
     */
    public List<String> initialiseSafeHouseTextures() {
        List<String> safeHouseTextures = new ArrayList<String>();
        safeHouseTextures.add("safe_house_0");
        safeHouseTextures.add("safe_house_90");
        safeHouseTextures.add("safe_house_180");
        safeHouseTextures.add("safe_house_270");
        return safeHouseTextures;
    }

    /**
     * Initialises the textures for the Watch tower Building Tpe
     *
     * @return the watch tower textures
     */
    public List<String> initialiseWatchTowerTextures() {
        List<String> watchTowerTextures = new ArrayList<String>();
        watchTowerTextures.add("watchtower_0");
        watchTowerTextures.add("watchtower_90");
        watchTowerTextures.add("watchtower_180");
        watchTowerTextures.add("watchtower_270");
        return watchTowerTextures;
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



