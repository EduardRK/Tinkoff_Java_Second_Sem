package edu.java.configuration;

import edu.java.domain.jdbc.JdbcChatLinkRepository;
import edu.java.domain.jdbc.JdbcChatRepository;
import edu.java.domain.jdbc.JdbcLinkRepository;
import edu.java.service.jdbc.JdbcScrapperService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.JdbcClient;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "JDBC")
public class JdbcServiceConfig {
    public JdbcServiceConfig() {
    }

    @Bean
    public JdbcLinkRepository jdbcLinkRepository(JdbcClient jdbcClient) {
        return new JdbcLinkRepository(jdbcClient);
    }

    @Bean
    public JdbcChatRepository jdbcChatRepository(JdbcClient jdbcClient) {
        return new JdbcChatRepository(jdbcClient);
    }

    @Bean
    public JdbcChatLinkRepository jdbcChatLinkRepository(JdbcClient jdbcClient) {
        return new JdbcChatLinkRepository(jdbcClient);
    }

    @Bean
    public JdbcScrapperService scrapperService(
        JdbcLinkRepository linkRepository,
        JdbcChatRepository chatRepository,
        JdbcChatLinkRepository chatLinkRepository
    ) {
        return new JdbcScrapperService(linkRepository, chatRepository, chatLinkRepository);
    }
}
