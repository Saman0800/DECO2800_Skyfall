package deco2800.skyfall.entities.enemies;

import deco2800.skyfall.entities.AgentEntity;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.PlayerPeon;
import deco2800.skyfall.entities.MainCharacter;

import deco2800.skyfall.worlds.world.World;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ World.class, AbstractEntity.class, MainCharacter.class })
public class EnemySpawnTableTest {

    @Mock
    private World testWorld;

    @Mock
    private AbstractEntity dummyEntity1;

    @Mock
    private AbstractEntity dummyEntity2;

    @Mock
    private MainCharacter dummyCharacter;

    EnemySpawnTable testTable;

    @Before
    public void createTestEnvironment() {

        dummyCharacter = mock(MainCharacter.class);
        when(dummyCharacter.getRow()).thenReturn(0f);
        when(dummyCharacter.getCol()).thenReturn(0f);

        mockStatic(MainCharacter.class);
        when(MainCharacter.getInstance(0, 0, 0.05f, "Main Piece", 10)).thenReturn(dummyCharacter);

        dummyEntity1 = mock(AbstractEntity.class);
        when(dummyEntity1.getRow()).thenReturn(0f);
        when(dummyEntity1.getCol()).thenReturn(0f);

        dummyEntity2 = mock(AbstractEntity.class);
        when(dummyEntity2.getRow()).thenReturn(100f);
        when(dummyEntity2.getCol()).thenReturn(100f);

        testWorld = mock(World.class);
        List<AgentEntity> agentEntities = new ArrayList<>();
        agentEntities.add(new Scout(-4f, -1f, 0.1f, "Forest", Enemy.EnemyType.SCOUT));
        agentEntities.add(new Scout(-4f, -1f, 0.1f, "Forest", Enemy.EnemyType.SCOUT));
        agentEntities.add(new Scout(-4f, -1f, 0.1f, "Forest", Enemy.EnemyType.SCOUT));
        agentEntities.add(new Scout(-4f, -1f, 0.1f, "Forest", Enemy.EnemyType.SCOUT));
        agentEntities.add(new PlayerPeon(10f, 10f, 2));
        when(testWorld.getSortedAgentEntities()).thenReturn(agentEntities);

        testTable = new EnemySpawnTable(50, 10, 10, null, null, testWorld);
    }

    @Test
    public void testGetAllEnemies() {
        List<AgentEntity> expectedList = new ArrayList<>();
        expectedList.add(new Heavy(-4f, -1f, 0.2f, "Forest", Enemy.EnemyType.HEAVY));
        expectedList.add(new Heavy(-4f, -1f, 0.2f, "Forest", Enemy.EnemyType.HEAVY));
        expectedList.add(new Heavy(-4f, -1f, 0.2f, "Forest", Enemy.EnemyType.HEAVY));
        expectedList.add(new Heavy(-4f, -1f, 0.2f, "Forest", Enemy.EnemyType.HEAVY));

        assertEquals("Enemies were not filtered correctly.", expectedList, testTable.getAllEnemies());
    }

    @Test
    public void testInRange() {
        assertTrue(testTable.inRange(dummyEntity1, 0, 0, 50));
        assertFalse(testTable.inRange(dummyEntity2, 0, 0, 50));
    }

    @Test
    public void enemiesInTarget() {
        List<AgentEntity> expectedList = new ArrayList<>();
        expectedList.add(new Scout(-4f, -1f, 0.2f, "Forest", Enemy.EnemyType.SCOUT));
        expectedList.add(new Scout(-4f, -1f, 0.2f, "Forest", Enemy.EnemyType.SCOUT));

        assertEquals("Incorrect entities filtered.", expectedList, testTable.enemiesInTarget(0f, 0f, 50));
    }

    @Test
    public void testEnemiesNearTargetCount() {
        assertEquals("Incorrect entities filtered.", 2, testTable.enemiesNearTargetCount(0f, 0f));
    }

    @Test
    public void enemiesNearCharacter() {
        List<AgentEntity> expectedList = new ArrayList<>();
        expectedList.add(new Scout(-4f, -1f, 0.1f, "Forest", Enemy.EnemyType.SCOUT));
        expectedList.add(new Scout(-4f, -1f, 0.1f, "Forest", Enemy.EnemyType.SCOUT));

        assertEquals("Incorrect entities filtered.", expectedList, testTable.enemiesNearCharacter());
    }
}
