package edu.java.exceptions.NotFoundException;

public class ChatNotRegisteredException extends NotFoundException {
    public ChatNotRegisteredException(long id, String uri, String message) {
        super(id, uri, message);
    }

    public ChatNotRegisteredException(long id, String uri) {
        this(id, uri, DEFAULT_MESSAGE);
    }

    public ChatNotRegisteredException(long id) {
        this(id, "", DEFAULT_MESSAGE);
    }
}
