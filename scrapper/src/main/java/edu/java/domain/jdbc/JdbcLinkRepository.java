package edu.java.domain.jdbc;

import edu.java.domain.dto.Link;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.transaction.annotation.Transactional;

public class JdbcLinkRepository implements LinkRepository {
    private final JdbcClient jdbcClient;

    public JdbcLinkRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    @Transactional
    public List<Link> getAllLinksUpdateLastCheck() {
        List<Link> linkList = jdbcClient.sql("SELECT * FROM Links")
            .query(Link.class)
            .list();

        jdbcClient.sql("UPDATE Links SET last_check = ?")
            .params(OffsetDateTime.now())
            .update();
        return linkList;
    }

    @Override
    @Transactional
    public List<Link> getAllLinksUpdateLastCheckWithFilter(Duration earlyThen) {
        List<Link> linkList = jdbcClient.sql("SELECT * FROM Links WHERE last_check < ?")
            .param(OffsetDateTime.now().minus(earlyThen))
            .query(Link.class)
            .list();

        jdbcClient.sql("UPDATE Links SET last_check = ? WHERE last_check < ?")
            .params(OffsetDateTime.now(), OffsetDateTime.now().minus(earlyThen))
            .update();
        return linkList;
    }

    @Override
    public long addLink(Link link) {
        return jdbcClient.sql("INSERT INTO Links(uri, last_check, last_update) VALUES (?, ?, ?) RETURNING id")
            .params(link.uri(), link.lastCheck(), link.lastUpdate())
            .query(Long.class)
            .single();
    }

    @Override
    public long removeLink(Link link) {
        return jdbcClient.sql("DELETE FROM Links WHERE uri = ? RETURNING id")
            .param(link.uri())
            .query(Long.class)
            .single();
    }

    @Override
    public void updateLastUpdateTime(Link link) {
        jdbcClient.sql("UPDATE Links SET last_update = ? WHERE uri = ?")
            .params(link.lastUpdate(), link.uri())
            .update();
    }

    @Override
    public void updateAllLastUpdateTime(OffsetDateTime now) {
        jdbcClient.sql("UPDATE Links SET last_update = ?")
            .param(now)
            .update();
    }
}
