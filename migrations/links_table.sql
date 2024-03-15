--liquibase formatted sql

--changeset EduardRK:1
CREATE TABLE IF NOT EXISTS Links
(
    id SERIAL NOT NULL,
    uri VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    last_update TIMESTAMP WITH TIME ZONE,

    PRIMARY KEY(id),
    UNIQUE (id, uri)
);
--rollback DROP TABLE Links
