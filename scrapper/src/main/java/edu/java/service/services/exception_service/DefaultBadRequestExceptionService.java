package edu.java.service.services.exception_service;

import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.responses.ApiErrorResponse;
import edu.java.service.services.BadRequestExceptionService;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public final class DefaultBadRequestExceptionService implements BadRequestExceptionService {
    private static final String CHAT_WITH_ID = "Chat with id ";

    public DefaultBadRequestExceptionService() {

    }

    @Override
    public ApiErrorResponse incorrectData(BadRequestException exception) {
        return new ApiErrorResponse(
            "Incorrect id: " + exception.ids().getFirst(),
            String.valueOf(HttpStatus.BAD_REQUEST),
            "IncorrectData",
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList()
        );
    }

    @Override
    public ApiErrorResponse chatAlreadyRegistered(BadRequestException exception) {
        return new ApiErrorResponse(
            CHAT_WITH_ID + exception.ids().getFirst() + " already registered",
            String.valueOf(HttpStatus.BAD_REQUEST),
            "ChatAlreadyRegistered",
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList()
        );
    }
}
