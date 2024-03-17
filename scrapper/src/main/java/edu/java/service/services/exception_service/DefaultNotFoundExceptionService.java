package edu.java.service.services.exception_service;

import edu.java.exceptions.NotFoundException.NotFoundException;
import edu.java.responses.ApiErrorResponse;
import edu.java.service.services.NotFoundExceptionService;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public final class DefaultNotFoundExceptionService implements NotFoundExceptionService {
    private static final String CHAT_WITH_ID = "Chat with id ";

    public DefaultNotFoundExceptionService() {

    }

    @Override
    public ApiErrorResponse chatNotTrackedUri(NotFoundException exception) {
        return new ApiErrorResponse(
            CHAT_WITH_ID + exception.id() + " doesn't track link " + exception.uri(),
            String.valueOf(HttpStatus.NOT_FOUND),
            "ChatNotTrackedUri",
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList()
        );
    }

    @Override
    public ApiErrorResponse chatNotRegistered(NotFoundException exception) {
        return new ApiErrorResponse(
            CHAT_WITH_ID + exception.id() + " not registered",
            String.valueOf(HttpStatus.NOT_FOUND),
            "ChatNotRegistered",
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList()
        );
    }
}
