package edu.java.service.services.exception_service;

import edu.java.exceptions.BadRequestException.BadRequestException;
import edu.java.responses.ApiErrorResponse;

public interface BadRequestExceptionService {
    ApiErrorResponse incorrectData(BadRequestException exception);

    ApiErrorResponse chatAlreadyRegistered(BadRequestException exception);

    ApiErrorResponse uriAlreadyTracked(BadRequestException exception);
}
