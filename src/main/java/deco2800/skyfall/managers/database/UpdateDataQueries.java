package deco2800.skyfall.managers.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateDataQueries {

    private Connection connection;

    public UpdateDataQueries(Connection connection) {
        this.connection = connection;
    }

    /**
     * Updates the save table
     *
     * @param id the save id
     * @param data misc. data about the save
     * @throws SQLException if the update could not be performed
     */
    public void updateSave(long id, String data) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE SAVES SET data = ? WHERE save_id = ?");
        preparedStatement.setString(1, data);
        preparedStatement.setLong(2, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * Updates the world table
     *
     * @param saveId the save id
     * @param worldId the world id
     * @param isCurrentWorld whether or not this is the current world
     * @param data misc. data about the world
     * @throws SQLException if the update could not be performed
     */
    public void updateWorld(long saveId, long worldId, boolean isCurrentWorld, String data) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE WORLDS SET is_current_world = ?, data = ? WHERE save_id = ? AND world_id = ?");
        preparedStatement.setBoolean(1, isCurrentWorld);
        preparedStatement.setString(2, data);
        preparedStatement.setLong(3, saveId);
        preparedStatement.setLong(4, worldId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * Updates the main character table
     *
     * @param id the character's id
     * @param saveId the save's id
     * @param data misc. data about the character
     * @throws SQLException if the update could not be performed
     */
    public void updateMainCharacter(long id, long saveId, String data) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE MAIN_CHARACTER SET data = ? WHERE character_id = ? AND save_id = ?");
        preparedStatement.setString(1, data);
        preparedStatement.setLong(2, id);
        preparedStatement.setLong(3, saveId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * Updates the nodes table
     *
     * @param worldId the world ID of the node
     * @param xPos the x position of the node
     * @param yPos the y position of the node
     * @param data misc. data about the node
     * @param nodeId the id of the node
     * @param biomeId the id of the node's biome
     * @throws SQLException if the update could not be performed
     */
    public void updateNodes(long worldId, double xPos, double yPos, String data, long nodeId, long biomeId) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE NODES SET data = ?, node_id = ?, biome_id = ? WHERE world_id = ? AND x_pos = ? AND y_pos = ?");
        preparedStatement.setLong(4,worldId);
        preparedStatement.setDouble(5, xPos);
        preparedStatement.setDouble(6, yPos);
        preparedStatement.setString(1,data);
        preparedStatement.setLong(2,nodeId);
        preparedStatement.setLong(3, biomeId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * Updates the edges table
     *
     * @param worldID the id of the edge's world
     * @param edgeID the id of the edge
     * @param biomeID the id of the edge's biome
     * @param data misc. data about the edge
     * @throws SQLException if the update could not be performed
     */
    public void updateEdges(long worldID, long edgeID, long biomeID, String data) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE EDGES SET biome_id = ?, data = ? WHERE world_id = ? AND edge_id = ?");
        preparedStatement.setLong(3, worldID);
        preparedStatement.setLong(4, edgeID);
        preparedStatement.setLong(1, biomeID);
        preparedStatement.setString(2, data);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * Updates the biomes table
     *
     * @param biomeId the id of the biome
     * @param worldId the id of the biome's world
     * @param biomeType the type of biome this is
     * @param data misc. data about the biome
     * @throws SQLException if the update could not be performed
     */
    public void updateBiome(long biomeId, long worldId, String biomeType, String data) throws SQLException{

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE BIOMES SET biome_type = ?, data = ? WHERE biome_id = ? AND world_id = ?");
        preparedStatement.setLong(3, biomeId);
        preparedStatement.setLong(4, worldId);
        preparedStatement.setString(1, biomeType);
        preparedStatement.setString(2, data);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * Updates the chunks table
     *
     * @param worldId the id of the chunk's world
     * @param x the x position of the chunk in chunk coords
     * @param y the y position of the chunk in chunk coords
     * @param data misc. data about the chunk
     * @throws SQLException if the update could not be performed
     */
    public void updateChunk(long worldId, int x, int y, String data) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE CHUNKS SET data = ? WHERE world_id = ? AND x = ? AND y = ?");
        preparedStatement.setLong(2, worldId);
        preparedStatement.setInt(3, x);
        preparedStatement.setInt(4, y);
        preparedStatement.setString(1, data);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * Updates the entitiy table
     *
     * @param type the type of entity this is
     * @param x the x position of the entity
     * @param y the y position of the entity
     * @param chunkX the entity's chunk's x position
     * @param chunkY the entity's chunk's y position
     * @param worldId the id of the entity's world
     * @param data misc. data about the entity
     * @param entityId the id of the entity
     * @throws SQLException if the update could not be performed
     */
    public void updateEntity(String type, double x, double y, int chunkX, int chunkY, long worldId, String data, long entityId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE ENTITIES SET type = ?, x = ?, y = ?, chunk_x = ?, chunk_y = ?, data = ? WHERE world_id = ? AND entity_id = ?");
        preparedStatement.setString(1, type);
        preparedStatement.setDouble(2, x);
        preparedStatement.setDouble(3, y);
        preparedStatement.setInt(4, chunkX);
        preparedStatement.setInt(5, chunkY);
        preparedStatement.setLong(7, worldId);
        preparedStatement.setString(6, data);
        preparedStatement.setLong(8, entityId);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
