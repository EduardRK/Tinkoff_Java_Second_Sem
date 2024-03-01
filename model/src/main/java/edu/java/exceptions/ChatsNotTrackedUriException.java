package edu.java.exceptions;

import java.util.List;

public class ChatsNotTrackedUriException extends RuntimeException {
    private final List<Integer> ids;
    private final String uri;

    public ChatsNotTrackedUriException(List<Integer> ids, String uri) {
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
