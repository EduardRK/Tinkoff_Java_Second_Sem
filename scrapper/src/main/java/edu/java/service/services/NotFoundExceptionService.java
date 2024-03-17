package edu.java.service.services;

import edu.java.exceptions.NotFoundException.NotFoundException;
import edu.java.responses.ApiErrorResponse;

public interface NotFoundExceptionService {
    ApiErrorResponse chatNotTrackedUri(NotFoundException exception);

    ApiErrorResponse chatNotRegistered(NotFoundException exception);
}
