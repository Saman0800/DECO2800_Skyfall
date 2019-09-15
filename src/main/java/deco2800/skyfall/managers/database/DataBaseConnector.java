package deco2800.skyfall.managers.database;

import com.google.gson.Gson;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.saving.Save;
import deco2800.skyfall.saving.Saveable;
import deco2800.skyfall.worlds.biomes.AbstractBiome;
import deco2800.skyfall.worlds.generation.VoronoiEdge;
import deco2800.skyfall.worlds.generation.delaunay.WorldGenNode;
import deco2800.skyfall.worlds.world.Chunk;
import deco2800.skyfall.worlds.world.World;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import org.javatuples.Pair;
import org.apache.derby.jdbc.EmbeddedDriver;

public class DataBaseConnector {
    Connection connection;

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

    //FIXME:jeffvan12 Adding saving and updating functionality
    //TODO:jeffvan change it so it doesn't take a connection
    public void saveGame(Save save) throws SQLException{
        //Given a save game
        long saveId = save.getSaveID();
        Statement statement = connection.createStatement();

        InsertDataQueries insertQueries = new InsertDataQueries(statement);
        Gson gson = new Gson();


        insertQueries.insertSave(saveId, gson.toJson(save.save()));


        //Looping through the worlds in the save and saving them
        for (World world : save.getWorlds()){
            saveWorld(world);
            //TODO:Figuring out which world needs to be saved
        }
        saveMainCharacter(save.getMainCharacter());
        statement.close();
    }

    //Svaing the world and its parameters
    public void saveWorld(World world) throws SQLException{
        Statement statement = connection.createStatement();
        InsertDataQueries insertQueries = new InsertDataQueries(statement);
        Gson gson = new Gson();

        insertQueries.insertWorld(world.getSave().getSaveID(), world.getID(),
                world.getSave().getCurrentWorld().getID() == world.getID(), gson.toJson(world.save()));

        for (AbstractBiome biome : world.getBiomes()) {
            System.out.println("here0");
            try {
                insertQueries.insertBiome(biome.getBiomeID(), world.getID(), biome.getBiomeName(), gson.toJson(biome.save()));
            } catch (SQLException e) {
                System.out.println(e);
                throw e;
            }
        }

        // Save nodes
        for (WorldGenNode worldGenNode : world.getWorldGenNodes()) {
            System.out.println("here1");
            try {
                insertQueries.insertNodes(world.getID(), worldGenNode.getX(), worldGenNode.getY(),
                        gson.toJson(worldGenNode.save()), worldGenNode.getID(), worldGenNode.getBiome().getBiomeID());
            } catch (SQLException e) {
                System.out.println(e);
                throw e;
            }
        }

        // Save beach edges
        for (VoronoiEdge voronoiEdge : world.getBeachEdges().keySet()) {
            System.out.println("here2");
            insertQueries.insertEdges(world.getID(), voronoiEdge.getID(),
                    world.getBeachEdges().get(voronoiEdge).getBiomeID(),
                    gson.toJson(voronoiEdge.save()));
        }

        // Save river edges
        for (VoronoiEdge voronoiEdge : world.getRiverEdges().keySet()) {
            insertQueries.insertEdges(world.getID(), voronoiEdge.getID(),
                    world.getRiverEdges().get(voronoiEdge).getBiomeID(),
                    gson.toJson(voronoiEdge.save()));
        }

        for (Chunk chunk : world.getLoadedChunks().values()) {
            System.out.println("here3");
            saveChunk(chunk, world);
        }

        statement.close();

    }

    public void saveMainCharacter(MainCharacter character) throws SQLException {
        Statement statement = connection.createStatement();
        InsertDataQueries insertQueries = new InsertDataQueries(statement);
        Gson gson = new Gson();

        insertQueries.insertMainCharacter(character.getID(), character.getSave().getSaveID(), gson.toJson(character.save()));



        statement.close();
    }

    public void saveChunk(Chunk chunk, World world) throws SQLException {
        Statement statement = connection.createStatement();
        InsertDataQueries insertQueries = new InsertDataQueries(statement);
        Gson gson = new Gson();

        insertQueries.insertChunk(world.getID(), chunk.getX(), chunk.getY(), gson.toJson(chunk.save()));

        for (AbstractEntity entity : chunk.getEntities()) {
            if (entity instanceof StaticEntity) {
                insertQueries.insertEntity(((StaticEntity)entity).getEntityType(), entity.getCol(), entity.getRow(),
                        chunk.getX(), chunk.getY(), world.getID(), gson.toJson(((StaticEntity)entity).save()), entity.getEntityID());
            }
        }

        statement.close();
    }


//    public World loadWorld(long worldId) throws SQLException{
//        String query = String.format("SELECT * FROM WORLDS WHERE WORLD_ID = %s", worldId);
//        Statement statement = connection.createStatement();
//        ResultSet result = statement.executeQuery(query);
//
//        if (!result.next()){
//            return null;
//        }
//
//        //TODO load the nodes
//        // Load the edges
//        // Load the biomes
//        // Add all this stuff to the world class
//
//
//        return new World();
//
//    }





    /**
     * Class used to load multiple chunks
     * @param x
     * @param y
     * @param world
     * @param radius
     * @return
     */
    public HashMap<Pair<Integer, Integer>, Chunk> loadChunks(int x, int y, World world, int radius){
        //Check if the chunk exists in the database
        //Numbers should be calculated on the chunk the player will be in, not the player coordinates
        for (int row = y-radius; row<y+radius; row++){
            for (int col = x - radius; col<x+radius; col++){

            }
        }

        return null;
    }

    public Chunk loadChunk(World world,int x, int y) throws SQLException{

        Gson gson = new Gson();


        String query = String.format("SELECT * FROM CHUNKS WHERE X=%s and Y=%s and WORLD_ID=%s", x, y, world.getID());
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(query);


//        //FIXME:jeffvan12 create the chunk if the chunk does not exist and return the chunk
        if (!result.next()) {
            Chunk chunk = new Chunk(world, x, y);
            chunk.generateEntities();
            statement.close();
            return chunk;
        }

        return new Chunk(gson.fromJson(result.getString(4), Chunk.ChunkMemento.class), world);


        //TODO:jeffvan12 else setup the chunks from the data
        //Adding the entities to the chunk
        //Create the chunk from the table data if it does exist in the game
    }



    public static Chunk loadChunkAt(World world, int x, int y) {
        // FIXME:Ontonator Implement.
        Chunk chunk = new Chunk(world, x, y);
        chunk.generateEntities();
        return chunk;
    }





    public void  dynamicSavingChunks(){
    }







}
