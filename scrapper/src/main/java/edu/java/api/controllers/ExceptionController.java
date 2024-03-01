package edu.java.api.controllers;

import edu.java.exceptions.ChatAlreadyRegisteredException;
import edu.java.exceptions.ChatNotRegisteredException;
import edu.java.exceptions.IncorrectDataException;
import edu.java.exceptions.UriNotTrackedException;
import edu.java.responses.ApiErrorResponse;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public final class ExceptionController {
    private static final String CHAT_WITH_ID = "Chat with id ";

    public ExceptionController() {
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IncorrectDataException.class)
    public ResponseEntity<ApiErrorResponse> incorrectDataException(IncorrectDataException exception) {
        ApiErrorResponse response = new ApiErrorResponse(
            "Incorrect id: " + exception.id(),
            String.valueOf(HttpStatus.BAD_REQUEST),
            "IncorrectData",
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ChatNotRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> chatNotRegistered(ChatNotRegisteredException exception) {
        ApiErrorResponse response = new ApiErrorResponse(
            CHAT_WITH_ID + exception.id() + " not registered",
            String.valueOf(HttpStatus.NOT_FOUND),
            "ChatNotRegistered",
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ChatAlreadyRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> chatAlreadyRegistered(ChatAlreadyRegisteredException exception) {
        ApiErrorResponse response = new ApiErrorResponse(
            CHAT_WITH_ID + exception.id() + " already registered",
            String.valueOf(HttpStatus.BAD_REQUEST),
            "ChatAlreadyRegistered",
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UriNotTrackedException.class)
    public ResponseEntity<ApiErrorResponse> uriNotTracked(UriNotTrackedException exception) {
        ApiErrorResponse response = new ApiErrorResponse(
            CHAT_WITH_ID + exception.id() + " doesn't track link " + exception.uri(),
            String.valueOf(HttpStatus.NOT_FOUND),
            "UriNotTracked",
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
