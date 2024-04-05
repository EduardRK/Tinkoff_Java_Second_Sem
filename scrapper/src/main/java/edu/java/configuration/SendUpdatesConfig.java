package edu.java.configuration;

import edu.java.requests.LinkUpdateRequest;
import edu.java.service.send_update.ScrapperQueueProducerService;
import edu.java.service.send_update.SendUpdateService;
import edu.java.service.send_update.TelegramBotClientService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import reactor.util.retry.Retry;

@Configuration
public class SendUpdatesConfig {
    public SendUpdatesConfig() {

    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
    public SendUpdateService telegramBotClientService(Retry retry) {
        return new TelegramBotClientService(retry);
    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
    public SendUpdateService scrapperQueueProducerService(
        KafkaConfig kafkaConfig,
        KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate
    ) {
        return new ScrapperQueueProducerService(kafkaConfig, kafkaTemplate);
    }
}
