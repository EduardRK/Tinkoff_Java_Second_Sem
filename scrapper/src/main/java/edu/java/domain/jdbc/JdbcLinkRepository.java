package edu.java.domain.jdbc;

import edu.java.domain.dto.Link;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcLinkRepository implements LinkRepository {
    private final JdbcClient jdbcClient;

    @Autowired
    public JdbcLinkRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    @Transactional
    public List<Link> getAllLinksUpdateLastCheck() {
        List<Link> linkList = jdbcClient.sql("SELECT * FROM link")
            .query(Link.class)
            .list();

        jdbcClient.sql("UPDATE link SET last_check = ?")
            .params(OffsetDateTime.now())
            .update();
        return linkList;
    }

    @Override
    @Transactional
    public List<Link> getAllLinksUpdateLastCheckWithFilter(Duration earlyThen) {
        OffsetDateTime minuses = OffsetDateTime.now().minus(earlyThen);

        List<Link> linkList = jdbcClient.sql("SELECT * FROM link WHERE last_check < ?")
            .param(minuses)
            .query(Link.class)
            .list();

        jdbcClient.sql("UPDATE link SET last_check = ? WHERE last_check < ?")
            .params(OffsetDateTime.now(), minuses)
            .update();
        return linkList;
    }

    @Override
    public long addLink(Link link) {
        Long linkExist = jdbcClient.sql("SELECT COUNT(*) FROM link WHERE uri = ?")
            .param(link.uri())
            .query(Long.class)
            .single();

        if (linkExist == 0) {
            return jdbcClient.sql("INSERT INTO link(uri, last_check, last_update) VALUES (?, ?, ?) RETURNING id")
                .params(link.uri(), link.lastCheck(), link.lastUpdate())
                .query(Long.class)
                .single();
        } else {
            return jdbcClient.sql("SELECT id FROM link WHERE uri = ?")
                .param(link.uri())
                .query(Long.class)
                .single();
        }
    }

    @Override
    public long removeLink(Link link) {
        return jdbcClient.sql("DELETE FROM link WHERE uri = ? RETURNING id")
            .param(link.uri())
            .query(Long.class)
            .single();
    }

    @Override
    public void updateLastUpdateTime(Link link) {
        jdbcClient.sql("UPDATE link SET last_update = ? WHERE uri = ?")
            .params(link.lastUpdate(), link.uri())
            .update();
    }

    @Override
    public void updateAllLastUpdateTime(OffsetDateTime now) {
        jdbcClient.sql("UPDATE link SET last_update = ?")
            .param(now)
            .update();
    }
}
