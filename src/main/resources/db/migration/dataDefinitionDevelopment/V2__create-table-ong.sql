CREATE TABLE ong (
    id UUID PRIMARY KEY UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    version int8
);
