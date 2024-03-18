--liquibase formatted sql

--changeset EduardRK:3
CREATE TABLE IF NOT EXISTS ChatLink
(
    chat_id BIGINT REFERENCES Chats(id),
    link_id BIGINT REFERENCES Links(id) ON DELETE CASCADE,

    PRIMARY KEY (chat_id, link_id)
);
--rollback DROP TABLE ChatLink
