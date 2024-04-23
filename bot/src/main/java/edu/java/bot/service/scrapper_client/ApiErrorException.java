package edu.java.bot.service.scrapper_client;

import edu.java.responses.ApiErrorResponse;

public class ApiErrorException extends Exception {
    private final ApiErrorResponse apiErrorResponse;

    public ApiErrorException(ApiErrorResponse apiErrorResponse) {
        this.apiErrorResponse = apiErrorResponse;
    }

    public ApiErrorResponse apiErrorResponse() {
        return apiErrorResponse;
    }
}
