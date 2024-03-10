--liquibase formatted sql

--changeset EduardRK:2
create TABLE IF NOT EXISTS Chats
(
    id BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (id)
);
