--DROP DATABASE IF EXISTS postgres_testing ;

--CREATE DATABASE postgres_testing
--WITH OWNER = seu_usuario
--ENCODING = 'UTF8'
--LC_COLLATE = 'en_US.UTF-8'
--LC_CTYPE = 'en_US.UTF-8'
--TEMPLATE = template0;


CREATE TABLE social_action (
    id UUID PRIMARY KEY UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    version int8
);

CREATE TABLE ong (
    id UUID PRIMARY KEY UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    version int8
);
