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
import javax.swing.text.html.parser.Entity;
import org.flywaydb.core.Flyway;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DatabaseManager.class})
public class DataBaseConnectorTest {

//    private static DataBaseConnector dataBaseConnectorActual = new DataBaseConnector();
    private static DataBaseConnector dataBaseConnectorExpected = new DataBaseConnector();
    private static Flyway flyway;
    private static InsertDataQueries insertDataQueries;

    @BeforeClass
    public static void setupOnce() {
        flyway = new Flyway();
        dataBaseConnectorExpected.start("src/test/java/deco2800/skyfall/managers/database/ExpectedDatabase");
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
//        MainCharacter.getInstance().setCol(0);
//        MainCharacter.getInstance().setRow(0);

        mockStatic(DatabaseManager.class);
        when(DatabaseManager.get().getDataBaseConnector()).thenReturn(dataBaseConnectorExpected);


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

}
