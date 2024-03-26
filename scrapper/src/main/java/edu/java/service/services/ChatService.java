package edu.java.service.services;

import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.exceptions.NotFoundException.NotFoundException;

public interface ChatService {
    void registerChat(long tgChatId) throws BadRequestException;

    void deleteChat(long tgChatId) throws BadRequestException, NotFoundException;
}
