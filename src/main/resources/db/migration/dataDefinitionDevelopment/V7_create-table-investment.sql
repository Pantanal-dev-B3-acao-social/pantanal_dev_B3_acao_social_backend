create table investment(
    id UUID PRIMARY KEY UNIQUE NOT NULL,
    value_money int8 NOT NULL,
    date_ TIMESTAMP NOT NULL,
    motivation VARCHAR(255) NOT NULL,
    approved_by TIMESTAMP NOT NULL,
    version int8,
    created_by UUID,
    last_modified_by UUID,
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP,
    deleted_date TIMESTAMP,
    deleted_by UUID,
    social_action_id UUID NOT NULL,

    Foreign key (social_action_id) REFERENCES social_action(id)
)