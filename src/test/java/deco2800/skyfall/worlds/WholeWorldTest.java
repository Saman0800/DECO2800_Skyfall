package deco2800.skyfall.worlds;

import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class WholeWorldTest {

    @Test
    public void worldTest(){
        WorldBuilder worldBuilder = new WorldBuilder();
        WorldDirector.constructTestWorld(worldBuilder);
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
}
