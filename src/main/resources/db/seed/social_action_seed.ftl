-- O bloco abaixo será executado durante uma migração bem-sucedida.
-- Ele insere os dados falsos gerados pelo JavaFaker para a entidade social_action.

-- Gere os dados falsos usando o JavaFaker
DO $$
DECLARE
  name1 TEXT := faker.name().fullName();
  email1 TEXT := faker.internet().emailAddress();
  name2 TEXT := faker.name().fullName();
  email2 TEXT := faker.internet().emailAddress();
BEGIN
  -- Inserir os dados falsos na tabela social_action
  INSERT INTO social_action (id, name, description, version, organizer)
  VALUES
    (
        'c16c67f3-34df-4b85-b2ac-7b95132697a1',
        name1,
        email1,
        1,
        name1
    ),
    (
        'e249bf41-95b9-4cb1-aa3c-2a9cd3f7f5cc',
        name2,
        email2,
        1,
        name2
    );
END $$;
