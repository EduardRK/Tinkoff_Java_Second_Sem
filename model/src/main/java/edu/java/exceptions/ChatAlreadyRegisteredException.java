package edu.java.exceptions;

public class ChatAlreadyRegisteredException extends RuntimeException {
    private final Integer id;

    public ChatAlreadyRegisteredException(Integer id) {
        this.id = id;
    }

    public Integer id() {
        return id;
    }
}
