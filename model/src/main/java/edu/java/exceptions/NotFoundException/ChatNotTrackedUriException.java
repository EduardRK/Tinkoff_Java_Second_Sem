package edu.java.exceptions.NotFoundException;

public class ChatNotTrackedUriException extends NotFoundException {
    public ChatNotTrackedUriException(Integer id, String uri, String message) {
        super(id, uri, message);
    }

    public ChatNotTrackedUriException(Integer id, String uri) {
        this(id, uri, DEFAULT_MESSAGE);
    }
}
