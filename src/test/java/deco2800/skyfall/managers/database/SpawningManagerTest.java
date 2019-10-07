package deco2800.skyfall.managers.database;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.enemies.Heavy;
import deco2800.skyfall.managers.AnimationManager;
import deco2800.skyfall.managers.EnvironmentManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.SpawningManager;
import deco2800.skyfall.worlds.world.World;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//Currently this test is broken
//Will need to be redesigned with better mocking
//will be done next sprint
@Ignore
public class SpawningManagerTest {
    GameManager gm = GameManager.get();
    EnvironmentManager em;

    World mockWorld = mock(World.class);
    MainCharacter mockPlayer = MainCharacter.getInstance();

    SpawningManager sm = null;

    @Before
    public void initialize() {
        try {
            SpawningManager.createSpawningManager();
        }
        catch  (ExceptionInInitializerError e) {
            //initialize will be called on every test, if there
            //already exists a SpawningManger, supress error
            return;
        }

        gm.setWorld(mockWorld);
        try {
            em = new EnvironmentManager();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        em.setTime(20, 0);
        gm.addManager(em);
        sm = gm.getManager(SpawningManager.class);
        when(mockPlayer.getRow()).thenReturn(0.0f);
        when(mockPlayer.getCol()).thenReturn(0.0f);
    }

    @Test
    public void testFactory() {
        assertTrue(sm != null);
    }

    @Test
    public void testAddingEntities() {
        assertTrue(sm.getEntCountInSpawnTable() == 0);
        sm.addEnemyForSpawning(new Heavy(3,2f, 0.7f, "Forest","enemyHeavy"), 1.0f);
        assertTrue(sm.getEntCountInSpawnTable() == 1);
    }

    @Test
    public void testClearingDeadEntities() {
        Heavy h = new Heavy(3,2f, 0.7f, "Forest","enemyHeavy");
        assertTrue(sm.getNumberOfEntsManaged() == 1);
        when(h.isDead()).thenReturn(true);
        sm.addEnemyForSpawning(h, 1.0f);
        sm.onTick(0);
        assertTrue(sm.getNumberOfEntsManaged() == 1);
        when(h.isDead()).thenReturn(false);
        sm.onTick(0);
        assertTrue(sm.getNumberOfEntsManaged() == 0);
    }

    @Test
    public void testCullingEntities() {
        assertTrue(sm.getEntCountInSpawnTable() == 0);
        Heavy h = new Heavy(3,2f, 0.7f, "Forest","enemyHeavy");
        sm.addEnemyForSpawning(h, 1.0f);
        sm.onTick(0);
        assertTrue(sm.getNumberOfEntsManaged() == 1);
        when(mockPlayer.getRow()).thenReturn(2*sm.getCullingDistance());
        when(mockPlayer.getCol()).thenReturn(2*sm.getCullingDistance());
        sm.onTick(0);
        assertTrue(sm.getNumberOfEntsManaged() == 0);
        assertTrue(sm.getEntCountInSpawnTable() == 1);
    }

    @Test
    public void testGettingEnt() {
        Heavy h = new Heavy(3,2f, 0.7f, "Forest","enemyHeavy");
        sm.addEnemyForSpawning(h, 1.0f);
        sm.onTick(0);
        Heavy h2 = sm.getFirstEnemy(Heavy.class);
        assertEquals(h, h2);
    }
}
