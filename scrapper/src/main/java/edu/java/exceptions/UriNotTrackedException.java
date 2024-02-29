package edu.java.exceptions;

public class UriNotTrackedException extends RuntimeException {
    private final Integer id;
    private final String uri;

    public UriNotTrackedException(Integer id, String uri) {
        this.id = id;
        this.uri = uri;
    }

    public Integer id() {
        return id;
    }

    public String uri() {
        return uri;
    }
}
