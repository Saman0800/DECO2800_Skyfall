package deco2800.skyfall.managers.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import com.badlogic.gdx.graphics.g3d.model.data.ModelNodeKeyframe;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.SaveableEntity.SaveableEntityMemento;
import deco2800.skyfall.entities.worlditems.Bone;
import deco2800.skyfall.entities.worlditems.DesertCacti;
import deco2800.skyfall.entities.worlditems.DesertRock;
import deco2800.skyfall.entities.worlditems.DesertShrub;
import deco2800.skyfall.entities.worlditems.ForestMushroom;
import deco2800.skyfall.entities.worlditems.ForestRock;
import deco2800.skyfall.entities.worlditems.ForestShrub;
import deco2800.skyfall.entities.worlditems.ForestTree;
import deco2800.skyfall.entities.worlditems.Leaves;
import deco2800.skyfall.entities.worlditems.MountainRock;
import deco2800.skyfall.entities.worlditems.MountainTree;
import deco2800.skyfall.entities.worlditems.OrganicMound;
import deco2800.skyfall.entities.worlditems.SnowClump;
import deco2800.skyfall.entities.worlditems.SnowShrub;
import deco2800.skyfall.entities.worlditems.SwampRock;
import deco2800.skyfall.entities.worlditems.SwampShrub;
import deco2800.skyfall.entities.worlditems.SwampTree;
import deco2800.skyfall.entities.worlditems.TreeStump;
import deco2800.skyfall.entities.worlditems.VolcanicRock;
import deco2800.skyfall.entities.worlditems.VolcanicShrub;
import deco2800.skyfall.entities.worlditems.VolcanicTree;
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
import org.junit.Ignore;
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
public class DataBaseConnectorLoadingTest {

    //    private static DataBaseConnector dataBaseConnectorActual = new DataBaseConnector();
    private static DataBaseConnector dataBaseConnectorExpected = new DataBaseConnector();
    private static InsertDataQueries insertDataQueries;

    private static final long WOLRD_ID =  19471190335142L;
    private static final long SAVE_ID = 19470155121187L;

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

    @AfterClass
    public static void cleanUp(){
        dataBaseConnectorExpected.close();
    }

    @Test
    public void loadWorldsTest() {
        try {
            SaveMemento saveMemMock = Mockito.mock(SaveMemento.class);
            Save saveMock = Mockito.mock(Save.class);
            when(saveMock.getSaveID()).thenReturn(SAVE_ID);
            when(saveMemMock.getWorldID()).thenReturn(WOLRD_ID);
            World world = dataBaseConnectorExpected.loadWorlds(saveMock, saveMemMock);
            assertEquals(WOLRD_ID, world.getID());
            assertEquals(576, world.getWorldGenNodes().size());
            assertEquals(85, world.getBeachEdges().size());
            assertEquals(6, world.getRiverEdges().size());
        } catch (SQLException | LoadException e) {
            fail("Failed to load the worlds due to an exception occuring");
        }
    }


    //FIXME:jeffvan12 not entirely sure how to fix this
    @Test
    public void loadGameTest() {
        Save save = dataBaseConnectorExpected.loadGame();
        assertEquals(SAVE_ID, save.getSaveID());
        assertEquals(1, save.getWorlds().size());
        assertEquals(0, save.getWorlds().get(0).getEntities().size());
        assertEquals(WOLRD_ID, save.getWorlds().get(0).getID());
        assertEquals(576, save.getWorlds().get(0).getWorldGenNodes().size());
        assertEquals(85, save.getWorlds().get(0).getBeachEdges().size());
        assertEquals(6, save.getWorlds().get(0).getRiverEdges().size());
    }


    @Test
    public void loadBiomesTest() {
        try {
            World worldMock = Mockito.mock(World.class);
            when(worldMock.getID()).thenReturn(WOLRD_ID);
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
            when(worldMock.getID()).thenReturn(WOLRD_ID);
            ArrayList<AbstractBiome> biomes = (ArrayList<AbstractBiome>) dataBaseConnectorExpected
                .loadBiomes(worldMock);

            ArrayList<WorldGenNode> nodes = (ArrayList<WorldGenNode>) dataBaseConnectorExpected
                .loadNodes(worldMock, biomes);

            int countNodesInForst = 0;
            int nodesInSpecificId = 0;

            for (WorldGenNode node : nodes) {
                if (node.getBiome() instanceof ForestBiome) {
                    countNodesInForst++;
                }
            }

            assertEquals(22, countNodesInForst);

        } catch (LoadException | SQLException e) {
            fail("Failed to load the nodes due to an exception occuring");
        }
    }

    @Test
    public void loadBeachEdgesTest() {

        try {
            World worldMock = Mockito.mock(World.class);
            when(worldMock.getID()).thenReturn(WOLRD_ID);
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
            when(worldMock.getID()).thenReturn(WOLRD_ID);
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
        try {
            SaveMemento saveMemMock = Mockito.mock(SaveMemento.class);
            Save saveMock = Mockito.mock(Save.class);
            when(saveMock.getSaveID()).thenReturn(SAVE_ID);
            when(saveMemMock.getWorldID()).thenReturn(WOLRD_ID);
            World world = dataBaseConnectorExpected.loadWorlds(saveMock, saveMemMock);

            Chunk chunk = dataBaseConnectorExpected.loadChunk(world, 0, 0);

            assertEquals(0, chunk.getX());
            assertEquals(0, chunk.getY());
            assertEquals(6, chunk.getEntities().size());
            assertEquals(WOLRD_ID, chunk.getWorld().getID());
            assertEquals(100,chunk.getTiles().size());
        } catch (SQLException | LoadException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createEntityFromMementoTest() {
        try {
            SaveableEntityMemento memento = Mockito.mock(SaveableEntityMemento.class);
            when(memento.getEntityType()).thenReturn("Bone");
            assertTrue(dataBaseConnectorExpected.createEntityFromMemento(memento) instanceof Bone);
            when(memento.getEntityType()).thenReturn("DesertShrub");
            assertTrue(dataBaseConnectorExpected.createEntityFromMemento(memento) instanceof DesertShrub);
            when(memento.getEntityType()).thenReturn("DesertCacti");
            assertTrue(dataBaseConnectorExpected.createEntityFromMemento(memento) instanceof DesertCacti);
            when(memento.getEntityType()).thenReturn("DesertRock");
            assertTrue(dataBaseConnectorExpected.createEntityFromMemento(memento) instanceof DesertRock);
            when(memento.getEntityType()).thenReturn("ForestMushroom");
            assertTrue(dataBaseConnectorExpected.createEntityFromMemento(memento) instanceof ForestMushroom);
            when(memento.getEntityType()).thenReturn("ForestShrub");
            assertTrue(dataBaseConnectorExpected.createEntityFromMemento(memento) instanceof ForestShrub);
            when(memento.getEntityType()).thenReturn("Leaves");
            assertTrue(dataBaseConnectorExpected.createEntityFromMemento(memento) instanceof Leaves);
            when(memento.getEntityType()).thenReturn("TreeStump");
            assertTrue(dataBaseConnectorExpected.createEntityFromMemento(memento) instanceof TreeStump);
            when(memento.getEntityType()).thenReturn("OrganicMound");
            assertTrue(dataBaseConnectorExpected.createEntityFromMemento(memento) instanceof OrganicMound);
            when(memento.getEntityType()).thenReturn("MountainRock");
            assertTrue(dataBaseConnectorExpected.createEntityFromMemento(memento) instanceof MountainRock);
            when(memento.getEntityType()).thenReturn("MountainTree");
            assertTrue(dataBaseConnectorExpected.createEntityFromMemento(memento) instanceof MountainTree);
            when(memento.getEntityType()).thenReturn("ForestRock");
            assertTrue(dataBaseConnectorExpected.createEntityFromMemento(memento) instanceof ForestRock);
            when(memento.getEntityType()).thenReturn("SnowClump");
            assertTrue(dataBaseConnectorExpected.createEntityFromMemento(memento) instanceof SnowClump);
            when(memento.getEntityType()).thenReturn("SnowShrub");
            assertTrue(dataBaseConnectorExpected.createEntityFromMemento(memento) instanceof SnowShrub);
            when(memento.getEntityType()).thenReturn("ForestTree");
            assertTrue(dataBaseConnectorExpected.createEntityFromMemento(memento) instanceof ForestTree);
            when(memento.getEntityType()).thenReturn("SwampShrub");
            assertTrue(dataBaseConnectorExpected.createEntityFromMemento(memento) instanceof SwampShrub);
            when(memento.getEntityType()).thenReturn("SwampRock");
            assertTrue(dataBaseConnectorExpected.createEntityFromMemento(memento) instanceof SwampRock);
            when(memento.getEntityType()).thenReturn("SwampTree");
            assertTrue(dataBaseConnectorExpected.createEntityFromMemento(memento) instanceof SwampTree);
            when(memento.getEntityType()).thenReturn("VolcanicShrub");
            assertTrue(dataBaseConnectorExpected.createEntityFromMemento(memento) instanceof VolcanicShrub);
            when(memento.getEntityType()).thenReturn("VolcanicRock");
            assertTrue(dataBaseConnectorExpected.createEntityFromMemento(memento) instanceof VolcanicRock);
            when(memento.getEntityType()).thenReturn("VolcanicTree");
            assertTrue(dataBaseConnectorExpected.createEntityFromMemento(memento) instanceof VolcanicTree);
        } catch (LoadException e) {
            fail("Unable to find class of given entity from given string");
        }
    }

    @Test
    public void loadSaveInformationTest() {
        ArrayList<Save> saves = (ArrayList<Save>) dataBaseConnectorExpected.loadSaveInformation();
        assertEquals(1, saves.size());
        assertEquals(SAVE_ID, saves.get(0).getSaveID());
    }
}
