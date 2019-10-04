package deco2800.skyfall.managers.database;

import static deco2800.skyfall.managers.database.InsertQueriesTest.flyway;
import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import deco2800.skyfall.managers.DatabaseManager;
import org.flywaydb.core.Flyway;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DatabaseManager.class})
@PowerMockIgnore({"java.sql.*", "org.apache.*"})
public class DatabaseConnectorSavingTest {

    private static DataBaseConnector dataBaseConnectorExpected = new DataBaseConnector();
    private static Flyway flyway;
    private static InsertDataQueries insertDataQueries;

    @BeforeClass
    public static void setupOnce() {
        dataBaseConnectorExpected.start("src/test/java/deco2800/skyfall/managers/database/ExpectedDatabase");
        DatabaseManager dm = mock(DatabaseManager.class);
        when(dm.getDataBaseConnector()).thenReturn(dataBaseConnectorExpected);

        mockStatic(DatabaseManager.class);
        when(DatabaseManager.get()).thenReturn(dm);

//        MainCharacter mc = mock(MainCharacter.class);
//        when(mc.getCol()).thenReturn((float) 0);
//        when(mc.getRow()).thenReturn((float) 0);
//
//        mockStatic(MainCharacter.class);
//        when(MainCharacter.getInstance()).thenReturn(mc);
    }

    @Before
    public void setup() {
        flyway.clean();
        flyway.migrate();
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

}
