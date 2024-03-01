package edu.java.bot.api.controllers;

import edu.java.bot.api.responses.ApiErrorResponse;
import edu.java.bot.service.bot_service.exeptions.ChatNotRegisteredException;
import edu.java.bot.service.bot_service.exeptions.UriNotTrackedException;
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
    @ExceptionHandler(ChatNotRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> chatNotRegistered(ChatNotRegisteredException exception) {
        ApiErrorResponse response = new ApiErrorResponse(
            chatNotRegisteredDescription(exception),
            String.valueOf(HttpStatus.NOT_FOUND.value()),
            "ChatNotRegisteredException",
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UriNotTrackedException.class)
    public ResponseEntity<ApiErrorResponse> uriNotTracked(UriNotTrackedException exception) {
        ApiErrorResponse response = new ApiErrorResponse(
            uriNotTrackedDescription(exception),
            String.valueOf(HttpStatus.BAD_REQUEST.value()),
            "UriNotTrackedException",
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private String chatNotRegisteredDescription(ChatNotRegisteredException exception) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("These chats were not found:").append(' ');

        for (Integer id : exception.chatIds()) {
            stringBuilder.append(id).append(' ');
        }

        return stringBuilder.toString();
    }

    private String uriNotTrackedDescription(UriNotTrackedException exception) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("These chats do not track the link:").append('\n');

        for (Integer id : exception.ids()) {
            stringBuilder.append(id).append(": ").append(exception.url());
        }

        return stringBuilder.toString();
    }
}
