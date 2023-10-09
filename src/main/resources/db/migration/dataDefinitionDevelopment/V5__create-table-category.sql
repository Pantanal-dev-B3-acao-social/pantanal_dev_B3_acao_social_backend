-- V2__create_category_table.sql

-- Cria a tabela category
CREATE TABLE category (
    id UUID PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    code VARCHAR(255) NOT NULL,
    visibility VARCHAR(255),
    created_by UUID,
    last_modified_by UUID,
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP,
    deleted_date TIMESTAMP,
    deleted_by UUID,
    category_group_id UUID NOT NULL
);

-- Adiciona a restrição de chave estrangeira para category_group_id
ALTER TABLE category
ADD CONSTRAINT fk_category_category_group_id
FOREIGN KEY (category_group_id)
REFERENCES category_group (id);

-- Cria um índice na coluna created_by para melhor desempenho em consultas
CREATE INDEX idx_category_created_by
ON category (created_by);

-- Cria um índice na coluna last_modified_by para melhor desempenho em consultas
CREATE INDEX idx_category_last_modified_by
ON category (last_modified_by);
