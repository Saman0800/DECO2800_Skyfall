package deco2800.skyfall.worlds;

import deco2800.skyfall.worlds.world.World;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class WholeWorldTest {

    @Test
    public void worldTest(){
//        AbstractWorld world = new RocketWorld(0, 10, 1, new int[] {20,10,10}, 3,2);
//        AbstractWorld world = new RocketWorld(0, 30, 5, new int[] {10,10,10,10,10}, 2,5);
        World world = new RocketWorld(0, 30, 5, new int[] {5,5,5,5,5}, 2,5);
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
