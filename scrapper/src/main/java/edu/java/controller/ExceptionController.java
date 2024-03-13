package edu.java.controller;

import edu.java.exceptions.BadRequestException.ChatAlreadyRegisteredException;
import edu.java.exceptions.BadRequestException.IncorrectDataException;
import edu.java.exceptions.NotFoundException.ChatNotRegisteredException;
import edu.java.exceptions.NotFoundException.ChatNotTrackedUriException;
import edu.java.responses.ApiErrorResponse;
import edu.java.service.services.exception_service.BadRequestExceptionService;
import edu.java.service.services.exception_service.NotFoundExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public final class ExceptionController extends ResponseEntityExceptionHandler {
    private final NotFoundExceptionService notFoundExceptionService;
    private final BadRequestExceptionService badRequestExceptionService;

    @Autowired
    public ExceptionController(
        NotFoundExceptionService notFoundExceptionService,
        BadRequestExceptionService badRequestExceptionService
    ) {
        this.notFoundExceptionService = notFoundExceptionService;
        this.badRequestExceptionService = badRequestExceptionService;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IncorrectDataException.class)
    public ResponseEntity<ApiErrorResponse> incorrectData(IncorrectDataException exception) {
        ApiErrorResponse response = badRequestExceptionService.incorrectData(exception);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ChatNotRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> chatNotRegistered(ChatNotRegisteredException exception) {
        ApiErrorResponse response = notFoundExceptionService.chatNotRegistered(exception);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ChatAlreadyRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> chatAlreadyRegistered(ChatAlreadyRegisteredException exception) {
        ApiErrorResponse response = badRequestExceptionService.chatAlreadyRegistered(exception);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ChatNotTrackedUriException.class)
    public ResponseEntity<ApiErrorResponse> chatNotTrackedUri(ChatNotTrackedUriException exception) {
        ApiErrorResponse response = notFoundExceptionService.chatNotTrackedUri(exception);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
