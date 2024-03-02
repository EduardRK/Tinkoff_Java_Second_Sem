package edu.java.bot.service.bot_service.handlers;

import edu.java.exceptions.BadRequestException.BadRequestException;

public interface Handler<T> {
    void put(T value) throws BadRequestException;
}
