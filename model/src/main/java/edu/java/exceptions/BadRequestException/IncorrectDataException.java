package edu.java.exceptions.BadRequestException;

import java.util.List;

public class IncorrectDataException extends BadRequestException {
    public IncorrectDataException(List<Long> ids, String uri, String message) {
        super(ids, uri, message);
    }

    public IncorrectDataException(List<Long> ids, String uri) {
        this(ids, uri, DEFAULT_MESSAGE);
    }

    public IncorrectDataException(long id, String uri) {
        this(List.of(id), uri);
    }

    public IncorrectDataException(long id) {
        this(id, "");
    }

    public long id() {
        return this.ids().getFirst();
    }
}
