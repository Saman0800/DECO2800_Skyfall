package deco2800.skyfall.managers.database;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.junit.Assert.*;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.worlditems.SnowShrub;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.managers.GameManager;
import deco2800.skyfall.saving.LoadException;
import deco2800.skyfall.saving.Save;
import deco2800.skyfall.saving.Save.SaveMemento;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.biomes.ForestBiome;
import deco2800.skyfall.worlds.world.Chunk;
import deco2800.skyfall.worlds.world.Chunk.ChunkMemento;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.World.WorldMemento;
import deco2800.skyfall.worlds.world.WorldBuilder;
import deco2800.skyfall.worlds.world.WorldDirector;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
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
        PreparedStatement getSaves = null;
        ResultSet saveResults = null;
        try {
            Save save = new Save();
            MainCharacter.resetInstance();
            MainCharacter.getInstance();
            MainCharacter.getInstance().setSave(save);

            World mockedWorld = Mockito.mock(World.class);
            when(mockedWorld.getSeed()).thenReturn(0L);

            save.setCurrentWorld(mockedWorld);

            dbConnector.saveGame(save);

//            ArrayList<Save> saves = (ArrayList<Save>) dbConnector.loadSaveInformation();

            int count = 0;
            getSaves = dbConnector.getConnection().prepareStatement("SELECT * FROM SAVES");
            saveResults = getSaves.executeQuery();
            while (saveResults.next()) {
                count++;
            }
            assertEquals(1, count);

        } catch (SQLException e) {
            fail("Failed to save world due to an exception occurring : " + e);
        } finally {
            try {
                getSaves.close();
                saveResults.close();
            } catch (SQLException e) {
            }
        }

    }

    @Test
    public void updateSaveTest(){
        ResultSet saveResults = null;
        PreparedStatement getSaves = null;
        try {
            Save save = new Save();

            MainCharacter.resetInstance();
            MainCharacter.getInstance();
            MainCharacter.getInstance().setSave(save);

            World mockedWorld = Mockito.mock(World.class);
            when(mockedWorld.getSeed()).thenReturn(0L);

            save.setCurrentWorld(mockedWorld);

            dbConnector.saveGame(save);
            dbConnector.saveGame(save);

//            ArrayList<Save> saves = (ArrayList<Save>) dbConnector.loadSaveInformation();

            int count = 0;
            getSaves = dbConnector.getConnection().prepareStatement("SELECT * FROM SAVES");
            saveResults = getSaves.executeQuery();
            while (saveResults.next()) {
                count++;
            }
            assertEquals(1, count);

        } catch (SQLException e) {
            fail("Failed to update the save due to an exception occurring : " + e);
        } finally {
            try {
                saveResults.close();
                getSaves.close();
            } catch (SQLException e) {
            }
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
            assertEquals(23, world.getBiomes().size());
            assertEquals(144, world.getWorldGenNodes().size());
            assertEquals(21, world.getBeachEdges().size());
            assertEquals(0, world.getRiverEdges().size());
            assertEquals(-4962768465676381896L, world.getTileOffsetNoiseGeneratorX().getSeed());
            assertEquals(4437113781045784766L, world.getTileOffsetNoiseGeneratorY().getSeed());

        } catch (SQLException | IOException  e) {
            fail("Failed to save world : " + e);
        }
    }


    @Test
    public void updateWorldTest(){
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
            dbConnector.saveWorld(world);

            SaveMemento saveMemMock = Mockito.mock(SaveMemento.class);
            Save saveMock = Mockito.mock(Save.class);
            when(saveMock.getSaveID()).thenReturn(saveId);
            when(saveMemMock.getWorldID()).thenReturn(worldId);
            World worldLoaded = dbConnector.loadWorlds(saveMock, saveMemMock);

            assertEquals(30, worldLoaded.getWorldParameters().getWorldSize());
            assertEquals(5, worldLoaded.getWorldParameters().getNodeSpacing());
            assertEquals(23, world.getBiomes().size());
            assertEquals(144, world.getWorldGenNodes().size());
            assertEquals(21, world.getBeachEdges().size());
            assertEquals(0, world.getRiverEdges().size());
            assertEquals(-4962768465676381896L, world.getTileOffsetNoiseGeneratorX().getSeed());
            assertEquals(4437113781045784766L, world.getTileOffsetNoiseGeneratorY().getSeed());

        } catch (SQLException | IOException  e) {
            fail("Failed to update the world : " + e);
        }
    }

    @Test
    public void saveChunkTest() {
        ResultSet chunkResult = null;
        PreparedStatement getChunk = null;
        try {

            World worldMock = Mockito.mock(World.class);
            when(worldMock.getID()).thenReturn(0L);

            Save saveMock = Mockito.mock(Save.class);
            when(saveMock.getCurrentWorld()).thenReturn(worldMock);
            when(saveMock.getSaveID()).thenReturn(0L);

            SaveMemento saveMementoMock = Mockito.mock(SaveMemento.class);
            WorldMemento worldMementoMock = Mockito.mock(WorldMemento.class);

            InsertDataQueries insertDataQueries = new InsertDataQueries(dbConnector.getConnection());

            insertDataQueries.insertSave(0, saveMementoMock);
            insertDataQueries.insertWorld(0, 0, true, worldMementoMock);

            ArrayList<Tile> tiles = new ArrayList<>();
            ArrayList<AbstractEntity> entities = new ArrayList<>();
            Chunk chunk = new Chunk(worldMock, 3, 4, tiles, entities);
            chunk.addEntity(new SnowShrub());
            chunk.generateEntities();

            dbConnector.saveChunk(chunk);

            getChunk = dbConnector.getConnection().prepareStatement("SELECT * FROM CHUNKS");
            chunkResult = getChunk.executeQuery();

            chunkResult.next();

            long worldId = chunkResult.getLong("world_id");
            int xCoord = chunkResult.getInt("x");
            int yCoord = chunkResult.getInt("y");


            byte[] buffer = chunkResult.getBytes("data");
            ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
            ChunkMemento chunkMemento = (ChunkMemento) objectIn.readObject();

            Field xField = chunkMemento.getClass().getDeclaredField("x");
            xField.setAccessible(true);
            Field yField = chunkMemento.getClass().getDeclaredField("y");
            yField.setAccessible(true);

            assertEquals("The chunk memento x field does not match the expected value ",3,
                (int) xField.get(chunkMemento));
            assertEquals("The chunk memento y field does not match the expected value",4,
                (int) yField.get(chunkMemento));

            assertEquals(0, worldId);
            assertEquals(3, xCoord);
            assertEquals(4, yCoord);


        } catch (SQLException | IOException | ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to insert the chunk : " + e);
        } finally{
            try {
                assert chunkResult != null;
                getChunk.close();
                chunkResult.close();
            } catch (SQLException e) {
            }
        }
    }

    @Test
    public void updateChunkTest(){
        ResultSet chunkResult = null;
        PreparedStatement getChunk = null;
        try {

            World worldMock = Mockito.mock(World.class);
            when(worldMock.getID()).thenReturn(0L);

            Save saveMock = Mockito.mock(Save.class);
            when(saveMock.getCurrentWorld()).thenReturn(worldMock);
            when(saveMock.getSaveID()).thenReturn(0L);

            SaveMemento saveMementoMock = Mockito.mock(SaveMemento.class);
            WorldMemento worldMementoMock = Mockito.mock(WorldMemento.class);

            InsertDataQueries insertDataQueries = new InsertDataQueries(dbConnector.getConnection());

            insertDataQueries.insertSave(0, saveMementoMock);
            insertDataQueries.insertWorld(0, 0, true, worldMementoMock);

            ArrayList<Tile> tiles = new ArrayList<>();
            ArrayList<AbstractEntity> entities = new ArrayList<>();
            Chunk chunk = new Chunk(worldMock, 3, 4, tiles, entities);
            chunk.addEntity(new SnowShrub());
            chunk.generateEntities();

            //Inserting the chunk for the first time
            dbConnector.saveChunk(chunk);
            //Updated the chunk
            dbConnector.saveChunk(chunk);

            getChunk = dbConnector.getConnection().prepareStatement("SELECT * FROM CHUNKS");
            chunkResult = getChunk.executeQuery();

            chunkResult.next();

            long worldId = chunkResult.getLong("world_id");
            int xCoord = chunkResult.getInt("x");
            int yCoord = chunkResult.getInt("y");


            byte[] buffer = chunkResult.getBytes("data");
            ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
            ChunkMemento chunkMemento = (ChunkMemento) objectIn.readObject();

            Field xField = chunkMemento.getClass().getDeclaredField("x");
            xField.setAccessible(true);
            Field yField = chunkMemento.getClass().getDeclaredField("y");
            yField.setAccessible(true);

            assertEquals("The chunk memento x field does not match the expected value ",3,
                (int) xField.get(chunkMemento));
            assertEquals("The chunk memento y field does not match the expected value",4,
                (int) yField.get(chunkMemento));

            assertEquals(0, worldId);
            assertEquals(3, xCoord);
            assertEquals(4, yCoord);

        } catch (SQLException | IOException | ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to update the chunk : " + e);
        } finally{
            try {
                assert chunkResult != null;
                getChunk.close();
                chunkResult.close();
            } catch (SQLException e) {
            }
        }
    }

    @Test
    public void saveMainCharacterTest() {
        PreparedStatement getSaves = null;
        ResultSet saveResults = null;
        try {
            Save save = Mockito.mock(Save.class);
            when(save.getSaveID()).thenReturn(0L);
            MainCharacter.resetInstance();
            MainCharacter.getInstance();
            MainCharacter.getInstance().setSave(save);

            SaveMemento saveMementoMock = Mockito.mock(SaveMemento.class);
            InsertDataQueries insertDataQueries = new InsertDataQueries(dbConnector.getConnection());

            insertDataQueries.insertSave(0, saveMementoMock);

            dbConnector.saveMainCharacter();

            int count = 0;
            getSaves = dbConnector.getConnection().prepareStatement("SELECT * FROM MAIN_CHARACTER");
            saveResults = getSaves.executeQuery();
            while (saveResults.next()) {
                count++;
            }
            assertEquals(1, count);

        } catch (SQLException | IOException e) {
            fail("Failed to the main character due to an exception occurring : " + e);
        } finally {
            try {
                getSaves.close();
                saveResults.close();
            } catch (SQLException e) {
            }
        }

    }

}
