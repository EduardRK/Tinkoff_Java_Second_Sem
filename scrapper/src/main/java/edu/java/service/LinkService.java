package edu.java.service;

import edu.java.domain.dto.Link;
import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.exceptions.NotFoundException.NotFoundException;
import edu.java.responses.LinkResponse;
import edu.java.responses.ListLinksResponse;
import java.time.Duration;
import java.util.List;

public interface LinkService {
    LinkResponse add(long tgChatId, String uri) throws BadRequestException;

    LinkResponse remove(long tgChatId, String uri) throws BadRequestException, NotFoundException;

    ListLinksResponse listAll(long tgChatId) throws BadRequestException;

    List<Link> findAllWithFilter(Duration updateCheckTime);

    void updateLastUpdateTime(Link link);

    void updateAllLastUpdateTime();
}
