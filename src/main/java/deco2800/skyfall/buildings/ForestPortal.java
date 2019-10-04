package deco2800.skyfall.buildings;

import com.badlogic.gdx.graphics.Texture;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.resources.Blueprint;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.biomes.DesertBiome;
import deco2800.skyfall.worlds.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ForestPortal extends AbstractPortal implements Blueprint {

    public String currentBiome = "forest";
    public String nextBiome = "desert";
    public boolean blueprintLearned = false;
    public String name = "forestPortal";
    Texture texture;
    // a logger
    private final transient Logger logger = LoggerFactory.getLogger(ForestPortal.class);




    /**
     * Constructor for an building entity with normal rendering size.
     * @param col the col position on the world
     * @param row the row position on the world
     * @param renderOrder the height position on the world
     */
    public ForestPortal(float col, float row, int renderOrder) {
        super(col, row, renderOrder);
        // TODO: @CGulley set texture to the texture of the forest portal
        // TODO: @CGulley set the functionality to move to the next biome

    }

    @Override
    public void setTexture(String texture) {
        super.setTexture(texture);
    }

    @Override
    public void onTick(long i) {
        // do nothing so far
    }

    /**
     * Returns the number of wood required for the item.
     * @return The amount of wood needed
     */
    public int getRequiredWood() {
        return 0;
    }

    /**
     * Returns the number of stones required for the item.
     * @return The amount of stone needed
     */
    public int getRequiredStone() {
        return 0;
    }

    /**
     * Returns the number of metal required for the item.
     * @return The amount of metal needed
     */
    public int getRequiredMetal() {
        return 0;
    }

    /**
     * Returns a map of the name of the required resource and
     * the required number of each resource to create the item.
     * @return a hashamp of the required resources and their number.
     */
    public Map<String,Integer> getAllRequirements() {
        return super.getBuildCost();
    }

    /**
     * Get the name of the Portal
     *
     * @return String - The name of the portal
     */
    public String getName() {
        return this.name;
    }

    /**
     * a getter method to check if a player has learned the blueprint
     *
     * @return true if the player has learned the blueprint, false otherwise
     */
    public boolean isBlueprintLearned() {
        return blueprintLearned;
    }

    /**
     * changes the boolean blueprintLearned to true.
     */
    public void toggleBlueprintLearned() {
        if (blueprintLearned == true) {
            blueprintLearned = false;
        } else {
            blueprintLearned = true;
        }

    }

    /**
     * @return - cost of building the building
     */
    public int getCost() {
        return 0;
    }


    public void unlocknext(MainCharacter character) {
        super.unlocknext(character, nextBiome);
    }

    /**
     * Move characters location to the next biome
     * To be implemented when a player clicks on the portal
     * @param character - The Character to teleport
     * @param  world - The world to teleport through
     */
    public void teleport(MainCharacter character, World world) {
        character.unlockBiome(nextBiome);
        //move to a random place on the map
        AbstractBiome next = null;
        for (AbstractBiome biome: world.getBiomes()) {
            if (biome.getBiomeName() == nextBiome) {
                next = biome;
            }
        }

        if (next == null) {
            // TODO: @CGulley add a logger and send message to the logger about invalid biome
            logger.warn("No next biome");

        } else {
            ArrayList<Tile> biomeTiles = next.getTiles();
            Tile firstTile = biomeTiles.get(0);
            // Setting the characters tile to the next biome
            character.setPosition(firstTile.getCol(),firstTile.getRow());
        }
    }


}

