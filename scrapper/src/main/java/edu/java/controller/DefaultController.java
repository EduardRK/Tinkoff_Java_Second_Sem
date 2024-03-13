package edu.java.controller;

import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.exceptions.NotFoundException.NotFoundException;
import edu.java.requests.AddLinkRequest;
import edu.java.requests.RemoveLinkRequest;
import edu.java.responses.ApiErrorResponse;
import edu.java.responses.LinkResponse;
import edu.java.responses.ListLinksResponse;
import edu.java.service.services.default_service.ScrapperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "default")
@RestController
public final class DefaultController {
    private final ScrapperService scrapperService;

    @Autowired
    public DefaultController(ScrapperService scrapperService) {
        this.scrapperService = scrapperService;
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
    @GetMapping(
        value = "/links",
        produces = "application/json"
    )
    public ResponseEntity<ListLinksResponse> allTrackedLinks(
        @RequestHeader("id") int id
    ) throws BadRequestException {
        ListLinksResponse listLinksResponse = scrapperService.allTrackedLinks(id);

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
    @PostMapping(
        value = "/links",
        produces = "application/json"
    )
    public ResponseEntity<LinkResponse> addNewTrackLink(
        @RequestHeader("id") int id,
        @RequestBody AddLinkRequest addLinkRequest
    ) throws BadRequestException {
        LinkResponse linkResponse = scrapperService.addNewTrackLink(id, addLinkRequest);

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
    @DeleteMapping(
        value = "/links",
        produces = "application/json"
    )
    public ResponseEntity<LinkResponse> untrackLink(
        @RequestHeader("id") int id,
        @RequestBody RemoveLinkRequest removeLinkRequest
    ) throws BadRequestException, NotFoundException {
        LinkResponse linkResponse = scrapperService.untrackLink(id, removeLinkRequest);

        return new ResponseEntity<>(linkResponse, HttpStatus.OK);
    }

    @Operation(summary = "Зарегистрировать чат")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Чат зарегистрирован",
                     content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = ApiErrorResponse.class)
                     )
        )
    })
    @PostMapping(
        value = "/tg-chat/{id}",
        produces = "application/json"
    )
    public ResponseEntity<?> registerChat(@PathVariable int id) throws BadRequestException {
        scrapperService.registerChat(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Удалить чат")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Чат успешно удалён",
                     content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = ApiErrorResponse.class)
                     )
        ),
        @ApiResponse(responseCode = "404",
                     description = "Чат не существует",
                     content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = ApiErrorResponse.class)
                     )
        )
    })
    @DeleteMapping(
        value = "/tg-chat/{id}",
        produces = "application/json"
    )
    public ResponseEntity<?> deleteChat(@PathVariable int id) throws BadRequestException, NotFoundException {
        scrapperService.deleteChat(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
