package edu.java.exceptions;

import java.util.List;

public class ChatsNotRegisteredException extends RuntimeException {
    private final List<Integer> ids;

    public ChatsNotRegisteredException(List<Integer> ids) {
        this.ids = ids;
    }

    public List<Integer> ids() {
        return ids;
    }
}
