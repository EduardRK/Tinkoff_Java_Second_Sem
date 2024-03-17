package edu.java.service.services;

import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.exceptions.NotFoundException.NotFoundException;
import edu.java.responses.LinkResponse;
import edu.java.responses.ListLinksResponse;

public interface LinkService {
    LinkResponse add(long tgChatId, String uri) throws BadRequestException;

    LinkResponse remove(long tgChatId, String uri) throws BadRequestException, NotFoundException;

    ListLinksResponse listAll(long tgChatId) throws BadRequestException;
}
