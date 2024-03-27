package edu.java.service.services.default_service;

import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.exceptions.NotFoundException.NotFoundException;
import edu.java.requests.AddLinkRequest;
import edu.java.requests.RemoveLinkRequest;
import edu.java.responses.LinkResponse;
import edu.java.responses.ListLinksResponse;

public interface ScrapperService {
    ListLinksResponse allTrackedLinks(int id) throws BadRequestException;

    LinkResponse addNewTrackLink(int id, AddLinkRequest addLinkRequest) throws BadRequestException;

    LinkResponse untrackLink(int id, RemoveLinkRequest removeLinkRequest) throws BadRequestException, NotFoundException;

    void registerChat(int id) throws BadRequestException;

    void deleteChat(int id) throws BadRequestException, NotFoundException;
}
