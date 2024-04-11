package edu.java.bot.service.handlers;

import edu.java.exceptions.BadRequestException.BadRequestException;

public interface Handler<T> {
    void put(T value);
}
