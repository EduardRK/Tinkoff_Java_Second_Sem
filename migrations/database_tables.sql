--liquibase formatted sql
create table if not exists chat_link
(
    chat_id bigint references chats(id),
    link_id bigint references links(id),
    primary key (chat_id, link_id)
);

create table if not exists chats
(
    id bigint unique not null primary key
);

create table if not exists links
(
    id bigint unique not null primary key,
    uri text unique not null
);
--rollback drop table links, chats, chat_link;
