CREATE TABLE document (
  version BIGINT NOT NULL,
  id UUID NOT NULL,
  contract_id UUID NOT NULL,
  document_id UUID NOT NULL,
  created_by UUID,
  created_date TIMESTAMP,
  last_modified_by UUID,
  last_modified_date TIMESTAMP,
  deleted_date TIMESTAMP,
  deleted_by UUID,
  PRIMARY KEY (id),
  FOREIGN KEY (contract_id) REFERENCES contract (id)
);