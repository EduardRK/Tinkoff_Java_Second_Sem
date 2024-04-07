package edu.java.service.jpa;

import edu.java.domain.dto.Chat;
import edu.java.domain.dto.Link;
import edu.java.domain.jpa.JpaChatRepository;
import edu.java.domain.jpa.JpaLinkRepository;
import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.exceptions.NotFoundException.NotFoundException;
import edu.java.responses.LinkResponse;
import edu.java.responses.ListLinksResponse;
import edu.java.scrapper.IntegrationTest;
import jakarta.persistence.EntityManager;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class JpaScrapperServiceTest extends IntegrationTest {
    @Autowired
    JpaChatRepository chatRepository;
    @Autowired
    JpaLinkRepository linkRepository;
    @Autowired
    EntityManager entityManager;
    JpaScrapperService scrapperService;
    @Autowired
    private JdbcClient jdbcClient;

    @BeforeEach
    void setScrapperService() {
        scrapperService = new JpaScrapperService(linkRepository, chatRepository);
    }

    @Test
    @Transactional
    @Rollback
    void registerChat() throws BadRequestException, NotFoundException {
        scrapperService.registerChat(12);

        entityManager.flush();

        Assertions.assertEquals(
            12,
            jdbcClient.sql("SELECT id FROM chat WHERE id = 12").query(Long.class).single()
        );

        scrapperService.deleteChat(12);
    }

    @Test
    @Transactional
    @Rollback
    void deleteChat() throws BadRequestException, NotFoundException {
        scrapperService.registerChat(12);

        entityManager.flush();

        Assertions.assertEquals(
            12,
            jdbcClient.sql("SELECT id FROM chat WHERE id = 12").query(Long.class).single()
        );

        scrapperService.deleteChat(12);

        entityManager.flush();

        Assertions.assertEquals(
            0,
            jdbcClient.sql("SELECT COUNT(*) FROM chat WHERE id = 12").query(Long.class).single()
        );
    }

    @Test
    @Transactional
    @Rollback
    void add() throws BadRequestException {
        scrapperService.registerChat(12);

        scrapperService.add(12, "SomeLink.com");

        entityManager.flush();

        Long single = jdbcClient.sql("SELECT id FROM chat WHERE id = 12")
            .query(Long.class)
            .single();

        Assertions.assertEquals(12, single);

        String single1 = jdbcClient.sql("SELECT uri FROM link WHERE uri = 'SomeLink.com'")
            .query(String.class)
            .single();

        Assertions.assertEquals("SomeLink.com", single1);
    }

    @Test
    @Transactional
    @Rollback
    void remove() throws BadRequestException, NotFoundException {
        scrapperService.registerChat(12);

        scrapperService.add(12, "SomeLink.com");

        entityManager.flush();

        Long single = jdbcClient.sql("SELECT id FROM chat WHERE id = 12")
            .query(Long.class)
            .single();

        Assertions.assertEquals(12, single);

        String single1 = jdbcClient.sql("SELECT uri FROM link WHERE uri = 'SomeLink.com'")
            .query(String.class)
            .single();

        Assertions.assertEquals("SomeLink.com", single1);

        scrapperService.remove(12, "SomeLink.com");

        entityManager.flush();

        Long single2 = jdbcClient.sql("SELECT COUNT(*) FROM link WHERE uri = 'SomeLink.com'")
            .query(Long.class)
            .single();

        Assertions.assertEquals(0, single2);
    }

    @Test
    @Transactional
    @Rollback
    void listAll() throws BadRequestException {
        scrapperService.registerChat(12);
        scrapperService.add(12, "SomeLink1.com");
        scrapperService.add(12, "SomeLink2.com");
        scrapperService.add(12, "SomeLink3.com");
        entityManager.flush();

        ListLinksResponse listLinksResponse = scrapperService.listAll(12);

        Assertions.assertEquals(3, listLinksResponse.size());

        Assertions.assertTrue(
            listLinksResponse.links()
                .stream()
                .map(LinkResponse::url)
                .toList()
                .containsAll(List.of("SomeLink1.com", "SomeLink2.com", "SomeLink3.com"))
        );
    }

    @Test
    @Transactional
    @Rollback
    void findAllWithFilter() {
        jdbcClient.sql("INSERT INTO link(uri, last_check, last_update) VALUES (?, ?, ?) RETURNING id")
            .params("SomeLink1.com", OffsetDateTime.MIN, OffsetDateTime.MIN)
            .query(Long.class)
            .single();

        jdbcClient.sql("INSERT INTO link(uri, last_check, last_update) VALUES (?, ?, ?) RETURNING id")
            .params("SomeLink2.com", OffsetDateTime.now(), OffsetDateTime.now())
            .query(Long.class)
            .single();

        List<Link> allWithFilter = scrapperService.findAllWithFilter(Duration.ofHours(12));

        Assertions.assertEquals(1, allWithFilter.size());
        Assertions.assertEquals("SomeLink1.com", allWithFilter.getFirst().uri());
    }

    @Test
    @Transactional
    @Rollback
    void updateLastUpdateTime() {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

        Long single = jdbcClient.sql("INSERT INTO link(uri, last_check, last_update) VALUES (?, ?, ?) RETURNING id")
            .params("SomeLink1.com", OffsetDateTime.MIN, OffsetDateTime.parse("2021-10-20T12:30:45+03:00"))
            .query(Long.class)
            .single();

        scrapperService.updateLastUpdateTime(new Link(single, null, null, now));

        OffsetDateTime offsetDateTime = jdbcClient.sql("SELECT last_update FROM link WHERE id = ?")
            .param(single)
            .query(OffsetDateTime.class)
            .single();

        Assertions.assertEquals(
            now.truncatedTo(ChronoUnit.SECONDS),
            offsetDateTime.truncatedTo(ChronoUnit.SECONDS)
        );
    }

    @Test
    @Transactional
    @Rollback
    void getAllChats() throws BadRequestException {
        scrapperService.registerChat(12);
        scrapperService.registerChat(13);
        scrapperService.registerChat(14);
        entityManager.flush();

        scrapperService.add(12, "SomeLink.com");
        scrapperService.add(13, "SomeLink.com");
        scrapperService.add(14, "SomeLink.com");
        entityManager.flush();

        Long single = jdbcClient.sql("SELECT id FROM link WHERE uri = 'SomeLink.com'")
            .query(Long.class)
            .single();

        List<Chat> allChats = scrapperService.getAllChats(single);

        Assertions.assertTrue(
            allChats.stream()
                .map(Chat::chatId)
                .toList()
                .containsAll(List.of(12L, 13L, 14L))
        );
    }

    @Test
    @Transactional
    @Rollback
    void updateAllLastUpdateTime() {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

        Long single1 = jdbcClient.sql("INSERT INTO link(uri, last_check, last_update) VALUES (?, ?, ?) RETURNING id")
            .params("SomeLink1.com", OffsetDateTime.MIN, OffsetDateTime.parse("2021-10-20T12:30:45+03:00"))
            .query(Long.class)
            .single();

        Long single2 = jdbcClient.sql("INSERT INTO link(uri, last_check, last_update) VALUES (?, ?, ?) RETURNING id")
            .params("SomeLink2.com", OffsetDateTime.MIN, OffsetDateTime.parse("2021-11-20T12:30:45+03:00"))
            .query(Long.class)
            .single();

        scrapperService.updateAllLastUpdateTime();

        OffsetDateTime offsetDateTime1 = jdbcClient.sql("SELECT last_update FROM link WHERE id = ?")
            .param(single1)
            .query(OffsetDateTime.class)
            .single();

        OffsetDateTime offsetDateTime2 = jdbcClient.sql("SELECT last_update FROM link WHERE id = ?")
            .param(single2)
            .query(OffsetDateTime.class)
            .single();

        Assertions.assertEquals(
            now.truncatedTo(ChronoUnit.SECONDS),
            offsetDateTime1.truncatedTo(ChronoUnit.SECONDS)
        );

        Assertions.assertEquals(
            now.truncatedTo(ChronoUnit.SECONDS),
            offsetDateTime2.truncatedTo(ChronoUnit.SECONDS)
        );
    }

    @Test
    @Transactional
    @Rollback
    void getAllLinks() throws BadRequestException {
        scrapperService.registerChat(12);
        entityManager.flush();

        scrapperService.add(12, "SomeLink1.com");
        scrapperService.add(12, "SomeLink2.com");
        scrapperService.add(12, "SomeLink3.com");
        entityManager.flush();

        List<Link> allLinks = scrapperService.getAllLinks(12);

        Assertions.assertTrue(
            allLinks
                .stream()
                .map(Link::uri)
                .toList()
                .containsAll(List.of("SomeLink1.com", "SomeLink2.com", "SomeLink3.com"))
        );
    }
}
