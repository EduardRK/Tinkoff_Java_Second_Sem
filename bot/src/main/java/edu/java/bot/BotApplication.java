package edu.java.bot;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.configuration.DlqKafkaConfig;
import edu.java.bot.configuration.RetryConfig;
import edu.java.bot.configuration.UpdateKafkaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(
    value = {
        ApplicationConfig.class,
        RetryConfig.class,
        UpdateKafkaConfig.class,
        DlqKafkaConfig.class
    }
)
public class BotApplication {
    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }
}
