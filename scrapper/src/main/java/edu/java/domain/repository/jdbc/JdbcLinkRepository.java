package edu.java.domain.repository.jdbc;

import edu.java.domain.dto.Link;
import edu.java.domain.repository.LinkRepository;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcLinkRepository implements LinkRepository {
    private static final String ALL_LINK_SQL_STATEMENT = "SELECT * FROM Links";
    private static final String ALL_LINK_WITH_FILTER_SQL_STATEMENT = "SELECT * FROM Links WHERE last_check < ?";
    private static final String ADD_LINK_SQL_STATEMENT = "INSERT INTO Links(uri, last_check, last_update) "
        + "VALUES(:uri, :last_check, :last_update) ON CONFLICT (uri) "
        + "DO UPDATE Links SET last_check = :last_check, last_update = :last_update RETURNING id";
    private static final String REMOVE_LINK_SQL_STATEMENT = "DELETE FROM Links WHERE uri = ? RETURNING id";
    private final JdbcClient jdbcClient;

    @Autowired
    public JdbcLinkRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<Link> findAll() {
        return jdbcClient.sql(ALL_LINK_SQL_STATEMENT)
            .query(Link.class)
            .list();
    }

    @Override
    public List<Link> findAllWithFilter(Duration earlyThen) {
        return jdbcClient.sql(ALL_LINK_WITH_FILTER_SQL_STATEMENT)
            .param(OffsetDateTime.now().minus(earlyThen))
            .query(Link.class)
            .list();
    }

    @Override
    public long addLink(Link link) {
        return jdbcClient.sql(ADD_LINK_SQL_STATEMENT)
            .param("uri", link.uri().toString())
            .param("last_check", link.lastCheck().toString())
            .param("last_update", link.lastUpdate().toString())
            .query(Long.class)
            .single();
    }

    @Override
    public long removeLink(Link link) {
        return jdbcClient.sql(REMOVE_LINK_SQL_STATEMENT)
            .param(link.uri())
            .query(Long.class)
            .single();
    }
}
