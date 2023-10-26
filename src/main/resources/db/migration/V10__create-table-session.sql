CREATE TABLE session (
    id UUID NOT NULL PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    time TIMESTAMP,
    status VARCHAR(255),
    visibility VARCHAR(255),
    created_by UUID,
    last_modified_by UUID,
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP,
    deleted_date TIMESTAMP,
    deleted_by UUID,
    social_action_id UUID NOT NULL,
    version BIGINT,
    engagement_score INT,

    FOREIGN KEY (social_action_id)
    REFERENCES social_action(id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);
