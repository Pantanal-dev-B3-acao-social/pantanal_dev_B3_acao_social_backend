CREATE TABLE pcd_person (
  id UUID PRIMARY KEY,
  person_id UUID NOT NULL,
  pcd_id UUID NOT NULL,
  created_by UUID,
  created_date TIMESTAMP,
  last_modified_by UUID,
  last_modified_date TIMESTAMP,
  deleted_date TIMESTAMP,
  deleted_by UUID,
  version BIGINT
);

ALTER TABLE pcd_person ADD CONSTRAINT fk_person FOREIGN KEY (person_id) REFERENCES person (id);
ALTER TABLE pcd_person ADD CONSTRAINT fk_pcd FOREIGN KEY (pcd_id) REFERENCES pcd (id);