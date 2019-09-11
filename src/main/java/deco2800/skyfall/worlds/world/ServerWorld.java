package deco2800.skyfall.worlds.world;   //Whether the world is a tutorial world

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.world.World;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Server world acts as the husk of an actual world, controlled by a server connection.
 * <p>
 * Clients will not call onTick()
 */
public class ServerWorld extends World {

    public ServerWorld(WorldParameters worldParameters) {
        super(worldParameters);
    }

    /**
     * Do not tick entities in this onTick method, entities will be ticked by the server.
     */
    @Override
    public void onTick(long i) {
        // TODO: Lerping etc
    }

    @Override
    protected void generateWorld(Random random) {
        // TODO Auto-generated method stub

    }
}
