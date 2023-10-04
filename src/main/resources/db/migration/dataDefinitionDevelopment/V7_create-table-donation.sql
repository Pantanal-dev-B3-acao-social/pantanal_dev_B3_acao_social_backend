CREATE TABLE donation(
    id UUID PRIMARY Key unique not null,
    donation_date DATE unique not null,
    value_money DOUBLE Not null,
    motivation varchar(255) Not null,
    version int8
);