package edu.java.configuration;

import edu.java.domain.jpa.JpaChatRepository;
import edu.java.domain.jpa.JpaLinkRepository;
import edu.java.service.ScrapperService;
import edu.java.service.jpa.JpaScrapperService;
import jakarta.persistence.EntityManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaServiceConfig {

    public JpaServiceConfig() {
    }

    @Bean
    public ScrapperService jpaScrapperService(
        JpaChatRepository chatRepository,
        JpaLinkRepository linkRepository,
        EntityManager entityManager
    ) {
        return new JpaScrapperService(linkRepository, chatRepository, entityManager);
    }
}
