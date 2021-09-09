CREATE TABLE file (
    id bigint auto_increment primary key ,
    name varchar(20),
    category varchar(50),
    created_by varchar(50),
    created_date datetime,
    last_modified_by varchar(50),
    last_modified_date datetime
);

CREATE TABLE file_data (
    id bigint auto_increment primary key ,
    version int NOT NULL,
    file_id bigint NOT NULL,
    data longblob,
    created_by varchar(50),
    created_date datetime,
    last_modified_by varchar(50),
    last_modified_date datetime
)

