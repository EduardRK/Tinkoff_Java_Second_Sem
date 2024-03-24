package edu.java.domain.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


public class JdbcChatRepository implements ChatRepository {
    private static final String REGISTER_CHAT_SQL_QUERY = "INSERT INTO Chats(id) VALUES(?)";
    private static final String DELETE_CHAT_SQL_QUERY = "DELETE FROM Chats WHERE id = (?)";
    private static final String EXIST_CHAT_SQL_QUERY = "SELECT COUNT(*) FROM Chats WHERE id = (?)";
    private final JdbcClient jdbcClient;

    public JdbcChatRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    @Transactional
    public void registerChat(long tgChatId) {
        jdbcClient.sql(REGISTER_CHAT_SQL_QUERY)
            .param(tgChatId)
            .update();
    }

    @Override
    @Transactional
    public void deleteChat(long tgChatId) {
        jdbcClient.sql(DELETE_CHAT_SQL_QUERY)
            .param(tgChatId)
            .update();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean chatRegistered(long tgChatId) {
        return jdbcClient.sql(EXIST_CHAT_SQL_QUERY)
            .param(tgChatId)
            .query(Long.class)
            .single() >= 1;
    }

    @Override
    public boolean correctChatId(long tgChatId) {
        return tgChatId > 0;
    }
}
