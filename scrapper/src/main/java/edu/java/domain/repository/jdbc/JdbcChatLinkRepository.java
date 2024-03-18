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
    private static final String ADD_SQL_QUERY = "INSERT INTO ChatLink(chat_id, link_id) VALUES(?, ?)";
    private static final String REMOVE_SQL_QUERY = "DELETE FROM ChatLink WHERE chat_id = ? AND link_id = ?";
    private static final String GET_CHATS_SQL_QUERY = "SELECT chat_id FROM ChatLink WHERE link_id = ?";
    private static final String GET_LINKS_SQL_QUERY = "SELECT Links.* FROM ChatLink INNER JOIN Links "
        + "ON ChatLink.link_id = Links.id WHERE chat_id = ?";
    private static final String CHAT_TRACKED_LINK_SQL_QUERY = "SELECT COUNT(*) FROM ChatLink INNER JOIN Links "
        + "ON ChatLink.link_id = Links.id WHERE chat_id = ? AND uri = ?";
    private final JdbcClient jdbcClient;

    @Autowired
    public JdbcChatLinkRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public void addChatLink(long tgChatId, long linkId) {
        jdbcClient.sql(ADD_SQL_QUERY)
            .params(
                tgChatId,
                linkId
            )
            .update();
    }

    @Override
    public void removeChatLink(long tgChatId, long linkId) {
        jdbcClient.sql(REMOVE_SQL_QUERY)
            .params(
                tgChatId,
                linkId
            )
            .update();
    }

    @Override
    public List<Chat> allChats(long linkId) {
        return jdbcClient.sql(GET_CHATS_SQL_QUERY)
            .param(linkId)
            .query(Chat.class)
            .list();
    }

    @Override
    public List<Link> allLinks(long tgChatId) {
        return jdbcClient.sql(GET_LINKS_SQL_QUERY)
            .param(tgChatId)
            .query(Link.class)
            .list();
    }

    @Override
    public boolean chatTrackedLink(long tgChatId, String uri) {
        return jdbcClient.sql(CHAT_TRACKED_LINK_SQL_QUERY)
            .params(tgChatId, uri)
            .query(Integer.class)
            .single() >= 1;
    }

}
