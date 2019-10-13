package deco2800.skyfall.buildings;

import com.badlogic.gdx.graphics.Texture;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.SaveableEntity;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.saving.Save;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * An AbstractPortal is an item that can transport a players position to the
 * specified Biome, given the player has reached the necessary requirements.
 */
public abstract class AbstractPortal extends SaveableEntity {

    // a logger
    private final transient Logger log = LoggerFactory.getLogger(BuildingEntity.class);
    // a building object name
    private static final String ENTITY_ID_STRING = "PortalID";
    // The next biome to teleport to
    String nextBiome;

    private Map<String, Integer> buildCost;

    String currentBiome;
    String name;
    boolean blueprintLearned;
    Texture texture;

    /**
     * Constructor for an building entity with normal rendering size.
     *
     * @param col         the col position on the world
     * @param row         the row position on the world
     * @param renderOrder the height position on the world
     * @param
     */
    public AbstractPortal(float col, float row, int renderOrder) {
        super(col, row, renderOrder);
        this.setObjectName(ENTITY_ID_STRING);

        if (!WorldUtil.validColRow(new HexVector(col, row))) {
            log.debug("Invalid position");
        }
    }

    public Map<String, Integer> getBuildCost() {
        return this.buildCost;
    }

    /**
     * Sets the next Biome to teleport to
     */
    public void setNext(String nextBiome) {
        this.nextBiome = nextBiome;
    }


    /**
     * Move characters location to the next biome To be implemented when a player
     * clicks on the portal
     *
     * @param save - The Save file this is for
     */
    public void teleport(Save save) {
        // For now, individual functionality in child classes

        // Create a random world
        MainCharacter character = save.getMainCharacter();
        World currentWorld = character.getSave().getCurrentWorld();
        World nextWorld = WorldDirector.constructSingleBiomeWorld(new WorldBuilder(), currentWorld.getSeed() + 1, true, nextBiome).getWorld();
        // Add this world to the save
        save.getWorlds().add(nextWorld);
        save.setCurrentWorld(nextWorld);
        nextWorld.setSave(save);
        // Move main character to origin of new world
        save.getMainCharacter().setPosition(0, 0);
        DatabaseManager.get().getDataBaseConnector().saveGame(save);

        AbstractEntity.resetID();
        Tile.resetID();
        GameManager gameManager = GameManager.get();
        gameManager.setWorld(nextWorld);

        character.unlockBiome(nextBiome);
    }

    public void unlocknext(MainCharacter character, String next) {

    }

    /**
     * Returns the next biome for this portal
     *
     * @return the next biome for this portal
     */
    public String getNextBiome() {
        return this.nextBiome;
    }



}
