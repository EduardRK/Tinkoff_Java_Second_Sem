package edu.java.controller;

import edu.java.exceptions.BadRequestException.ChatAlreadyRegisteredException;
import edu.java.exceptions.BadRequestException.IncorrectDataException;
import edu.java.exceptions.BadRequestException.UriAlreadyTrackedException;
import edu.java.exceptions.NotFoundException.ChatNotRegisteredException;
import edu.java.exceptions.NotFoundException.ChatNotTrackedUriException;
import edu.java.exceptions.TooManyRequestsException;
import edu.java.responses.ApiErrorResponse;
import edu.java.service.exception.ExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public final class RestExceptionController extends ResponseEntityExceptionHandler {
    private final ExceptionService exceptionService;

    @Autowired
    public RestExceptionController(
        ExceptionService exceptionService
    ) {
        this.exceptionService = exceptionService;
    }

    @ExceptionHandler(value = IncorrectDataException.class)
    public ResponseEntity<ApiErrorResponse> incorrectData(IncorrectDataException exception) {
        ApiErrorResponse response = exceptionService.incorrectData(exception);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ChatAlreadyRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> chatAlreadyRegistered(ChatAlreadyRegisteredException exception) {
        ApiErrorResponse response = exceptionService.chatAlreadyRegistered(exception);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UriAlreadyTrackedException.class)
    public ResponseEntity<ApiErrorResponse> uriAlreadyTracked(UriAlreadyTrackedException exception) {
        ApiErrorResponse response = exceptionService.uriAlreadyTracked(exception);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ChatNotRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> chatNotRegistered(ChatNotRegisteredException exception) {
        ApiErrorResponse response = exceptionService.chatNotRegistered(exception);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ChatNotTrackedUriException.class)
    public ResponseEntity<ApiErrorResponse> chatNotTrackedUri(ChatNotTrackedUriException exception) {
        ApiErrorResponse response = exceptionService.chatNotTrackedUri(exception);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = TooManyRequestsException.class)
    public ResponseEntity<ApiErrorResponse> tooManyRequests(TooManyRequestsException exception) {
        ApiErrorResponse response = exceptionService.tooManyRequests(exception);

        return new ResponseEntity<>(response, HttpStatus.TOO_MANY_REQUESTS);
    }
}
