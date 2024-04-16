package edu.java.service.exception;

import edu.java.exceptions.TooManyRequestsException;
import edu.java.responses.ApiErrorResponse;

public interface TooManyRequestsService {
    ApiErrorResponse tooManyRequests(TooManyRequestsException exception);
}
