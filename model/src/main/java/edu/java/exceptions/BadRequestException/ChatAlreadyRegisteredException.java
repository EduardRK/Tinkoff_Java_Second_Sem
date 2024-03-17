package edu.java.exceptions.BadRequestException;

import java.util.List;

public class ChatAlreadyRegisteredException extends BadRequestException {
    public ChatAlreadyRegisteredException(List<Long> ids, String uri, String message) {
        super(ids, uri, message);
    }

    public ChatAlreadyRegisteredException(List<Long> ids, String uri) {
        this(ids, uri, DEFAULT_MESSAGE);
    }

    public ChatAlreadyRegisteredException(long id, String uri) {
        this(List.of(id), uri);
    }

    public ChatAlreadyRegisteredException(long id) {
        this(id, "");
    }

    public long id() {
        return this.ids().getFirst();
    }
}
