package edu.java.service;

import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.exceptions.NotFoundException.NotFoundException;
import org.springframework.cache.annotation.CacheEvict;

public interface ChatService {
    void registerChat(long tgChatId) throws BadRequestException;

    @CacheEvict(cacheNames = "chats", key = "#tgChatId")
    void deleteChat(long tgChatId) throws BadRequestException, NotFoundException;
}
