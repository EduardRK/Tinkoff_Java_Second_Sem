package edu.java.domain.repository.jdbc;

import edu.java.domain.dto.Chat;
import edu.java.domain.dto.Link;
import edu.java.domain.repository.ChatLinkRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcChatLinkRepository implements ChatLinkRepository {
    private final JdbcClient jdbcClient;

    @Autowired
    public JdbcChatLinkRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public void addChatLink(long tgChatId, long linkId) {
        jdbcClient.sql("INSERT INTO ChatLink(chat_id, link_id) VALUES(?, ?)")
            .params(
                tgChatId,
                linkId
            )
            .update();
    }

    @Override
    public void removeChatLink(long tgChatId, long linkId) {
        jdbcClient.sql("DELETE FROM ChatLink WHERE chat_id = ? AND link_id = ?")
            .params(
                tgChatId,
                linkId
            )
            .update();
    }

    @Override
    public List<Chat> getAllChats(long linkId) {
        return jdbcClient.sql("SELECT chat_id FROM ChatLink WHERE link_id = ?")
            .param(linkId)
            .query(Chat.class)
            .list();
    }

    @Override
    public List<Link> getAllLinks(long tgChatId) {
        return jdbcClient.sql("SELECT Links.* FROM ChatLink INNER JOIN Links "
                + "ON ChatLink.link_id = Links.id WHERE chat_id = ?")
            .param(tgChatId)
            .query(Link.class)
            .list();
    }

    @Override
    public boolean chatTrackedLink(long tgChatId, String uri) {
        return jdbcClient.sql("SELECT COUNT(*) FROM ChatLink INNER JOIN Links "
                + "ON ChatLink.link_id = Links.id WHERE chat_id = ? AND uri = ?")
            .params(tgChatId, uri)
            .query(Integer.class)
            .single() >= 1;
    }

    @Override
    public void deleteAllTrackLink(long tgChatId) {
        jdbcClient.sql("DELETE FROM ChatLink WHERE chat_id = ?")
            .param(tgChatId)
            .update();
    }

}
