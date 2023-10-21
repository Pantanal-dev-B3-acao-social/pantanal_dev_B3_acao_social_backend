CREATE TABLE voluntary (
    id UUID PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 1,
    observation TEXT,
    social_action_id UUID NOT NULL,
    person_id UUID NOT NULL,
    approved_by UUID,
    approved_date TIMESTAMP,
    status VARCHAR(255),
    feedback_score_voluntary INTEGER,
    feedback_voluntary TEXT,
    created_by UUID NOT NULL,
    created_date TIMESTAMP NOT NULL,
    last_modified_by UUID,
    last_modified_date TIMESTAMP,
    deleted_date TIMESTAMP,
    deleted_by UUID,
    FOREIGN KEY (social_action_id) REFERENCES social_action(id),
    FOREIGN KEY (person_id) REFERENCES person(id),
    FOREIGN KEY (approved_by) REFERENCES person(id)
);

-- índices para colunas que frequentemente são usadas em buscas podem ser adicionados para melhorar o desempenho, por exemplo:
CREATE INDEX idx_voluntary_social_action_id ON voluntary(social_action_id);
CREATE INDEX idx_voluntary_person_id ON voluntary(person_id);
