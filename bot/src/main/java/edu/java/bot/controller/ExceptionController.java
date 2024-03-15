package edu.java.bot.controller;

import edu.java.bot.service.services.exception_service.BadRequestExceptionService;
import edu.java.bot.service.services.exception_service.ExceptionService;
import edu.java.exceptions.BadRequestException.ChatsNotRegisteredException;
import edu.java.exceptions.BadRequestException.ChatsNotTrackedUriException;
import edu.java.responses.ApiErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public final class ExceptionController extends ResponseEntityExceptionHandler {
    private final ExceptionService exceptionService;

    @Autowired
    public ExceptionController(BadRequestExceptionService badRequestExceptionExceptionService) {
        this.exceptionService = badRequestExceptionExceptionService;
    }

    @ExceptionHandler(value = ChatsNotRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> chatsNotRegistered(ChatsNotRegisteredException exception) {
        ApiErrorResponse response = exceptionService.chatsNotRegistered(exception);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ChatsNotTrackedUriException.class)
    public ResponseEntity<ApiErrorResponse> chatsNotTrackedUri(ChatsNotTrackedUriException exception) {
        ApiErrorResponse response = exceptionService.chatsNotTrackedUri(exception);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
