package edu.java.bot.configuration;

import edu.java.bot.service.RetryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.util.retry.Retry;

@Configuration
public class RetryTypeConfig {
    private final RetryFactory retryFactory;

    @Autowired
    public RetryTypeConfig(RetryConfig retryConfig) {
        this.retryFactory = new RetryFactory(retryConfig);
    }

    @Bean
    @ConditionalOnProperty(prefix = "retry", name = "type", havingValue = "constant")
    public Retry constantRetry() {
        return retryFactory.constantRetry();
    }

    @Bean
    @ConditionalOnProperty(prefix = "retry", name = "type", havingValue = "linear")
    public Retry linearRetry() {
        return retryFactory.linearRetry();
    }

    @Bean
    @ConditionalOnProperty(prefix = "retry", name = "type", havingValue = "exponential")
    public Retry exponentialRetry() {
        return retryFactory.exponentialRetry();
    }
}
