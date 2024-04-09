package edu.java.domain.jdbc;

import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class JdbcChatRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcChatRepository jdbcChatRepository;

    @Test
    @Transactional
    @Rollback
    void registerChat() {
        jdbcChatRepository.registerChat(3);

        Assertions.assertTrue(
            jdbcChatRepository.chatRegistered(3)
        );
    }

    @Test
    @Transactional
    @Rollback
    void deleteChat() {
        jdbcChatRepository.registerChat(5);

        Assertions.assertTrue(
            jdbcChatRepository.chatRegistered(5)
        );

        jdbcChatRepository.deleteChat(5);

        Assertions.assertFalse(
            jdbcChatRepository.chatRegistered(5)
        );
    }

    @Test
    @Transactional
    @Rollback
    void chatRegistered() {
        Assertions.assertFalse(
            jdbcChatRepository.chatRegistered(1)
        );

        jdbcChatRepository.registerChat(1);

        Assertions.assertTrue(
            jdbcChatRepository.chatRegistered(1)
        );
    }

    @ParameterizedTest
    @CsvSource(value = {
        "12, true",
        "0, false",
        "-10, false"
    })
    void correctChatId(long chatId, boolean expected) {
        Assertions.assertEquals(
            jdbcChatRepository.correctChatId(chatId),
            expected
        );
    }

    @BeforeEach
    void postgresRun() {
        Assertions.assertTrue(POSTGRES.isRunning());
    }
}
