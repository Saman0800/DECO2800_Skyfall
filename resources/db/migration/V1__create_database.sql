create table SAVES
(
    SAVE_ID BIGINT not null
        primary key,
    DATA    BLOB
);

create table MAIN_CHARACTER
(
    CHARACTER_ID BIGINT not null
        primary key,
    SAVE_ID      BIGINT not null
        references SAVES,
    DATA         BLOB
);

create table WORLDS
(
    SAVE_ID          BIGINT not null
        references SAVES,
    WORLD_ID         BIGINT not null
        primary key,
    IS_CURRENT_WORLD BOOLEAN,
    DATA             BLOB
);

create table BIOMES
(
    BIOME_ID   BIGINT not null,
    WORLD_ID   BIGINT not null
        references WORLDS,
    BIOME_TYPE CLOB,
    DATA       BLOB,
    primary key (BIOME_ID, WORLD_ID)
);

create table CHUNKS
(
    WORLD_ID BIGINT  not null
        references WORLDS,
    X        INTEGER not null,
    Y        INTEGER not null,
    DATA     BLOB,
    primary key (WORLD_ID, X, Y)
);

create table EDGES
(
    WORLD_ID BIGINT not null,
    EDGE_ID  BIGINT not null
        primary key,
    BIOME_ID BIGINT not null,
    DATA     BLOB,
    foreign key (BIOME_ID, WORLD_ID) references BIOMES
);

create table ENTITIES
(
    ENTITY_ID BIGINT  not null,
    TYPE      CLOB,
    X         DOUBLE  not null,
    Y         DOUBLE  not null,
    CHUNK_X   INTEGER not null,
    CHUNK_Y   INTEGER not null,
    WORLD_ID  BIGINT  not null,
    DATA      BLOB,
    primary key (ENTITY_ID, WORLD_ID),
    foreign key (WORLD_ID, CHUNK_X, CHUNK_Y) references CHUNKS
);

create table NODES
(
    NODE_ID  BIGINT not null
        primary key,
    WORLD_ID BIGINT not null
        references WORLDS,
    X_POS    DOUBLE not null,
    Y_POS    DOUBLE not null,
    DATA     BLOB,
    BIOME_ID BIGINT not null,
    foreign key (BIOME_ID, WORLD_ID) references WORLDS (BIOME_ID, WORLD_ID)
);
