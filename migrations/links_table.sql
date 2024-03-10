--liquibase formatted sql

--changeset EduardRK:1
create TABLE IF NOT EXISTS Links
(
    id BIGINT NOT NULL,
    uri VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    last_update TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY(id),
    UNIQUE (id, uri)
);
