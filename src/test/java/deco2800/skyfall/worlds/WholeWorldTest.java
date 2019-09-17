package deco2800.skyfall.worlds;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.database.DataBaseConnector;
import deco2800.skyfall.worlds.world.Chunk;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WorldBuilder.class, WorldDirector.class, DatabaseManager.class, DataBaseConnector.class })
public class WholeWorldTest {

    @Test
    public void worldTest() throws Exception {
        World world = generateWorld();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/test/java/deco2800/skyfall/worlds/ExampleWorldOutput.txt"));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line =  reader.readLine()) != null){
                content.append(line).append("\n");
            }

            assertEquals(content.toString(), world.worldToString());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    // In test file to enable mocking.
    @Test
    @PowerMockIgnore
    public void generateExampleWorld() throws Exception {
        World world = generateWorld();

        try {
            world.saveWorld("src/test/java/deco2800/skyfall/worlds/ExampleWorldOutput.txt");
        } catch (IOException e){
            System.out.println("Could not save world");
        }
    }

    private World generateWorld() throws Exception {
        Random random = new Random(0);
        whenNew(Random.class).withAnyArguments().thenReturn(random);

        DataBaseConnector connector = mock(DataBaseConnector.class);
        when(connector.loadChunk(any(World.class), anyInt(), anyInt())).then(
                (Answer<Chunk>) invocation -> {
                    Chunk chunk = new Chunk(invocation.getArgumentAt(0, World.class),
                                            invocation.getArgumentAt(1, Integer.class),
                                            invocation.getArgumentAt(2, Integer.class));
                    chunk.generateEntities();
                    return chunk;
                });

        DatabaseManager manager = mock(DatabaseManager.class);
        when(manager.getDataBaseConnector()).thenReturn(connector);

        mockStatic(DatabaseManager.class);
        when(DatabaseManager.get()).thenReturn(manager);

        Field field = MainCharacter.class.getDeclaredField("mainCharacterInstance");
        field.setAccessible(true);
        field.set(null, null);

        WorldBuilder worldBuilder = new WorldBuilder();
        WorldDirector.constructTestWorld(worldBuilder);
        worldBuilder.setType("single_player");
        worldBuilder.setStaticEntities(false);
        World world = worldBuilder.getWorld();

        world.onTick(0);

        return world;
    }

    @Test
    public void testFrictionMap(){
        WorldBuilder worldBuilder = new WorldBuilder();
        WorldDirector.constructTestWorld(worldBuilder);
        worldBuilder.setType("single_player");
        worldBuilder.setStaticEntities(false);
        World world = worldBuilder.getWorld();
        Map<String, Float> frictionMap = world.frictionMap;

        Map<String, Float> expectedFrictionMap = new HashMap<>();
        frictionMap.put("grass", 0.8f);
        frictionMap.put("forest", 0.76f);
        frictionMap.put("water", 0.4f);
        frictionMap.put("ocean", 0.4f);
        frictionMap.put("lake", 0.4f);
        frictionMap.put("volcanic", 0.6f);
        frictionMap.put("mountain", 0.67f);
        frictionMap.put("desert", 0.59f);
        frictionMap.put("ice", 1f);
        frictionMap.put("snow", 1f);
        expectedFrictionMap.putAll(frictionMap);

        Assert.assertEquals(expectedFrictionMap, frictionMap);
    }
}
