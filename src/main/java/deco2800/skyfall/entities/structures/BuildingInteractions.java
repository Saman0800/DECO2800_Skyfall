package deco2800.skyfall.entities.structures;

import deco2800.skyfall.buildings.BuildingEntity;
import deco2800.skyfall.buildings.BuildingType;
import deco2800.skyfall.entities.AbstractEntity;

public class BuildingInteractions {

    // Click on an object here

    // Check the Enum-> building type
    // create a switch case that checks the building type and then calls all of the
    // respective building Classes.
    //
    // tiles have a get biome method!!

    /**
     * Check if entity is building
     * 
     * @param input - Entity to check
     * @return - True if building, else false.
     */
    public boolean checkIfBuilding(AbstractEntity input) {
        return (input instanceof BuildingEntity);
    }

    /**
     * Interacts with building by finding its buildingType
     * 
     * @param currentBuilding - BuildingType of buildingEntity.
     */
    public void interactBuilding(BuildingType currentBuilding) {
        switch (currentBuilding.getName()) {
        case "Cabin":
            cabinInteract();
            break;
        case "StorageUnit":
            storageUnitInteract();
            break;
        case "TownCentre":
            townCentreInteract();
            break;
        case "Fence":
            fenceInteract();
            break;
        case "SafeHouse":
            safeHouseInteract();
            break;
        case "WatchTower":
            watchTowerInteract();
            break;
        case "Castle":
            castleInteract();
            break;
        default:
            break;
        }
    }

    /**
     * Cabin interact method
     */
    private void cabinInteract() {
        // Do nothing here.
    }

    /**
     * Storage Unit Interact method
     */
    private void storageUnitInteract() {
        // Do nothing here.
    }

    /**
     * Town Centre Interact method
     */
    private void townCentreInteract() {
        // Do nothing here.
    }

    /**
     * Fence Interact method
     */
    private void fenceInteract() {
        // Do nothing here.
    }

    /**
     * Safe House Interact method
     */
    private void safeHouseInteract() {
        // Do nothing here.
    }

    /**
     * Watch Tower Interact method
     */
    private void watchTowerInteract() {
        // Do nothing here.
    }

    /**
     * Castle Interact method
     */
    private void castleInteract() {
        // Do nothing here.
    }
}
