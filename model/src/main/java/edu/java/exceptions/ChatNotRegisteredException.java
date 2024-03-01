package edu.java.exceptions;

public class ChatNotRegisteredException extends RuntimeException {
    private final Integer id;

    public ChatNotRegisteredException(Integer id) {
        this.id = id;
    }

    public Integer id() {
        return id;
    }
}
