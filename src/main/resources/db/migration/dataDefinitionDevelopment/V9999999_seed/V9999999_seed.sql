--//@UNDO
-- Este bloco não fará nada no caso de um rollback.

--//@SPLIT

-- O bloco abaixo será executado no caso de um rollback.
-- Certifique-se de desfazer quaisquer alterações feitas pelo script de inserção de dados falsos.
-- Pode ser uma operação DELETE ou outra ação apropriada.

--//@UNDO

--//@SPLIT

-- O bloco abaixo será executado durante uma migração bem-sucedida.
-- Ele insere os dados falsos gerados pelo FreeMarker.

<#include "classpath:db/seed/social_action_seed.ftl">
