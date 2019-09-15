package deco2800.skyfall.managers.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContainsDataQueries {
    private Connection connection;

    public ContainsDataQueries(Connection connection) {
        this.connection = connection;
    }

    public boolean containsSave(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM SAVES WHERE (save_id) = (?)");
        preparedStatement.setLong(1, id);
        ResultSet result = preparedStatement.executeQuery();
        preparedStatement.close();
        return result.next();
    }

    public boolean containsWorld(long saveId, long worldId) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM WORLDS WHERE (save_id, world_id) = (?,?)");
        preparedStatement.setLong(1, saveId);
        preparedStatement.setLong(2, worldId);
        ResultSet result = preparedStatement.executeQuery();
        preparedStatement.close();
        return result.next();
    }

    public boolean containsMainCharacter(long id, long saveId, String data) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM MAIN_CHARACTER WHERE (character_id, save_id) = (?,?)");
        preparedStatement.setLong(1, id);
        preparedStatement.setLong(2, saveId);
        ResultSet result = preparedStatement.executeQuery();
        preparedStatement.close();
        return result.next();
    }

    public boolean containsNode(long worldId, double xPos, double yPos) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM NODES WHERE (world_id, x_pos, y_pos) = (?,?,?) "
                + "(?,?,?,?,?,?)");
        preparedStatement.setLong(1,worldId);
        preparedStatement.setDouble(2, xPos);
        preparedStatement.setDouble(3, yPos);
        ResultSet result = preparedStatement.executeQuery();
        preparedStatement.close();
        return result.next();
    }

    public boolean containsEdge(long worldID, long edgeID) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM EDGES  WHERE (world_id, edge_id) = (?, ?)");
        preparedStatement.setLong(1, worldID);
        preparedStatement.setLong(2, edgeID);
        ResultSet result = preparedStatement.executeQuery();
        preparedStatement.close();
        return result.next();
    }

    public boolean containsBiome(long biomeId, long worldId) throws SQLException{

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM BIOMES WHERE (biome_id, world_id) = (?, ?)");
        preparedStatement.setLong(1, biomeId);
        preparedStatement.setLong(2, worldId);
        ResultSet result = preparedStatement.executeQuery();
        preparedStatement.close();
        return result.next();
    }

    public boolean containsChunk(long worldId, int x, int y) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM CHUNKS WHERE (world_id, x, y) = (?, ?, ?)");
        preparedStatement.setLong(1, worldId);
        preparedStatement.setInt(2, x);
        preparedStatement.setInt(3, y);
        ResultSet result = preparedStatement.executeQuery();
        preparedStatement.close();
        return result.next();
    }

    public boolean containsEntity(long worldId, long entityId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ENTITIES WHERE (world_id, entity_id) = (?, ?)");
        preparedStatement.setLong(1, worldId);
        preparedStatement.setLong(2, entityId);
        ResultSet result = preparedStatement.executeQuery();
        preparedStatement.close();
        return result.next();
    }
}
