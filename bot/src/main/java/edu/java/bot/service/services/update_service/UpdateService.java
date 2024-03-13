package edu.java.bot.service.services.update_service;

import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.requests.LinkUpdateRequest;

public interface UpdateService {
    void handleUpdate(LinkUpdateRequest linkUpdateRequest) throws BadRequestException;
}
