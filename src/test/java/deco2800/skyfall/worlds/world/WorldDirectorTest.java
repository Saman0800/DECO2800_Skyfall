package deco2800.skyfall.worlds.world;


import deco2800.skyfall.entities.PlayerPeon;
import deco2800.skyfall.managers.*;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GameManager.class, DatabaseManager.class, PlayerPeon.class })
public class WorldDirectorTest {
    @Before
    public void setup() {
        GameManager mockGM = mock(GameManager.class);
        mockStatic(GameManager.class);

        GameMenuManager gameMenu = mock(GameMenuManager.class);
        when(GameManager.getManagerFromInstance(GameMenuManager.class)).thenReturn(gameMenu);

        InputManager input = mock(InputManager.class);
        when(GameManager.getManagerFromInstance(InputManager.class)).thenReturn(input);

        PhysicsManager physics = new PhysicsManager();
        when(mockGM.getManager(PhysicsManager.class)).thenReturn(physics);

        when(GameManager.get()).thenReturn(mockGM);
        when(mockGM.getWorld()).thenReturn(null);
    }

    @Test
    public void constructNBiomeSinglePlayerWorldTest(){
        WorldBuilder builder = Mockito.mock(WorldBuilder.class);
        WorldDirector.constructNBiomeSinglePlayerWorld(builder, 3, false);
        verify(builder).setType("single_player");
        verify(builder).setWorldSize(100);
        verify(builder).setNodeSpacing(20);
        verify(builder).setSeed(any(Integer.class));
        verify(builder, times(2)).addLake(1);
        verify(builder).addRiver();
        verify(builder).setRiverSize(1);
        verify(builder).setBeachSize(2);
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
        verify(builder).setType("single_player");
        verify(builder).setWorldSize(30);
        verify(builder).setNodeSpacing(5);
        verify(builder, times(1)).addBiome(any(AbstractBiome.class), any(Integer.class));
    }

}
