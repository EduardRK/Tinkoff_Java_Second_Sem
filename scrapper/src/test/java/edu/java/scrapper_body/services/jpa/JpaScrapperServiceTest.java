package edu.java.scrapper_body.services.jpa;

import edu.java.domain.jpa.JpaChatRepository;
import edu.java.domain.jpa.JpaLinkRepository;
import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.exceptions.NotFoundException.NotFoundException;
import edu.java.scrapper.IntegrationTest;
import edu.java.service.ScrapperService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

@SpringBootTest
class JpaScrapperServiceTest extends IntegrationTest {
    @Autowired
    private JpaLinkRepository linkRepository;
    @Autowired
    private JpaChatRepository chatRepository;
    @Autowired
    private ScrapperService scrapperService;
    @Autowired
    private JdbcClient jdbcClient;

    @Test
    void registerChat() throws BadRequestException {
        scrapperService.registerChat(12);

        Assertions.assertEquals(
            12,
            jdbcClient.sql("SELECT id FROM Chats WHERE id = 12").query(Long.class).single()
        );
    }

    @Test
    void deleteChat() throws BadRequestException, NotFoundException {
        jdbcClient.sql("INSERT INTO Chats(id) VALUES(12)").update();

        Assertions.assertEquals(
            12,
            jdbcClient.sql("SELECT id FROM Chats WHERE id = 12").query(Long.class).single()
        );

        scrapperService.deleteChat(12);

        Assertions.assertFalse(
            jdbcClient.sql("SELECT id FROM Chats").query(Long.class).list().contains(12L)
        );
    }
}
