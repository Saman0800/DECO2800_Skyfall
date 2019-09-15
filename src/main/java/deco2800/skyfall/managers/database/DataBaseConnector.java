package deco2800.skyfall.managers.database;

import com.google.gson.Gson;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.saving.Save;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.generation.VoronoiEdge;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode;
import deco2800.skyfall.worlds.world.Chunk;
import deco2800.skyfall.worlds.world.World;
import org.apache.derby.jdbc.EmbeddedDriver;

import java.sql.*;
import java.util.ArrayList;

public class DataBaseConnector {
    private Connection connection;

    //FIXME:jeffvan12 change to a method
    public static void main(String[] args) {
        DataBaseConnector dbConnector = new DataBaseConnector();
        dbConnector.start();
        dbConnector.close();
    }

    public void start(){
        try {
            //Connects to the data base
            Driver derbyData = new EmbeddedDriver();
            DriverManager.registerDriver(derbyData);
            connection = DriverManager.getConnection("jdbc:derby:Database;create=true");

            createTables();


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void close() {
        try {
            connection.close();
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * Creates the table if they do not already exit
     * @throws SQLException
     */
    public void createTables() throws SQLException {
        //If there are any missing tables, then drop all the tables and add them all back
        //other wise don't do that

        CreateTablesQueries queries = new CreateTablesQueries();
        DatabaseMetaData dbm = connection.getMetaData();
        ResultSet tables = dbm.getTables(null,null,"%", null);
        ArrayList<String> tablesCheck = queries.getTableNames();
        Statement statement = connection.createStatement();

        ArrayList<String> foundTables = new ArrayList<>();
        while (tables.next()){
            if (!tables.getString(3).startsWith("SYS")){
                foundTables.add(tables.getString(3));
            }
        }

        if (foundTables.size() != tablesCheck.size()){
            for (String toDelete : foundTables){
                statement.execute(String.format("DROP TABLE %s", toDelete));
            }
            for (String query : queries.getQueries()){
                statement.execute(query);
            }
        }
    }

    public void saveGame(Save save) {
        try {
            //Given a save game
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

            //Looping through the worlds in the save and saving them
            for (World world : save.getWorlds()) {
                saveWorld(world);
                //TODO:Figuring out which world needs to be saved
            }
            saveMainCharacter(save.getMainCharacter());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Svaing the world and its parameters
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
                    updateQueries.updateBiome(biome.getBiomeID(), world.getID(), biome.getBiomeName(), gson.toJson(biome.save()));
                } else {
                    insertQueries.insertBiome(biome.getBiomeID(), world.getID(), biome.getBiomeName(), gson.toJson(biome.save()));
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
                if (containsQueries.containsEdge(world.getID(), voronoiEdge.getID())) {
                    updateQueries.updateEdges(world.getID(), voronoiEdge.getID(),
                            world.getBeachEdges().get(voronoiEdge).getBiomeID(),
                            gson.toJson(voronoiEdge.save()));
                } else {
                    insertQueries.insertEdges(world.getID(), voronoiEdge.getID(),
                            world.getBeachEdges().get(voronoiEdge).getBiomeID(),
                            gson.toJson(voronoiEdge.save()));
                }
            }

            // Save river edges
            for (VoronoiEdge voronoiEdge : world.getRiverEdges().keySet()) {
                if (containsQueries.containsEdge(world.getID(), voronoiEdge.getID())) {
                    updateQueries.updateEdges(world.getID(), voronoiEdge.getID(),
                            world.getRiverEdges().get(voronoiEdge).getBiomeID(),
                            gson.toJson(voronoiEdge.save()));
                } else {
                    insertQueries.insertEdges(world.getID(), voronoiEdge.getID(),
                            world.getRiverEdges().get(voronoiEdge).getBiomeID(),
                            gson.toJson(voronoiEdge.save()));
                }
            }

            for (Chunk chunk : world.getLoadedChunks().values()) {
                saveChunk(chunk);
            }
    }

    public void saveMainCharacter(MainCharacter character) throws SQLException {
            ContainsDataQueries containsQueries = new ContainsDataQueries(connection);
            InsertDataQueries insertQueries = new InsertDataQueries(connection);
            UpdateDataQueries updateQueries = new UpdateDataQueries(connection);
            Gson gson = new Gson();


            if (containsQueries.containsMainCharacter(character.getID(), character.getSave().getSaveID())) {
                updateQueries.updateMainCharacter(character.getID(), character.getSave().getSaveID(), gson.toJson(character.save()));
            } else {
                insertQueries.insertMainCharacter(character.getID(), character.getSave().getSaveID(), gson.toJson(character.save()));
            }
    }

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
                if (entity instanceof StaticEntity) {
                    if (containsQueries.containsEntity(world.getID(), entity.getEntityID())) {
                        updateQueries.updateEntity(((StaticEntity) entity).getEntityType(), entity.getCol(), entity.getRow(),
                                chunk.getX(), chunk.getY(), world.getID(), gson.toJson(((StaticEntity) entity).save()), entity.getEntityID());
                    } else {
                        insertQueries.insertEntity(((StaticEntity) entity).getEntityType(), entity.getCol(), entity.getRow(),
                                chunk.getX(), chunk.getY(), world.getID(), gson.toJson(((StaticEntity) entity).save()), entity.getEntityID());
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public World loadWorld(long worldId, Save save) throws SQLException{
//        String query = String.format("SELECT * FROM WORLDS WHERE WORLD_ID = %s", worldId);
//
//        Statement statement = connection.createStatement();
//        ResultSet result = statement.executeQuery(query);
//
//        if (!result.next()){
//            return null;
//        }
//
//        Gson gson = new Gson();
//
//        World world = new World(gson.fromJson(result.getString(4), World.WorldMemento.class), save);
//    }

    /**
     * Loads a chunk
     * @param world The world where the chunk is
     * @param x The x position of the chunk
     * @param y The y positoin of the chunk
     * @return The chunk from the database if it exists in the database. A new chunk if the chunk does not exist in the database.
     * @throws SQLException
     */
    public Chunk loadChunk(World world,int x, int y){
        try{
            Gson gson = new Gson();
            //TODO:jeffvan12 Make it so it uses a preparedStatement
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM CHUNKS WHERE X = ? and Y = ? and WORLD_ID = ?");
            preparedStatement.setInt(1,x);
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

            PreparedStatement entityquery = connection.prepareStatement("SELECT * FROM ENTITIES WHERE CHUNK_X = ? and CHUNK_Y = ?");
//            entityquery.setInt(chunk.getX());
//            ResultSet entityResult =



            preparedStatement.close();
            return chunk;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
