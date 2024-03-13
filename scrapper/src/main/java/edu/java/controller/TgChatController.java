package edu.java.controller;

import edu.java.domain.DataBase;
import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.exceptions.NotFoundException.NotFoundException;
import edu.java.responses.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tg-chat/{id}")
public final class TgChatController {
    private final DataBase<Integer, String> dataBase;

    @Autowired
    public TgChatController(DataBase<Integer, String> dataBase) {
        this.dataBase = dataBase;
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
    @PostMapping(produces = "application/json")
    public ResponseEntity<?> registerChat(@PathVariable int id) throws BadRequestException {
        dataBase.addNewUserByKey(id);
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
    @DeleteMapping(produces = "application/json")
    public ResponseEntity<?> deleteChat(@PathVariable int id) throws BadRequestException, NotFoundException {
        dataBase.deleteUserByKey(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

