package edu.java.exceptions;

public class IncorrectDataException extends RuntimeException {
    private final Integer id;

    public IncorrectDataException(Integer id) {
        this.id = id;
    }

    public Integer id() {
        return id;
    }
}
