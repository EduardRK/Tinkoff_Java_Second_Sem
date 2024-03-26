package edu.java.domain.repository.jdbc;

import edu.java.domain.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcChatRepository implements ChatRepository {
    private final JdbcClient jdbcClient;

    @Autowired
    public JdbcChatRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public void registerChat(long tgChatId) {
        jdbcClient.sql("INSERT INTO Chats(id) VALUES(?)")
            .param(tgChatId)
            .update();
    }

    @Override
    public void deleteChat(long tgChatId) {
        jdbcClient.sql("DELETE FROM Chats WHERE id = (?)")
            .param(tgChatId)
            .update();
    }

    @Override
    public boolean chatRegistered(long tgChatId) {
        return jdbcClient.sql("SELECT COUNT(*) FROM Chats WHERE id = (?)")
            .param(tgChatId)
            .query(Long.class)
            .single() >= 1;
    }

    @Override
    public boolean correctChatId(long tgChatId) {
        return tgChatId > 0;
    }
}
