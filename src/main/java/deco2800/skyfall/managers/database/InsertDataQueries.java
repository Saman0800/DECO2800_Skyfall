package deco2800.skyfall.managers.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertDataQueries {

    private Statement statement;

    public InsertDataQueries(Statement statement) {
        this.statement = statement;
    }

    public void insertSave(long id, String data) throws SQLException{
        String query = String.format("insert into SAVES (save_id, data) values (%s, '%s')", id, data);
        statement.executeUpdate(query);
    }

    public void insertWorld(long saveId, long worldId, boolean isCurrentWorld, String data) throws SQLException{
        String query = String.format("insert into WORLDS (save_id, world_id, is_current_world, data) values (%s,%s,"
                + "%s,'%s')", saveId, worldId, isCurrentWorld, data);
        statement.executeUpdate(query);
    }

    public void insertMainCharacter(long id, long saveId, String data) throws SQLException{
        String query = String.format("insert into MAIN_CHARACTER (character_id, save_id, data) values (%s,%s,'%s')", id
            , saveId, data);
        statement.executeUpdate(query);
    }

    public void insertNodes(long worldId, double xPos, double yPos, String data, long nodeId) throws SQLException{
        String query = String.format("insert into NODES (world_id, x_pos, y_pos, data, node_id) values (%s, %s, %s, "
                + "'%s',%s)", worldId, xPos, yPos, data, nodeId);
        statement.executeUpdate(query);
    }

    public void insertEdges(long worldID, long edgeID, int biomeID, String data) throws SQLException{
        String query = String.format("insert into EDGES (world_id, edge_id, biome_id, data) values (%s,%s,%s,'%s')", worldID,
            edgeID, biomeID, data);
        statement.executeUpdate(query);
    }

    public void insertBiome(int biomeId, int worldId, String biomeType, String tileGenerator, String data) throws SQLException{

        String query = String.format("insert into BIOMES (biome_id, world_id, biome_type, tile_generator, 'data) "
            + "values (%s,%s, '%s', '%s', '%s')", biomeId, worldId, biomeType, tileGenerator, data);
        statement.executeUpdate(query);
    }

    public void insertChunk(int worldId, int x, int y, String data) throws SQLException{
        String query = String.format("insert into CHUNKS (world_id, x, y, data) values (%s,%s,%s,'%s')", worldId, x, y,
            data);
        statement.executeUpdate(query);
    }

    public void insertEntity(String type, int x, int y, int chunkX, int chunkY, int worldId, String data) throws SQLException{
        String query = String.format("insert into ENTITIES (type, x, y, chunk_x, chunk_y, world_id, data) values (%s,"
                + "%s,%s,%s,%s,%s,'%s')", type, x, y, chunkX, chunkY, worldId, data);
        statement.executeUpdate(query);
    }
}