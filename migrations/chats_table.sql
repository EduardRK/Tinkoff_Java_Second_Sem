--liquibase formatted sql

--changeset EduardRK:2
CREATE TABLE IF NOT EXISTS Chats
(
    id BIGINT NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (id)
);
--rollback DROP TABLE Chats
