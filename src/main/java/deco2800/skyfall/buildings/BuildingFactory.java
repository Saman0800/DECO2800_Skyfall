package deco2800.skyfall.buildings;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;

/**
 * A BuildingFactory is a class to handle the creating process of all building
 * entity classes, including both BuildingEntity based class and BuildingEntity
 * subclasses.
 */
public class BuildingFactory {

    /**
     * Get the number of buildings that a factory could build.
     * 
     * @return the number of building entities
     */
    public int getCount() {
        return BuildingType.values().length;
    }

    /**
     * Add to made buildings and remove from crafted
     * 
     * @param type the building type
     */
    private void addToConstucted(BuildingType type) {
        GameMenuManager manager = GameManager.getManagerFromInstance(GameMenuManager.class);
        MainCharacter mc = manager.getMainCharacter();
        mc.addConstructedBuilding(type);
        mc.removeCraftedBuilding(type);
    }

    /**
     * Create cabin building based on BuildingEntity class with defined default
     * renderOrder.
     * 
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a cabin object
     */
    public BuildingEntity createCabin(float col, float row) {
        addToConstucted(BuildingType.CABIN);
        return new BuildingEntity(col, row, 2, BuildingType.CABIN);
    }

    /**
     * Create a storage unit based on BuildingEntity class with defined default
     * renderOrder.
     * 
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a storage unit building object
     */
    public BuildingEntity createStorageUnit(float col, float row) {
        addToConstucted(BuildingType.STORAGE_UNIT);
        return new BuildingEntity(col, row, 2, BuildingType.STORAGE_UNIT);
    }

    /**
     * Create a town centre building based on BuildingEntity class with defined
     * default renderOrder.
     * 
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a town centre object
     */
    public BuildingEntity createTownCentreBuilding(float col, float row) {
        addToConstucted(BuildingType.TOWNCENTRE);
        return new BuildingEntity(col, row, 2, BuildingType.TOWNCENTRE);
    }

    /**
     * Create fence building based on BuildingEntity class with defined default
     * renderOrder.
     * 
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a fence object
     */
    public BuildingEntity createFenceBuilding(float col, float row) {
        addToConstucted(BuildingType.FENCE);
        return new BuildingEntity(col, row, 2, BuildingType.FENCE);
    }

    /**
     * Create safeHouse building based on BuildingEntity class with defined default
     * renderOrder.
     * 
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a safeHouse object
     */
    public BuildingEntity createSafeHouse(float col, float row) {
        addToConstucted(BuildingType.SAFEHOUSE);
        return new BuildingEntity(col, row, 2, BuildingType.SAFEHOUSE);
    }

    /**
     * Create watch tower building based on BuildingEntity class with defined
     * default renderOrder.
     * 
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a safeHouse object
     */
    public BuildingEntity createWatchTower(float col, float row) {
        addToConstucted(BuildingType.WATCHTOWER);
        return new BuildingEntity(col, row, 2, BuildingType.WATCHTOWER);
    }

    /**
     * Create Castle building based on BuildingEntity class with defined default
     * renderOrder.
     * 
     * @param col column coordinate that a building located on
     * @param row row coordinate that a building located on
     * @return a safeHouse object
     */
    public BuildingEntity createCastle(float col, float row) {
        addToConstucted(BuildingType.CASTLE);
        return new BuildingEntity(col, row, 2, BuildingType.CASTLE);
    }

    public BuildingEntity createForestPortal(float col, float row) {
        addToConstucted(BuildingType.FORESTPORTAL);
        return new BuildingEntity(col, row, 2, BuildingType.FORESTPORTAL);
    }

    public BuildingEntity createMountainPortal(float col, float row) {
        addToConstucted(BuildingType.MOUNTAINPORTAL);
        return new BuildingEntity(col, row, 2, BuildingType.MOUNTAINPORTAL);
    }

    public BuildingEntity createDesertPortal(float col, float row) {
        addToConstucted(BuildingType.DESERTPORTAL);
        return new BuildingEntity(col, row, 2, BuildingType.DESERTPORTAL);
    }

    public BuildingEntity createVolcanoPortal(float col, float row) {
        addToConstucted(BuildingType.VOLCANOPORTAL);
        return new BuildingEntity(col, row, 2, BuildingType.VOLCANOPORTAL);
    }
}
