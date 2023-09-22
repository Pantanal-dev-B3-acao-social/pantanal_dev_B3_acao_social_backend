CREATE TABLE social_action (
    id UUID PRIMARY KEY UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    version int8,
--    organizer VARCHAR(255) NOT NULL
);

CREATE TABLE Z_AUD_SOCIAL_ACTION (
    id UUID PRIMARY KEY UNIQUE NOT NULL,
    revision INT4 NOT NULL,
    typee INT2,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    version int8,
--    organizer VARCHAR(255) NOT NULL
);

