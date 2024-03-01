package edu.java.bot.api.controllers;

import edu.java.exceptions.ChatsNotRegisteredException;
import edu.java.exceptions.ChatsNotTrackedUriException;
import edu.java.responses.ApiErrorResponse;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public final class ExceptionController {
    public ExceptionController() {
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ChatsNotRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> chatsNotRegistered(ChatsNotRegisteredException exception) {
        ApiErrorResponse response = new ApiErrorResponse(
            chatsNotRegisteredDescription(exception),
            String.valueOf(HttpStatus.NOT_FOUND.value()),
            "ChatNotRegisteredException",
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ChatsNotTrackedUriException.class)
    public ResponseEntity<ApiErrorResponse> uriNotTracked(ChatsNotTrackedUriException exception) {
        ApiErrorResponse response = new ApiErrorResponse(
            uriNotTrackedDescription(exception),
            String.valueOf(HttpStatus.BAD_REQUEST.value()),
            "UriNotTrackedException",
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private String chatsNotRegisteredDescription(ChatsNotRegisteredException exception) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("These chats were not found:").append(' ');

        for (Integer id : exception.ids()) {
            stringBuilder.append(id).append(' ');
        }

        return stringBuilder.toString();
    }

    private String uriNotTrackedDescription(ChatsNotTrackedUriException exception) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("These chats do not track the link:").append('\n');

        for (Integer id : exception.ids()) {
            stringBuilder.append(id).append(": ").append(exception.uri());
        }

        return stringBuilder.toString();
    }
}
