package deco2800.skyfall.buildings;

import deco2800.skyfall.resources.Blueprint;

import java.util.*;

/**
 * A BuildingTime Enum outlines all of the building types available in the
 * skyfall game as well as their set values/attributes
 */
public enum BuildingType implements Blueprint {

    CABIN(cabinString, 10, 1, 1, 7, cabinString0), STORAGE_UNIT(storageUnitString, 5, 2, 2, 6, storageUnitStringLower),
    TOWNCENTRE(townCentreString, 80, 3, 3, 0, townCentreStringLower), FENCE("Fence", 5, 1, 1, 3, "fenceN-S"),
    SAFEHOUSE("SafeHouse", 5, 5, 5, 7, "safe_house_0"), WATCHTOWER("WatchTower", 8, 1, 1, 8, "watchtower_0"),
    CASTLE("Castle", 10, 1, 1, 6, "castle_0"), FORESTPORTAL("ForestPortal", 10, 1, 1, 6, "portal_forest"),
    DESERTPORTAL("DesertPortal", 10, 1, 1, 6, "portal_desert"),
    MOUNTAINPORTAL("MountainPortal", 10, 1, 1, 6, "portal_mountain"),
    VOLCANOPORTAL("VolcanoPortal", 10, 1, 1, 6, "portal_volcano");

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
        switch (name) {
        case cabinString:
            initialiseCabin();
            break;
        case storageUnitString:
            initialiseStorageUnit();
            break;
        case townCentreString:
            initialiseTownCentre();
            break;
        case "Wall":
            initialiseFence();
            break;
        case "WatchTower":
            initialiseWatchTower();
            break;
        case "Castle":
            initialiseCastle();
            break;
        case "SafeHouse":
            initialiseSafeHouse();
            break;
        case "Fence":
            initialiseFence();
            break;
        case "ForestPortal":
            initialiseForestPortal();
            break;
        case "DesertPortal":
            initialiseDesertPortal();
            break;
        case "MountainPortal":
            initialiseMountainPortal();
            break;
        case "VolcanoPortal":
            initialiseVolcanoPortal();
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
        // Need to add initialise textures back.

        buildingTextures.add(cabinString0);
        buildingTextures.add("cabin_90");
        buildingTextures.add("cabin_180");
        buildingTextures.add("cabin_270");

        buildingCost = new HashMap<>();
        buildingCost.put(woodString, 5);
        buildingCost.put(stoneString, 2);
    }

    public List<String> initialiseCabinTextures() {
        List<String> cabinTextures = new ArrayList<>();
        cabinTextures.add(cabinString0);
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
        buildingTextures.add(storageUnitStringLower);

        buildingCost = new HashMap<>();
        buildingCost.put(woodString, 4);
        buildingCost.put(stoneString, 2);
    }

    public List<String> initialiseStorageUnitTextures() {
        List<String> storageTextures = new ArrayList<>();
        storageTextures.add(storageUnitStringLower);
        return storageTextures;
    }

    /**
     * Initialises the textures and building costs for the TownCentre Building Tpe
     *
     */
    public void initialiseTownCentre() {
        buildingTextures = new ArrayList<>();
        buildingTextures.add(townCentreStringLower);

        buildingCost = new HashMap<>();
        buildingCost.put(woodString, 10);
        buildingCost.put(stoneString, 5);
        buildingCost.put(metalString, 2);
    }

    public List<String> initialiseTownCentreTextures() {
        List<String> townTextures = new ArrayList<>();
        townTextures.add(townCentreStringLower);
        return townTextures;
    }

    /**
     * Initialises the textures and building costs for the Castle Building Tpe
     *
     */
    public void initialiseCastle() {
        buildingCost = new HashMap<>();
        buildingCost.put(woodString, 20);
        buildingCost.put(stoneString, 10);
        buildingCost.put(metalString, 5);
    }

    public List<String> initialiseCastleTextures() {
        List<String> castleTextures = new ArrayList<>();
        castleTextures.add("castle_0");
        castleTextures.add("castle_90");
        castleTextures.add("castle_180");
        castleTextures.add("castle_270");
        return castleTextures;
    }

    /**
     * Initialises the textures and building costs for the Safe House Building Tpe
     *
     */
    public void initialiseSafeHouse() {
        buildingCost = new HashMap<>();
        buildingCost.put(woodString, 10);
        buildingCost.put(stoneString, 5);
        buildingCost.put(metalString, 2);
    }

    public List<String> initialiseSafeHouseTextures() {
        List<String> safeHouseTextures = new ArrayList<>();
        safeHouseTextures.add("safe_house_0");
        safeHouseTextures.add("safe_house_90");
        safeHouseTextures.add("safe_house_180");
        safeHouseTextures.add("safe_house_270");
        return safeHouseTextures;
    }

    /**
     * Initialises the textures and building costs for the Watch tower Building Tpe
     *
     */
    public void initialiseWatchTower() {
        buildingCost = new HashMap<>();
        buildingCost.put(woodString, 10);
        buildingCost.put(stoneString, 7);
        buildingCost.put(metalString, 3);
    }

    public void initialiseForestPortal() {
        buildingCost = new HashMap<>();
        buildingCost.put(woodString, 0);
        buildingCost.put(stoneString, 0);
        buildingCost.put(metalString, 0);

        buildingTextures = new ArrayList<>();
        // Need to add initialise textures back.

        buildingTextures.add("portal_forest");
    }

    public void initialiseDesertPortal() {
        buildingCost = new HashMap<>();
        buildingCost.put(woodString, 0);
        buildingCost.put(stoneString, 0);
        buildingCost.put(metalString, 0);

        buildingTextures = new ArrayList<>();
        // Need to add initialise textures back.

        buildingTextures.add("portal_desert");
    }

    public void initialiseMountainPortal() {
        buildingCost = new HashMap<>();
        buildingCost.put(woodString, 0);
        buildingCost.put(stoneString, 0);
        buildingCost.put(metalString, 0);

        buildingTextures = new ArrayList<>();
        // Need to add initialise textures back.

        buildingTextures.add("portal_mountain");
    }

    public void initialiseVolcanoPortal() {
        buildingCost = new HashMap<>();
        buildingCost.put(woodString, 0);
        buildingCost.put(stoneString, 0);
        buildingCost.put(metalString, 0);

        buildingTextures = new ArrayList<>();
        // Need to add initialise textures back.

        buildingTextures.add("portal_volcano");
    }

    public List<String> initialiseWatchTowerTextures() {
        List<String> watchTowerTextures = new ArrayList<>();
        watchTowerTextures.add("watchtower_0");
        watchTowerTextures.add("watchtower_90");
        watchTowerTextures.add("watchtower_180");
        watchTowerTextures.add("watchtower_270");
        return watchTowerTextures;
    }

    /**
     * Initialises the textures and build costs for the Wall Building Tpe
     *
     */
    public void initialiseFence() {
        initialiseFenceTextures();
        buildingCost = new HashMap<>();
        buildingCost.put(woodString, 2);
    }

    public List<String> initialiseFenceTextures() {
        List<String> fenceTextures = new ArrayList<>();

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

    @Override
    public int getRequiredWood() {
        return buildingCost.get(woodString);
    }

    @Override
    public int getRequiredStone() {
        return buildingCost.get(stoneString);
    }

    @Override
    public int getRequiredMetal() {
        return buildingCost.get(metalString);
    }

    @Override
    public Map<String, Integer> getAllRequirements() {
        return Collections.unmodifiableMap(buildingCost);
    }

    /**
     * Gets the name of the Building Type
     *
     * @return Building Type Name
     */
    public String getName() {
        return name;
    }

    @Override
    public int getCost() {
        switch (name) {
        case cabinString:
            return 100;
        case storageUnitString:
            return 150;
        case townCentreString:
            return 200;
        case "Wall":
            return 250;
        default:
            return 0;
        }
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
        return getAllRequirements();
    }
}
