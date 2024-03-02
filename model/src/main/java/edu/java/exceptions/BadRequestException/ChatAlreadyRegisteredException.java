package edu.java.exceptions.BadRequestException;

import java.util.List;

public class ChatAlreadyRegisteredException extends BadRequestException {
    public ChatAlreadyRegisteredException(List<Integer> ids, String uri, String message) {
        super(ids, uri, message);
    }

    public ChatAlreadyRegisteredException(List<Integer> ids, String uri) {
        this(ids, uri, DEFAULT_MESSAGE);
    }

    public ChatAlreadyRegisteredException(Integer id, String uri) {
        this(List.of(id), uri);
    }

    public ChatAlreadyRegisteredException(Integer id) {
        this(id, "");
    }

    public Integer id() {
        return this.ids().getFirst();
    }
}
