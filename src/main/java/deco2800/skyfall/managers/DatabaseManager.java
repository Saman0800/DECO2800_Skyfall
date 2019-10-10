package deco2800.skyfall.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import deco2800.skyfall.entities.AbstractEntity;
import deco2800.skyfall.entities.AgentEntity;
import deco2800.skyfall.entities.MainCharacter;
import deco2800.skyfall.entities.StaticEntity;
import deco2800.skyfall.entities.worlditems.ForestRock;
import deco2800.skyfall.managers.database.DataBaseConnector;
import deco2800.skyfall.util.HexVector;
import deco2800.skyfall.worlds.Tile;
import deco2800.skyfall.worlds.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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
    public void startDataBaseConnector() {
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