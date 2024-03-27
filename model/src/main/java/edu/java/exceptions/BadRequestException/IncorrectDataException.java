package edu.java.exceptions.BadRequestException;

import java.util.List;

public class IncorrectDataException extends BadRequestException {
    public IncorrectDataException(List<Integer> ids, String uri, String message) {
        super(ids, uri, message);
    }

    public IncorrectDataException(List<Integer> ids, String uri) {
        this(ids, uri, DEFAULT_MESSAGE);
    }

    public IncorrectDataException(Integer id, String uri) {
        this(List.of(id), uri);
    }

    public IncorrectDataException(Integer id) {
        this(id, "");
    }

    public Integer id() {
        return this.ids().getFirst();
    }
}
