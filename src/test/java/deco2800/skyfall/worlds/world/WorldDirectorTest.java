package deco2800.skyfall.worlds.world;


import deco2800.skyfall.entities.Spider;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class WorldDirectorTest {


    @Test
    public void constructSimpleSinglePlayerWorldTest(){
        WorldBuilder builder = Mockito.mock(WorldBuilder.class);
        WorldDirector.constructSimpleSinglePlayerWorld(builder);
        verify(builder).setType("single_player");
        verify(builder).setWorldSize(160);
        verify(builder).setNodeSpacing(15);
        verify(builder, times(2)).addLake(5);
        verify(builder).setRiverSize(2);
        verify(builder).setBeachSize(12);
        verify(builder).setStaticEntities(true);
        verify(builder, times(6)).addBiome(any(AbstractBiome.class), any(Integer.class));
    }

    @Test
    public void constructNBiomeSinglePlayerWorldTest(){
        WorldBuilder builder = Mockito.mock(WorldBuilder.class);
        WorldDirector.constructNBiomeSinglePlayerWorld(builder, 3);
        verify(builder).setType("single_player");
        verify(builder).setWorldSize(160);
        verify(builder).setNodeSpacing(15);
        verify(builder).setSeed(any(Integer.class));
        verify(builder, times(1)).addLake(5);
        verify(builder).setRiverSize(3);
        verify(builder).setBeachSize(12);
        verify(builder).setStaticEntities(true);
        verify(builder, times(3)).addBiome(any(AbstractBiome.class), any(Integer.class));
    }

    @Test
    public void constructTutorialWorldTest(){
        WorldBuilder builder = Mockito.mock(WorldBuilder.class);
        WorldDirector.constructTutorialWorld(builder);
        verify(builder).setType("tutorial");
        verify(builder).setWorldSize(80);
        verify(builder).setNodeSpacing(15);
        verify(builder).setSeed(2);
        verify(builder, times(1)).addLake(5);
        verify(builder).setRiverSize(5);
        verify(builder).setBeachSize(12);
        verify(builder).setStaticEntities(true);
        verify(builder, times(3)).addBiome(any(AbstractBiome.class), any(Integer.class));
    }


    @Test
    public void constructServerWorldTest(){
        WorldBuilder builder = Mockito.mock(WorldBuilder.class);
        WorldDirector.constructServerWorld(builder);
        verify(builder).setType("server");
        verify(builder).setWorldSize(80);
        verify(builder).setNodeSpacing(15);
        verify(builder, times(3)).addBiome(any(AbstractBiome.class), any(Integer.class));
    }

    @Test
    public void constructTestWorldTest(){
        WorldBuilder builder = Mockito.mock(WorldBuilder.class);
        WorldDirector.constructTestWorld(builder);
        verify(builder).setType("test");
        verify(builder).setWorldSize(30);
        verify(builder).setNodeSpacing(5);
        verify(builder).addLake(any(Integer.class));
        verify(builder).setStaticEntities(true);
        verify(builder, times(6)).addBiome(any(AbstractBiome.class), any(Integer.class));
    }

}
