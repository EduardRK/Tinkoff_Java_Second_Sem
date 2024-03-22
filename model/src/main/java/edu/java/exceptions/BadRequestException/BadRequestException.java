package edu.java.exceptions.BadRequestException;

import java.util.List;

public abstract class BadRequestException extends Exception {
    protected static final String DEFAULT_MESSAGE = "Bad request";
    private final List<Integer> ids;
    private final String uri;

    protected BadRequestException(List<Integer> ids, String uri, String message) {
        super(message);
        this.ids = ids;
        this.uri = uri;
    }

    public List<Integer> ids() {
        return ids;
    }

    public String uri() {
        return uri;
    }
}
