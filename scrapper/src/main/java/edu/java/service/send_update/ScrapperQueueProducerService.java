package edu.java.service.send_update;

import edu.java.configuration.KafkaConfig;
import edu.java.requests.LinkUpdateRequest;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
public class ScrapperQueueProducerService implements SendUpdateService {
    private final KafkaConfig kafkaConfig;
    private final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;

    public ScrapperQueueProducerService(
        KafkaConfig kafkaConfig,
        KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate
    ) {
        this.kafkaConfig = kafkaConfig;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(LinkUpdateRequest linkUpdateRequest) {
        log.info("Sending message to Kafka: {}", linkUpdateRequest);
        kafkaTemplate.send(kafkaConfig.topicName(), linkUpdateRequest);
    }

    @Override
    public void send(List<LinkUpdateRequest> linkUpdateRequestList) {
        linkUpdateRequestList
            .parallelStream()
            .forEach(this::send);
    }
}
