--liquibase formatted sql

--changeset EduardRK:3
CREATE TABLE IF NOT EXISTS chat_link
(
    chat_id BIGINT REFERENCES chat(id),
    link_id BIGINT REFERENCES link(id) ON DELETE CASCADE,

    PRIMARY KEY (chat_id, link_id)
);
--rollback DROP TABLE ChatLink
