package edu.java.domain.repository;

import edu.java.domain.dto.Link;
import java.time.Duration;
import java.util.List;

public interface LinkRepository {
    List<Link> findAll();

    List<Link> findAllWithFilter(Duration earlyThen);

    long addLink(Link link);

    long removeLink(Link link);

    void updateLastUpdateTime(Link link);
}
