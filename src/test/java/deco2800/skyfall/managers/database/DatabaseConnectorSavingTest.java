package deco2800.skyfall.managers.database;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.junit.Assert.*;

import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.saving.LoadException;
import deco2800.skyfall.saving.Save;
import deco2800.skyfall.saving.Save.SaveMemento;
import deco2800.skyfall.worlds.biomes.ForestBiome;
import deco2800.skyfall.worlds.world.Chunk;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.flywaydb.core.Flyway;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

public class DatabaseConnectorSavingTest {

    private static DataBaseConnector dbConnector = new DataBaseConnector();
    private static Flyway flyway;

    @BeforeClass
    public static void setupOnce() {
        flyway = new Flyway();
        dbConnector.start("src/test/java/deco2800/skyfall/managers/database/SavingTestDataBase");
        flyway.setDataSource("jdbc:derby:src/test/java/deco2800/skyfall/managers/database/SavingTestDataBase;"
            + "create=true", "", "");
    }

    @Before
    public void setup() {
        flyway.clean();
        flyway.migrate();
    }

    @AfterClass
    public static void cleanUp() {
        dbConnector.close();
    }

    @Test
    public void saveGameTest() {
        try {
            Save save = new Save();

            World mockedWorld = Mockito.mock(World.class);
            when(mockedWorld.getSeed()).thenReturn(0L);

            save.setCurrentWorld(mockedWorld);

            dbConnector.saveGame(save);

            ArrayList<Save> saves = (ArrayList<Save>) dbConnector.loadSaveInformation();

            int count = 0;
            PreparedStatement getSaves = dbConnector.getConnection().prepareStatement("SELECT * FROM SAVES");
            ResultSet saveResults = getSaves.executeQuery();
            while (saveResults.next()){
                count++;
            }
            assertEquals(1, count);

        } catch (SQLException e) {
            fail("Failed to save world due to an exception occurring : " + e);
        }

    }

    @Test
    public void saveWorldTest() {
        try {
            InsertDataQueries insertDataQueries = new InsertDataQueries(dbConnector.getConnection());

            WorldBuilder worldBuilder = new WorldBuilder();
            WorldDirector.constructTestWorld(worldBuilder);
            World world = worldBuilder.getWorld();

            Save save = new Save();

            save.addWorld(world);
            save.setCurrentWorld(world);
            world.setSave(save);

            insertDataQueries.insertSave(save.getSaveID(), save.save());

            long worldId = world.getID();
            long saveId = save.getSaveID();

            dbConnector.saveWorld(world);

            SaveMemento saveMemMock = Mockito.mock(SaveMemento.class);
            Save saveMock = Mockito.mock(Save.class);
            when(saveMock.getSaveID()).thenReturn(saveId);
            when(saveMemMock.getWorldID()).thenReturn(worldId);
            World worldLoaded = dbConnector.loadWorlds(saveMock, saveMemMock);


            assertEquals(30, worldLoaded.getWorldParameters().getWorldSize());
            assertEquals(5, worldLoaded.getWorldParameters().getNodeSpacing());
            assertEquals(23,world.getBiomes().size());
            assertEquals(144, world.getWorldGenNodes().size());
            assertEquals(21, world.getBeachEdges().size());
            assertEquals(0, world.getRiverEdges().size());
            assertEquals(-4962768465676381896L, world.getTileOffsetNoiseGeneratorX().getSeed());
            assertEquals(4437113781045784766L, world.getTileOffsetNoiseGeneratorY().getSeed());

        } catch (SQLException | IOException | LoadException  e) {
            fail("Failed to save world : " + e);
        }
    }

    //FIXME:jeffvan12 Finish this test
    @Test
    @Ignore
    public void saveChunkTest() {
        Save saveMock = Mockito.mock(Save.class);
        when(saveMock.getCurrentWorld().getID()).thenReturn(0L);

    }

}
