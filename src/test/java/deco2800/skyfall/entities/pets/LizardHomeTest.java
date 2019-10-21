package deco2800.skyfall.entities.pets;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.InputManager;
import deco2800.skyfall.managers.OnScreenMessageManager;
import deco2800.skyfall.managers.PhysicsManager;
import deco2800.skyfall.managers.database.DataBaseConnector;
import deco2800.skyfall.worlds.world.Chunk;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import java.util.Random;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GameManager.class, MainCharacter.class, WorldBuilder.class, WorldDirector.class, DatabaseManager.class,
                  DataBaseConnector.class })
public class LizardHomeTest {

    private World w = null;
    private MainCharacter mc=null;
    private  LizardHome lizardHome=null;
    private PhysicsManager physics;
    @Mock
    private GameManager mockGM;

    /**
     * set up world
     */
    @Before
    public void Setup() throws Exception {
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

        MainCharacter.resetInstance();

        lizardHome=new LizardHome(0,0,mc);
        mc = MainCharacter.getInstance(1f, 1f, 1f, "Main Piece", 2);
        WorldBuilder worldBuilder = new WorldBuilder();
        WorldDirector.constructTestWorld(worldBuilder, 0);
        w = worldBuilder.getWorld();
        mockGM = mock(GameManager.class);
        mockStatic(GameManager.class);
        when(GameManager.get()).thenReturn(mockGM);
        when(mockGM.getWorld()).thenReturn(w);
        InputManager Im = new InputManager();

        OnScreenMessageManager mockOSMM = mock(OnScreenMessageManager.class);
        when(mockGM.getManager(OnScreenMessageManager.class)).thenReturn(mockOSMM);

        physics = new PhysicsManager();
        when(mockGM.getManager(PhysicsManager.class)).thenReturn(physics);

        when(GameManager.getManagerFromInstance(InputManager.class)).thenReturn(Im);

    }

    /**
     * Tempt to cut lizard home
     */
    @Test
    public void cutlizardHomeTree() {
        lizardHome.setHealth(10);
        lizardHome.cutlizardHomeTree();
        Assert.assertEquals(lizardHome.getHealth(),9);
    }

}