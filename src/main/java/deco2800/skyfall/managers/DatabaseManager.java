package deco2800.skyfall.managers;

import deco2800.skyfall.managers.database.DataBaseConnector;

public final class DatabaseManager extends AbstractManager {

    // The current instance of the DataBaseManager
    private static DatabaseManager instance = null;

    // An instance of DataBaseConnector that is used to connect and manage data in
    // the database
    private static DataBaseConnector dataBaseConnector;

    private DatabaseManager() {
        /*
         * This constructor is not called, but added to deal with the: Add a private
         * constructor to hide the implicit public one. code smell
         */
    }

    /**
     * Gets the current DatabaseManger, and if it does exist create it and return it
     *
     * @return A DataBaseManager
     */
    public static DatabaseManager get() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }


    /**
     * Creates and starts and new DataBaseConnector
     */
    public static void startDataBaseConnector() {
        dataBaseConnector = new DataBaseConnector();
        dataBaseConnector.start("Database");
    }

    /**
     * Closes the connection of the DataBaseConnector to the database
     */
    public void closeDataBaseConnector() {
        dataBaseConnector.close();
    }

    /**
     * Gets the Database connector
     *
     * @return The database connector
     */
    public DataBaseConnector getDataBaseConnector() {
        return dataBaseConnector;
    }

}