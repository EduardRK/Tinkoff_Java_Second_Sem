package edu.java.service.services.default_service;

import edu.java.domain.DataBase;
import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.exceptions.NotFoundException.NotFoundException;
import edu.java.requests.AddLinkRequest;
import edu.java.requests.RemoveLinkRequest;
import edu.java.responses.LinkResponse;
import edu.java.responses.ListLinksResponse;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultScrapperService implements ScrapperService {
    private final DataBase<Integer, String> dataBase;

    @Autowired
    public DefaultScrapperService(DataBase<Integer, String> dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public ListLinksResponse allTrackedLinks(int id) throws BadRequestException {
        Set<String> links = dataBase.allDataByKey(id);

        List<LinkResponse> linkResponses = links.stream()
            .map(link -> new LinkResponse(id, link))
            .toList();

        return new ListLinksResponse(
            linkResponses,
            linkResponses.size()
        );
    }

    @Override
    public LinkResponse addNewTrackLink(int id, AddLinkRequest addLinkRequest) throws BadRequestException {
        dataBase.addValue(id, addLinkRequest.link());

        return new LinkResponse(
            id,
            addLinkRequest.link()
        );
    }

    @Override
    public LinkResponse untrackLink(int id, RemoveLinkRequest removeLinkRequest)
        throws BadRequestException, NotFoundException {
        dataBase.deleteValue(id, removeLinkRequest.link());

        return new LinkResponse(
            id,
            removeLinkRequest.link()
        );
    }

    @Override
    public void registerChat(int id) throws BadRequestException {
        dataBase.addNewUserByKey(id);
    }

    @Override
    public void deleteChat(int id) throws BadRequestException, NotFoundException {
        dataBase.deleteUserByKey(id);
    }
}
