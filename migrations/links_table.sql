--liquibase formatted sql

--changeset EduardRK:1
CREATE TABLE IF NOT EXISTS link
(
    id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
    uri VARCHAR(255) NOT NULL,
    last_check TIMESTAMP WITH TIME ZONE,
    last_update TIMESTAMP WITH TIME ZONE,

    PRIMARY KEY(id),
    UNIQUE (uri)
);
--rollback DROP TABLE Links
