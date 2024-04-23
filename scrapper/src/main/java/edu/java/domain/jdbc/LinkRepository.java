package edu.java.domain.jdbc;

import edu.java.domain.dto.Link;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

public interface LinkRepository {
    List<Link> getAllLinksUpdateLastCheck();

    List<Link> getAllLinksUpdateLastCheckWithFilter(Duration earlyThen);

    long addLink(Link link);

    long removeLink(Link link);

    void updateLastUpdateTime(Link link, OffsetDateTime now);

    void updateAllLastUpdateTime(OffsetDateTime now);
}
