--liquibase formatted sql

--changeset EduardRK:3
create TABLE IF NOT EXISTS ChatLink
(
    chat_id BIGINT REFERENCES Chats(id),
    link_id BIGINT REFERENCES Links(id),
    PRIMARY KEY (chat_id, link_id)
);
