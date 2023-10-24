-- V3__create_person_table.sql

-- Cria a tabela person
CREATE TABLE person (
    id UUID PRIMARY KEY NOT NULL,
    user_by UUID NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    date_birth TIMESTAMP,
    status VARCHAR(255),
    cpf VARCHAR(14) NOT NULL UNIQUE,
    created_by UUID,
    created_date TIMESTAMP,
    last_modified_by UUID,
    last_modified_date TIMESTAMP,
    deleted_date TIMESTAMP,
    deleted_by UUID,
    version BIGINT
);