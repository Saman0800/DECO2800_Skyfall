package deco2800.skyfall.managers.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateDataQueries {

    private Connection connection;

    public UpdateDataQueries(Connection connection) {
        this.connection = connection;
    }

    public void updateSave(long id, String data) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE SAVES SET data = ? WHERE save_id = ?");
        preparedStatement.setString(1, data);
        preparedStatement.setLong(2, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void updateWorld(long saveId, long worldId, boolean isCurrentWorld, String data) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE WORLDS SET is_current_world = ?, data = ? WHERE save_id = ? AND world_id = ?");
        preparedStatement.setBoolean(1, isCurrentWorld);
        preparedStatement.setString(2, data);
        preparedStatement.setLong(3, saveId);
        preparedStatement.setLong(4, worldId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void updateMainCharacter(long id, long saveId, String data) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE MAIN_CHARACTER SET data = ? WHERE character_id = ? AND save_id = ?");
        preparedStatement.setString(1, data);
        preparedStatement.setLong(2, id);
        preparedStatement.setLong(3, saveId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

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

    public void updateEdges(long worldID, long edgeID, long biomeID, String data) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE EDGES SET biome_id = ?, data = ? WHERE world_id = ? AND edge_id = ?");
        preparedStatement.setLong(3, worldID);
        preparedStatement.setLong(4, edgeID);
        preparedStatement.setLong(1, biomeID);
        preparedStatement.setString(2, data);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void updateBiome(long biomeId, long worldId, String biomeType, String data) throws SQLException{

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE BIOMES SET biome_type = ?, data = ? WHERE biome_id = ? AND world_id = ?");
        preparedStatement.setLong(3, biomeId);
        preparedStatement.setLong(4, worldId);
        preparedStatement.setString(1, biomeType);
        preparedStatement.setString(2, data);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void updateChunk(long worldId, int x, int y, String data) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE CHUNKS SET data = ? WHERE world_id = ? AND x = ? AND y = ?");
        preparedStatement.setLong(2, worldId);
        preparedStatement.setInt(3, x);
        preparedStatement.setInt(4, y);
        preparedStatement.setString(1, data);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

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
