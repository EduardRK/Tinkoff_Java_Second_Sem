package edu.java.bot.service.services.update_service;

import edu.java.bot.service.handlers.Handler;
import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.requests.LinkUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;

public class UpdateHandlerService implements UpdateService {
    private final Handler<LinkUpdateRequest> handler;

    @Autowired
    public UpdateHandlerService(Handler<LinkUpdateRequest> handler) {
        this.handler = handler;
    }

    @Override
    public void handleUpdate(LinkUpdateRequest linkUpdateRequest) throws BadRequestException {
        handler.put(linkUpdateRequest);
    }
}
