CREATE TABLE pcd (
  id UUID PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  observation VARCHAR(255) NOT NULL,
  code VARCHAR(255) NOT NULL,
  acronym VARCHAR(255) NOT NULL,
  created_by UUID,
  last_modified_by UUID,
  created_date TIMESTAMP,
  last_modified_date TIMESTAMP,
  deleted_date TIMESTAMP,
  deleted_by UUID
);