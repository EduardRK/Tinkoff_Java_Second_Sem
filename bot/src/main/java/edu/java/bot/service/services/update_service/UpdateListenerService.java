package edu.java.bot.service.services.update_service;

import edu.java.bot.service.handlers.Handler;
import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.requests.LinkUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
public class UpdateListenerService implements UpdateService {
    private final Handler<LinkUpdateRequest> handler;

    @Autowired
    public UpdateListenerService(Handler<LinkUpdateRequest> handler) {
        this.handler = handler;
    }

    @Override
    @KafkaListener(topics = "${kafka.topic-name}")
    public void handleUpdate(LinkUpdateRequest linkUpdateRequest) throws BadRequestException {
        log.info("Get message from kafka {}:", linkUpdateRequest);
        handler.put(linkUpdateRequest);
    }
}
