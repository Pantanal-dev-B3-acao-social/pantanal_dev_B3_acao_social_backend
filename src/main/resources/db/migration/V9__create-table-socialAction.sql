-- Cria a tabela social_action
CREATE TABLE social_action (
    id UUID PRIMARY KEY NOT NULL,
    name VARCHAR(255) ,
    description VARCHAR(255) ,
    created_by UUID,
    last_modified_by UUID,
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP,
    deleted_date TIMESTAMP,
    deleted_by UUID,
    ong_id UUID,
    Foreign key (ong_id) references ong (id)
);

-- Cria um índice na coluna last_modified_by para melhor desempenho em consultas
CREATE INDEX idx_social_action_last_modified_by
ON social_action (last_modified_by);
