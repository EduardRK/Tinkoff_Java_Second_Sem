package edu.java.exceptions.NotFoundException;

public class ChatNotRegisteredException extends NotFoundException {
    public ChatNotRegisteredException(Integer id, String uri, String message) {
        super(id, uri, message);
    }

    public ChatNotRegisteredException(Integer id, String uri) {
        this(id, uri, DEFAULT_MESSAGE);
    }

    public ChatNotRegisteredException(Integer id) {
        this(id, "", DEFAULT_MESSAGE);
    }
}
