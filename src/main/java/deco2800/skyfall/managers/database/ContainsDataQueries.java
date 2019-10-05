package deco2800.skyfall.managers.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Queries used to determine whether a database contains an item
 */
public class ContainsDataQueries {

    //Connection to the database
    private Connection connection;

    /**
     * Constructor for this class
     *
     * @param connection The connection to the database
     */
    ContainsDataQueries(Connection connection) {
        this.connection = connection;
    }

    /**
     * Determines if a database contains a save
     *
     * @param id The save being searched for
     * @return true if it does contain the save, false if it does not
     * @throws SQLException If an issue arises when performing the query
     */
    boolean containsSave(long id) throws SQLException {
        connection.setAutoCommit(false);
        boolean hasNext;
        try (PreparedStatement preparedStatement = connection
            .prepareStatement("SELECT * FROM SAVES WHERE save_id = ?")) {
            preparedStatement.setLong(1, id);
            try (ResultSet result = preparedStatement.executeQuery()) {
                // Has to be done before closing to ensure autocommit stays off
                hasNext = result.next();
            }
        }
        connection.setAutoCommit(true);
        return hasNext;
    }

    /**
     * Checks if a world is in a database
     *
     * @param saveId  The saveId of the world to be checked
     * @param worldId The id of the world to be checked
     * @return true if it does contain the save, false if it does not
     * @throws SQLException If an issue arises when performing the query
     */
    boolean containsWorld(long saveId, long worldId) throws SQLException {

        connection.setAutoCommit(false);
        boolean hasNext;
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM WORLDS WHERE save_id = ?"
            + " AND world_id "
            + "= ?")) {
            preparedStatement.setLong(1, saveId);
            preparedStatement.setLong(2, worldId);
            try (ResultSet result = preparedStatement.executeQuery()) {
                hasNext = result.next();
            }
        }
        connection.setAutoCommit(true);
        return hasNext;

    }

    /**
     * Checks if a main characters exists in the database
     *
     * @param id     The idea of the main character to be checked
     * @param saveId The save of the main character
     * @return true if it does contain the save, false if it does not
     * @throws SQLException If an issue arises when performing the query
     */
    public boolean containsMainCharacter(long id, long saveId) throws SQLException {

            connection.setAutoCommit(false);
        boolean hasNext;
        try (PreparedStatement preparedStatement = connection
            .prepareStatement("SELECT * FROM MAIN_CHARACTER WHERE character_id = ? AND save_id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, saveId);
            try (ResultSet result = preparedStatement.executeQuery()) {
                hasNext = result.next();
            }
        }
        connection.setAutoCommit(true);
            return hasNext;

    }

    /**
     * Checks if a node exists in the database
     *
     * @param worldId The id of the world where the node is
     * @param xPos    The x position of the chunk
     * @param yPos    The y position of the chunk
     * @return true if it does contain the save, false if it does not
     * @throws SQLException If an issue arises when performing the query
     */
    boolean containsNode(long worldId, double xPos, double yPos) throws SQLException {

            connection.setAutoCommit(false);
        boolean hasNext;
        try (PreparedStatement preparedStatement = connection
            .prepareStatement("SELECT * FROM NODES WHERE world_id = ? AND x_pos = ? AND y_pos = ?")) {
            preparedStatement.setLong(1, worldId);
            preparedStatement.setDouble(2, xPos);
            preparedStatement.setDouble(3, yPos);
            try (ResultSet result = preparedStatement.executeQuery()) {
                hasNext = result.next();
            }
        }
        connection.setAutoCommit(true);
            return hasNext;

    }

    /**
     * Checks if a edge exists in the database
     *
     * @param edgeID The id of the edge
     * @return true if it does contain the save, false if it does not
     * @throws SQLException If an issue arises when performing the query
     */
    boolean containsEdge(long edgeID) throws SQLException {

            connection.setAutoCommit(false);
        boolean hasNext;
        try (PreparedStatement preparedStatement = connection
            .prepareStatement("SELECT * FROM EDGES WHERE edge_id = ?")) {
            preparedStatement.setLong(1, edgeID);
            try (ResultSet result = preparedStatement.executeQuery()) {
                hasNext = result.next();
            }
        }
        connection.setAutoCommit(true);
            return hasNext;

    }

    /**
     * Checks if a biome exists in the database
     *
     * @param biomeId The id of the biome to be checked
     * @param worldId The id of the world where the biome is
     * @return true if it does contain the save, false if it does not
     * @throws SQLException If an issue arises when performing the query
     */
    boolean containsBiome(long biomeId, long worldId) throws SQLException {

            connection.setAutoCommit(false);
        boolean hasNext;
        try (PreparedStatement preparedStatement = connection
            .prepareStatement("SELECT * FROM BIOMES WHERE biome_id = ? AND world_id = ?")) {
            preparedStatement.setLong(1, biomeId);
            preparedStatement.setLong(2, worldId);
            try (ResultSet result = preparedStatement.executeQuery()) {
                hasNext = result.next();
            }
        }
        connection.setAutoCommit(true);
            return hasNext;

    }

    /**
     * Checks if a chunk exists in the database
     *
     * @param worldId The id of the world where the chunks exists
     * @param x       The x position of the chunk
     * @param y       The y position of the chunk
     * @return true if it does contain the save, false if it does not
     * @throws SQLException If an issue arises when performing the query
     */
    boolean containsChunk(long worldId, int x, int y) throws SQLException {

            connection.setAutoCommit(false);
        boolean hasNext;
        try (PreparedStatement preparedStatement = connection
            .prepareStatement("SELECT * FROM CHUNKS WHERE world_id = ? AND x = ? AND y = ?")) {
            preparedStatement.setLong(1, worldId);
            preparedStatement.setInt(2, x);
            preparedStatement.setInt(3, y);
            try (ResultSet result = preparedStatement.executeQuery()) {
                hasNext = result.next();
            }
        }
        connection.setAutoCommit(true);
            return hasNext;

    }

    /**
     * Checks if the database contains an entity
     *
     * @param worldId  The id of the world where the entity exists
     * @param entityId The id of the entity to be checked
     * @return true if it does contain the save, false if it does not
     * @throws SQLException If an issue arises when performing the query
     */
    boolean containsEntity(long worldId, long entityId) throws SQLException {

            connection.setAutoCommit(false);
        boolean hasNext;
        try (PreparedStatement preparedStatement = connection
            .prepareStatement("SELECT * FROM ENTITIES WHERE world_id = ? AND entity_id = ?")) {
            preparedStatement.setLong(1, worldId);
            preparedStatement.setLong(2, entityId);
            try (ResultSet result = preparedStatement.executeQuery()) {
                hasNext = result.next();
            }
        }
        connection.setAutoCommit(true);
            return hasNext;

    }
}
