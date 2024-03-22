package edu.java.bot.service.services.exception_service;

import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.responses.ApiErrorResponse;

public interface ExceptionService {
    ApiErrorResponse chatsNotRegistered(BadRequestException exception);

    ApiErrorResponse chatsNotTrackedUri(BadRequestException exception);
}
