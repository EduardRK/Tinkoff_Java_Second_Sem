package edu.java.exceptions.BadRequestException;

import java.util.List;

public class BadRequestException extends Exception {
    protected static final String DEFAULT_MESSAGE = "Bad request";
    private final List<Long> ids;
    private final String uri;

    public BadRequestException(List<Long> ids, String uri, String message) {
        super(message);
        this.ids = ids;
        this.uri = uri;
    }

    public List<Long> ids() {
        return ids;
    }

    public String uri() {
        return uri;
    }
}
