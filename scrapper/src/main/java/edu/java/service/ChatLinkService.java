package edu.java.service;

import edu.java.domain.dto.Chat;
import edu.java.domain.dto.Link;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;

public interface ChatLinkService {
    List<Chat> getAllChats(long linkId);

    @Cacheable(cacheNames = "chats", key = "#chatId")
    List<Link> getAllLinks(long chatId);
}
