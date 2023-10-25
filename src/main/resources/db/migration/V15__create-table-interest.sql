-- Version: 1
CREATE TABLE interest (
    version BIGINT NOT NULL,
    id UUID NOT NULL,
    person_id UUID NOT NULL,
    category_id UUID NOT NULL,
    approved_by UUID,
    approved_date TIMESTAMP,
    created_by UUID,
    created_date TIMESTAMP,
    last_modified_by UUID,
    last_modified_date TIMESTAMP,
    deleted_date TIMESTAMP,
    deleted_by UUID,
    PRIMARY KEY (id),
    FOREIGN KEY (person_id) REFERENCES person(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);