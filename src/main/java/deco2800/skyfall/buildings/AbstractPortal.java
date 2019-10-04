package deco2800.skyfall.buildings;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.util.Collider;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * An AbstractPortal is an item that can transport a players position to the
 * specified Biome, given the player has reached the necessary requirements.
 */
public abstract class AbstractPortal extends AbstractEntity {

    // a logger
    private final transient Logger log = LoggerFactory.getLogger(BuildingEntity.class);
    // a building object name
    private static final String ENTITY_ID_STRING = "PortalID";



    private Map<String, Integer> buildCost;

    private float col;
    private float row;


    /**
     * Constructor for an building entity with normal rendering size.
     * @param col the col position on the world
     * @param row the row position on the world
     * @param renderOrder the height position on the world
     * @param
     */
    public AbstractPortal(float col, float row, int renderOrder) {
        super( col, row, renderOrder);
        this.setObjectName(ENTITY_ID_STRING);

        if (!WorldUtil.validColRow(new HexVector(col, row))) {
            log.debug("Invalid position");
        }
    }

    public  Map<String,Integer> getBuildCost() {
        return this.buildCost;
    }



    /**
     * Unlocks the next biome
     */
    public void unlocknext(MainCharacter character, String nextBiome) {
        character.unlockBiome(nextBiome);
    }


    /**
     * Move characters location to the next biome
     * To be implemented when a player clicks on the portal
     * @param character - The Character to teleport
     * @param nextBiome - the Biome to teleport to
     */
    public void teleport(MainCharacter character, AbstractBiome nextBiome) {
        // TODO: @CGulley - Create a general teleport method for all portals.
        //  For now, individual functionality in child classes

    }





}
