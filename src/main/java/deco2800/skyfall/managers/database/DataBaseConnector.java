package deco2800.skyfall.managers.database;

import com.google.gson.Gson;
import deco2800.skyfall.saving.Save;
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
        System.out.println(String.format("%s", true));
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
            long currentWorldId = world.getWorldId();

            //Saving the world
            insertQueries.insertWorld(saveId, world.getWorldId(),
                save.getCurrentWorld().getWorldId() == currentWorldId, gson.toJson(world.save()));






            //Looping through each world and saving it's data


            //TODO:Figuring out which world needs to be saved
        }
        statement.close();
    }



    public World loadWorld(long worldId) throws SQLException{
        String query = String.format("SELECT * FROM WORLDS WHERE WORLD_ID = %s", worldId);
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(query);

        if (!result.next()){
            return null;
        }

        //TODO load the nodes
        // Load the edges
        // Load the biomes
        // Add all this stuff to the world class


    }


    /**
     * Class used to load multiple chunks
     * @param x
     * @param y
     * @param world
     * @param radius
     * @return
     */
    public HashMap<Pair<Integer, Integer>, Chunk> dynamicLoadingChunks(int x, int y, World world, int radius){
        //Check if the chunk exists in the database
        //Numbers should be calculated on the chunk the player will be in, not the player coordinates
        for (int row = y-radius; row<y+radius; row++){
            for (int col = x - radius; col<x+radius; col++){

            }
        }

        return null;
    }

    public Chunk loadChunk(int x, int y, World world) throws SQLException{
        String query = String.format("SELECT * FROM CHUNKS WHERE X=%s and Y=%s and WORLD_ID=%s", x, y, world.getWorldId());
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(query);


//        //FIXME:jeffvan12 create the chunk if the chunk does not exist and return the chunk
//        if (!result.next()){
        statement.close();
        return new Chunk(world, x, y);


        //TODO:jeffvan12 else setup the chunks from the data

        //Adding the entities to the chunk




        //Create the chunk from the table data if it does exist in the game
    }


    public void  dynamicSavingChunks(){
    }







}
