package edu.java.exceptions.BadRequestException;

import java.util.List;

public class ChatsNotRegisteredException extends BadRequestException {
    public ChatsNotRegisteredException(List<Integer> ids, String uri, String message) {
        super(ids, uri, message);
    }

    public ChatsNotRegisteredException(List<Integer> ids, String uri) {
        this(ids, uri, DEFAULT_MESSAGE);
    }
}
