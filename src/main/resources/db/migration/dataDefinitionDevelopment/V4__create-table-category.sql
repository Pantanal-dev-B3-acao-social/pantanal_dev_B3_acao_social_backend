CREATE TABLE category (
    id UUID PRIMARY KEY UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    code VARCHAR(255) NOT NULL,
    version int8
);
