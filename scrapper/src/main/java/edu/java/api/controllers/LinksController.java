package edu.java.api.controllers;

import edu.java.database.DataBase;
import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.exceptions.NotFoundException.NotFoundException;
import edu.java.requests.AddLinkRequest;
import edu.java.requests.RemoveLinkRequest;
import edu.java.responses.LinkResponse;
import edu.java.responses.ListLinksResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/links")
public final class LinksController {
    private final DataBase<Integer, String> dataBase;

    @Autowired
    public LinksController(DataBase<Integer, String> dataBase) {
        this.dataBase = dataBase;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<ListLinksResponse> allTrackedLinks(
        @RequestHeader("id") int id
    ) throws BadRequestException {
        List<String> links = dataBase.allDataByKey(id);

        List<LinkResponse> linkResponses = links.stream()
            .map(link -> new LinkResponse(id, link))
            .toList();

        ListLinksResponse listLinksResponse = new ListLinksResponse(
            linkResponses,
            linkResponses.size()
        );

        return new ResponseEntity<>(listLinksResponse, HttpStatus.OK);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<LinkResponse> addNewTrackLink(
        @RequestHeader("id") int id,
        @RequestBody AddLinkRequest addLinkRequest
    ) throws BadRequestException {
        dataBase.addValue(id, addLinkRequest.link());

        LinkResponse linkResponse = new LinkResponse(
            id,
            addLinkRequest.link()
        );

        return new ResponseEntity<>(linkResponse, HttpStatus.OK);
    }

    @DeleteMapping(produces = "application/json")
    public ResponseEntity<LinkResponse> untrackLink(
        @RequestHeader("id") int id,
        @RequestBody RemoveLinkRequest removeLinkRequest
    ) throws BadRequestException, NotFoundException {
        dataBase.deleteValue(id, removeLinkRequest.link());

        LinkResponse linkResponse = new LinkResponse(
            id,
            removeLinkRequest.link()
        );

        return new ResponseEntity<>(linkResponse, HttpStatus.OK);
    }
}
