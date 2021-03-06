package deco2800.skyfall.worlds.world;


import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.GameMenuManager;
import deco2800.skyfall.managers.InputManager;
import deco2800.skyfall.managers.PhysicsManager;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GameManager.class, DatabaseManager.class, MainCharacter.class })
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
        WorldDirector.constructNBiomeSinglePlayerWorld(builder, 0, 3, false);
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
    public void constructServerWorldTest(){
        WorldBuilder builder = Mockito.mock(WorldBuilder.class);
        WorldDirector.constructServerWorld(builder, 0);
        verify(builder).setType("server");
        verify(builder).setWorldSize(80);
        verify(builder).setNodeSpacing(15);
        verify(builder, times(3)).addBiome(any(AbstractBiome.class), any(Integer.class));
    }

    @Test
    public void constructTestWorldTest(){
        WorldBuilder builder = Mockito.mock(WorldBuilder.class);
        WorldDirector.constructTestWorld(builder, 0);
        verify(builder).setType("single_player");
        verify(builder).setWorldSize(30);
        verify(builder).setNodeSpacing(5);
        verify(builder, times(1)).addBiome(any(AbstractBiome.class), any(Integer.class));
    }

}
