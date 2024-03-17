package edu.java.domain.repository.jdbc;

import edu.java.domain.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcChatRepository implements ChatRepository {
    private static final String REGISTER_CHAT_SQL_STATEMENT = "INSERT INTO Chats(id) VALUES(?)";
    private static final String DELETE_CHAT_SQL_STATEMENT = "DELETE FROM Chats WHERE id = (?)";
    private static final String EXIST_CHAT_SQL_STATEMENT = "SELECT COUNT(*) FROM Chats WHERE id = (?)";
    private final JdbcClient jdbcClient;

    @Autowired
    public JdbcChatRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public void registerChat(long tgChatId) {
        jdbcClient.sql(REGISTER_CHAT_SQL_STATEMENT)
            .param(tgChatId)
            .update();
    }

    @Override
    public void deleteChat(long tgChatId) {
        jdbcClient.sql(DELETE_CHAT_SQL_STATEMENT)
            .param(tgChatId)
            .update();
    }

    @Override
    public boolean chatRegistered(long tgChatId) {
        return jdbcClient.sql(EXIST_CHAT_SQL_STATEMENT)
            .param(tgChatId)
            .query(Long.class)
            .single() >= 1;
    }

    @Override
    public boolean correctChatId(long tgChatId) {
        return tgChatId > 0;
    }
}
