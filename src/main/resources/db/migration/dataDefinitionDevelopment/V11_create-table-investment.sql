create table investment(
    id UUID PRIMARY KEY UNIQUE NOT NULL,
    value_money NUMERIC NOT NULL,
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
    company_id UUID NOT NULL,

    Foreign key (social_action_id) REFERENCES social_action(id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    Foreign key (company_id) REFERENCES company(id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
