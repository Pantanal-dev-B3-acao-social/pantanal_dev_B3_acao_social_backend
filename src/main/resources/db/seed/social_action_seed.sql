-- src/main/resources/db/seed/V1__InsertInitialData.
--/home/kaio/Documentos/ufms/pantanal_dev/projeto/acao_social/src/main/resources/db/factory/SocialActionFactory.java
--INSERT INTO users (username, email, password) VALUES
--    (
--        '#{T(src/main/resources/db/factory/SocialActionFactory).generateRandomUsername()}',
--        '#{T(src/main/resources/db/factory/SocialActionFactory).generateRandomEmail()}',
--        '#{T(src/main/resources/db/factory/SocialActionFactory).generateRandomPassword()}'
--    );
--INSERT INTO users (username, email, password) VALUES
--    (
--        '#{T(src/main/resources/db/factory/SocialActionFactory).generateRandomUsername()}',
--        '#{T(src/main/resources/db/factory/SocialActionFactory).generateRandomEmail()}',
--        '#{T(src/main/resources/db/factory/SocialActionFactory).generateRandomPassword()}'
--    );
-- Outros comandos de inserção de dados aqui
-- Seed script para social_action
INSERT INTO social_action (id, name, description, version, organizer)
VALUES
    ('c16c67f3-34df-4b85-b2ac-7b95132697a1', 'Social Action 1', 'Description 1', 1, 'Organizer 1'),
    ('e249bf41-95b9-4cb1-aa3c-2a9cd3f7f5cc', 'Social Action 2', 'Description 2', 1, 'Organizer 2'),
    ('4973b155-ff7b-40a7-8395-8c9732e636bf', 'Social Action 3', 'Description 3', 1, 'Organizer 3'),
    ('862c8397-7e51-490e-b1ce-17f73830d238', 'Social Action 4', 'Description 4', 1, 'Organizer 4'),
    ('f6c399a7-0c04-497d-a429-6e81cd41df5f', 'Social Action 5', 'Description 5', 1, 'Organizer 5'),
    ('9e895897-3621-4b50-bf08-50b5cde2f8b5', 'Social Action 6', 'Description 6', 1, 'Organizer 6'),
    ('5d2a14cb-cf0e-47f0-9c27-0e529e7be9df', 'Social Action 7', 'Description 7', 1, 'Organizer 7'),
    ('f6637aef-17e1-4167-87a4-5be5405048bb', 'Social Action 8', 'Description 8', 1, 'Organizer 8'),
    ('10805942-af9e-4d98-af5e-e35d9a9e5393', 'Social Action 9', 'Description 9', 1, 'Organizer 9'),
    ('4ae9e741-1de3-4c27-846e-1ec080a3f44d', 'Social Action 10', 'Description 10', 1, 'Organizer 10');
--
--INSERT INTO Z_AUD_SOCIAL_ACTION (id, revision, typee, name, description, version, organizer)
--VALUES
--    ('c16c67f3-34df-4b85-b2ac-7b95132697a1', 1, 0, 'Social Action 1', 'Description 1', 1, 'Organizer 1'),
--    ('e249bf41-95b9-4cb1-aa3c-2a9cd3f7f5cc', 1, 0, 'Social Action 2', 'Description 2', 1, 'Organizer 2'),
--    ('4973b155-ff7b-40a7-8395-8c9732e636bf', 1, 0, 'Social Action 3', 'Description 3', 1, 'Organizer 3'),
--    ('862c8397-7e51-490e-b1ce-17f73830d238', 1, 0, 'Social Action 4', 'Description 4', 1, 'Organizer 4'),
--    ('f6c399a7-0c04-497d-a429-6e81cd41df5f', 1, 0, 'Social Action 5', 'Description 5', 1, 'Organizer 5'),
--    ('9e895897-3621-4b50-bf08-50b5cde2f8b5', 1, 0, 'Social Action 6', 'Description 6', 1, 'Organizer 6'),
--    ('5d2a14cb-cf0e-47f0-9c27-0e529e7be9df', 1, 0, 'Social Action 7', 'Description 7', 1, 'Organizer 7'),
--    ('f6637aef-17e1-4167-87a4-5be5405048bb', 1, 0, 'Social Action 8', 'Description 8', 1, 'Organizer 8'),
--    ('10805942-af9e-4d98-af5e-e35d9a9e5393', 1, 0, 'Social Action 9', 'Description 9', 1, 'Organizer 9'),
--    ('4ae9e741-1de3-4c27-846e-1ec080a3f44d', 1, 0, 'Social Action 10', 'Description 10', 1, 'Organizer 10');
