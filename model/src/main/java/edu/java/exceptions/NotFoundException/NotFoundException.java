package edu.java.exceptions.NotFoundException;

public abstract class NotFoundException extends Exception {
    protected static final String DEFAULT_MESSAGE = "Not found";
    protected final long id;
    protected final String uri;

    protected NotFoundException(long id, String uri, String message) {
        super(message);
        this.id = id;
        this.uri = uri;
    }

    public long id() {
        return id;
    }

    public String uri() {
        return uri;
    }
}
