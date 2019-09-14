package deco2800.skyfall.managers.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.derby.jdbc.EmbeddedDriver;

public class DataBaseConnector {

    //FIXME:jeffvan12 change to a method
    public static void main(String[] args) {
        DataBaseConnector dbConnector = new DataBaseConnector();
        dbConnector.run();
    }

    public void run(){
        Connection connection;
        System.out.println(String.format("%s", true));
        try {
            //Connects to the data base
            Driver derbyData = new EmbeddedDriver();
            DriverManager.registerDriver(derbyData);
            connection = DriverManager.getConnection("jdbc:derby:Database;create=true");

            createTables(connection);


            Statement statement = connection.createStatement();

            InsertDataQueries insertQueries = new InsertDataQueries(statement);


            connection.close();

            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Creates the table if they do not already exit
     * @param connection
     * @throws SQLException
     */
    public void createTables(Connection connection) throws SQLException {
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
    public void SaveWorld(){

    }
}
