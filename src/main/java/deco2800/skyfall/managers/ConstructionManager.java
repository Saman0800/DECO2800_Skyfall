package deco2800.skyfall.managers;

import java.util.ArrayList;
import java.util.TreeMap;

public class ConstructionManager extends AbstractManager {
    //This manager while control all features related to construction

    TreeMap<String, Boolean> terrain_map = new TreeMap<String, Boolean>();

    //TODO: Add single tile terrain check
    //uses terrain_map to know if a tile is valid or not

    //TODO: Add multi tile terrain check
    //uses terrain_map to know if a tile is valid or not

    //TODO: Inventory check
    //return a list of how much of each relevant resources the player owns

    //TODO: Inventory remove
    //takes in a structure class and removes the material cost from
    //player inventory

    //TODO: everything to do with UI

    public void initialiseTerrainMap() {
        //TODO: create method
        //Get information for all terrain types from .txt document
        //texture, boolean
        //e.g. grass, true
        //run updateTerrainMap for each piece of information
    }

    public void updateTerrainMap(String texture, Boolean value) {
        terrain_map.put(texture, value);
    }
}
