package deco2800.skyfall.managers.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import com.badlogic.gdx.graphics.g3d.model.data.ModelNodeKeyframe;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.PhysicsManager;
import deco2800.skyfall.saving.AbstractMemento;
import deco2800.skyfall.saving.LoadException;
import deco2800.skyfall.saving.Save;
import deco2800.skyfall.saving.Save.SaveMemento;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.biomes.BeachBiome;
import deco2800.skyfall.worlds.biomes.ForestBiome;
import deco2800.skyfall.worlds.biomes.RiverBiome;
import deco2800.skyfall.worlds.generation.VoronoiEdge;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode;
import deco2800.skyfall.worlds.world.Chunk;
import deco2800.skyfall.worlds.world.World;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.swing.text.html.parser.Entity;
import javax.xml.crypto.Data;
import org.flywaydb.core.Flyway;
import org.javatuples.Pair;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DatabaseManager.class})
@PowerMockIgnore({"java.sql.*", "org.apache.*"})
public class DataBaseConnectorTest {

    //    private static DataBaseConnector dataBaseConnectorActual = new DataBaseConnector();
    private static DataBaseConnector dataBaseConnectorExpected = new DataBaseConnector();
    private static Flyway flyway;
    private static InsertDataQueries insertDataQueries;

    @BeforeClass
    public static void setupOnce() {
        flyway = new Flyway();
        dataBaseConnectorExpected.start("src/test/java/deco2800/skyfall/managers/database/ExpectedDatabase");
        DatabaseManager dm = mock(DatabaseManager.class);
        when(dm.getDataBaseConnector()).thenReturn(dataBaseConnectorExpected);

        mockStatic(DatabaseManager.class);
        when(DatabaseManager.get()).thenReturn(dm);
    }

    @Before
    public void setup() {
//        flyway.clean();
//        flyway.migrate();
    }

    @AfterClass
    public static void cleanUp() {
        dataBaseConnectorExpected.close();
    }


    @Test
    public void saveGameTest() {

    }

    @Test
    public void saveWorldTest() {

    }

    @Test
    public void saveChunkTest() {

    }

    @Test
    public void loadGameTest() {
        Save save = dataBaseConnectorExpected.loadGame();
        assertEquals(13024847749615L, save.getSaveID());
        assertEquals(1, save.getWorlds().size());
        assertEquals(7, save.getWorlds().get(0).getEntities().size());
        assertEquals(13026308866574L, save.getWorlds().get(0).getID());
        assertEquals(576, save.getWorlds().get(0).getWorldGenNodes().size());
        assertEquals(85, save.getWorlds().get(0).getBeachEdges().size());
        assertEquals(6, save.getWorlds().get(0).getRiverEdges().size());
    }

    @Test
    public void loadWorldsTest() {
        try {
            SaveMemento saveMemMock = Mockito.mock(SaveMemento.class);
            Save saveMock = Mockito.mock(Save.class);
            when(saveMock.getSaveID()).thenReturn(13024847749615L);
            when(saveMemMock.getWorldID()).thenReturn(13026308866574L);
            World world = dataBaseConnectorExpected.loadWorlds(saveMock, saveMemMock);
            assertEquals(13026308866574L, world.getID());
            assertEquals(576, world.getWorldGenNodes().size());
            assertEquals(85, world.getBeachEdges().size());
            assertEquals(6, world.getRiverEdges().size());
        } catch (SQLException | LoadException e) {
            fail("Failed to load the worlds due to an exception occuring");
        }
    }

    @Test
    public void loadBiomesTest() {
        try {
            World worldMock = Mockito.mock(World.class);
            when(worldMock.getID()).thenReturn(13026308866574L);
            ArrayList<AbstractBiome> biomes = (ArrayList<AbstractBiome>) dataBaseConnectorExpected
                .loadBiomes(worldMock);
            assertEquals(98, biomes.size());
        } catch (SQLException | LoadException e) {
            fail("Failed to load the biomes due to an exception occuring");
        }
    }

    @Test
    public void loadNodesTest() {
        try {
            World worldMock = Mockito.mock(World.class);
            when(worldMock.getID()).thenReturn(13026308866574L);
            ArrayList<AbstractBiome> biomes = (ArrayList<AbstractBiome>) dataBaseConnectorExpected
                .loadBiomes(worldMock);

            ArrayList<WorldGenNode> nodes = (ArrayList<WorldGenNode>) dataBaseConnectorExpected
                .loadNodes(worldMock, biomes);

            int countNodesInForst = 0;
            int nodesInSpecificId = 0;

            for (WorldGenNode node : nodes){
                if (node.getBiome() instanceof ForestBiome){
                    countNodesInForst++;
                }
                if (node.getBiome().getBiomeID() == 13026277283086L){
                    nodesInSpecificId++;
                }
            }

            assertEquals(22, countNodesInForst);
            assertEquals(24, nodesInSpecificId);

        } catch (LoadException | SQLException e) {
            fail("Failed to load the nodes due to an exception occuring");
        }
    }

    @Test
    public void loadBeachEdgesTest() {

        try {
            World worldMock = Mockito.mock(World.class);
            when(worldMock.getID()).thenReturn(13026308866574L);
            ArrayList<AbstractBiome> biomes = (ArrayList<AbstractBiome>) dataBaseConnectorExpected
                .loadBiomes(worldMock);

            LinkedHashMap<VoronoiEdge, BeachBiome> edges = dataBaseConnectorExpected.loadBeachEdges(worldMock, biomes);

            assertEquals(85, edges.size());

        } catch (SQLException | LoadException e) {
            fail("Failed to load the beach edges due to an exception occuring");
        }

    }

    @Test
    public void loadRiverEdgesTest() {
        try {
            World worldMock = Mockito.mock(World.class);
            when(worldMock.getID()).thenReturn(13026308866574L);
            ArrayList<AbstractBiome> biomes = (ArrayList<AbstractBiome>) dataBaseConnectorExpected
                .loadBiomes(worldMock);

            LinkedHashMap<VoronoiEdge, RiverBiome> edges = dataBaseConnectorExpected.loadRiverEdges(worldMock, biomes);

            assertEquals(6, edges.size());

        } catch (SQLException | LoadException e) {
            fail("Failed to load the beach edges due to an exception occuring");
        }
    }


    @Test
    public void loadChunkTest() {

        World worldMock = Mockito.mock(World.class);
        HashMap<Pair<Integer, Integer>, Chunk> loadedChunks = new HashMap<>();
        when(worldMock.getID()).thenReturn(13026308866574L);
        when(worldMock.getLoadedChunks()).thenReturn(loadedChunks);

        Chunk chunk = dataBaseConnectorExpected.loadChunk(worldMock, 0,0);


//        assertEquals(5, chunk.getEntities().size());




    }

    @Test
    public void createEntityFromMementoTest(){


    }


    @Test
    public void loadSaveInformationTest() {
    }


}
