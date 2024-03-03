package edu.java.bot.api.controllers;

import edu.java.bot.service.bot_service.handlers.Handler;
import edu.java.bot.service.bot_service.handlers.LinkUpdateHandler;
import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.requests.LinkUpdateRequest;
import edu.java.responses.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/update")
public final class UpdateController {
    private final Handler<LinkUpdateRequest> handler;

    @Autowired
    public UpdateController(LinkUpdateHandler handler) {
        this.handler = handler;
    }

    @Operation(summary = "Отправить обновление")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Обновление обработано",
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
    @PostMapping(produces = "application/json")
    ResponseEntity<?> newUpdateFromClient(@RequestBody LinkUpdateRequest linkUpdateRequest) throws BadRequestException {
        handler.put(linkUpdateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
