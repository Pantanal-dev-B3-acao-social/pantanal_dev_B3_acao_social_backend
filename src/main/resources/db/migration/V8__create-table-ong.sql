-- Create table for OngEntity
CREATE TABLE ong (
    id UUID NOT NULL PRIMARY KEY,
    version BIGINT,
    name VARCHAR(255) NOT NULL,
    cnpj VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(255),
    created_by UUID,
    last_modified_by UUID,
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP,
    deleted_date TIMESTAMP,
    deleted_by UUID,
    responsibility_id UUID NOT NULL,
    FOREIGN KEY (responsibility_id) REFERENCES person (id)
);

-- Create index on cnpj for uniqueness constraint
CREATE UNIQUE INDEX idx_ong_cnpj ON ong (cnpj);
