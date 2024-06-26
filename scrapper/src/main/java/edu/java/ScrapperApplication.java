package edu.java;

import edu.java.configuration.ApplicationConfig;
import edu.java.configuration.CacheConfig;
import edu.java.configuration.KafkaConfig;
import edu.java.configuration.RateLimitingConfig;
import edu.java.configuration.RetryConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(
    value = {
        ApplicationConfig.class,
        RetryConfig.class,
        RateLimitingConfig.class,
        CacheConfig.class,
        KafkaConfig.class
    }
)
@EnableCaching
@EnableScheduling
public class ScrapperApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScrapperApplication.class, args);
    }
}
