package edu.java.api.controllers;

import edu.java.database.DataBase;
import edu.java.database.InMemoryDataBase;
import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.exceptions.NotFoundException.NotFoundException;
import edu.java.requests.AddLinkRequest;
import edu.java.requests.RemoveLinkRequest;
import edu.java.responses.ApiErrorResponse;
import edu.java.responses.LinkResponse;
import edu.java.responses.ListLinksResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.Set;
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
    public LinksController(InMemoryDataBase inMemoryDataBase) {
        this.dataBase = inMemoryDataBase;
    }

    @Operation(summary = "Получить все отслеживаемые ссылки")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Ссылки успешно получены",
                     content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = ListLinksResponse.class))
        ),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = ApiErrorResponse.class)
                     )
        )
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<ListLinksResponse> allTrackedLinks(
        @RequestHeader("id") int id
    ) throws BadRequestException {
        Set<String> links = dataBase.allDataByKey(id);

        List<LinkResponse> linkResponses = links.stream()
            .map(link -> new LinkResponse(id, link))
            .toList();

        ListLinksResponse listLinksResponse = new ListLinksResponse(
            linkResponses,
            linkResponses.size()
        );

        return new ResponseEntity<>(listLinksResponse, HttpStatus.OK);
    }

    @Operation(summary = "Добавить отслеживание ссылки")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Ссылка успешно добавлена",
                     content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = LinkResponse.class))
        ),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = ApiErrorResponse.class)
                     )
        )
    })
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

    @Operation(summary = "Убрать отслеживание ссылки")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Ссылка успешно убрана",
                     content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = LinkResponse.class)
                     )
        ),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = ApiErrorResponse.class)
                     )
        ),
        @ApiResponse(responseCode = "404",
                     description = "Ссылка не найдена",
                     content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = ApiErrorResponse.class)
                     )
        )
    })
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
