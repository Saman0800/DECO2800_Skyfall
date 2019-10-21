package deco2800.skyfall.managers.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import deco2800.skyfall.entities.MainCharacter.MainCharacterMemento;
import deco2800.skyfall.entities.SaveableEntity.SaveableEntityMemento;
import deco2800.skyfall.saving.Save;
import deco2800.skyfall.saving.Save.SaveMemento;
import deco2800.skyfall.worlds.biomes.AbstractBiome.AbstractBiomeMemento;
import deco2800.skyfall.worlds.generation.VoronoiEdge.VoronoiEdgeMemento;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode.WorldGenNodeMemento;
import deco2800.skyfall.worlds.world.Chunk.ChunkMemento;
import deco2800.skyfall.worlds.world.World.WorldMemento;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.flywaydb.core.Flyway;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class InsertQueriesTest {

    static DataBaseConnector dataBaseConnector = new DataBaseConnector();
    static Flyway flyway;
    static InsertDataQueries insertDataQueries;

    @BeforeClass
    public static void setupOnce() {
        flyway = new Flyway();
        dataBaseConnector.start("src/test/java/deco2800/skyfall/managers/database/TestDataBase");
        flyway.setDataSource("jdbc:derby:src/test/java/deco2800/skyfall/managers/database/TestDataBase;create=true", "",
                "");
        insertDataQueries = new InsertDataQueries(dataBaseConnector.getConnection());
    }

    @Before
    public void setup() {
        flyway.clean();
        flyway.migrate();
    }

    @AfterClass
    public static void cleanUp() {
        dataBaseConnector.close();
    }

    @Test
    public void InsertSaveTest() {
        Save saveMock = Mockito.mock(Save.class);
        try {
            insertDataQueries.insertSave(saveMock.getSaveID(), saveMock.save());
            int count = 0;
            PreparedStatement getSaves = dataBaseConnector.getConnection().prepareStatement("SELECT * FROM SAVES");
            ResultSet saveResults = getSaves.executeQuery();
            while (saveResults.next()) {
                count++;
            }
            assertEquals(1, count);
        } catch (SQLException | IOException e) {
            fail();
        }
    }

    @Test
    public void InsertWorldTest() {
        try {
            insertSaveData();
            WorldMemento worldMementoMock = Mockito.mock(WorldMemento.class);

            insertDataQueries.insertWorld(0, 0, false, worldMementoMock);
            PreparedStatement getSaves = dataBaseConnector.getConnection().prepareStatement("SELECT * FROM WORLDS");
            ResultSet worldResults = getSaves.executeQuery();

            int count = 0;
            while (worldResults.next()) {
                count++;
            }
            assertEquals(1, count);

        } catch (SQLException | IOException e) {
            fail();
        }
    }

    @Test
    public void insertMainCharacterTest() {

        try {
            insertSaveData();
            MainCharacterMemento mainCharacterMock = Mockito.mock(MainCharacterMemento.class);
            insertDataQueries.insertMainCharacter(0, 0, mainCharacterMock);

            PreparedStatement getMainCharacter = dataBaseConnector.getConnection()
                    .prepareStatement("SELECT * FROM " + "MAIN_CHARACTER");
            ResultSet mainCharacterResults = getMainCharacter.executeQuery();

            int count = 0;
            while (mainCharacterResults.next()) {
                count++;
            }

            assertEquals(1, count);

        } catch (SQLException | IOException e) {
            fail("An error occurred when attempting to insert the main character");
        }
    }

    @Test
    public void insertBiomeTest() {
        try {
            insertSaveData();
            WorldMemento worldMock = Mockito.mock(WorldMemento.class);
            AbstractBiomeMemento biomeMock = Mockito.mock(AbstractBiomeMemento.class);
            insertDataQueries.insertWorld(0, 0, true, worldMock);
            insertDataQueries.insertBiome(0, 0, "test", biomeMock);

            PreparedStatement getMainCharacter = dataBaseConnector.getConnection()
                    .prepareStatement("SELECT * FROM " + "BIOMES");
            ResultSet biomeResults = getMainCharacter.executeQuery();

            int count = 0;
            while (biomeResults.next()) {
                count++;
            }

            assertEquals(1, count);

        } catch (SQLException | IOException e) {
            fail("An error occurred when attempting to insert the a Biome");
        }
    }

    @Test
    public void insertNodeTest() {
        try {
            insertSaveData();
            WorldGenNodeMemento nodeMock = Mockito.mock(WorldGenNodeMemento.class);
            WorldMemento worldMock = Mockito.mock(WorldMemento.class);
            AbstractBiomeMemento biomeMock = Mockito.mock(AbstractBiomeMemento.class);
            insertDataQueries.insertWorld(0, 0, true, worldMock);
            insertDataQueries.insertBiome(0, 0, "test", biomeMock);

            insertDataQueries.insertNodes(0, 0, 0, nodeMock, 0, 0);

            PreparedStatement getMainCharacter = dataBaseConnector.getConnection()
                    .prepareStatement("SELECT * FROM " + "NODES");
            ResultSet nodeResults = getMainCharacter.executeQuery();

            int count = 0;
            while (nodeResults.next()) {
                count++;
            }

            assertEquals(1, count);

        } catch (SQLException | IOException e) {
            fail("An error occurred when attempting to insert a node");
        }
    }

    @Test
    public void insertEdgeTest() {

        try {
            insertSaveData();
            WorldMemento worldMock = Mockito.mock(WorldMemento.class);
            VoronoiEdgeMemento edgeMock = Mockito.mock(VoronoiEdgeMemento.class);
            AbstractBiomeMemento biomeMock = Mockito.mock(AbstractBiomeMemento.class);
            insertDataQueries.insertWorld(0, 0, true, worldMock);
            insertDataQueries.insertBiome(0, 0, "test", biomeMock);

            insertDataQueries.insertEdges(0, 0, 0, edgeMock);

            PreparedStatement getMainCharacter = dataBaseConnector.getConnection()
                    .prepareStatement("SELECT * FROM " + "EDGES");
            ResultSet edgeResults = getMainCharacter.executeQuery();

            int count = 0;
            while (edgeResults.next()) {
                count++;
            }

            assertEquals(1, count);

        } catch (SQLException | IOException e) {
            fail("An error occurred when attempting to insert an edge");
        }
    }

    @Test
    public void insertChunkTest() {
        try {
            insertSaveData();
            WorldMemento worldMock = Mockito.mock(WorldMemento.class);
            ChunkMemento chunkMock = Mockito.mock(ChunkMemento.class);
            insertDataQueries.insertWorld(0, 0, true, worldMock);
            insertDataQueries.insertChunk(0, 0, 0, chunkMock);

            PreparedStatement getMainCharacter = dataBaseConnector.getConnection()
                    .prepareStatement("SELECT * FROM " + "CHUNKS");
            ResultSet chunkResults = getMainCharacter.executeQuery();

            int count = 0;
            while (chunkResults.next()) {
                count++;
            }

            assertEquals(1, count);

        } catch (SQLException | IOException e) {
            fail("An error occurred when attempting to insert a chunk");
        }
    }

    @Test
    public void insertEntityTest() {
        try {
            insertSaveData();
            WorldMemento worldMock = Mockito.mock(WorldMemento.class);
            ChunkMemento chunkMock = Mockito.mock(ChunkMemento.class);
            SaveableEntityMemento entityMock = Mockito.mock(SaveableEntityMemento.class);
            insertDataQueries.insertWorld(0, 0, true, worldMock);
            insertDataQueries.insertChunk(0, 0, 0, chunkMock);
            insertDataQueries.insertEntity("test", 0, 0, 0, entityMock, 0, 0, 0);

            PreparedStatement getMainCharacter = dataBaseConnector.getConnection()
                    .prepareStatement("SELECT * FROM " + "ENTITIES");
            ResultSet chunkResults = getMainCharacter.executeQuery();

            int count = 0;
            while (chunkResults.next()) {
                count++;
            }

            assertEquals(1, count);

        } catch (SQLException | IOException e) {
            fail("An error occurred when attempting to insert an entity");
        }
    }

    public void insertSaveData() throws SQLException, IOException {
        SaveMemento saveMock = Mockito.mock(SaveMemento.class);
        insertDataQueries.insertSave(0, saveMock);
    }

}
