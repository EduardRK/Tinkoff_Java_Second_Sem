package edu.java.service.exception;

import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.exceptions.NotFoundException.NotFoundException;
import edu.java.responses.ApiErrorResponse;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DefaultExceptionService implements ExceptionService {

    private static final String CHAT_WITH_ID = "Chat with id ";

    public DefaultExceptionService() {

    }

    @Override
    public ApiErrorResponse incorrectData(BadRequestException exception) {
        return new ApiErrorResponse(
            "Incorrect user id",
            String.valueOf(HttpStatus.BAD_REQUEST),
            "IncorrectData",
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList()
        );
    }

    @Override
    public ApiErrorResponse chatAlreadyRegistered(BadRequestException exception) {
        return new ApiErrorResponse(
            "User already registered",
            String.valueOf(HttpStatus.BAD_REQUEST),
            "ChatAlreadyRegistered",
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList()
        );
    }

    @Override
    public ApiErrorResponse uriAlreadyTracked(BadRequestException exception) {
        return new ApiErrorResponse(
            "Link already tracked",
            String.valueOf(HttpStatus.BAD_REQUEST),
            "UriAlreadyTracked",
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList()
        );
    }

    @Override
    public ApiErrorResponse chatNotTrackedUri(NotFoundException exception) {
        return new ApiErrorResponse(
            "User doesn't track link",
            String.valueOf(HttpStatus.NOT_FOUND),
            "ChatNotTrackedUri",
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList()
        );
    }

    @Override
    public ApiErrorResponse chatNotRegistered(NotFoundException exception) {
        return new ApiErrorResponse(
            "User not registered",
            String.valueOf(HttpStatus.NOT_FOUND),
            "ChatNotRegistered",
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList()
        );
    }
}
