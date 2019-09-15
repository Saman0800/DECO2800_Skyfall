package deco2800.skyfall.managers.database;

import java.util.ArrayList;

public class CreateTablesQueries {

    public String createSaveTableSql = "CREATE TABLE SAVES"
        + "("
        + "    save_id       bigint not null,"
        + "    data clob,"
        + "    PRIMARY KEY (save_id)"
        + ")";

    public String createWorldsTableSql = "CREATE TABLE WORLDS" +
            "(" +
            "    save_id bigint not null ," +
            "    world_id bigint not null ," +
            "    is_current_world boolean," +
            "    data clob," +
            "    primary key (world_id) ," +
            "    foreign key (save_id) references SAVES(save_id)" +
            ")";


    public String createMainCharacterTableSql = "CREATE TABLE MAIN_CHARACTER" +
            "(" +
            "    character_id        bigint NOT NULL," +
            "    save_id             bigint not null ," +
            "    data blob," +
            "    PRIMARY KEY (character_id)," +
            "    FOREIGN KEY (save_id) references SAVES (save_id)" +
            ")";

    public String createBiomesTableSql = "CREATE table BIOMES" +
            "(" +
            "    biome_id       bigint not null ," +
            "    world_id       bigint not null ," +
            "    biome_type     CLOB ," +
            "    data CLOB," +
            "    primary key (biome_id, world_id)," +
            "    foreign key (world_id) references WORLDS(world_id)" +
            ")";

    public String createNodesTableSql = "CREATE TABLE NODES" +
            "(" +
            "    node_id bigint not null," +
            "    world_id bigint not null ," +
            "    x_pos double not null ," +
            "    y_pos double not null ," +
            "    data CLOB," +
            "    biome_id bigint not null," +
            "    primary key (node_id)," +
            "    foreign key (world_id) references WORLDS(world_id)" +
            ")";

    public String createEdgesTableSql = "CREATE TABLE EDGES" +
            "(" +
            "    world_id bigint not null," +
            "    edge_id bigint not null," +
            "    biome_id bigint not null," +
            "    data clob," +
            "    primary key (edge_id)," +
            "    foreign key (biome_id, world_id) references BIOMES(biome_id, world_id)" +
            ")";



    public String createChunksTableSql = "CREATE TABLE CHUNKS" +
            "(" +
            "    world_id bigint not null ," +
            "    x int not null ," +
            "    y int not null ," +
            "    data CLOB," +
            "    PRIMARY KEY (world_id, x, y)," +
            "    FOREIGN KEY (world_id) references WORLDS(world_id)" +
            ")";

    public String createEntitiesSql = "CREATE TABLE ENTITIES" +
            "(" +
            "    entity_id bigint," +
            "    type clob," +
            "    x double not null ," +
            "    y double not null ," +
            "    chunk_x int not null," +
            "    chunk_y int not null," +
            "    world_id bigint not null," +
            "    data clob," +
            "    PRIMARY KEY (entity_id, world_id)," +
            "    foreign key (world_id, chunk_x, chunk_y) references CHUNKS(world_id, x, y)" +
            ")";


    public ArrayList<String> getQueries(){
        ArrayList<String> queries = new ArrayList<>();
        queries.add(createSaveTableSql);
        queries.add(createWorldsTableSql);
        queries.add(createMainCharacterTableSql);
        queries.add(createBiomesTableSql);
        queries.add(createNodesTableSql);
        queries.add(createEdgesTableSql);
        queries.add(createChunksTableSql);
        queries.add(createEntitiesSql);
        return queries;
    }

    public ArrayList<String> getTableNames(){
        ArrayList<String> tableNames = new ArrayList<>();
        tableNames.add("SAVES");
        tableNames.add("WORLDS");
        tableNames.add("MAIN_CHARACTER");
        tableNames.add("BIOMES");
        tableNames.add("NODES");
        tableNames.add("EDGES");
        tableNames.add("CHUNKS");
        tableNames.add("ENTITIES");
        return tableNames;
    }
}
