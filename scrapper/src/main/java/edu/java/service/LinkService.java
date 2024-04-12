package edu.java.service;

import edu.java.domain.dto.Link;
import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.exceptions.NotFoundException.NotFoundException;
import edu.java.responses.LinkResponse;
import edu.java.responses.ListLinksResponse;
import java.time.Duration;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface LinkService {
    @CacheEvict(cacheNames = "chats", key = "#tgChatId")
    LinkResponse add(long tgChatId, String uri) throws BadRequestException;

    @CacheEvict(cacheNames = "chats", key = "#tgChatId")
    LinkResponse remove(long tgChatId, String uri) throws BadRequestException, NotFoundException;

    @Cacheable(cacheNames = "chats", key = "#tgChatId")
    ListLinksResponse listAll(long tgChatId) throws BadRequestException;

    List<Link> findAllWithFilter(Duration updateCheckTime);

    void updateLastUpdateTime(Link link);

    void updateAllLastUpdateTime();
}
