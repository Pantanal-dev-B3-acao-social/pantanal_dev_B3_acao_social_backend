-- V1__create_donation_table.sql

CREATE TABLE donation (
    id UUID PRIMARY KEY,
    social_action_id UUID,
    donated_by UUID,
    donation_date TIMESTAMP NOT NULL,
    value_money NUMERIC(10, 2) NOT NULL,
    motivation VARCHAR(255) NOT NULL,
    approved_by UUID,
    approved_date TIMESTAMP,
    created_by UUID NOT NULL,
    last_modified_by UUID,
    created_date TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP,
    deleted_date TIMESTAMP,
    deleted_by UUID,
    FOREIGN KEY (social_action_id) REFERENCES social_action(id),
    FOREIGN KEY (donated_by) REFERENCES person(id),
    FOREIGN KEY (approved_by) REFERENCES person(id)
);
