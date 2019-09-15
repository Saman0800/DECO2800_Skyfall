CREATE TABLE SAVES
(
    save_id       bigint not null,
    data clob,
    PRIMARY KEY (save_id)
);


CREATE TABLE WORLDS
(
    save_id bigint not null ,
    world_id bigint not null ,
    is_current_world boolean,
    data clob,
    primary key (world_id) ,
    foreign key (save_id) references SAVES(save_id)
);


CREATE TABLE MAIN_CHARACTER
(
    character_id        bigint NOT NULL,
    save_id             bigint not null ,
    data blob,
    PRIMARY KEY (character_id),
    FOREIGN KEY (save_id) references SAVES (save_id)
);

CREATE TABLE NODES
(
    world_id bigint not null ,
    x_pos bigint not null ,
    y_pos bigint not null ,
    data CLOB,
    node_id bigint,
    primary key (node_id),
    foreign key (world_id) references WORLDS(world_id)
);


CREATE TABLE EDGES
(
    node_one_id bigint,
    node_two_id bigint,
    primary key (node_one_id, node_two_id),
    foreign key (node_two_id) references NODES(node_id),
    foreign key (node_one_id) references NODES(node_id)
);


CREATE table BIOMES
(
    biome_id       bigint not null ,
    world_id       bigint not null ,
    biome_type     CLOB ,
    tile_generator blob not null ,
    data CLOB,
    primary key (biome_id, world_id),
    foreign key (world_id) references WORLDS(world_id)
);

CREATE TABLE CHUNKS
(
    world_id bigint not null ,
    x int not null ,
    y int not null ,
    data CLOB,
    PRIMARY KEY (world_id, x, y),
    FOREIGN KEY (world_id) references WORLDS(world_id)
);

CREATE TABLE ENTITIES
(
    type clob,
    x int not null ,
    y int not null ,
    chunk_x int not null,
    chunk_y int not null,
    world_id bigint not null,
    data clob,
    PRIMARY KEY (world_id,chunk_x, chunk_y, x, y),
    foreign key (world_id, chunk_x, chunk_y) references CHUNKS(world_id, x, y)
)




