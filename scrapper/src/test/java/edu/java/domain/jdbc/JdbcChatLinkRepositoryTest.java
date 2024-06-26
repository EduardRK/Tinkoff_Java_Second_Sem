package edu.java.domain.jdbc;

import edu.java.domain.dto.Chat;
import edu.java.domain.dto.Link;
import edu.java.scrapper.IntegrationTest;
import java.net.URI;
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
class JdbcChatLinkRepositoryTest extends IntegrationTest {
    @Autowired
    JdbcChatLinkRepository jdbcChatLinkRepository;
    @Autowired
    JdbcChatRepository jdbcChatRepository;
    @Autowired
    JdbcLinkRepository jdbcLinkRepository;

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "JDBC");
    }

    @Test
    @Transactional
    @Rollback
    void addChatLink() {
        jdbcChatRepository.registerChat(1);
        long id = jdbcLinkRepository.addLink(Link.link(URI.create("SomeTestLink")));
        jdbcChatLinkRepository.addChatLink(1, id);

        Assertions.assertTrue(
            jdbcChatLinkRepository.getAllLinks(1).stream().map(Link::id).toList().contains(id)
        );
    }

    @Test
    @Transactional
    @Rollback
    void removeChatLink() {
        jdbcChatRepository.registerChat(1);
        long id = jdbcLinkRepository.addLink(Link.link(URI.create("SomeTestLink")));
        jdbcChatLinkRepository.addChatLink(1, id);

        jdbcChatLinkRepository.removeChatLink(1, id);

        Assertions.assertFalse(
            jdbcChatLinkRepository.getAllLinks(1).stream().map(Link::id).toList().contains(id)
        );
    }

    @Test
    @Transactional
    @Rollback
    void allChats() {
        jdbcChatRepository.registerChat(1);
        long id = jdbcLinkRepository.addLink(Link.link(URI.create("SomeTestLink1.com")));
        jdbcChatLinkRepository.addChatLink(1, id);

        List<Chat> chats = jdbcChatLinkRepository.getAllChats(id);

        Assertions.assertTrue(
            chats.stream().map(Chat::chatId).toList().contains(1L)
        );
    }

    @Test
    @Transactional
    @Rollback
    void allLinks() {
        jdbcChatRepository.registerChat(1);
        long id1 = jdbcLinkRepository.addLink(Link.link(URI.create("SomeTestLink1.com")));
        jdbcChatLinkRepository.addChatLink(1, id1);

        long id2 = jdbcLinkRepository.addLink(Link.link(URI.create("SomeTestLink2.com")));
        jdbcChatLinkRepository.addChatLink(1, id2);

        List<Link> links = jdbcChatLinkRepository.getAllLinks(1);

        Assertions.assertTrue(
            links.stream().map(Link::uri).toList().containsAll(List.of("SomeTestLink1.com", "SomeTestLink2.com"))
        );

        Assertions.assertTrue(
            links.stream().map(Link::id).toList().containsAll(List.of(id1, id2))
        );
    }

    @Test
    @Transactional
    @Rollback
    void chatTrackedLink() {
        jdbcChatRepository.registerChat(1);
        long id1 = jdbcLinkRepository.addLink(Link.link(URI.create("SomeTestLink1.com")));
        jdbcChatLinkRepository.addChatLink(1, id1);

        long id2 = jdbcLinkRepository.addLink(Link.link(URI.create("SomeTestLink2.com")));
        jdbcChatLinkRepository.addChatLink(1, id2);

        Assertions.assertTrue(
            jdbcChatLinkRepository.chatTrackedLink(1, "SomeTestLink1.com")
        );

        Assertions.assertFalse(
            jdbcChatLinkRepository.chatTrackedLink(1, "SomeTestLink3.com")
        );
    }

    @Test
    @Transactional
    @Rollback
    void deleteAllTrackLink() {
        jdbcChatRepository.registerChat(1);
        long id1 = jdbcLinkRepository.addLink(Link.link(URI.create("SomeTestLink1.com")));
        jdbcChatLinkRepository.addChatLink(1, id1);

        long id2 = jdbcLinkRepository.addLink(Link.link(URI.create("SomeTestLink2.com")));
        jdbcChatLinkRepository.addChatLink(1, id2);

        Assertions.assertTrue(
            jdbcChatLinkRepository.chatTrackedLink(1, "SomeTestLink1.com")
        );

        Assertions.assertTrue(
            jdbcChatLinkRepository.chatTrackedLink(1, "SomeTestLink2.com")
        );

        jdbcChatLinkRepository.deleteAllTrackLink(1);

        Assertions.assertFalse(
            jdbcChatLinkRepository.chatTrackedLink(1, "SomeTestLink1.com")
        );

        Assertions.assertFalse(
            jdbcChatLinkRepository.chatTrackedLink(1, "SomeTestLink2.com")
        );
    }

    @BeforeEach
    void postgresRun() {
        Assertions.assertTrue(POSTGRES.isRunning());
    }
}
