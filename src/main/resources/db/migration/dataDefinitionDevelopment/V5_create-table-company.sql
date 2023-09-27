CREATE TABLE company(
    id UUID PRIMARY Key unique not null,
    name varchar(255) Not null,
    description varchar(255) Not null,
    cnpj varchar(18),
    version int8
);