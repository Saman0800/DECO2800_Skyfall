package deco2800.skyfall.entities.enemies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.badlogic.gdx.graphics.OrthographicCamera;

import org.javatuples.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.AgentEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.EnvironmentManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.util.WorldUtil;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.world.Chunk;
import deco2800.skyfall.worlds.world.World;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ World.class, AbstractEntity.class, MainCharacter.class, WorldUtil.class, GameManager.class })
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
        agentEntities.add(new Heavy(0f, 0f, 0.2f));
        agentEntities.add(new Heavy(20f, 20f, 0.1f));
        agentEntities.add(new Heavy(100f, 150f, 0.1f));
        agentEntities.add(new Heavy(150f, 50f, 0.1f));
        when(testWorld.getSortedAgentEntities()).thenReturn(agentEntities);

        testTable = new EnemySpawnTable(50, 10, 10, null, null, null, testWorld);
    }

    @Test
    public void testGetAllEnemies() {
        List<AgentEntity> expectedList = new ArrayList<>();
        expectedList.add(new Heavy(0f, 0f, 0.1f));
        expectedList.add(new Heavy(20f, 20f, 0.1f));
        expectedList.add(new Heavy(100f, 150f, 0.1f));
        expectedList.add(new Heavy(150f, 50f, 0.1f));

        assertEquals("Abstract enemies were not filtered correctly.", expectedList, testTable.getAllEnemies());
    }

    @Test
    public void testInRange() {
        assertTrue(testTable.inRange(dummyEntity1, 0, 0, 50));
        assertFalse(testTable.inRange(dummyEntity2, 0, 0, 50));
    }

    @Test
    public void enemiesInTarget() {
        List<AgentEntity> expectedList = new ArrayList<>();
        expectedList.add(new Heavy(0f, 0f, 0.1f));
        expectedList.add(new Heavy(20f, 20f, 0.1f));

        assertEquals("Incorrect entities filtered.", expectedList, testTable.enemiesInTarget(0f, 0f, 50));
    }

    @Test
    public void testEnemiesNearTargetCount() {
        assertEquals("Incorrect entities filtered.", 2, testTable.enemiesNearTargetCount(0f, 0f));
    }

    @Test
    public void enemiesNearCharacter() {
        List<AgentEntity> expectedList = new ArrayList<>();
        expectedList.add(new Heavy(0f, 0f, 0.1f));
        expectedList.add(new Heavy(20f, 20f, 0.1f));

        assertEquals("Incorrect entities filtered.", expectedList, testTable.enemiesNearCharacter());
    }

    @Mock
    private World testWorldPartiton;

    EnemySpawnTable testSpawnTable;

    @Mock
    private Chunk forestChunk;

    @Mock
    private Chunk desertChunk;

    @Mock
    private AbstractBiome forestBiome;

    @Mock
    private AbstractBiome desertBiome;

    @Mock
    private List<Tile> forestTiles;

    @Mock
    Tile forestTile1;

    @Mock
    Tile forestTile2;

    @Mock
    private List<Tile> desertTiles;

    @Before
    public void createSpawnEnvironment() {

        // GameManager dummyGameManager = mock(GameManager.class);
        // mockStatic(GameManager.class);

        // when(dummyGameManager.getCamera()).thenReturn(null);
        // when(GameManager.get()).thenReturn(dummyGameManager);

        mockStatic(WorldUtil.class);
        float[] dummyReturn = { 0.0f, 0.0f };
        when(WorldUtil.colRowToWorldCords(any(Float.class), any(Float.class))).thenReturn(dummyReturn);
        when(WorldUtil.areCoordinatesOffScreen(any(Float.class), any(Float.class), any(OrthographicCamera.class)))
                .thenReturn(false);
        testWorldPartiton = mock(World.class);

        forestChunk = mock(Chunk.class);
        desertChunk = mock(Chunk.class);

        forestBiome = mock(AbstractBiome.class);
        when(forestBiome.getBiomeName()).thenReturn("forest");

        desertBiome = mock(AbstractBiome.class);
        when(desertBiome.getBiomeName()).thenReturn("desert");

        forestTiles = new ArrayList<>();

        forestTile1 = mock(Tile.class);
        forestTile2 = mock(Tile.class);
        when(forestTile1.getBiome()).thenReturn(forestBiome);
        when(forestTile2.getBiome()).thenReturn(forestBiome);

        when(forestTile1.isObstructed()).thenReturn(false);
        when(forestTile2.isObstructed()).thenReturn(false);

        forestTiles.add(forestTile1);
        forestTiles.add(forestTile2);

        Tile desertTile1 = mock(Tile.class);
        Tile desertTile2 = mock(Tile.class);
        when(desertTile1.getBiome()).thenReturn(desertBiome);
        when(desertTile2.getBiome()).thenReturn(desertBiome);

        desertTiles = new ArrayList<>();
        desertTiles.add(desertTile1);
        desertTiles.add(desertTile2);

        when(forestChunk.getTiles()).thenReturn(forestTiles);
        when(desertChunk.getTiles()).thenReturn(desertTiles);

        // FAKE CHUNKS
        HashMap<Pair<Integer, Integer>, Chunk> testChunks = new HashMap<>();
        testChunks.put(new Pair<Integer, Integer>(0, 0), forestChunk);
        testChunks.put(new Pair<Integer, Integer>(1, 1), desertChunk);

        when(testWorldPartiton.getLoadedChunks()).thenReturn(testChunks);
    }

    @Test
    public void testPartitonTiles() {

        Map<String, List<Tile>> partitionedTiles = EnemySpawnTable.partitonTiles(testWorldPartiton);

        HashMap<String, List<Tile>> expectedPartition = new HashMap<>();
        expectedPartition.put("forest", forestTiles);
        expectedPartition.put("desert", desertTiles);

        assertEquals("Unexpected tile partiton.", expectedPartition, partitionedTiles);
    }

    @Test
    public void testSpawnEnemies() {

        Function<HexVector, ? extends Enemy> spawnAbductor = hexPos -> new Scout(hexPos.getCol(), hexPos.getRow(), 0.7f,
                "Forest");

        Map<String, List<Function<HexVector, ? extends Enemy>>> biomeToConstructor = new HashMap<>();
        List<Function<HexVector, ? extends Enemy>> forestList = new ArrayList<>();
        forestList.add(spawnAbductor);

        biomeToConstructor.put("forest", forestList);

        Function<EnvironmentManager, Double> probAdjFunc = environMang -> 1.0;

        testSpawnTable = new EnemySpawnTable(70, 30, 3, biomeToConstructor, null, probAdjFunc, testWorldPartiton);

        testSpawnTable.notifyTimeUpdate(6);

        // Assert testWorldPartiton called twice
        verify(forestTile1, times(1)).isObstructed();
        verify(forestTile2, times(1)).isObstructed();

    }
}
