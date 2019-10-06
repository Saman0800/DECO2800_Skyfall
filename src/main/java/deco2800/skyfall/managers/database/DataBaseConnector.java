package deco2800.skyfall.managers.database;

import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.MainCharacter.MainCharacterMemento;
import deco2800.skyfall.entities.SaveableEntity.SaveableEntityMemento;
import deco2800.skyfall.entities.StaticEntity;
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
import deco2800.skyfall.resources.GoldPiece;
import deco2800.skyfall.saving.DatabaseException;
import deco2800.skyfall.saving.LoadException;
import deco2800.skyfall.saving.RunTimeLoadException;
import deco2800.skyfall.saving.RunTimeSaveException;
import deco2800.skyfall.saving.Save;
import deco2800.skyfall.saving.Save.SaveMemento;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.biomes.AbstractBiome.AbstractBiomeMemento;
import deco2800.skyfall.worlds.biomes.BeachBiome;
import deco2800.skyfall.worlds.biomes.DesertBiome;
import deco2800.skyfall.worlds.biomes.ForestBiome;
import deco2800.skyfall.worlds.biomes.LakeBiome;
import deco2800.skyfall.worlds.biomes.MountainBiome;
import deco2800.skyfall.worlds.biomes.OceanBiome;
import deco2800.skyfall.worlds.biomes.RiverBiome;
import deco2800.skyfall.worlds.biomes.SnowyMountainsBiome;
import deco2800.skyfall.worlds.biomes.SwampBiome;
import deco2800.skyfall.worlds.biomes.VolcanicMountainsBiome;
import deco2800.skyfall.worlds.generation.VoronoiEdge;
import deco2800.skyfall.worlds.generation.VoronoiEdge.VoronoiEdgeMemento;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode.WorldGenNodeMemento;
import deco2800.skyfall.worlds.world.Chunk;
import deco2800.skyfall.worlds.world.Chunk.ChunkMemento;
import deco2800.skyfall.worlds.world.World;
import deco2800.skyfall.worlds.world.World.WorldMemento;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.derby.jdbc.EmbeddedDriver;
import org.flywaydb.core.Flyway;

public class DataBaseConnector {

    /* The connection to the database */
    private Connection connection;

    private Flyway flyway;

    private String dataBaseName;

    /**
     * Starts a connection to the database
     *
     * @param dataBaseName The name of the database to be connected to
     */
    public void start(String dataBaseName) {
        try {
            this.dataBaseName = dataBaseName;
            migrateDatabase();
            Driver derbyData = new EmbeddedDriver();
            DriverManager.registerDriver(derbyData);
            connection = DriverManager.getConnection("jdbc:derby:" + dataBaseName + ";create=true");
        } catch (Exception e) {
            throw new DatabaseException("Failed to start database : " + dataBaseName, e);
        }
    }

    /**
     * Closes the connection to the database
     */
    public void close() {
        try {
            connection.close();
            DriverManager.getConnection("jdbc:derby:" + dataBaseName + ";shutdown=true");
        } catch (SQLException ignore) {
            // Should ignore exception, as shutting down database always throws exceptions
        }
    }

    /**
     * Gets the database connection
     *
     * @return The database connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Saves the game
     *
     * @param save the save file
     */
    public void saveGame(Save save) {
        try {
            // Given a save game
            long saveId = save.getSaveID();
            ContainsDataQueries containsQueries = new ContainsDataQueries(connection);
            InsertDataQueries insertQueries = new InsertDataQueries(connection);
            UpdateDataQueries updateQueries = new UpdateDataQueries(connection);

            if (containsQueries.containsSave(saveId)) {
                updateQueries.updateSave(saveId, save.save());
            } else {
                insertQueries.insertSave(saveId, save.save());
            }

            saveMainCharacter();
            // Looping through the worlds in the save and saving them
            for (World world : save.getWorlds()) {
                saveWorld(world);
            }
        } catch (SQLException | IOException e) {
            throw new RunTimeSaveException("Failed to save the game ", e);
        }
    }

    /**
     * Saves a world and its contents
     *
     * @param world the world to save
     * @throws SQLException if there is an error with the SQL queries
     */
    public void saveWorld(World world) throws SQLException, IOException {
        ContainsDataQueries containsQueries = new ContainsDataQueries(connection);
        InsertDataQueries insertQueries = new InsertDataQueries(connection);
        UpdateDataQueries updateQueries = new UpdateDataQueries(connection);

        if (containsQueries.containsWorld(world.getSave().getSaveID(), world.getID())) {
            updateQueries.updateWorld(world.getSave().getSaveID(), world.getID(),
                world.getSave().getCurrentWorld().getID() == world.getID(), world.save());
        } else {
            insertQueries.insertWorld(world.getSave().getSaveID(), world.getID(),
                world.getSave().getCurrentWorld().getID() == world.getID(), world.save());
        }

        for (AbstractBiome biome : world.getBiomes()) {
            if (containsQueries.containsBiome(biome.getBiomeID(), world.getID())) {
                updateQueries.updateBiome(biome.getBiomeID(), world.getID(), biome.getBiomeName(), biome.save());
            } else {
                insertQueries.insertBiome(biome.getBiomeID(), world.getID(), biome.getBiomeName(), biome.save());
            }
        }

        // Save nodes
        for (WorldGenNode worldGenNode : world.getWorldGenNodes()) {
            if (containsQueries.containsNode(world.getID(), worldGenNode.getX(), worldGenNode.getY())) {
                updateQueries.updateNodes(world.getID(), worldGenNode.getX(), worldGenNode.getY(), worldGenNode.save(),
                        worldGenNode.getID(), worldGenNode.getBiome().getBiomeID());
            } else {
                insertQueries.insertNodes(world.getID(), worldGenNode.getX(), worldGenNode.getY(), worldGenNode.save(),
                    worldGenNode.getID(), worldGenNode.getBiome().getBiomeID());
            }
        }

        // Save beach edges
        for (VoronoiEdge voronoiEdge : world.getBeachEdges().keySet()) {
            if (containsQueries.containsEdge(voronoiEdge.getID())) {
                updateQueries.updateEdges(world.getID(), voronoiEdge.getID(),
                    world.getBeachEdges().get(voronoiEdge).getBiomeID(), voronoiEdge.save());
            } else {
                insertQueries.insertEdges(world.getID(), voronoiEdge.getID(),
                    world.getBeachEdges().get(voronoiEdge).getBiomeID(), voronoiEdge.save());
            }
        }

        // Save river edges
        for (VoronoiEdge voronoiEdge : world.getRiverEdges().keySet()) {
            if (containsQueries.containsEdge(voronoiEdge.getID())) {
                updateQueries.updateEdges(world.getID(), voronoiEdge.getID(),
                    world.getRiverEdges().get(voronoiEdge).getBiomeID(), voronoiEdge.save());
            } else {
                insertQueries.insertEdges(world.getID(), voronoiEdge.getID(),
                    world.getRiverEdges().get(voronoiEdge).getBiomeID(), voronoiEdge.save());
            }
        }

        for (Chunk chunk : world.getLoadedChunks().values()) {
            saveChunk(chunk);
        }
    }

    // TODO:dannathan Fix or remove this.
    public void saveMainCharacter() throws SQLException {
        try {
            ContainsDataQueries containsQueries = new ContainsDataQueries(connection);
            InsertDataQueries insertQueries = new InsertDataQueries(connection);
            UpdateDataQueries updateQueries = new UpdateDataQueries(connection);

            if (containsQueries.containsMainCharacter(MainCharacter.getInstance().getID(),
                MainCharacter.getInstance().getSave().getSaveID())) {
                updateQueries.updateMainCharacter(MainCharacter.getInstance().getID(),
                    MainCharacter.getInstance().getSave().getSaveID(), MainCharacter.getInstance().save());
            } else {
                insertQueries.insertMainCharacter(MainCharacter.getInstance().getID(),
                    MainCharacter.getInstance().getSave().getSaveID(), MainCharacter.getInstance().save());
            }
        } catch (IOException e) {
            throw new RunTimeSaveException("Unable to save the main character to the database", e);
        }
    }

    /**
     * Saves a chunk
     *
     * @param chunk the chunk to save
     */
    public void saveChunk(Chunk chunk) {
        try {
            World world = chunk.getWorld();
            ContainsDataQueries containsQueries = new ContainsDataQueries(connection);
            InsertDataQueries insertQueries = new InsertDataQueries(connection);
            UpdateDataQueries updateQueries = new UpdateDataQueries(connection);

            if (containsQueries.containsChunk(world.getID(), chunk.getX(), chunk.getY())) {
                updateQueries.updateChunk(world.getID(), chunk.getX(), chunk.getY(), chunk.save());
            } else {
                insertQueries.insertChunk(world.getID(), chunk.getX(), chunk.getY(), chunk.save());
            }

            for (AbstractEntity entity : chunk.getEntities()) {
                if (entity instanceof StaticEntity && ((StaticEntity) entity).getEntityType() != null) {
                    if (containsQueries.containsEntity(world.getID(), entity.getEntityID())) {
                        updateQueries.updateEntity(((StaticEntity) entity).getEntityType(), entity.getCol(),
                            entity.getRow(), chunk.getX(), chunk.getY(), world.getID(),
                            ((StaticEntity) entity).save(), entity.getEntityID());
                    } else {
                        insertQueries.insertEntity(((StaticEntity) entity).getEntityType(), entity.getCol(),
                            entity.getRow(), chunk.getX(), chunk.getY(), world.getID(),
                            ((StaticEntity) entity).save(), entity.getEntityID());
                    }
                }
            }
            connection.commit();
        } catch (SQLException | IOException e) {
            throw new RunTimeSaveException("Failed to save the chunk", e);
        }
    }

    /**
     * Load the game
     *
     * @return loads the most recent save
     */
    public Save loadGame(long saveId) {

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM SAVES WHERE save_id = "
            + "?")) {
            preparedStatement.setLong(1, saveId);
            long saveID;
            byte[] buffer;
            try (ResultSet result = preparedStatement.executeQuery()) {
                connection.setAutoCommit(false);
                // fixme:jeffvan12 sort this out

                if (!result.next()) {
                    connection.setAutoCommit(true);
                    throw new SQLException();
                }

                saveID = result.getLong("save_id");
                connection.setAutoCommit(true);

                buffer = result.getBytes("data");
            }
            ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
            SaveMemento memento = (SaveMemento) objectIn.readObject();

            Save save = new Save(memento);
            World currentWorld = loadWorlds(save, memento);
            save.setCurrentWorld(currentWorld);
            save.setSaveID(saveID);

            return save;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RunTimeLoadException("Failed to load the game", e);
        }
    }

    public void loadMainCharacter(Save save) {
        try (PreparedStatement preparedStatement = connection
            .prepareStatement("SELECT * FROM MAIN_CHARACTER WHERE save_id = ?")) {
            preparedStatement.setLong(1, save.getSaveID());
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (!result.next()) {
                    connection.setAutoCommit(true);
                    throw new LoadException();
                }

                byte[] buffer = result.getBytes("data");
                ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
                MainCharacterMemento memento = (MainCharacterMemento) objectIn.readObject();
                MainCharacter.loadMainCharacter(memento, save);

                connection.setAutoCommit(true);
            }

        } catch (IOException | ClassNotFoundException | SQLException | LoadException e) {
            throw new RunTimeLoadException("Unable to load main character", e);
        }

    }

    /**
     * Loads the world of a save
     *
     * @param save        the save to load from
     * @param saveMemento the memento of the save
     * @return the save's current world
     */
    public World loadWorlds(Save save, Save.SaveMemento saveMemento) {
        try {
            connection.setAutoCommit(false);
            World currentWorld;
            try (PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM WORLDS WHERE save_id = ?")) {
                preparedStatement.setLong(1, save.getSaveID());
                try (ResultSet result = preparedStatement.executeQuery()) {

                    if (!result.next()) {
                        connection.setAutoCommit(true);
                        throw new LoadException();
                    }

                    currentWorld = null;

                    do {
                        boolean isCurrentWorld = result.getBoolean("is_current_world");
                        long worldID = result.getLong("world_id");
                        if (!isCurrentWorld && saveMemento.getWorldID() == worldID
                            || isCurrentWorld && saveMemento.getWorldID() != worldID) {
                            throw new LoadException();
                        }

                        byte[] buffer = result.getBytes("data");
                        ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
                        WorldMemento memento = (WorldMemento) objectIn.readObject();

                        World world = new World(memento, save);
                        if (isCurrentWorld) {
                            currentWorld = world;
                        }

                        List<AbstractBiome> biomeList = loadBiomes(world);
                        world.getWorldParameters().setBiomes(biomeList);
                        world.setWorldGenNodes(loadNodes(world, biomeList));
                        world.setRiverEdges((LinkedHashMap<VoronoiEdge, RiverBiome>) loadRiverEdges(world, biomeList));
                        world.setBeachEdges((LinkedHashMap<VoronoiEdge, BeachBiome>) loadBeachEdges(world, biomeList));
                        world.generateStartEntities();
                        save.addWorld(world);
                    } while (result.next());
                    connection.setAutoCommit(true);
                }
            }

            return currentWorld;

        } catch (ClassNotFoundException | IOException | LoadException | SQLException e) {
            throw new RunTimeLoadException("Failed to load the world", e);
        }
    }

    /**
     * Loads the biomes in a world being loaded
     *
     * @param world the world being loaded
     * @return A list of biomes in the world
     */
    public List<AbstractBiome> loadBiomes(World world) {
        try {
            connection.setAutoCommit(false);
            LinkedHashMap<AbstractBiome, Long> biomes;
            LinkedHashMap<Long, AbstractBiome> ids;
            try (PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM biomes WHERE world_id = " + "?")) {
                preparedStatement.setLong(1, world.getID());

                try (ResultSet result = preparedStatement.executeQuery()) {

                    if (!result.next()) {
                        connection.setAutoCommit(true);
                        throw new LoadException();
                    }

                    biomes = new LinkedHashMap<>();
                    ids = new LinkedHashMap<>();

                    do {
                        String biomeType = result.getString("biome_type");

                        byte[] buffer = result.getBytes("data");
                        ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
                        AbstractBiomeMemento memento = (AbstractBiomeMemento) objectIn.readObject();

                        AbstractBiome biome;

                        switch (biomeType) {
                            case "beach":
                                biome = new BeachBiome(memento);
                                break;
                            case "desert":
                                biome = new DesertBiome(memento);
                                break;
                            case "forest":
                                biome = new ForestBiome(memento);
                                break;
                            case "lake":
                                biome = new LakeBiome(memento);
                                break;
                            case "mountain":
                                biome = new MountainBiome(memento);
                                break;
                            case "ocean":
                                biome = new OceanBiome(memento);
                                break;
                            case "river":
                                biome = new RiverBiome(memento);
                                break;
                            case "snowy_mountains":
                                biome = new SnowyMountainsBiome(memento);
                                break;
                            case "swamp":
                                biome = new SwampBiome(memento);
                                break;
                            case "volcanic_mountains":
                                biome = new VolcanicMountainsBiome(memento);
                                break;
                            default:
                                connection.setAutoCommit(false);
                                throw new LoadException();
                        }
                        biomes.put(biome, memento.getParentBiomeID());
                        ids.put(biome.getBiomeID(), biome);
                    } while (result.next());
                }
            }

            List<AbstractBiome> biomeList = new ArrayList<>();

            for (AbstractBiome biome : biomes.keySet()) {
                if (biomes.get(biome) == -1) {
                    biome.setParentBiome(null);
                } else {
                    // If there is an invalid parent id
                    if (!ids.containsKey(biomes.get(biome))) {
                        connection.setAutoCommit(false);
                        throw new LoadException();
                    } else {
                        biome.setParentBiome(ids.get(biomes.get(biome)));
                    }
                }
                biomeList.add(biome);
            }
            connection.setAutoCommit(true);
            return biomeList;
        } catch (ClassNotFoundException | IOException | SQLException | LoadException e) {
            throw new RunTimeLoadException("Failed to load biomes for a world", e);
        }
    }

    public List<WorldGenNode> loadNodes(World world, List<AbstractBiome> biomes) throws SQLException, LoadException {
        try {
            connection.setAutoCommit(false);
            List<WorldGenNode> nodes;
            try (PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM nodes WHERE world_id = ?")) {
                preparedStatement.setLong(1, world.getID());
                try (ResultSet result = preparedStatement.executeQuery()) {

                    if (!result.next()) {
                        connection.setAutoCommit(true);
                        throw new LoadException();
                    }

                    nodes = new ArrayList<>();
                    do {
                        long biomeID = result.getLong("biome_id");

                        byte[] buffer = result.getBytes("data");
                        ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
                        WorldGenNodeMemento memento = (WorldGenNodeMemento) objectIn.readObject();

                        WorldGenNode node = new WorldGenNode(memento);

                        boolean foundBiome = false;
                        for (AbstractBiome biome : biomes) {
                            if (biome.getBiomeID() == biomeID) {
                                node.setBiome(biome);
                                foundBiome = true;
                                break;
                            }
                        }
                        if (!foundBiome) {
                            connection.setAutoCommit(true);
                            throw new LoadException();
                        }

                        nodes.add(node);
                    } while (result.next());
                }
            }

            return nodes;
        } catch (ClassNotFoundException | IOException e) {
            throw new RunTimeLoadException("Failed to load nodes", e);
        }
    }

    /**
     * Loads the beach edges of a world
     *
     * @param world  the world being loaded
     * @param biomes the biomes in the world
     * @return a mapping from edges to beach biomes
     * @throws SQLException  If there is unexpected behaviour with the SQL queries
     * @throws LoadException If the save cannot construct a valid world
     */
    public Map<VoronoiEdge, BeachBiome> loadBeachEdges(World world, List<AbstractBiome> biomes)
        throws SQLException, LoadException {
        try {
            connection.setAutoCommit(false);
            LinkedHashMap<VoronoiEdge, BeachBiome> edges;
            try (PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM EDGES WHERE world_id = ?")) {
                preparedStatement.setLong(1, world.getID());
                try (ResultSet result = preparedStatement.executeQuery()) {

                    if (!result.next()) {
                        connection.setAutoCommit(true);
                        throw new LoadException();
                    }

                    edges = new LinkedHashMap<>();
                    do {
                        byte[] buffer = result.getBytes("data");
                        ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
                        VoronoiEdgeMemento memento = (VoronoiEdgeMemento) objectIn.readObject();
                        long biomeID = result.getLong("biome_id");

                        VoronoiEdge edge = new VoronoiEdge(memento);
                        edge.setWorld(world);
                        boolean foundBiome = false;
                        for (AbstractBiome biome : biomes) {
                            if (biome.getBiomeID() == biomeID) {
                                foundBiome = true;
                                if (biome.getBiomeName().equals("beach")) {
                                    edges.put(edge, (BeachBiome) biome);
                                    break;
                                } else if (!biome.getBiomeName().equals("river")) {
                                    connection.setAutoCommit(true);
                                    throw new LoadException();
                                }
                            }
                        }
                        if (!foundBiome) {
                            connection.setAutoCommit(true);
                            throw new LoadException();
                        }

                    } while (result.next());
                }
            }

            return edges;
        } catch (ClassNotFoundException | IOException e) {
            throw new RunTimeLoadException("Failed to load beach edges", e);
        }
    }

    /**
     * Loads the river edges of a world
     *
     * @param world  the world being loaded
     * @param biomes the biomes in the world
     * @return a mapping from edges to river biomes
     * @throws SQLException  If there is unexpected behaviour with the SQL queries
     * @throws LoadException If the save cannot construct a valid world
     */
    public Map<VoronoiEdge, RiverBiome> loadRiverEdges(World world, List<AbstractBiome> biomes)
        throws SQLException, LoadException {
        try {
            connection.setAutoCommit(false);

            LinkedHashMap<VoronoiEdge, RiverBiome> edges;
            try (PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM EDGES WHERE world_id = ?")) {
                preparedStatement.setLong(1, world.getID());
                try (ResultSet result = preparedStatement.executeQuery()) {

                    if (!result.next()) {
                        connection.setAutoCommit(true);
                        throw new LoadException();
                    }

                    edges = new LinkedHashMap<>();
                    do {
                        long biomeID = result.getLong("biome_id");

                        byte[] buffer = result.getBytes("data");
                        ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
                        VoronoiEdgeMemento memento = (VoronoiEdgeMemento) objectIn.readObject();

                        VoronoiEdge edge = new VoronoiEdge(memento);
                        edge.setWorld(world);
                        for (AbstractBiome biome : biomes) {
                            if (biome.getBiomeID() == biomeID && biome.getBiomeName().equals("river")) {
                                edges.put(edge, (RiverBiome) biome);
                                break;
                            }
                        }

                    } while (result.next());
                }
            }

            return edges;
        } catch (ClassNotFoundException | IOException e) {
            throw new LoadException("Failed to load nodes", e);
        }
    }

    /**
     * Loads a chunk and the entities in chunk
     *
     * @param world The world where the chunk is
     * @param x     The x position of the chunk
     * @param y     The y positoin of the chunk
     * @return The chunk from the database if it exists in the database. A new chunk if the chunk does not exist in the
     * database.
     */
    public Chunk loadChunk(World world, int x, int y) {
        try {
            connection.setAutoCommit(false);
            byte[] buffer;
            try (PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM CHUNKS WHERE X = ? and Y = ? and WORLD_ID = ?")) {
                preparedStatement.setInt(1, x);
                preparedStatement.setInt(2, y);
                preparedStatement.setLong(3, world.getID());
                try (ResultSet result = preparedStatement.executeQuery()) {
                    connection.setAutoCommit(false);

                    if (!result.next()) {
                        Chunk chunk = new Chunk(world, x, y);
                        chunk.generateEntities();
                        return chunk;
                    }

                    buffer = result.getBytes("data");
                }
            }
            ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
            ChunkMemento memento = (ChunkMemento) objectIn.readObject();
            Chunk chunk = new Chunk(world, memento);

            // Gets the entities within the chunk from the database and add them to the
            // chunk
            connection.setAutoCommit(false);
            try (PreparedStatement entityQuery = connection
                .prepareStatement("SELECT * FROM ENTITIES WHERE CHUNK_X = ? and CHUNK_Y = ? and WORLD_ID = ?")) {

                entityQuery.setInt(1, x);
                entityQuery.setInt(2, y);
                entityQuery.setLong(3, world.getID());
                try (ResultSet entityResult = entityQuery.executeQuery()) {

                    connection.setAutoCommit(false);
                    while (entityResult.next()) {
                        connection.setAutoCommit(false);
                        buffer = entityResult.getBytes("data");
                        objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
                        SaveableEntityMemento entityMemento = (SaveableEntityMemento) objectIn.readObject();
                        chunk.addEntity(createEntityFromMemento(entityMemento));
                    }
                }
            }
            connection.setAutoCommit(true);
            return chunk;
        } catch (IOException | ClassNotFoundException | SQLException | LoadException e) {
            throw new RunTimeLoadException("Failed to load a chunk", e);
        }
    }

    public Flyway getFlyway() {
        return flyway;
    }

    /**
     * Creates an entity from a given memento
     *
     * @param entityMemento The entities memento that contains its data
     * @return The created entity
     * @throws LoadException If the entity type does not exist
     */
    public AbstractEntity createEntityFromMemento(SaveableEntityMemento entityMemento) throws LoadException {
        switch (entityMemento.getEntityType()) {
            case "Bone":
                return new Bone(entityMemento);
            case "DesertShrub":
                return new DesertShrub(entityMemento);
            case "DesertCacti":
                return new DesertCacti(entityMemento);
            case "DesertRock":
                return new DesertRock(entityMemento);
            case "ForestMushroom":
                return new ForestMushroom(entityMemento);
            case "ForestShrub":
                return new ForestShrub(entityMemento);
            case "Leaves":
                return new Leaves(entityMemento);
            case "TreeStump":
                return new TreeStump(entityMemento);
            case "OrganicMound":
                return new OrganicMound(entityMemento);
            case "MountainRock":
                return new MountainRock(entityMemento);
            case "MountainTree":
                return new MountainTree(entityMemento);
            case "ForestRock":
                return new ForestRock(entityMemento);
            case "SnowClump":
                return new SnowClump(entityMemento);
            case "SnowShrub":
                return new SnowShrub(entityMemento);
            case "ForestTree":
                return new ForestTree(entityMemento);
            case "SwampShrub":
                return new SwampShrub(entityMemento);
            case "SwampRock":
                return new SwampRock(entityMemento);
            case "SwampTree":
                return new SwampTree(entityMemento);
            case "VolcanicShrub":
                return new VolcanicShrub(entityMemento);
            case "VolcanicRock":
                return new VolcanicRock(entityMemento);
            case "VolcanicTree":
                return new VolcanicTree(entityMemento);
            case "GoldPiece":
                return new GoldPiece(entityMemento);
            default:
                throw new LoadException(
                    String.format("Could not create %s from memento", entityMemento.getEntityType()));
        }
    }

    /**
     * Uses flyway to create the tables
     */
    private void migrateDatabase() {
        flyway = new Flyway();
        flyway.setDataSource("jdbc:derby:" + dataBaseName + ";create=true", "", "");

        flyway.setCleanOnValidationError(true);
        flyway.setValidateOnMigrate(true);
        flyway.migrate();
    }

    /**
     * Gets all the saves and there corresponding worlds
     *
     * @return A list of all the saves
     */
    public List<Save> loadSaveInformation() {
        try {
            ArrayList<Save> saves = new ArrayList<>();

            try (PreparedStatement saveStatement = connection.prepareStatement("SELECT * FROM SAVES")) {
                // Go through and load all the worlds for each save
                try (ResultSet saveSet = saveStatement.executeQuery()) {
                    while (saveSet.next()) {

                        byte[] buffer = saveSet.getBytes("data");
                        ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
                        SaveMemento memento = (SaveMemento) objectIn.readObject();

                        Save save = new Save(memento);

                        try (PreparedStatement worldsStatement = connection
                            .prepareStatement("SELECT * FROM WORLDS WHERE SAVE_ID = ?")) {
                            worldsStatement.setLong(1, save.getSaveID());

                            try (ResultSet worldSet = worldsStatement.executeQuery()) {
                                // Loop through those worlds and add them to the save
                                while (worldSet.next()) {
                                    World world = new World(worldSet.getLong("world_id"), save);
                                    save.addWorld(world);
                                }
                            }
                        }

                        saves.add(save);
                    }
                }
            }
            return saves;

        } catch (Exception e) {
            throw new RunTimeLoadException("Failed to load save information", e);
        }
    }

    private void saveTable(String tableName) {
        try {
            try (PreparedStatement ps = DatabaseManager.get().getDataBaseConnector().getConnection()
                .prepareStatement("CALL SYSCS_UTIL.SYSCS_EXPORT_TABLE_LOBS_TO_EXTFILE(?, ?, ?, ?, ?, ?, ?)")) {
                ps.setString(1, null);
                ps.setString(2, tableName);
                ps.setString(3, String.format("src/test/java/deco2800/skyfall/managers/database/PrebuiltData/%s.dat",
                    tableName));
                ps.setString(4, ",");
                ps.setString(5, "\"");
                ps.setString(6, "UTF-8");
                ps.setString(7, String.format("src/test/java/deco2800/skyfall/managers/database/PrebuiltData/%sLOB.dat",
                    tableName));
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RunTimeSaveException("Failed to save table : " + tableName, e);
        }
    }

    public void saveAllTables() {
        saveTable("SAVES");
        saveTable("MAIN_CHARACTER");
        saveTable("WORLDS");
        saveTable("BIOMES");
        saveTable("NODES");
        saveTable("EDGES");
        saveTable("CHUNKS");
        saveTable("ENTITIES");
    }

    public void loadAllTables() {
        loadTable("SAVES", 1);
        loadTable("MAIN_CHARACTER", 2);
        loadTable("WORLDS", 3);
        loadTable("BIOMES", 3);
        loadTable("NODES", 4);
        loadTable("EDGES", 3);
        loadTable("CHUNKS", 3);
        loadTable("ENTITIES", 7);
    }

    private void loadTable(String tableName, int dataIndex) {
        try {
            try (PreparedStatement ps = connection.prepareStatement(
                "CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE_LOBS_FROM_EXTFILE(?, ?, ?, ?, ?, ?, ?)")) {
                ps.setString(1, null);
                ps.setString(2, tableName);
                ps.setString(3, "src/test/java/deco2800/skyfall/managers/database/PrebuiltData/" + tableName + ".dat");
                ps.setString(4, ",");
                ps.setString(5, "\"");
                ps.setString(6, "UTF-8");
                ps.setInt(7, dataIndex);
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RunTimeLoadException("Failed to load table : " + tableName, e);
        }
    }

    // FIXME:jeffvan12 implement delete save method
    public void deleteSave(long saveId) {

    }

}
