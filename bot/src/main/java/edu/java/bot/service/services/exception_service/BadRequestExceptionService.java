package edu.java.bot.service.services.exception_service;

import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.responses.ApiErrorResponse;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public final class BadRequestExceptionService implements ExceptionService {
    public BadRequestExceptionService() {
    }

    @Override
    public ApiErrorResponse chatsNotRegistered(BadRequestException exception) {
        return new ApiErrorResponse(
            chatsNotRegisteredDescription(exception),
            String.valueOf(HttpStatus.BAD_REQUEST.value()),
            "ChatsNotRegisteredException",
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList()
        );
    }

    @Override
    public ApiErrorResponse chatsNotTrackedUri(BadRequestException exception) {
        return new ApiErrorResponse(
            chatsNotTrackedUriDescription(exception),
            String.valueOf(HttpStatus.BAD_REQUEST.value()),
            "ChatsNotTrackedUriException",
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList()
        );
    }

    private String chatsNotRegisteredDescription(BadRequestException exception) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("These chats were not found:").append(' ');

        for (Integer id : exception.ids()) {
            stringBuilder.append(id).append(' ');
        }

        return stringBuilder.toString();
    }

    private String chatsNotTrackedUriDescription(BadRequestException exception) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("These chats do not track the link:").append('\n');

        for (Integer id : exception.ids()) {
            stringBuilder.append(id).append(": ").append(exception.uri());
        }

        return stringBuilder.toString();
    }
}
