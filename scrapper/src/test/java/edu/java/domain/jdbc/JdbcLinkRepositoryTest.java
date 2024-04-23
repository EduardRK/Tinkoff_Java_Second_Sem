package edu.java.domain.jdbc;

import edu.java.domain.dto.Link;
import edu.java.scrapper.IntegrationTest;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class JdbcLinkRepositoryTest extends IntegrationTest {
    @Autowired
    JdbcLinkRepository jdbcLinkRepository;

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "JDBC");
    }

    @Test
    @Transactional
    @Rollback
    void findAll() {
        List<Long> list = new ArrayList<>();

        for (int i = 0; i < 10; ++i) {
            list.add(jdbcLinkRepository.addLink(Link.link(URI.create("SomeTestLink" + i + ".com"))));
        }

        Assertions.assertTrue(
            jdbcLinkRepository.getAllLinksUpdateLastCheck().stream().map(Link::id).toList().containsAll(list)
        );
    }

    @Test
    @Transactional
    @Rollback
    void findAllWithFilter() {
        jdbcLinkRepository.addLink(
            new Link(
                1,
                "SomeTestLink1.com",
                OffsetDateTime.MIN,
                OffsetDateTime.MAX
            )
        );

        jdbcLinkRepository.addLink(
            new Link(
                2,
                "SomeTestLink2.com",
                OffsetDateTime.MAX,
                OffsetDateTime.MAX
            )
        );

        Assertions.assertTrue(
            jdbcLinkRepository.getAllLinksUpdateLastCheckWithFilter(Duration.ofHours(1))
                .stream()
                .map(Link::uri)
                .toList()
                .contains("SomeTestLink1.com")
        );

        Assertions.assertFalse(
            jdbcLinkRepository.getAllLinksUpdateLastCheckWithFilter(Duration.ofHours(1))
                .stream()
                .map(Link::uri)
                .toList()
                .contains("SomeTestLink2.com")
        );
    }

    @Test
    @Transactional
    @Rollback
    void addLink() {
        jdbcLinkRepository.addLink(Link.link(URI.create("SomeTestLink.com")));

        Assertions.assertTrue(
            jdbcLinkRepository.getAllLinksUpdateLastCheck().stream().map(Link::uri).toList()
                .contains("SomeTestLink.com")
        );
    }

    @Test
    @Transactional
    @Rollback
    void removeLink() {
        jdbcLinkRepository.addLink(Link.link(URI.create("SomeTestLink.com")));

        Assertions.assertTrue(
            jdbcLinkRepository.getAllLinksUpdateLastCheck().stream().map(Link::uri).toList()
                .contains("SomeTestLink.com")
        );

        jdbcLinkRepository.removeLink(Link.link(URI.create("SomeTestLink.com")));

        Assertions.assertFalse(
            jdbcLinkRepository.getAllLinksUpdateLastCheck().stream().map(Link::uri).toList()
                .contains("SomeTestLink.com")
        );
    }

    @Test
    @Transactional
    @Rollback
    void updateLastUpdateTime() {
        Link link = new Link(
            0,
            "SomeTestLink.com",
            OffsetDateTime.MIN,
            OffsetDateTime.MIN
        );

        jdbcLinkRepository.addLink(link);

        jdbcLinkRepository.updateLastUpdateTime(
            new Link(
                0,
                "SomeTestLink.com",
                OffsetDateTime.MIN,
                OffsetDateTime.MAX
            ),
            OffsetDateTime.MAX
        );

        Assertions.assertTrue(
            jdbcLinkRepository
                .getAllLinksUpdateLastCheck()
                .stream()
                .map(Link::lastUpdate)
                .toList()
                .contains(OffsetDateTime.MAX)
        );
    }

    @BeforeEach
    void postgresRun() {
        Assertions.assertTrue(POSTGRES.isRunning());
    }
}
