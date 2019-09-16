package deco2800.skyfall.util;

import deco2800.skyfall.entities.structures.AbstractBuilding;
import deco2800.skyfall.worlds.Tile;

public class ConstructionPermissions {



    // Checks players inventory
    // Checks if player is allowed to create it
    /**
     * To be called by the Construction Manager to check a if a player has the
     * right permissions and inventory to create the selected building.
     * @param building - the building being passed into
     *
     * @return boolean - true if the building can be created, false otherwise.
     */
     public <T extends AbstractBuilding> boolean BuildingCreation( T building, Tile tile){

         // Should I use wildcards or something here?
         return true;
    }


    /**
     * To be called by the Construction Manager to check if a building can be placed
     * on the specified tile
     * g.
     * @param building - the building trying to be placed
     *
     * @return boolean - true if the building can be placed there, false otherwise.
     */
    public boolean BuildingPlacement(AbstractBuilding building){
        return true;
    }










}
