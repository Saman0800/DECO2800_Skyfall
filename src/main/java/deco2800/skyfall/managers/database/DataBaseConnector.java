package deco2800.skyfall.managers.database;

import com.google.gson.Gson;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.entities.worlditems.*;
import deco2800.skyfall.saving.LoadException;
import deco2800.skyfall.saving.Save;
import deco2800.skyfall.worlds.biomes.*;
import deco2800.skyfall.worlds.generation.VoronoiEdge;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode;
import deco2800.skyfall.worlds.world.Chunk;
import deco2800.skyfall.worlds.world.World;
import org.apache.derby.jdbc.EmbeddedDriver;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DataBaseConnector {
    /* The connection to the database */
    private Connection connection;

    /**
     * Starts a connection to the database
     */
    public void start() {
        try {
            // Connects to the data base
            Driver derbyData = new EmbeddedDriver();
            DriverManager.registerDriver(derbyData);
            connection = DriverManager.getConnection("jdbc:derby:Database;create=true");

            createTables();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Closes the connection to the database
     */
    public void close() {
        try {
            connection.close();
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (Exception e) {
            System.out.println(e);
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
     * Creates the table if they do not already exist
     *
     * @throws SQLException If an sqlexception occurs when creating the tables
     */
    public void createTables() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            // If there are any missing tables, then drop all the tables and add them all
            // back
            // other wise don't do that

            CreateTablesQueries queries = new CreateTablesQueries();
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "%", null);
            ArrayList<String> tablesCheck = queries.getTableNames();

            ArrayList<String> foundTables = new ArrayList<>();
            while (tables.next()) {
                if (!tables.getString(3).startsWith("SYS")) {
                    foundTables.add(tables.getString(3));
                }
            }

            if (foundTables.size() != tablesCheck.size()) {
                for (String toDelete : foundTables) {
                    statement.execute(String.format("DROP TABLE %s", toDelete));
                }
                for (String query : queries.getQueries()) {
                    statement.execute(query);
                }
            }
        }
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
            Gson gson = new Gson();

            if (containsQueries.containsSave(saveId)) {
                updateQueries.updateSave(saveId, gson.toJson(save.save()));
            } else {
                insertQueries.insertSave(saveId, gson.toJson(save.save()));
            }

            // Looping through the worlds in the save and saving them
            for (World world : save.getWorlds()) {
                saveWorld(world);
            }
            // TODO implement saving the main character
            // saveMainCharacter(save.getMainCharacter());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves a world and its contents
     *
     * @param world the world to save
     * @throws SQLException if there is an error with the SQL queries
     */
    public void saveWorld(World world) throws SQLException {
        ContainsDataQueries containsQueries = new ContainsDataQueries(connection);
        InsertDataQueries insertQueries = new InsertDataQueries(connection);
        UpdateDataQueries updateQueries = new UpdateDataQueries(connection);
        Gson gson = new Gson();

        if (containsQueries.containsWorld(world.getSave().getSaveID(), world.getID())) {
            updateQueries.updateWorld(world.getSave().getSaveID(), world.getID(),
                    world.getSave().getCurrentWorld().getID() == world.getID(), gson.toJson(world.save()));
        } else {
            insertQueries.insertWorld(world.getSave().getSaveID(), world.getID(),
                    world.getSave().getCurrentWorld().getID() == world.getID(), gson.toJson(world.save()));
        }

        for (AbstractBiome biome : world.getBiomes()) {
            if (containsQueries.containsBiome(biome.getBiomeID(), world.getID())) {
                updateQueries.updateBiome(biome.getBiomeID(), world.getID(), biome.getBiomeName(),
                        gson.toJson(biome.save()));
            } else {
                insertQueries.insertBiome(biome.getBiomeID(), world.getID(), biome.getBiomeName(),
                        gson.toJson(biome.save()));
            }
        }

        // Save nodes
        for (WorldGenNode worldGenNode : world.getWorldGenNodes()) {
            if (containsQueries.containsNode(world.getID(), worldGenNode.getX(), worldGenNode.getY())) {
                updateQueries.updateNodes(world.getID(), worldGenNode.getX(), worldGenNode.getY(),
                        gson.toJson(worldGenNode.save()), worldGenNode.getID(), worldGenNode.getBiome().getBiomeID());
            } else {
                insertQueries.insertNodes(world.getID(), worldGenNode.getX(), worldGenNode.getY(),
                        gson.toJson(worldGenNode.save()), worldGenNode.getID(), worldGenNode.getBiome().getBiomeID());
            }
        }

        // Save beach edges
        for (VoronoiEdge voronoiEdge : world.getBeachEdges().keySet()) {
            if (containsQueries.containsEdge(voronoiEdge.getID())) {
                updateQueries.updateEdges(world.getID(), voronoiEdge.getID(),
                        world.getBeachEdges().get(voronoiEdge).getBiomeID(), gson.toJson(voronoiEdge.save()));
            } else {
                insertQueries.insertEdges(world.getID(), voronoiEdge.getID(),
                        world.getBeachEdges().get(voronoiEdge).getBiomeID(), gson.toJson(voronoiEdge.save()));
            }
        }

        // Save river edges
        for (VoronoiEdge voronoiEdge : world.getRiverEdges().keySet()) {
            if (containsQueries.containsEdge(voronoiEdge.getID())) {
                updateQueries.updateEdges(world.getID(), voronoiEdge.getID(),
                        world.getRiverEdges().get(voronoiEdge).getBiomeID(), gson.toJson(voronoiEdge.save()));
            } else {
                insertQueries.insertEdges(world.getID(), voronoiEdge.getID(),
                        world.getRiverEdges().get(voronoiEdge).getBiomeID(), gson.toJson(voronoiEdge.save()));
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
    // character.getSave().getSaveID(), gson.toJson(character.save()));
    // } else {
    // insertQueries.insertMainCharacter(character.getID(),
    // character.getSave().getSaveID(), gson.toJson(character.save()));
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
            Gson gson = new Gson();

            if (containsQueries.containsChunk(world.getID(), chunk.getX(), chunk.getY())) {
                updateQueries.updateChunk(world.getID(), chunk.getX(), chunk.getY(), gson.toJson(chunk.save()));
            } else {
                insertQueries.insertChunk(world.getID(), chunk.getX(), chunk.getY(), gson.toJson(chunk.save()));
            }

            for (AbstractEntity entity : chunk.getEntities()) {
                if (entity instanceof StaticEntity && ((StaticEntity) entity).getEntityType() != null) {
                    if (containsQueries.containsEntity(world.getID(), entity.getEntityID())) {
                        updateQueries.updateEntity(((StaticEntity) entity).getEntityType(), entity.getCol(),
                                entity.getRow(), chunk.getX(), chunk.getY(), world.getID(),
                                gson.toJson(((StaticEntity) entity).save()), entity.getEntityID());
                    } else {
                        insertQueries.insertEntity(((StaticEntity) entity).getEntityType(), entity.getCol(),
                                entity.getRow(), chunk.getX(), chunk.getY(), world.getID(),
                                gson.toJson(((StaticEntity) entity).save()), entity.getEntityID());
                    }
                }
            }
            connection.commit();
        } catch (SQLException e) {
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
            Gson gson = new Gson();
            connection.setAutoCommit(false);
            // TODO:dannathan make this work for any savefile, not just the most recent
            // preparedStatement = connection.prepareStatement("SELECT * FROM SAVES");
            // result = preparedStatement.executeQuery();
            if (!result.next()) {
                connection.setAutoCommit(true);
                throw new SQLException();
            }

            long saveID = result.getLong("save_id");
            String data = result.getString("data");
            connection.setAutoCommit(true);
            Save.SaveMemento memento = gson.fromJson(data, Save.SaveMemento.class);

            Save save = new Save(memento);
            // TODO impelement loading the main character
            // loadMainCharacter(save);
            World currentWorld = loadWorlds(save, memento);
            save.setCurrentWorld(currentWorld);
            currentWorld.addEntity(MainCharacter.getInstance());
            save.setSaveID(saveID);

            return save;
        } catch (SQLException | LoadException e) {
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
            Gson gson = new Gson();
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
                String data = result.getString("data");
                boolean isCurrentWorld = result.getBoolean("is_current_world");
                long worldID = result.getLong("world_id");
                if (!isCurrentWorld && saveMemento.getWorldID() == worldID
                        || isCurrentWorld && saveMemento.getWorldID() != worldID) {
                    throw new LoadException();
                }
                World.WorldMemento memento = gson.fromJson(data, World.WorldMemento.class);
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
                String data = result.getString("data");
                String biomeType = result.getString("biome_type");
                AbstractBiome.AbstractBiomeMemento memento = gson.fromJson(data,
                        AbstractBiome.AbstractBiomeMemento.class);

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
        } finally {
            preparedStatement.close();
            result.close();
        }
    }

    public List<WorldGenNode> loadNodes(World world, List<AbstractBiome> biomes) throws SQLException, LoadException {
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        try {
            Gson gson = new Gson();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("SELECT * FROM nodes WHERE world_id = ?");
            preparedStatement.setLong(1, world.getID());
            result = preparedStatement.executeQuery();

            if (!result.next()) {
                connection.setAutoCommit(true);
                throw new LoadException();
            }

            List<WorldGenNode> nodes = new ArrayList<>();
            do {
                String data = result.getString("data");
                Long biomeID = result.getLong("biome_id");
                WorldGenNode.WorldGenNodeMemento memento = gson.fromJson(data, WorldGenNode.WorldGenNodeMemento.class);

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

            return nodes;
        } finally {
            preparedStatement.close();
            result.close();
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

            LinkedHashMap<VoronoiEdge, BeachBiome> edges = new LinkedHashMap<>();
            do {
                String data = result.getString("data");
                Long biomeID = result.getLong("biome_id");
                VoronoiEdge.VoronoiEdgeMemento memento = gson.fromJson(data, VoronoiEdge.VoronoiEdgeMemento.class);

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

            return edges;
        } finally {
            preparedStatement.close();
            result.close();
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
                VoronoiEdge.VoronoiEdgeMemento memento = gson.fromJson(data, VoronoiEdge.VoronoiEdgeMemento.class);

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
     * @return The chunk from the database if it exists in the database. A new chunk
     *         if the chunk does not exist in the database.
     * @throws SQLException
     */
    public Chunk loadChunk(World world, int x, int y) {
        try {
            Gson gson = new Gson();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM CHUNKS WHERE X = ? and Y = ? and WORLD_ID = ?");
            preparedStatement.setInt(1, x);
            preparedStatement.setInt(2, y);
            preparedStatement.setLong(3, world.getID());
            ResultSet result = preparedStatement.executeQuery();

            if (!result.next()) {
                Chunk chunk = new Chunk(world, x, y);
                chunk.generateEntities();
                preparedStatement.close();
                return chunk;
            }
            Chunk chunk = new Chunk(world, gson.fromJson(result.getString("DATA"), Chunk.ChunkMemento.class));

            // Gets the entities within the chunk from the database and add them to the
            // chunk
            PreparedStatement entityquery = connection
                    .prepareStatement("SELECT * FROM ENTITIES WHERE CHUNK_X = ? and CHUNK_Y = ? and WORLD_ID = ?");
            entityquery.setInt(1, x);
            entityquery.setInt(2, y);
            entityquery.setLong(3, world.getID());
            ResultSet entityResult = entityquery.executeQuery();

            while (entityResult.next()) {
                StaticEntity entity;
                StaticEntity.SaveableEntityMemento memento = gson.fromJson(entityResult.getString("data"),
                        StaticEntity.SaveableEntityMemento.class);
                switch (memento.entityType) {
                case "Bone":
                    entity = new Bone(memento);
                    break;
                case "DesertShrub":
                    entity = new DesertShrub(memento);
                    break;
                case "DesertCacti":
                    entity = new DesertCacti(memento);
                    break;
                case "DesertRock":
                    entity = new DesertRock(memento);
                    break;
                case "ForestMushroom":
                    entity = new ForestMushroom(memento);
                    break;
                case "ForestShrub":
                    entity = new ForestShrub(memento);
                    break;
                case "Leaves":
                    entity = new Leaves(memento);
                    break;
                case "TreeStump":
                    entity = new TreeStump(memento);
                    break;
                case "OrganicMound":
                    entity = new OrganicMound(memento);
                    break;
                case "MountainRock":
                    entity = new MountainRock(memento);
                    break;
                case "MountainTree":
                    entity = new MountainTree(memento);
                    break;
                case "ForestRock":
                    entity = new ForestRock(memento);
                    break;
                case "SnowClump":
                    entity = new SnowClump(memento);
                    break;
                case "SnowShrub":
                    entity = new SnowShrub(memento);
                    break;
                case "ForestTree":
                    entity = new ForestTree(memento);
                    break;
                case "SwampShrub":
                    entity = new SwampShrub(memento);
                    break;
                case "SwampRock":
                    entity = new SwampRock(memento);
                    break;
                case "SwampTree":
                    entity = new SwampTree(memento);
                    break;
                case "VolcanicShrub":
                    entity = new VolcanicShrub(memento);
                    break;
                case "VolcanicRock":
                    entity = new VolcanicRock(memento);
                    break;
                case "VolcanicTree":
                    entity = new VolcanicTree(memento);
                    break;
                default:
                    throw new RuntimeException(String.format("Could not create %s from memento", memento.entityType));
                }
                chunk.addEntity(entity);
            }

            preparedStatement.close();
            connection.setAutoCommit(true);
            return chunk;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
