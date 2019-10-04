package deco2800.skyfall.managers.database;

import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.managers.PhysicsManager;
import deco2800.skyfall.saving.AbstractMemento;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.flywaydb.core.Flyway;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GameManager.class})
public class DataBaseConnectorTest {

//    private static DataBaseConnector dataBaseConnectorActual = new DataBaseConnector();
    private static DataBaseConnector dataBaseConnectorExpected = new DataBaseConnector();
    private static Flyway flyway;
    private static InsertDataQueries insertDataQueries;

    @BeforeClass
    public static void setupOnce() {
        flyway = new Flyway();
        dataBaseConnectorExpected.start("src/test/java/deco2800/skyfall/managers/database/ExpectedDatabase");
//        dataBaseConnectorActual.start("src/test/java/deco2800/skyfall/managers/database/TestDataBase");
//        flyway.setDataSource("jdbc:derby:src/test/java/deco2800/skyfall/managers/database/TestDataBase;create=true", "",
//            "");
//        insertDataQueries = new InsertDataQueries(dataBaseConnectorActual.getConnection());
    }

    @Before
    public void setup() {
//        flyway.clean();
//        flyway.migrate();
    }

    @AfterClass
    public static void cleanUp() {
//        dataBaseConnectorActual.close();
//        dataBaseConnectorExpected.close();
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
        MainCharacter.getInstance().setCol(0);
        MainCharacter.getInstance().setRow(0);


        DatabaseManager dbm = mock(DatabaseManager.class);
        when(dbm.getDataBaseConnector()).thenReturn(dataBaseConnectorExpected);

        mockStatic(DatabaseManager.class);
        when(DatabaseManager.get()).thenReturn(dbm);


        try {
            PreparedStatement getSaves = DatabaseManager.get().getDataBaseConnector().getConnection().prepareStatement("SELECT * FROM "
                + "NODES");
            ResultSet worldResults = getSaves.executeQuery();

            int count = 0;
            while (worldResults.next()){
                count++;
            }
            System.out.println(count);

        } catch (SQLException e) {
            fail();
        }


//        Save save = dataBaseConnectorExpected.loadGame();
//        System.out.println(save.getSaveID());
    }

    @Test
    public void loadWorldsTest() {

    }

    @Test
    public void loadBiomesTest() {

    }

    @Test
    public void loadNodesTest() {

    }

    @Test
    public void loadBeachEdgesTest() {

    }

    @Test
    public void loadRiverEdgesTest() {

    }


    @Test
    public void loadChunkTest() {

    }


    @Test
    public void loadSaveInformationTest() {
    }


    public void populateDatabase() throws SQLException, IOException, ClassNotFoundException {





//        //Adding saves to database
//        SaveMemento saveMock = Mockito.mock(SaveMemento.class);
//        insertDataQueries.insertSave(0, saveMock);
//
//        //Adding worlds to database
//        WorldMemento worldMock = Mockito.mock(WorldMemento.class);
//        insertDataQueries.insertWorld(0, 0, true, worldMock);
//
//        //Adding chunks to the worlds
//        ChunkMemento chunkMock = Mockito.mock(ChunkMemento.class);
//        insertDataQueries.insertChunk(0, 0, 0, chunkMock);
//
//        //Adding edges to the world
//        VoronoiEdgeMemento edgeMock = Mockito.mock(VoronoiEdgeMemento.class);
//        insertDataQueries.insertEdges(0, 0, 0, edgeMock);
//
//        //Adding
//        SaveableEntityMemento entityMock = Mockito.mock(SaveableEntityMemento.class);
//        insertDataQueries.insertEntity("test", 0, 0, 0, 0, 0, entityMock, 0);
//
//        //FIXME:jeffvan12 implement main character testing, loading and saving the main character methods
//        // in DatabaseConnector should be finished first
//
    }



    public AbstractMemento loadMementoFromBytes(byte[] buffer) throws IOException, ClassNotFoundException {
        ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
        return (AbstractMemento) objectIn.readObject();
    }
}
