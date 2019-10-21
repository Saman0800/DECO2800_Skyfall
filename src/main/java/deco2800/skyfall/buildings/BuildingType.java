package deco2800.skyfall.buildings;

import deco2800.skyfall.resources.Blueprint;

import java.util.*;

/**
 * A BuildingTime Enum outlines all of the building types available in the
 * skyfall game as well as their set values/attributes
 */
public enum BuildingType implements Blueprint {

    CABIN(BluePrintNames.CABIN_STRING, 10, 1, 1, 7, BluePrintNames.CABIN_STRING_0), STORAGE_UNIT(
        BluePrintNames.STORAGE_UNIT_STRING, 5, 2, 2, 6,
        BluePrintNames.STORAGE_UNIT_STRING_LOWER),
    TOWNCENTRE(BluePrintNames.TOWN_CENTRE_STRING, 80, 3, 3, 0, BluePrintNames.TOWN_CENTRE_STRING_LOWER), FENCE("Fence", 5, 1, 1, 3, "fenceN-S"),
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
        case BluePrintNames.CABIN_STRING:
            initialiseCabin();
            break;
        case BluePrintNames.STORAGE_UNIT_STRING:
            initialiseStorageUnit();
            break;
        case BluePrintNames.TOWN_CENTRE_STRING:
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

        buildingTextures.add(BluePrintNames.CABIN_STRING_0);
        buildingTextures.add("cabin_90");
        buildingTextures.add("cabin_180");
        buildingTextures.add("cabin_270");

        buildingCost = new HashMap<>();
        buildingCost.put(BluePrintNames.WOOD_STRING, 15);
        buildingCost.put(BluePrintNames.STONE_STRING, 10);
        buildingCost.put(BluePrintNames.METAL_STRING, 5);

    }

    /**
     * Initialises the textures and building costs for the Storage Unit Building Tpe
     *
     */
    public void initialiseStorageUnit() {
        buildingTextures = new ArrayList<>();
        buildingTextures.add(BluePrintNames.STORAGE_UNIT_STRING_LOWER);

        buildingCost = new HashMap<>();
        buildingCost.put(BluePrintNames.WOOD_STRING, 4);
        buildingCost.put(BluePrintNames.STONE_STRING, 2);
    }

    /**
     * Initialises the textures and building costs for the TownCentre Building Tpe
     *
     */
    public void initialiseTownCentre() {
        buildingTextures = new ArrayList<>();
        buildingTextures.add(BluePrintNames.TOWN_CENTRE_STRING_LOWER);

        buildingCost = new HashMap<>();
        buildingCost.put(BluePrintNames.WOOD_STRING, 10);
        buildingCost.put(BluePrintNames.STONE_STRING, 5);
        buildingCost.put(BluePrintNames.METAL_STRING, 2);
    }

    /**
     * Initialises the textures and building costs for the Castle Building Tpe
     *
     */
    public void initialiseCastle() {
        buildingCost = new HashMap<>();
        buildingCost.put(BluePrintNames.WOOD_STRING, 20);
        buildingCost.put(BluePrintNames.STONE_STRING, 10);
        buildingCost.put(BluePrintNames.METAL_STRING, 5);
    }

    /**
     * Initialises the textures and building costs for the Safe House Building Tpe
     *
     */
    public void initialiseSafeHouse() {
        buildingCost = new HashMap<>();
        buildingCost.put(BluePrintNames.WOOD_STRING, 10);
        buildingCost.put(BluePrintNames.STONE_STRING, 5);
        buildingCost.put(BluePrintNames.METAL_STRING, 2);
    }

    /**
     * Initialises the textures and building costs for the Watch tower Building Tpe
     *
     */
    public void initialiseWatchTower() {
        buildingCost = new HashMap<>();
        buildingCost.put(BluePrintNames.WOOD_STRING, 20);
        buildingCost.put(BluePrintNames.STONE_STRING, 10);
        buildingCost.put(BluePrintNames.METAL_STRING, 10);
    }

    public void initialiseForestPortal() {
        buildingCost = new HashMap<>();
        buildingCost.put(BluePrintNames.WOOD_STRING, 0);
        buildingCost.put(BluePrintNames.STONE_STRING, 0);
        buildingCost.put(BluePrintNames.METAL_STRING, 0);

        buildingTextures = new ArrayList<>();
        // Need to add initialise textures back.

        buildingTextures.add("portal_forest");
    }

    public void initialiseDesertPortal() {
        buildingCost = new HashMap<>();
        buildingCost.put(BluePrintNames.WOOD_STRING, 0);
        buildingCost.put(BluePrintNames.STONE_STRING, 0);
        buildingCost.put(BluePrintNames.METAL_STRING, 0);

        buildingTextures = new ArrayList<>();
        // Need to add initialise textures back.

        buildingTextures.add("portal_desert");
    }

    public void initialiseMountainPortal() {
        buildingCost = new HashMap<>();
        buildingCost.put(BluePrintNames.WOOD_STRING, 0);
        buildingCost.put(BluePrintNames.STONE_STRING, 0);
        buildingCost.put(BluePrintNames.METAL_STRING, 0);

        buildingTextures = new ArrayList<>();
        // Need to add initialise textures back.

        buildingTextures.add("portal_mountain");
    }

    public void initialiseVolcanoPortal() {
        buildingCost = new HashMap<>();
        buildingCost.put(BluePrintNames.WOOD_STRING, 0);
        buildingCost.put(BluePrintNames.STONE_STRING, 0);
        buildingCost.put(BluePrintNames.METAL_STRING, 0);

        buildingTextures = new ArrayList<>();
        // Need to add initialise textures back.

        buildingTextures.add("portal_volcano");
    }

    /**
     * Initialises the textures and build costs for the Wall Building Tpe
     *
     */
    public void initialiseFence() {
        initialiseFenceTextures();
        buildingCost = new HashMap<>();
        buildingCost.put(BluePrintNames.WOOD_STRING, 2);
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
        return buildingCost.get(BluePrintNames.WOOD_STRING);
    }

    @Override
    public int getRequiredStone() {
        return buildingCost.get(BluePrintNames.STONE_STRING);
    }

    @Override
    public int getRequiredMetal() {
        return buildingCost.get(BluePrintNames.METAL_STRING);
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
        case BluePrintNames.CABIN_STRING:
            return 100;
        case BluePrintNames.STORAGE_UNIT_STRING:
            return 150;
        case BluePrintNames.TOWN_CENTRE_STRING:
            return 200;
        case BluePrintNames.WALL_STRING:
            return 250;
        case BluePrintNames.CASTLE_STRING:
            return 50;
        case BluePrintNames.WATCH_TOWER_STRING:
            return 75;
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
