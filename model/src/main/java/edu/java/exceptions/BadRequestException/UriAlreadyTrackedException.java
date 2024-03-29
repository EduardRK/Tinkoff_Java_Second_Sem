package edu.java.exceptions.BadRequestException;

import java.util.List;

public class UriAlreadyTrackedException extends BadRequestException {
    public UriAlreadyTrackedException(List<Long> ids, String uri, String message) {
        super(ids, uri, message);
    }

    public UriAlreadyTrackedException(List<Long> ids, String uri) {
        this(ids, uri, DEFAULT_MESSAGE);
    }
}
