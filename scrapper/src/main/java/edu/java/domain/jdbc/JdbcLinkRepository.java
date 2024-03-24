package edu.java.domain.jdbc;

import edu.java.domain.dto.Link;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.transaction.annotation.Transactional;

public class JdbcLinkRepository implements LinkRepository {
    private static final String ALL_LINK_SQL_QUERY = "SELECT * FROM Links";
    private static final String ALL_LINK_WITH_FILTER_SQL_QUERY = "SELECT * FROM Links WHERE last_check < ?";
    private static final String ADD_LINK_SQL_QUERY = "INSERT INTO Links(uri, last_check, last_update) "
        + "VALUES (?, ?, ?) RETURNING id";
    private static final String UPDATE_ALL_LINKS_AFTER_CHECK_SQL_QUERY = "UPDATE Links SET last_check = ?";
    private static final String UPDATE_ALL_LINKS_AFTER_CHECK_WITH_FILTER_SQL_QUERY = "UPDATE Links SET last_check = ? "
        + "WHERE last_check < ?";
    private static final String REMOVE_LINK_SQL_QUERY = "DELETE FROM Links WHERE uri = ? RETURNING id";
    private static final String UPDATE_LAST_UPDATE_TIME_SQL_QUERY = "UPDATE Links SET last_update = ? WHERE uri = ?";
    private final JdbcClient jdbcClient;

    public JdbcLinkRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    @Transactional
    public List<Link> findAll() {
        List<Link> linkList = jdbcClient.sql(ALL_LINK_SQL_QUERY)
            .query(Link.class)
            .list();
        jdbcClient.sql(UPDATE_ALL_LINKS_AFTER_CHECK_SQL_QUERY)
            .param(OffsetDateTime.now())
            .update();
        return linkList;
    }

    @Override
    @Transactional
    public List<Link> findAllWithFilter(Duration earlyThen) {
        List<Link> linkList = jdbcClient.sql(ALL_LINK_WITH_FILTER_SQL_QUERY)
            .param(OffsetDateTime.now().minus(earlyThen))
            .query(Link.class)
            .list();
        jdbcClient.sql(UPDATE_ALL_LINKS_AFTER_CHECK_WITH_FILTER_SQL_QUERY)
            .params(OffsetDateTime.now(), OffsetDateTime.now().minus(earlyThen))
            .update();
        return linkList;
    }

    @Override
    @Transactional
    public long addLink(Link link) {
        return jdbcClient.sql(ADD_LINK_SQL_QUERY)
            .params(link.uri(), link.lastCheck(), link.lastUpdate())
            .query(Long.class)
            .single();
    }

    @Override
    @Transactional
    public long removeLink(Link link) {
        return jdbcClient.sql(REMOVE_LINK_SQL_QUERY)
            .param(link.uri())
            .query(Long.class)
            .single();
    }

    @Override
    @Transactional
    public void updateLastUpdateTime(Link link) {
        jdbcClient.sql(UPDATE_LAST_UPDATE_TIME_SQL_QUERY)
            .params(link.lastUpdate(), link.uri())
            .update();
    }
}
