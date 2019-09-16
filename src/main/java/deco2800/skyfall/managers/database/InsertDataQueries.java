package deco2800.skyfall.managers.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Used to insert data into the database
 */
public class InsertDataQueries {

    private Connection connection;

    /**
     * Constructor for the InsertDataQueries
     * @param connection A connection to the database
     */
    public InsertDataQueries(Connection connection) {
        this.connection = connection;
    }

    /**
     * Inserts a save into the database
     * @param id The id os the save
     * @param data Other data related to the save
     * @throws SQLException If an error occurs in the sql insertion
     */
    public void insertSave(long id, String data) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("insert into SAVES (save_id, data) values (?, ?)");
        preparedStatement.setLong(1, id);
        preparedStatement.setString(2, data);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * Inserts a world into the database
     * @param saveId The save id of the save that the world is apart of
     * @param worldId The id of the world
     * @param isCurrentWorld If the world is the currennt world
     * @param data Other data related to the world
     * @throws SQLException If an error occurs in the sql insertion
     */
    public void insertWorld(long saveId, long worldId, boolean isCurrentWorld, String data) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into WORLDS (save_id, world_id, is_current_world, data) values (?,?,?,?)");
        preparedStatement.setLong(1, saveId);
        preparedStatement.setLong(2, worldId);
        preparedStatement.setBoolean(3, isCurrentWorld);
        preparedStatement.setString(4, data);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * Inserts a main character into the databaseo
     * @param id The id of the main character
     * @param saveId The save id
     * @param data Other data related to the main character stored in the character's memento
     * @throws SQLException
     */
    public void insertMainCharacter(long id, long saveId, String data) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("insert into MAIN_CHARACTER (character_id, save_id, data) values (?,?,?)");
        preparedStatement.setLong(1, id);
        preparedStatement.setLong(2, saveId);
        preparedStatement.setString(3, data);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * Inserts a node into the database
     * @param worldId, The world id
     * @param xPos, The x position of the node
     * @param yPos The y position of hte node
     * @param data Other data related to the node
     * @param nodeId The id of the node
     * @param biomeId The id of the biome that the node is in
     * @throws SQLException If an error occurs in the sql insertion
     */
    public void insertNodes(long worldId, double xPos, double yPos, String data, long nodeId, long biomeId) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("insert into NODES (world_id, x_pos, y_pos, data, node_id, biome_id) values "
                + "(?,?,?,?,?,?)");
        preparedStatement.setLong(1,worldId);
        preparedStatement.setDouble(2, xPos);
        preparedStatement.setDouble(3, yPos);
        preparedStatement.setString(4,data);
        preparedStatement.setLong(5,nodeId);
        preparedStatement.setLong(6, biomeId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     *
     * @param worldID The world id
     * @param edgeID The edge id
     * @param biomeID The biome id
     * @param data Other data related to the edge
     * @throws SQLException If an error occurs in the sql insertion
     */
    public void insertEdges(long worldID, long edgeID, long biomeID, String data) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("insert into EDGES (world_id, edge_id, biome_id, data) values (?, ?, ?, ?)");
        preparedStatement.setLong(1, worldID);
        preparedStatement.setLong(2, edgeID);
        preparedStatement.setLong(3, biomeID);
        preparedStatement.setString(4, data);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * @param biomeId The biome id
     * @param worldId The world id that the biome is in
     * @param biomeType The biome type
     * @param data Other data related to the world
     * @throws SQLException If an error occurs in the sql insertion
     */
    public void insertBiome(long biomeId, long worldId, String biomeType, String data) throws SQLException{

        PreparedStatement preparedStatement = connection.prepareStatement("insert into BIOMES (biome_id, world_id, biome_type, data) values (?, ?, ?, ?)");
        preparedStatement.setLong(1, biomeId);
        preparedStatement.setLong(2, worldId);
        preparedStatement.setString(3, biomeType);
        preparedStatement.setString(4, data);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * @param worldId The world id
     * @param x The row of the chunk
     * @param y The column the chunk
     * @param data Other data related to the chunk
     * @throws SQLException If an error occurs in the sql insertion
     */
    public void insertChunk(long worldId, int x, int y, String data) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("insert into CHUNKS (world_id, x, y, data) values (?, ?, ?, ?)");
        preparedStatement.setLong(1, worldId);
        preparedStatement.setInt(2, x);
        preparedStatement.setInt(3, y);
        preparedStatement.setString(4, data);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }


    /**
     *
     * @param type The type of the entity
     * @param x The x position of the entity
     * @param y The y position of the entity
     * @param chunkX The x position of the chunk that the entity is in
     * @param chunkY The y position of the chunk that the entity is in
     * @param worldId The world id
     * @param data Other data related to the entities
     * @param entityId The entitiy id
     * @throws SQLException If an error occurs in the sql insertion
     */
    public void insertEntity(String type, double x, double y, int chunkX, int chunkY, long worldId, String data, long entityId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into ENTITIES (type, x, y, chunk_x, chunk_y, world_id, data, entity_id) values (?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, type);
        preparedStatement.setDouble(2, x);
        preparedStatement.setDouble(3, y);
        preparedStatement.setInt(4, chunkX);
        preparedStatement.setInt(5, chunkY);
        preparedStatement.setLong(6, worldId);
        preparedStatement.setString(7, data);
        preparedStatement.setLong(8, entityId);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}