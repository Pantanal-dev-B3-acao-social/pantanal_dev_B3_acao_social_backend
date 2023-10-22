CREATE TABLE presence (
  version BIGINT NOT NULL,
  id UUID NOT NULL,
  person_id UUID NOT NULL,
  session_id UUID NOT NULL,
  approved_by UUID,
  approved_date TIMESTAMP,
  created_by UUID NOT NULL,
  created_date TIMESTAMP NOT NULL,
  last_modified_by UUID,
  last_modified_date TIMESTAMP,
  deleted_date TIMESTAMP,
  deleted_by UUID,
  PRIMARY KEY (id),
  FOREIGN KEY (person_id) REFERENCES person (id),
  FOREIGN KEY (session_id) REFERENCES session (id)
);