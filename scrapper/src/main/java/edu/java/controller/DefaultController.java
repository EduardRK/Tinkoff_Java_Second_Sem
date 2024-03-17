package edu.java.controller;

import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.exceptions.NotFoundException.NotFoundException;
import edu.java.requests.AddLinkRequest;
import edu.java.requests.RemoveLinkRequest;
import edu.java.responses.ApiErrorResponse;
import edu.java.responses.LinkResponse;
import edu.java.responses.ListLinksResponse;
import edu.java.service.services.ScrapperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
    @Parameters(value = {
        @Parameter(
            name = "Tg-Chat-Id",
            in = ParameterIn.HEADER,
            required = true,
            schema = @Schema(
                type = "integer",
                format = "int64"
            )
        )
    })
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Ссылки успешно получены",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ListLinksResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
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
        @RequestHeader(
            name = "Tg-Chat-Id",
            value = "Tg-Chat-Id"
        ) long tgChatId
    ) throws BadRequestException {
        ListLinksResponse listLinksResponse = scrapperService.listAll(tgChatId);

        return new ResponseEntity<>(listLinksResponse, HttpStatus.OK);
    }

    @Operation(summary = "Добавить отслеживание ссылки")
    @Parameters(value = {
        @Parameter(
            name = "Tg-Chat-Id",
            in = ParameterIn.HEADER,
            required = true,
            schema = @Schema(
                type = "integer",
                format = "int64"
            )
        )
    })
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Ссылка успешно добавлена",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = LinkResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
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
        @RequestHeader(
            name = "Tg-Chat-Id",
            value = "Tg-Chat-Id"
        ) int tgChatId,
        @RequestBody AddLinkRequest addLinkRequest
    ) throws BadRequestException {
        LinkResponse linkResponse = scrapperService.add(tgChatId, addLinkRequest.link());

        return new ResponseEntity<>(linkResponse, HttpStatus.OK);
    }

    @Operation(summary = "Убрать отслеживание ссылки")
    @Parameters(value = {
        @Parameter(
            name = "Tg-Chat-Id",
            in = ParameterIn.HEADER,
            required = true,
            schema = @Schema(
                type = "integer",
                format = "int64"
            )
        )
    })
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Ссылка успешно убрана",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = LinkResponse.class
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Некорректные параметры запроса",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = ApiErrorResponse.class
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Ссылка не найдена",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = ApiErrorResponse.class
                )
            )
        )
    })
    @DeleteMapping(
        value = "/links",
        produces = "application/json"
    )
    public ResponseEntity<LinkResponse> untrackLink(
        @RequestHeader(
            name = "Tg-Chat-Id",
            value = "Tg-Chat-Id"
        ) int tgChatId,
        @RequestBody RemoveLinkRequest removeLinkRequest
    ) throws BadRequestException, NotFoundException {
        LinkResponse linkResponse = scrapperService.remove(tgChatId, removeLinkRequest.link());

        return new ResponseEntity<>(linkResponse, HttpStatus.OK);
    }

    @Operation(summary = "Зарегистрировать чат")
    @Parameters(value = {
        @Parameter(
            name = "id",
            in = ParameterIn.PATH,
            required = true,
            schema = @Schema(
                type = "integer",
                format = "int64"
            )
        )
    })
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Чат зарегистрирован",
            content = @Content(
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Некорректные параметры запроса",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = ApiErrorResponse.class
                )
            )
        )
    })
    @PostMapping(
        value = "/tg-chat/{id}"
    )
    public ResponseEntity<?> registerChat(@PathVariable long id) throws BadRequestException {
        scrapperService.registerChat(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Удалить чат")
    @Parameters(value = {
        @Parameter(
            name = "id",
            in = ParameterIn.PATH,
            required = true,
            schema = @Schema(
                type = "integer",
                format = "int64"
            )
        )
    })
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Чат успешно удалён",
            content = @Content(
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Некорректные параметры запроса",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = ApiErrorResponse.class
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Чат не существует",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = ApiErrorResponse.class
                )
            )
        )
    })
    @DeleteMapping(
        value = "/tg-chat/{id}"
    )
    public ResponseEntity<?> deleteChat(@PathVariable long id) throws BadRequestException, NotFoundException {
        scrapperService.deleteChat(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
