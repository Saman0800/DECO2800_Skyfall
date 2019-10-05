package deco2800.skyfall.managers.database;

import com.google.gson.Gson;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.SaveableEntity.SaveableEntityMemento;
import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.entities.worlditems.*;
import deco2800.skyfall.managers.DatabaseManager;
import deco2800.skyfall.saving.LoadException;
import deco2800.skyfall.saving.Save;
import deco2800.skyfall.saving.Save.SaveMemento;
import deco2800.skyfall.worlds.biomes.*;
import deco2800.skyfall.worlds.biomes.AbstractBiome.AbstractBiomeMemento;
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
import org.apache.derby.jdbc.EmbeddedDriver;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
            // Connects to the data base
            migrateDatabase();
            Driver derbyData = new EmbeddedDriver();
            DriverManager.registerDriver(derbyData);
            connection = DriverManager.getConnection("jdbc:derby:" + dataBaseName + ";create=true");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Closes the connection to the database
     */
    public void close() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
            connection.close();
        } catch (Exception e) {
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

            // Looping through the worlds in the save and saving them
            for (World world : save.getWorlds()) {
                saveWorld(world);
            }
            // TODO implement saving the main character
            // saveMainCharacter(save.getMainCharacter());
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
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
                updateQueries.updateNodes(world.getID(), worldGenNode.getX(), worldGenNode.getY(),
                    worldGenNode.save(), worldGenNode.getID(), worldGenNode.getBiome().getBiomeID());
            } else {
                insertQueries.insertNodes(world.getID(), worldGenNode.getX(), worldGenNode.getY(),
                    worldGenNode.save(), worldGenNode.getID(), worldGenNode.getBiome().getBiomeID());
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
    // public void saveMainCharacter(MainCharacter character) throws SQLException {
    // ContainsDataQueries containsQueries = new ContainsDataQueries(connection);
    // InsertDataQueries insertQueries = new InsertDataQueries(connection);
    // UpdateDataQueries updateQueries = new UpdateDataQueries(connection);
    // Gson gson = new Gson();
    //
    //
    // if (containsQueries.containsMainCharacter(character.getID(),
    // character.getSave().getSaveID())) {
    // updateQueries.updateMainCharacter(character.getID(),
    // character.getSave().getSaveID(), character.save());
    // } else {
    // insertQueries.insertMainCharacter(character.getID(),
    // character.getSave().getSaveID(), character.save());
    // }
    // }

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
            throw new RuntimeException(e);
        }
    }

    /**
     * Load the game
     *
     * @return loads the most recent save
     */
    public Save loadGame() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM SAVES");
            ResultSet result = preparedStatement.executeQuery()) {
            connection.setAutoCommit(false);
            // TODO:dannathan make this work for any savefile, not just the most recent
            // preparedStatement = connection.prepareStatement("SELECT * FROM SAVES");
            // result = preparedStatement.executeQuery();
            if (!result.next()) {
                connection.setAutoCommit(true);
                throw new SQLException();
            }

            long saveID = result.getLong("save_id");
            connection.setAutoCommit(true);

            byte[] buffer = result.getBytes("data");
            ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
            SaveMemento memento = (SaveMemento) objectIn.readObject();

            Save save = new Save(memento);
            // TODO impelement loading the main character
            // loadMainCharacter(save);
            World currentWorld = loadWorlds(save, memento);
            save.setCurrentWorld(currentWorld);
            //FIXME:dannathan Probably should turn this back on
//            currentWorld.addEntity(MainCharacter.getInstance());
            save.setSaveID(saveID);

            return save;
        } catch (SQLException | LoadException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO:dannathan
    /*
     * public void loadMainCharacter(Save save) throws SQLException, LoadException {
     * Gson gson = new Gson(); connection.setAutoCommit(false); PreparedStatement
     * preparedStatement =
     * connection.prepareStatement("SELECT * FROM MAIN_CHARACTER WHERE save_id = ?"
     * ); preparedStatement.setLong(1, save.getSaveID()); ResultSet result =
     * preparedStatement.executeQuery(); if (!result.next()) {
     * connection.setAutoCommit(true); throw new LoadException(); }
     *
     * String data = result.getString("data"); MainCharacter.MainCharacterMemento
     * memento = gson.fromJson(data, MainCharacter.MainCharacterMemento.class);
     * MainCharacter.loadMainCharacter(memento, save);
     * connection.setAutoCommit(true);
     * save.setMainCharacter(MainCharacter.getInstance()); }
     */

    /**
     * Loads the world of a save
     *
     * @param save        the save to load from
     * @param saveMemento the memento of the save
     * @return the save's current world
     * @throws SQLException  If there is unexpected behaviour with the SQL queries
     * @throws LoadException If the save cannot construct a valid world
     */
    public World loadWorlds(Save save, Save.SaveMemento saveMemento) throws SQLException, LoadException {
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("SELECT * FROM WORLDS WHERE save_id = ?");
            preparedStatement.setLong(1, save.getSaveID());
            result = preparedStatement.executeQuery();

            if (!result.next()) {
                connection.setAutoCommit(true);
                throw new LoadException();
            }

            World currentWorld = null;

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
                world.setRiverEdges(loadRiverEdges(world, biomeList));
                world.setBeachEdges(loadBeachEdges(world, biomeList));
                world.generateStartEntities();
                save.addWorld(world);
            } while (result.next());

            return currentWorld;

        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
            result.close();
        }
    }

    /**
     * Loads the biomes in a world being loaded
     *
     * @param world the world being loaded
     * @return A list of biomes in the world
     * @throws SQLException  If there is unexpected behaviour with the SQL queries
     * @throws LoadException If the save cannot construct a valid world
     */
    public List<AbstractBiome> loadBiomes(World world) throws SQLException, LoadException {
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        try {
            Gson gson = new Gson();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("SELECT * FROM biomes WHERE world_id = ?");
            preparedStatement.setLong(1, world.getID());
            result = preparedStatement.executeQuery();

            if (!result.next()) {
                connection.setAutoCommit(true);
                throw new LoadException();
            }

            LinkedHashMap<AbstractBiome, Long> biomes = new LinkedHashMap<>();
            LinkedHashMap<Long, AbstractBiome> ids = new LinkedHashMap<>();

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

            List<AbstractBiome> biomeList = new ArrayList<>();

            for (AbstractBiome biome : biomes.keySet()) {
                long id = biome.getBiomeID();
                if (biomes.get(biome) == -1) {
                    biome.setParentBiome(null);
                } else {
                    // If there is an invalid parent id
                    if (!ids.keySet().contains(biomes.get(biome))) {
                        connection.setAutoCommit(false);
                        throw new LoadException();
                    } else {
                        biome.setParentBiome(ids.get(biomes.get(biome)));
                    }
                }
                biomeList.add(biome);
            }
            connection.setAutoCommit(false);
            return biomeList;
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
            result.close();
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
                        Long biomeID = result.getLong("biome_id");

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
            throw new RuntimeException(e);
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
    public LinkedHashMap<VoronoiEdge, BeachBiome> loadBeachEdges(World world, List<AbstractBiome> biomes)
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
                        Long biomeID = result.getLong("biome_id");

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
            throw new RuntimeException(e);
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
    public LinkedHashMap<VoronoiEdge, RiverBiome> loadRiverEdges(World world, List<AbstractBiome> biomes)
        throws SQLException, LoadException {
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        try {
            Gson gson = new Gson();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("SELECT * FROM EDGES WHERE world_id = ?");
            preparedStatement.setLong(1, world.getID());
            result = preparedStatement.executeQuery();

            if (!result.next()) {
                connection.setAutoCommit(true);
                throw new LoadException();
            }

            LinkedHashMap<VoronoiEdge, RiverBiome> edges = new LinkedHashMap<>();
            do {
                String data = result.getString("data");
                Long biomeID = result.getLong("biome_id");
                byte[] buffer = result.getBytes("data");
                ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
                VoronoiEdgeMemento memento = (VoronoiEdgeMemento) objectIn.readObject();

                VoronoiEdge edge = new VoronoiEdge(memento);
                edge.setWorld(world);
                for (AbstractBiome biome : biomes) {
                    if (biome.getBiomeID() == biomeID) {
                        if (biome.getBiomeName().equals("river")) {
                            edges.put(edge, (RiverBiome) biome);
                            break;
                        }
                    }
                }

            } while (result.next());

            return edges;
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
            result.close();
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
     * @throws SQLException
     */
    public Chunk loadChunk(World world, int x, int y) {
        PreparedStatement preparedStatement = null;
        PreparedStatement entityQuery = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection
                .prepareStatement("SELECT * FROM CHUNKS WHERE X = ? and Y = ? and WORLD_ID = ?");
            preparedStatement.setInt(1, x);
            preparedStatement.setInt(2, y);
            preparedStatement.setLong(3, world.getID());
            ResultSet result = preparedStatement.executeQuery();
            connection.setAutoCommit(false);

            if (!result.next()) {
                Chunk chunk = new Chunk(world, x, y);
                chunk.generateEntities();
                preparedStatement.close();
                return chunk;
            }

            byte[] buffer = result.getBytes("data");
            ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
            ChunkMemento memento = (ChunkMemento) objectIn.readObject();
            Chunk chunk = new Chunk(world, memento);

            // Gets the entities within the chunk from the database and add them to the
            // chunk
            connection.setAutoCommit(false);
            ResultSet entityResult;
            entityQuery = connection
                .prepareStatement("SELECT * FROM ENTITIES WHERE CHUNK_X = ? and CHUNK_Y = ? and WORLD_ID = ?");
            entityQuery.setInt(1, x);
            entityQuery.setInt(2, y);
            entityQuery.setLong(3, world.getID());
            entityResult = entityQuery.executeQuery();

            connection.setAutoCommit(false);
            while (entityResult.next()) {
                connection.setAutoCommit(false);
                buffer = entityResult.getBytes("data");
                objectIn = new ObjectInputStream(new ByteArrayInputStream(buffer));
                SaveableEntityMemento entityMemento = (SaveableEntityMemento) objectIn.readObject();
                chunk.addEntity(createEntityFromMemento(entityMemento));
            }
            connection.setAutoCommit(true);
            return chunk;
        } catch (IOException | ClassNotFoundException | SQLException | LoadException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (entityQuery != null) {
                    entityQuery.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
            default:
                throw new LoadException(String.format("Could not create %s from memento", entityMemento.entityType));
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
                //Go through and load all the worlds for each save
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
                                //Loop through those worlds andd add them to the save
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
            throw new RuntimeException(e);
        }
    }


    public void saveTable(String tableName){
        PreparedStatement ps = null;
        try {
            ps = DatabaseManager.get().getDataBaseConnector().getConnection().prepareStatement(
                "CALL SYSCS_UTIL.SYSCS_EXPORT_TABLE_LOBS_TO_EXTFILE(?,?,?,?,?,?, ?)");
            ps.setString(1,null);
            ps.setString(2,tableName);
            ps.setString(3,"src/test/java/deco2800/skyfall/managers/database/PrebuiltData/" + tableName + ".dat");
            ps.setString(4,",");
            ps.setString(5, "\"");
            ps.setString(6,"UTF-8");
            ps.setString(7, "src/test/java/deco2800/skyfall/managers/database/PrebuiltData/" + tableName + "LOB" +
                ".dat");
            ps.execute();
        } catch (SQLException e) {

        }finally {
            try {
                assert ps != null;
                ps.close();
            } catch (SQLException e) {
            }
        }
    }

    public void saveAllTables(){
        saveTable("SAVES");
        saveTable("WORLDS");
//        saveTable("MAIN_CHARACTER");
        saveTable("BIOMES");
        saveTable("NODES");
        saveTable("EDGES");
        saveTable("CHUNKS");
        saveTable("ENTITIES");
    }

    public void loadAllTables(){
        loadTable("SAVES", 1);
        loadTable("WORLDS", 3);
//        loadTable("MAIN_CHARACTER");
        loadTable("BIOMES", 3);
        loadTable("NODES", 4);
        loadTable("EDGES" , 3 );
        loadTable("CHUNKS", 3);
        loadTable("ENTITIES", 7);
    }

    public void loadTable(String tableName,int dataIndex){
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement( "CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE_LOBS_FROM_EXTFILE(?,?,?,?,?,?,?)");
            ps.setString(1,null);
            ps.setString(2,tableName);
            ps.setString(3,"src/test/java/deco2800/skyfall/managers/database/PrebuiltData/" + tableName + ".dat");
            ps.setString(4,",");
            ps.setString(5, "\"");
            ps.setString(6,"UTF-8");
            ps.setInt(7, dataIndex);
            ps.execute();
        } catch (SQLException e) {

        } finally {
            try {
                assert ps != null;
                ps.close();
            } catch (SQLException e) {
            }
        }
    }


    //FIXME:jeffvan12 implement delete save method
    public void deleteSave(long saveId){

    }

}
