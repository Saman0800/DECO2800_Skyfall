package deco2800.skyfall.entities.pets;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.PlayerPeon;
import deco2800.skyfall.managers.*;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GameManager.class, DatabaseManager.class, PlayerPeon.class })
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
    public void Setup() {
        lizardHome=new LizardHome(0,0,mc);
        mc = new MainCharacter(1f, 1f, 1f, "Main Piece", 2);
        WorldBuilder worldBuilder = new WorldBuilder();
        WorldDirector.constructTestWorld(worldBuilder);
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
        CopyOnWriteArrayList<Tile> tileMap = new CopyOnWriteArrayList<>();
        // generate world with tiles
        Tile tile1 = new Tile(0.0f, 0.0f);
        Tile tile2 = new Tile(0.0f, 1.0f);
        Tile tile3 = new Tile(1.0f, -1f);
        Tile tile4 = new Tile(2f, 1f);
        tileMap.add(tile1);
        tileMap.add(tile2);
        tileMap.add(tile4);
        tileMap.add(tile3);
        w.setTileMap(tileMap);
        lizardHome.setHealth(10);
        lizardHome.cutlizardHomeTree();
        Assert.assertEquals(lizardHome.getHealth(),9);
    }

    /**
     * harvest the lizard home and get lizard
     */
    @Test
    public void harvest() {
        CopyOnWriteArrayList<Tile> tileMap = new CopyOnWriteArrayList<>();
        // Populate world with tiles
        Tile tile1 = new Tile(0.0f, 0.0f);
        Tile tile2 = new Tile(0.0f, 1.0f);
        Tile tile3 = new Tile(1.0f, -1f);
        Tile tile4 = new Tile(2f, 1f);
        tileMap.add(tile1);
        tileMap.add(tile2);
        tileMap.add(tile4);
        tileMap.add(tile3);
        w.setTileMap(tileMap);
        lizardHome.setHealth(0);
        List<AbstractEntity> drops = lizardHome.harvest(tile1);
        Assert.assertEquals(drops.get(0) instanceof Lizard,true);

    }
}