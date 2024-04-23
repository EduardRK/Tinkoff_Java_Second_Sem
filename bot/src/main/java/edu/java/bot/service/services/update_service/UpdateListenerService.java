package edu.java.bot.service.services.update_service;

import edu.java.bot.configuration.DlqKafkaConfig;
import edu.java.bot.service.handlers.Handler;
import edu.java.requests.LinkUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
public class UpdateListenerService implements UpdateService {
    private final Handler<LinkUpdateRequest> handler;
    private final KafkaTemplate<String, LinkUpdateRequest> dlqKafkaTemplate;
    private final DlqKafkaConfig dlqKafkaConfig;

    @Autowired
    public UpdateListenerService(
        Handler<LinkUpdateRequest> handler,
        KafkaTemplate<String, LinkUpdateRequest> dlqKafkaTemplate,
        DlqKafkaConfig dlqKafkaConfig
    ) {
        this.handler = handler;
        this.dlqKafkaTemplate = dlqKafkaTemplate;
        this.dlqKafkaConfig = dlqKafkaConfig;
    }

    @Override
    @KafkaListener(
        topics = "${kafka.update-queue.topic-name}",
        groupId = "${kafka.update-queue.group-id}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleUpdate(LinkUpdateRequest linkUpdateRequest) {
        log.info("Get message from kafka {}:", linkUpdateRequest);
        try {
            handler.put(linkUpdateRequest);
        } catch (Exception e) {
            log.info("Message send to dlq {}:", linkUpdateRequest);
            dlqKafkaTemplate.send(dlqKafkaConfig.topicName(), linkUpdateRequest);
        }
    }
}
