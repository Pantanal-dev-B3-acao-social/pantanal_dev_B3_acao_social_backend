-- V1__create_category_group_table.sql

-- Cria a tabela category_group
CREATE TABLE category_group (
    id UUID PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    code VARCHAR(255) NOT NULL,
    category_group_id UUID,
    visibility VARCHAR(255)
--    ,
--    created_by UUID,
--    last_modified_by UUID,
--    created_date TIMESTAMP,
--    last_modified_date TIMESTAMP,
--    deleted_date TIMESTAMP,
--    deleted_by UUID
);

-- Adiciona a restrição de chave estrangeira para category_group_id
ALTER TABLE category_group
ADD CONSTRAINT fk_category_group_category_group_id
FOREIGN KEY (category_group_id)
REFERENCES category_group (id);

-- Cria um índice na coluna category_group_id para melhor desempenho em consultas
CREATE INDEX idx_category_group_category_group_id
ON category_group (category_group_id);
