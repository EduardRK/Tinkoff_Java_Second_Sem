package edu.java.exceptions.NotFoundException;

public class ChatNotTrackedUriException extends NotFoundException {
    public ChatNotTrackedUriException(long id, String uri, String message) {
        super(id, uri, message);
    }

    public ChatNotTrackedUriException(long id, String uri) {
        this(id, uri, DEFAULT_MESSAGE);
    }
}
