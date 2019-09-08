package deco2800.skyfall.worlds;
import org.junit.Assert;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;

public class WholeWorldTest {

    @Test
    public void worldTest(){
        WorldBuilder worldBuilder = new WorldBuilder();
        WorldDirector.constructTestWorld(worldBuilder);
        worldBuilder.setType("single_player");
        worldBuilder.setStaticEntities(false);
        World world = worldBuilder.getWorld();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/test/java/deco2800/skyfall/worlds/ExampleWorldOutput.txt"));
//            String content = new Scanner(new File("src/test/java/deco2800/skyfall/worlds/ExampleWorldOutput.txt")).useDelimiter().next();
//            System.out.println(content);
            String content = "";
            String line;
            while ((line =  reader.readLine()) != null){
                content += line + "\n";
            }

            assertEquals(content, world.worldToString());

        } catch (IOException e){
            throw new RuntimeException(e);
        }
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
