package edu.java.exceptions;

public class TooManyRequestsException extends Exception {
    public TooManyRequestsException(String message) {
        super(message);
    }
}
