package deco2800.skyfall.buildings;

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

    private Map<String, Integer> buildingCost;
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
                initialiseCabin();
                break;
            case "StorageUnit":
                initialiseStorageUnit();
                break;
            case "TownCentre":
                initialiseTownCentre();
                break;
            case "Wall":
                initialiseFence();
                break;
            default:
                break;
        }
    }

    /**
     * Initialises the textures for the Cabin Building Tpe
     *
     */
    public void initialiseCabin() {
        buildingTextures = new ArrayList<>();
        //Need to add initialise textures back.

        buildingTextures.add("cabin_0");
        buildingTextures.add("cabin_90");
        buildingTextures.add("cabin_180");
        buildingTextures.add("cabin_270");

        buildingCost = new HashMap<>();
        buildingCost.put("Wood", 7);
        buildingCost.put("Stone",4);
    }

    public ArrayList<String> initialiseCabinTextures() {
        ArrayList<String> cabinTextures = new ArrayList<>();
        cabinTextures.add("cabin_0");
        cabinTextures.add("cabin_90");
        cabinTextures.add("cabin_180");
        cabinTextures.add("cabin_270");
        return cabinTextures;
    }

    /**
     * Initialises the textures and building costs for the Storage Unit Building Tpe
     *
     */
    public void initialiseStorageUnit() {
        buildingTextures = new ArrayList<>();
        buildingTextures.add("storage_unit");

        buildingCost = new HashMap<>();
        buildingCost.put("Wood", 4);
        buildingCost.put("Stone",2);
    }

    /**
     * Initialises the textures and building costs for the TownCentre Building Tpe
     *
     */
    public void initialiseTownCentre() {
        buildingTextures = new ArrayList<>();
        buildingTextures.add("town_centre");

        buildingCost = new HashMap<>();
        buildingCost.put("Wood", 10);
        buildingCost.put("Stone",5);
        buildingCost.put("Metal",2);
    }

    /**
     * Initialises the textures and building costs for the Castle Building Tpe
     *
     */
    public void initialiseCastle() {
        List<String> castleCentreTextures = new ArrayList<String>();

        castleCentreTextures.add("castle_0");
        castleCentreTextures.add("castle_90");
        castleCentreTextures.add("castle_180");
        castleCentreTextures.add("castle_270");

        buildingCost = new HashMap<>();
        buildingCost.put("Wood", 20);
        buildingCost.put("Stone",10);
        buildingCost.put("Metal",5);
    }

    /**
     * Initialises the textures and building costs for the Safe House Building Tpe
     *
     */
    public void initialiseSafeHouse() {
        List<String> safeHouseTextures = new ArrayList<String>();
        safeHouseTextures.add("safe_house_0");
        safeHouseTextures.add("safe_house_90");
        safeHouseTextures.add("safe_house_180");
        safeHouseTextures.add("safe_house_270");

        buildingCost = new HashMap<>();
        buildingCost.put("Wood", 10);
        buildingCost.put("Stone",5);
        buildingCost.put("Metal",2);
    }

    /**
     * Initialises the textures and building costs for the Watch tower Building Tpe
     *
     */
    public void initialiseWatchTower() {
        List<String> watchTowerTextures = new ArrayList<String>();

        watchTowerTextures.add("watchtower_0");
        watchTowerTextures.add("watchtower_90");
        watchTowerTextures.add("watchtower_180");
        watchTowerTextures.add("watchtower_270");

        buildingCost = new HashMap<>();
        buildingCost.put("Wood", 10);
        buildingCost.put("Stone",7);
        buildingCost.put("Metal",3);
    }

    /**
     * Initialises the textures and build costs for the Wall Building Tpe
     *
     */
    public void initialiseFence() {
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

        buildingCost = new HashMap<>();
        buildingCost.put("Wood", 2);
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

    /**
     * Gets the building cost of the Building Type
     *
     * @return Building Cost
     */
    public Map<String, Integer> getBuildCost() {
        return buildingCost;
    }
}



